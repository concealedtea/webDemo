package com.falcon.dsp.service;

import org.apache.commons.lang3.StringUtils;
import com.falcon.dsp.common.Constant;
import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.common.GdtAction;
import com.falcon.dsp.common.MathUtil;
import com.falcon.dsp.enumration.UserRoleType;
import com.falcon.dsp.exception.FalconDspSqlException;
import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.interceptor.PageParameter;
import com.falcon.dsp.jdbc.entity.Advertiser;
import com.falcon.dsp.jdbc.entity.Campaign;
import com.falcon.dsp.jdbc.model.TableData;
import com.falcon.dsp.jdbc.model.UserModel;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.campaign.params.CampaignParam;
import com.falcon.dsp.rest.campaign.request.GdtCampaign;
import com.falcon.dsp.rest.campaign.response.GdtCampaignResult;
import com.falcon.dsp.rest.campaign.response.GdtCampaignTable;
import com.falcon.dsp.rest.campaign.response.GdtCampaignTableResult;
import com.falcon.dsp.rest.common.Conf;
import com.falcon.dsp.rest.finance.response.GdtFianaceBalanceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/31.
 */
@Service
public class CampaignService {

    @Autowired
    private GdtRestHandler gdtRestHandler;

    @Resource(name = "sqlHandler")
    private SqlSessionHandler sqlSessionHandler;

    @Autowired
    private AdvertiserService advertiserService;

    public TableData<Campaign> listTable(CampaignParam param) {

        PageParameter pageParameter = new PageParameter();
        pageParameter.setPageSize(param.getPageSize());
        pageParameter.setCurrentPage(param.getPage());

        Map<String, Object> whereCondition = null;

        if (param.getWhere() != null) {
            whereCondition = new HashMap(param.getWhere());
        } else {
            whereCondition = new HashMap();
        }

        if (param.getUid() != null) {
            whereCondition.put("uid", param.getUid());
        }

        if (!whereCondition.containsKey("cid")) {
            whereCondition.put("cid", new ArrayList<String>());
        }

        if (param.getCid() != null) {
            ((List) whereCondition.get("cid")).add(param.getCid());
        }

        if (param.getOrderby() != null) {
            whereCondition.put("orderby", param.getOrderby());
        }

        if (param.getOrderbyColumn() != null) {
            whereCondition.put("orderbyColumn", param.getOrderbyColumn());
        }

        if (param.getAgencyId() != null) {
            whereCondition.put("agencyId", param.getAgencyId());
        }

        List<Campaign> list = sqlSessionHandler.selectList("campaignMapper.campaign:list:page", whereCondition, pageParameter);

        TableData<Campaign> result = new TableData<>();
        result.setData(list);
        result.setRecordsTotal(pageParameter.getTotalCount());
        result.setRecordsFiltered(pageParameter.getTotalCount());

        return result;
    }

    public Campaign delete(Campaign campaign) {

        GdtCampaignResult result = gdtRestHandler.post(GdtAction.CAMPAIGN_DELETE, new GdtCampaign().convert(campaign), GdtCampaignResult.class, null);
        sqlSessionHandler.insert("campaignMapper.campaign:delete", campaign);
        return campaign;
    }

    public Campaign create(Campaign campaign,double rate) {
        String msg = MathUtil.booleanChangeBudget(Constant.MIN_DAY_BUDGET_VALUE,Constant.MAX_CAMPAIGN_DAY_BUDGET_VALUE,0,campaign.getDayBudget(),0,rate);
        if(!StringUtils.equals(msg,"SUCCESS")){
            throw new FalconDspSqlException(msg);
        }
        campaign.setDayBudget(MathUtil.mul(MathUtil.mul(campaign.getDayBudget(),rate),100));
        GdtCampaignResult result = gdtRestHandler.post(GdtAction.CAMPAIGN_CREATE, new GdtCampaign().convert(campaign), GdtCampaignResult.class, null);
        campaign.setCid(result.getData().getCid());
        campaign.setEnd(DateUtils.endDay(campaign.getEnd()));
        sqlSessionHandler.insert("campaignMapper.campaign:insert", campaign);
        return campaign;
    }

    public Campaign edit(Campaign campaign,UserModel user) {
        double rate = user.getRate();
        if(campaign.getDayBudget()!=null){
            //获取订单的今日消耗
            int dailyCost = campaignDailyCost(campaign);
            double currentBudget = sqlSessionHandler.selectOne("campaignMapper.campaign:budget:select", campaign);
            //设置限额为乘以服务费之后的真实限额
            double campaign_dayBudget = campaign.getDayBudget();
            double value = 0;
            try {
                value = MathUtil.div(currentBudget,rate)-campaign_dayBudget;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(value != 0){
                //如果限额不一致的话，存在修改限额的情况，需要判断是否符合修改的条件。
                if(user.getUserRole().equals(UserRoleType.AGENCY)){
                    Advertiser advertiser = advertiserService.selectOne(campaign.getUid());
                    double adverShow = campaign.getDayBudget()*100.0/(100-advertiser.getCommission());
                    if(adverShow != (int)adverShow){
                        throw new FalconDspSqlException("您所设置的每日限额经溢价后显示不为整数，请调整！");
                    }
                }
                String msg = MathUtil.booleanChangeBudget(Constant.MIN_DAY_BUDGET_VALUE,Constant.MAX_CAMPAIGN_DAY_BUDGET_VALUE,
                        Math.ceil(dailyCost/100.0),campaign_dayBudget,currentBudget,rate);
                if(!StringUtils.equals(msg,"SUCCESS")){
                    throw new FalconDspSqlException(msg);
                }
            }
            campaign.setDayBudget(MathUtil.mul(MathUtil.mul(campaign.getDayBudget(),rate),100));
        }
        GdtCampaign gdtCampaign = new GdtCampaign().convert(campaign);
        GdtCampaignResult result = gdtRestHandler.post(GdtAction.CAMPAIGN_UPDATE, gdtCampaign, GdtCampaignResult.class, null);
        gdtCampaign.setCid(result.getData().getCid());
        if (campaign.getEnd() != null) {
            campaign.setEnd(DateUtils.endDay(campaign.getEnd()));
        }
        return editWithoutGdt(campaign,rate);
    }

    @Transactional
    public Campaign editWithoutGdt(Campaign campaign,double rate) {
        sqlSessionHandler.update("campaignMapper.campaign:update", campaign);
        return sqlSessionHandler.selectOne("campaignMapper.campaign:select:one", new CampaignParam(campaign.getUid(),campaign.getCid(),null,rate));
    }

    public Campaign read(CampaignParam campaignParam,double rate) {
        campaignParam.setMarkup(rate);
        return sqlSessionHandler.selectOne("campaignMapper.campaign:select:one", campaignParam);
    }

    public boolean checkName(Campaign campaign) {
        int result = 0;

        result = sqlSessionHandler.selectOne("campaignMapper.campaign:checkname", campaign);

        if (result > 0) {
            return false;
        }
        return true;
    }

    public int campaignDailyCost(Campaign campaign){
        Map<String,Integer> map = new HashMap<>(2);
        map.put("uid",campaign.getUid());
        map.put("cid",campaign.getCid());
        GdtFianaceBalanceResult result = gdtRestHandler.get(GdtAction.CAMPAIGN_GET_DAY_COST,GdtFianaceBalanceResult.class,map);
        return result.getData().getDailyCost();
    }

    /**
     *    通过广点通接口 获取单个订单
     */

    public GdtCampaign getGdtCampaign(int uid,int cid){
        Map<String,Integer> map = new HashMap<>(2);
        map.put("uid",uid);
        map.put("cid",cid);
        GdtCampaignResult result = gdtRestHandler.get(GdtAction.CAMPAIGN_READ,GdtCampaignResult.class,map);
        return result.getData();
    }

    /**
     * 查询广告主下的订单列表
     * @param uid 广告主id
     * @param isNormal 是否只查询正常的
     * @return
     */
    public List<GdtCampaign> getGdtCampaignList(int uid,boolean isNormal){
        Map<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        map.put("page_size",100);
        if(isNormal){
            map.put("where","{\"{status}\":\"ADSTATUS_NORMAL\"}");
        }
        GdtCampaignTableResult result = gdtRestHandler.get(GdtAction.CAMPAIGN_SELECT,GdtCampaignTableResult.class,map);
        GdtCampaignTable gt = result.getData();
        Conf conf = gt.getConf();
        List<GdtCampaign> list = gt.getList();
        for(int i= 2;i<=conf.getTotalPage();i++){
            map.put("page",i);
            result = gdtRestHandler.get(GdtAction.CAMPAIGN_SELECT,GdtCampaignTableResult.class,map);
            list.addAll(result.getData().getList());
        }
        return list;
    }

    public List<Integer> getCampaignTree(Integer uid,List<Integer> tree){
        List<Integer> cidTree;
        if(tree!=null&&tree.size()!=0){
            cidTree =  sqlSessionHandler.selectList("campaignMapper.campaign:cids:uids", tree);
        }else{
            cidTree =  sqlSessionHandler.selectList("campaignMapper.campaign:cids:uid", uid);
        }
        return cidTree;
    }
}
