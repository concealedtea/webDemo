package com.falcon.dsp.service;

import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.common.GdtAction;
import com.falcon.dsp.common.JsonUtil;
import com.falcon.dsp.common.MathUtil;
import com.falcon.dsp.enumration.FalStrategyStatus;
import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.interceptor.PageParameter;
import com.falcon.dsp.jdbc.entity.*;
import com.falcon.dsp.jdbc.model.TableData;
import com.falcon.dsp.jdbc.model.TableParam;
import com.falcon.dsp.jdbc.model.UserModel;
import com.falcon.dsp.jdbc.model.UtilityModel;
import com.falcon.dsp.param.StrategyParam;
import com.falcon.dsp.param.TargetParam;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.campaign.params.CampaignParam;
import com.falcon.dsp.rest.campaign.request.GdtCampaign;
import com.falcon.dsp.rest.common.Conf;
import com.falcon.dsp.rest.common.Filter;
import com.falcon.dsp.rest.creative.request.GdtCreative;
import com.falcon.dsp.rest.strategy.request.GdtStrategy;
import com.falcon.dsp.rest.strategy.request.GdtUtility;
import com.falcon.dsp.rest.strategy.response.GdtStrategyResult;
import com.falcon.dsp.rest.strategy.response.GdtStrategyTable;
import com.falcon.dsp.rest.strategy.response.GdtStrategyTableResult;
import com.falcon.dsp.rest.strategy.response.GdtUtilityResult;
import com.falcon.dsp.rest.target.request.GdtTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by falcon-zhangxg on 2016/4/8.
 */

@Service
public class StragegyService {

    private static Logger logger = LoggerFactory.getLogger(StragegyService.class);

    @Resource(name = "sqlHandler")
    private SqlSessionHandler sqlSessionHandler;

    @Autowired
    private GdtRestHandler restHandler;

    @Autowired
    private TargetService targetService;

    @Autowired
    private CreativeService creativeService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private CampaignService campaignService;

    @Transactional
    public Strategy create(StrategyParam strategyParam,double rate) {

        TargetParam targetParam = strategyParam.getTargetParam();

        if (targetParam == null) {
            targetParam = new TargetParam();
        }

        targetParam.setUid(strategyParam.getUid());
        targetParam.settName(strategyParam.getName() + System.currentTimeMillis());
        Target target = targetService.create(targetParam);
        int tid = target.getTid();

        String platformValue = creativeService.getPlatformValue(strategyParam.getCreativeId());
        GdtStrategy gdtStrategy = strategyParam.fetchGdtStrategyModel();
        gdtStrategy.setMid(tid);
        gdtStrategy.setSiteset(new String[]{platformValue});
        GdtStrategyResult returnData = restHandler.post(GdtAction.AD_GROUP_CREATE, gdtStrategy, GdtStrategyResult.class, null);
        Strategy strategy = strategyParam.fetchStrategyDBModel();
        gdtStrategy.setAid(returnData.getData().getAid());

        try {
            //策略创建成功后，添加素材
            materialService.create(returnData.getData().getAid(), strategyParam.getCreativeId());
        } catch (Exception ex) {
            GdtStrategy data = new GdtStrategy();
            data.setUid(gdtStrategy.getUid());
            data.setAid(gdtStrategy.getAid());
            GdtStrategyResult deleteResult = restHandler.post(GdtAction.AD_GROUP_DELETE, data, GdtStrategyResult.class, null);
            throw ex;
        }
        strategy.setSid(returnData.getData().getAid());
        strategy.setTargetId(tid);
        strategy.setStatus(FalStrategyStatus.ADSTATUS_PENDING.getValue());
        strategy.setEnd(DateUtils.endDay(strategy.getEnd()));
        sqlSessionHandler.insert("strategyMapper.strategy:insert", strategy);

        strategyParam.setId(returnData.getData().getAid());
        Strategy result = this.read(strategyParam);
        //当策略时间超出订单范围后，修改订单时间为策略时间
        int campaignId = result.getCampaignId();
        CampaignParam campaignParam = new CampaignParam();
        campaignParam.setCid(campaignId);
        updateCampaignTime(rate, result, campaignParam);

        return result;
    }

    /**
     * 更改订单时间
     * @param rate
     * @param result
     * @param campaignParam
     */
    private void updateCampaignTime(double rate, Strategy result, CampaignParam campaignParam) {
        Campaign campaign = sqlSessionHandler.selectOne("campaignMapper.campaign:select:one:info",campaignParam);
        //比订单早
        if (result.getStart() != null && campaign.getStart() != null && result.getStart().getTime() < campaign.getStart().getTime()) {
            campaign.setStart(result.getStart());
            campaignService.editWithoutGdt(campaign,rate);
        }
        //比订单晚
        if (result.getEnd() != null && campaign.getEnd() != null && result.getEnd().getTime() > campaign.getEnd().getTime()) {
            campaign.setEnd(result.getEnd());
            campaignService.editWithoutGdt(campaign,rate);
        }
    }

    public TableData<Strategy> list(TableParam param,UserModel user) {

        PageParameter pageParameter = new PageParameter();
        pageParameter.setPageSize(param.getPageSize());
        pageParameter.setCurrentPage(param.getPage());

        Map<String, Object> whereCondition = new HashMap();
        whereCondition.put("rate",user.getRate());
        if (param.getUid() != null) {
            whereCondition.put("uid", param.getUid());
        }

        if (param.getCampaignId() != null) {
            whereCondition.put("campaignId", param.getCampaignId());
        }

        if (param.getStrategyId() != null) {
            whereCondition.put("sid", new ArrayList<String>());
            ((List) whereCondition.get("sid")).add(param.getStrategyId());
        }

        if (param.getSearchKeyword() != null) {
            if (param.getSearchKeyword() != null) {
                Pattern pattern = Pattern.compile("[0-9,;]+");
                Matcher matcher = pattern.matcher(param.getSearchKeyword());
                if (matcher.matches()) {
                    String[] cids = param.getSearchKeyword().replaceAll(";", ",").split(",");
                    whereCondition.put("sid", Arrays.asList(cids));
                }
                whereCondition.put("sname", param.getSearchKeyword());
            }
        }

        if (param.getSearchStatus() != null) {
            whereCondition.put("status", param.getSearchStatus());
        }

        if (param.getOrderby() != null) {
            whereCondition.put("orderby", param.getOrderby());
        }

        if (param.getOrdercolumns() != null) {
            whereCondition.put("orderbyColumn", param.getOrdercolumns());
        }

        List<Strategy> list = sqlSessionHandler.selectList("strategyMapper.strategy:list:page", whereCondition, pageParameter);

        TableData<Strategy> result = new TableData<>();
        result.setData(list);
        result.setRecordsTotal(pageParameter.getTotalCount());
        result.setRecordsFiltered(pageParameter.getTotalCount());

        return result;
    }

    public List<Strategy> list(int campaignId) {
        Map<String, Object> params = new HashMap<>();
//        params.put()
        return sqlSessionHandler.selectList("strategyMapper.strategy:list:page", params);
    }

    public Strategy read(StrategyParam strategyParam) {
        Map<String, Object> map = new HashMap<>();
        map.put("sid", strategyParam.getId());
        return sqlSessionHandler.selectOne("strategyMapper.strategy:select:one", map);
    }

    @Transactional
    public Strategy edit(StrategyParam strategyParam,double rate) {

        Strategy strategyDBModel = strategyParam.fetchStrategyDBModel();
        Strategy strategy = sqlSessionHandler.selectOne("strategyMapper.strategy:select:one:simple", strategyDBModel);

        if (strategyParam.getTargetParam() != null) {
            TargetParam targetParam = strategyParam.getTargetParam();
            targetParam.setUid(strategyParam.getUid());
            targetParam.setTid(strategy.getTargetId());
            targetParam.settName(strategy.getSname() + System.currentTimeMillis());
            targetService.edit(targetParam);
        }

        GdtStrategyResult data = restHandler.post(GdtAction.AD_GROUP_UPDATE, strategyParam.fetchGdtStrategyModel(), GdtStrategyResult.class, null);
        if (data.getRet() == 0) {
            if(strategyDBModel.getEnd()!=null){
                strategyDBModel.setEnd(DateUtils.endDay(strategyDBModel.getEnd()));
            }
            sqlSessionHandler.update("strategyMapper.strategy:update", strategyDBModel);
        }

        Strategy result = this.read(strategyParam);
        //当策略时间超出订单范围后，修改订单时间为策略时间
        int campaignId = result.getCampaignId();
        CampaignParam campaignParam = new CampaignParam();
        campaignParam.setCid(campaignId);
        updateCampaignTime(rate, result, campaignParam);

        return result;
    }

    public Map<Integer, String> nameList(Integer campaignId) {
        Map<Integer, String> result = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("campaign_id", campaignId);
        List<HashMap> strategyList = sqlSessionHandler.selectList("strategyMapper.strategy:id:list", map);
        if (strategyList != null && strategyList.size() > 0) {
            for (HashMap item : strategyList) {
                Integer sid = Integer.parseInt(item.get("sid").toString());
                String sname = item.get("sname").toString();
                result.put(sid, sname);
            }
        }
        return result;
    }

    public Map<String, Date> getTimaRange(Integer campaignId) {
        Map<String, Date> result = null;
        Map<String, Object> map = new HashMap<>();
        map.put("campaign_id", campaignId);
        List<HashMap> strategyList = sqlSessionHandler.selectList("strategyMapper.strategy:time:range", map);
        if (strategyList != null && strategyList.size() > 0) {
            HashMap item = strategyList.get(0);
            if (item != null) {
                result = new HashMap<>();
                Date start = (Date) item.get("start");
                Date end = (Date) item.get("end");
                result.put("start", start);
                result.put("end", end);
            }
        }
        return result;
    }

    public UtilityModel getUtility(StrategyParam strategyParam,UserModel user) {

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("uid", strategyParam.getUid());
        GdtStrategy gdtStrategy = strategyParam.fetchGdtStrategyModel();
        if (strategyParam.getCreativeId() != null) {
            List<Material> list = materialService.list(strategyParam.getCreativeId());
            objectMap.put("creative", list);
            String platformValue = creativeService.getPlatformValue(strategyParam.getCreativeId());
            gdtStrategy.setSiteset(new String[]{platformValue});
        }
        objectMap.put("ad_group", strategyParam.fetchGdtStrategyModel());

        if (strategyParam.getTargetParam() != null) {
            GdtTarget gdtTarget = strategyParam.getTargetParam().fetchGdtTarget();
            objectMap.put("ad_target", gdtTarget);
        }
        GdtUtilityResult gdtUtilityResult = restHandler.post(GdtAction.UTILITIES_ESTIMATE, objectMap, GdtUtilityResult.class, null);
        GdtUtility gdtUtility = gdtUtilityResult.getData();
        try {
            double a1 = MathUtil.doubleFormatRoundUp(MathUtil.div(MathUtil.div(gdtUtility.getMinBid(),100),user.getRate()));
            double a2 =  MathUtil.doubleFormatRoundUp(MathUtil.div(MathUtil.div(gdtUtility.getMaxBid(),100),user.getRate()));
            return new UtilityModel(gdtUtility.getUser(), gdtUtility.getExposure(),a1 ,a2);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
           return null;
        }
    }

    /**
     * 通过广点通，查询用户订单下 策略列表
     * @param uid
     * @param cid
     * @return
     */
    public List<GdtStrategy> getGdtStrategyList(int uid,int cid){
        Map<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        map.put("page_size",100);
        if(cid!=0){
            map.put("filter",JsonUtil.toJson(new Filter[] {new Filter("cid","EQUALS",cid+"")}));
        }
        GdtStrategyTableResult result = restHandler.get(GdtAction.AD_GROUP_SELECT,GdtStrategyTableResult.class,map);
        GdtStrategyTable gt = result.getData();
        Conf conf = gt.getConf();
        List<GdtStrategy> list = gt.getList();
       for(int i= 2;i<=conf.getTotalPage();i++){
            map.put("page",i);
            result = restHandler.get(GdtAction.AD_GROUP_SELECT,GdtStrategyTableResult.class,map);
            list.addAll(result.getData().getList());
        }
        return list;
    }



    private String getIds(List<GdtCreative> creativeList){
        StringBuffer sb = new StringBuffer("(");
        for (GdtCreative gdtCreative : creativeList) {
            sb.append("'").append(gdtCreative.getCrtSize()).append("',");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");
        return sb.toString();
    }

    /**
     * 同步广告主下的所有订单
     * @param uid
     */
    @Transactional
    public void syncCampaignByUid(int uid){
        List<GdtCampaign> list = campaignService.getGdtCampaignList(uid,false);
        List<GdtCampaign> list_t = new ArrayList<>();
        int i = 1;
        List<Strategy> strategyInsertList = new ArrayList<>();
        for(GdtCampaign campaign:list){
            //查询出策略列表
            List<GdtStrategy> strategyList = getGdtStrategyList(uid,campaign.getCid());
            int startTime=2147483647,endTime=0;
            //循环策略列表确定出订单的计划开始与结束时间
            for(GdtStrategy gs:strategyList){
                //选出策略中的最小开始时间与，最大结束时间作为订单的开始与结束时间
                if(gs.getBeginTime()<startTime) startTime=gs.getBeginTime();
                if(gs.getEndTime()>endTime) endTime = gs.getEndTime();
                if(gs.getEndTime()==0){
                    gs.setEndTime((int) (DateUtils.getNextYearDate().getTime()/1000));
                }
                strategyInsertList.add(gs.getStrategy(0,uid));
            }
            //将该订单写入到数据库 1457625600  1459439999
            if(startTime==2147483647){
                campaign.setStart(new Date());
            }else{
                campaign.setStart(DateUtils.formatIntToDate(startTime));
            }

            if(endTime==0){
                campaign.setEnd(DateUtils.getNextYearDate());
            }else{
                campaign.setEnd(DateUtils.formatIntToDate(endTime));
            }
            campaign.setUid(uid);
            list_t.add(campaign);
            if(i%100==0){
                //每100条写入一次
                sqlSessionHandler.insert("campaignMapper.campaign:insert:batch",list_t);
                //将策略列表写入到数据库
                if(strategyInsertList!=null&&strategyInsertList.size()>0){
                    sqlSessionHandler.insert("strategyMapper.strategy:insert:batch",strategyInsertList);
                }
                strategyInsertList.clear();
                list_t.clear();
            }
            i++;
        }
        //处理循环之后剩余的不足100的数据
        if(i%100!=0){
           sqlSessionHandler.insert("campaignMapper.campaign:insert:batch",list_t);
            if(strategyInsertList!=null&&strategyInsertList.size()>0){
                sqlSessionHandler.insert("strategyMapper.strategy:insert:batch",strategyInsertList);
            }
        }
    }

    /**
     * 根据传过来cids  同步订单信息，包括订单下策略、创意等。
     * 暂时保留，暂未用到。
     * @param uid
     * @param cids
     */

    @Transactional
    public void syncCampaign(int uid,String cids){
        String[] cidArray = cids.split(",");
        for(String cid:cidArray){
            //查询出订单,写入到campaign
            GdtCampaign campaign = campaignService.getGdtCampaign(uid,Integer.parseInt(cid));

            //查询订单下的策略列表。写入到strategy
            List<GdtStrategy> strategyList = getGdtStrategyList(uid,campaign.getCid());

            List<Strategy> list = new ArrayList<>();
           List<Material> materialList = new ArrayList<>();
           List<Target> targetList = new ArrayList<>();
            int startTime=2147483647,endTime=0;
            //选出策略中订单的最大与最小时间
            for(GdtStrategy gs:strategyList){
                //查询策略下的素材列表
                List<GdtCreative> creativeList = creativeService.getGdtCreativeList(uid,gs.getAid());
                String[] siteSet = gs.getSiteset();
                String siteValue = siteSet!=null&&siteSet.length>0?siteSet[0]:"";
                if(creativeList!=null&&creativeList.size()>0){
                    //根据素材列表，组成一条创意的虚拟记录
                    GdtCreative ce = creativeList.get(0);
                    Creative c = new Creative();
                    c.setCid(gs.getCid());

                    Map map = new HashMap();
                    map.put("siteValue",siteValue);
                    map.put("ids",getIds(creativeList));
                    List<Map> mapList = sqlSessionHandler.selectList("adSpacePositionMapper.adSpace:position:select",map);
                    if(mapList!=null&&mapList.size()==1){
                        c.setAdSpacePositionId((Integer) mapList.get(0).get("ad_space_position_id"));//需要根据crtSize查询
                    }else{
                        //记录异常，查询出多个广告位置
                        logger.error("同步订单：查询出多个广告位置！订单ID:"+cid+";策略ID:"+gs.getAid());
                        //TODO 这里是否该抛出异常。
                    }

                    c.setCreativeName(ce.getTitle());
                    c.setDestUrl(ce.getDestUrl());
                    c.setRemark(ce.getDesc());
                    c.setUid(uid);
                    c.setStatus(1);

                    //将虚拟创意写入到数据库中，并且返回数据，获取到创意id
                    sqlSessionHandler.insert("creativeMapper.creative:insert:return:id",c);
                    //组成需要写入策略的列表
                    list.add(gs.getStrategy(c.getId(),uid));
                    //组成需要写入素材的列表
                    materialList.clear();
                    for (GdtCreative gdtCreative : creativeList) {
                        Material m = gdtCreative.getMaterial(c.getId());
                        materialList.add(m);
                    }
                    //将素材列表写入到数据库
                    if(materialList!=null&&materialList.size()>0){
                        sqlSessionHandler.insert("materialMapper.material:insert:batch",materialList);
                    }

                    //调用接口查询定向信息，加入到定向列表中
                    GdtTarget target = targetService.getGdtTargetInfo(uid,gs.getMid());
                    targetList.add(target.getTarget());

                }
                //选出策略中的最小开始时间与，最大结束时间作为订单的开始与结束时间
                if(gs.getBeginTime()<startTime) startTime=gs.getBeginTime();
                if(gs.getEndTime()>endTime) endTime = gs.getEndTime();
            }

            //写入定向信息列表
            if(targetList!=null&&targetList.size()>0){
                sqlSessionHandler.insert("targetMapper.target:insert:batch",targetList);
            }

            //将策略列表写入到数据库
            if(list!=null&&list.size()>0){
                sqlSessionHandler.insert("strategyMapper.strategy:insert:batch",list);
            }

            //将该订单写入到数据库
            if(startTime==2147483647){
                campaign.setStart(new Date());
            }else{
                campaign.setStart(DateUtils.formatIntToDate(startTime));
            }

            if(endTime==0){
                campaign.setStart(new Date());
            }else{
                campaign.setEnd(DateUtils.formatIntToDate(endTime));
            }
            sqlSessionHandler.insert("campaign:insert",new Campaign().convert(campaign));
        }
    }
}

