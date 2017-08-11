package com.falcon.dsp.service;

import org.apache.commons.lang3.StringUtils;
import com.falcon.dsp.common.Constant;
import com.falcon.dsp.common.GdtAction;
import com.falcon.dsp.common.MathUtil;
import com.falcon.dsp.enumration.*;
import com.falcon.dsp.exception.FalDspException;
import com.falcon.dsp.exception.FalconDspSqlException;
import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.interceptor.PageParameter;
import com.falcon.dsp.jdbc.entity.AccountInvoice;
import com.falcon.dsp.jdbc.entity.Advertiser;
import com.falcon.dsp.jdbc.entity.Agency;
import com.falcon.dsp.jdbc.entity.FundsRealtime;
import com.falcon.dsp.jdbc.model.*;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.advertiser.response.GdtAdvertiserResult;
import com.falcon.dsp.rest.common.Conf;
import com.falcon.dsp.rest.finance.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class FinanceService {

    @Autowired
    private GdtRestHandler gdtRestHandler;

    @Resource(name = "sqlHandler")
    private SqlSessionHandler sqlSessionHandler;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private AdvertiserService advertiserService;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询广告主资金账户列表
     * @param uid
     * @return
     */
    public GdtFinanceBalanceTableResult listBalance(int uid) {
        Map<String, Object> params  = new HashMap<>(2);
        params.put("uid",uid);
        return gdtRestHandler.get(GdtAction.FINANCE_SELECT, GdtFinanceBalanceTableResult.class ,params);
    }

    /**
     * 更新广告主的实时消耗信息
     * @param uid
     * @param list
     */
    @Transactional
    public void updateFundsRealTime(int uid, List<GdtFinanceBalance> list) {
        List<FundsRealtime> list1 = new ArrayList<>();
        for(GdtFinanceBalance gb: list){
            list1.add(new FundsRealtime(uid,gb));
        }
        sqlSessionHandler.update("fundsRealtime:delete",uid);
        sqlSessionHandler.insert("fundsRealtime:insert:list",list1);
    }

    public int getAdvertiserDailyCost(int uid){
        Map map = new HashMap(1);
        map.put("uid",uid);
        GdtFianaceBalanceResult result = gdtRestHandler.get(GdtAction.FINANCE_GET_DAY_BUDGET, GdtFianaceBalanceResult.class ,map);
       return result.getData().getDailyCost();
    }

    /**
     * 设置广告主的日限额
     * @param map
     * @param user
     */
    public void setDayBudget(Map<String, Object> map, UserModel user){
        //"uid" -> "79643"  "day_budget" -> "5100"
        Integer uid = Integer.parseInt(map.get("uid").toString());
        int day_budget = Integer.parseInt(map.get("day_budget").toString());
        if(user.getUserRole().equals(UserRoleType.AGENCY)){
           Advertiser advertiser = advertiserService.selectOne(uid);
            try {
                double adverShow = MathUtil.div(MathUtil.mul(day_budget,100),MathUtil.sub(100,advertiser.getCommission()));
                if(adverShow != (int)adverShow){
                    throw new FalconDspSqlException("您所设置的每日限额经溢价后显示不为整数，请调整！");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        //查询出当前uid的日消耗和日限额。
        Advertiser result = advertiserService.selectOne(uid);
        int dailyCost = getAdvertiserDailyCost(uid);
        String msg = MathUtil.booleanChangeBudget(Constant.MIN_DAY_BUDGET_VALUE,Constant.MAX_ADVERTISER_DAY_BUDGET_VALUE,dailyCost/100.0,day_budget,result.getDayBudget()/100.0,user.getRate());
        if(StringUtils.equals(msg,"SUCCESS")){
            map.put("day_budget",(int)MathUtil.mul(MathUtil.doubleFormatRoundUp(MathUtil.mul(day_budget,user.getRate())),100));
            gdtRestHandler.post(GdtAction.FINANCE_SET_DAY_BUDGET,map,GdtFianaceBalanceResult.class,null);
            map.put("day_budget",MathUtil.mul(MathUtil.mul(day_budget,100),(user.getRate())));
            sqlSessionHandler.update("advertiser:update:budget",map);
        }else{
            throw new FalconDspSqlException(msg);
        }

    }

    /**
     *  从数据库查询出代理商的流水列表
     */
    public TableData<Map> getAccountInvoiceList(Map map, PageParameter page){
        List list = sqlSessionHandler.selectList("accountInvoice:select:list:page",map,page);
        TableData<Map> result = new TableData<>();
        result.setData(list);
        result.setRecordsTotal(page.getTotalCount());
        result.setRecordsFiltered(page.getTotalCount());
        return result;
    }

    /**
     * 调用接口查询出广告主的流水信息
     * @param map
     * @return
     */
    public TableData<Map> getAccountFinstament(Map map,UserModel user){

        GdtFinanceTradeTableResult gr = null;
        try {
            gr = gdtRestHandler.get(GdtAction.FINANCE_FINSTAMENT, GdtFinanceTradeTableResult.class, map);
        } catch (FalDspException ex) {
            logger.error("query account trade flow error : cause by {}", ex.getMessage());
            return null;
        }
        GdtFinanceTradeTable data = gr.getData();
        List<GdtTrade> gtList = data.getList();
        List<Map> mapList = new ArrayList<>();
        String uid = map.get("uid").toString();
        for(GdtTrade gt:gtList){
            Map mapn = new HashMap(5);
            mapn.put("uid",uid);
            mapn.put("trans_time", gt.getDate());
            double amount = MathUtil.doubleFormat(gt.getAmount()/user.getRate()/100.0);
            if(gt.getFinanceTradeType()== FinanceTradeType.RECHARGE){
                mapn.put("transfer_in",amount);
                mapn.put("transfer_out",0);
                mapn.put("description","客户充值");
            }else if(gt.getFinanceTradeType()== FinanceTradeType.CONSUME){
                mapn.put("transfer_in",0);
                mapn.put("transfer_out",amount);
                mapn.put("description","系统扣费");
            }else if(gt.getFinanceTradeType()== FinanceTradeType.RECOVER){
                mapn.put("transfer_in",0);
                mapn.put("transfer_out",amount);
                mapn.put("description","资金回划");
            }else if(gt.getFinanceTradeType()== FinanceTradeType.EXPIRE){
                mapn.put("transfer_in",0);
                mapn.put("transfer_out",amount);
                mapn.put("description","到期清零");
            }
            mapList.add(mapn);
        }

        Conf conf = data.getConf();
        TableData<Map> result = new TableData<>();
        result.setData(mapList);
        result.setRecordsTotal(conf.getTotalNum());
        result.setRecordsFiltered(conf.getTotalNum());
        return result;
    }

    /**
     * 代理商账户的转入转出，有管理员来操作
     * @param av
     * @param agencyModel
     * @param agency
     */
    @Transactional
    public void agencyTrade(AccountInvoice av, AgencyTradeResultModel agencyModel, Agency agency){
        av.setAgencyAccount(Integer.parseInt(gdtRestHandler.getUid()));
        sqlSessionHandler.update("agencyMapper.agency:update:balance", agencyModel);
        sqlSessionHandler.insert("accountInvoice:insert", av);
        getLoggerMessage(av,agency);
    }

    /**
     * 拼写转入转出地logger日志
     * @param accountInvoice
     * @param acy
     */
    private void getLoggerMessage(AccountInvoice accountInvoice,Agency acy){

        if(accountInvoice.getTransType().equals(FinanceFlowType.TRANSFER_IN)){
            logger.info("代理商{}({})向其账户账户充值{}元", acy.getName(), acy.getId(), accountInvoice.getAmount() / 100);
        }else{
            logger.info("代理商{}({})从其账户账户转出{}元", acy.getName(), acy.getId(), accountInvoice.getAmount() / 100);
        }

    }

    /**
     * 获取管理员财务管理下的资金流水
     * @param tp
     * @return
     */
    public TableData<FinanceFlowModel> getAdminFinanceFlow(FinanceFlowParam tp) {
        PageParameter page = new PageParameter();
        page.setCurrentPage(tp.getCurrentPage());
        page.setPageSize(tp.getPageSize());

        List<FinanceFlowModel> list = sqlSessionHandler.selectList("accountInvoice:agency:list:page",tp.toMap(),page);

        TableData<FinanceFlowModel> result = new TableData<>();
        result.setData(list);
        result.setRecordsTotal(page.getTotalCount());
        result.setRecordsFiltered(page.getTotalCount());
        return result;
    }

    /**
     * 根据用户查询资金信息
     * @param user
     * @return
     */
    public Map getCrruteUserFinance(UserModel user) {
        if (user.getUserRole() == UserRoleType.ADVERTISER) {
            //广告主账户
            GdtFinanceBalanceTableResult balances = listBalance(user.getUid());
            List<GdtFinanceBalance> listBalance = balances.getData().getList();
            long balance_cash = 0, balance_virtual = 0;
            for (GdtFinanceBalance ge : listBalance) {
                if (ge.getFinanceType() == FinanceType.CASH) {
                    balance_cash = ge.getBalance();
                } else if (ge.getFinanceType() == FinanceType.VIRTUAL) {
                    balance_virtual = ge.getBalance();
                }
            }

            Map map = new HashMap<>(5);
            map.put("balanceCash", Math.round(balance_cash) / 100.0/user.getRate());
            map.put("balanceVirtual", Math.round(balance_virtual) / 100.0/user.getRate());
            GdtAdvertiserResult gr = advertiserService.getGdtAdvertiserByUid(user.getUid(), null);
            Advertiser ad = advertiserService.selectOne(user.getUid());
            CustomerStatus cs = gr.getData().getCustomerStatus();
            map.put("dayBudget", MathUtil.getRoundNumberDivision(ad.getDayBudget()/100.0,user.getRate()));
            map.put("isAgency", false);
            map.put("customer_status",cs==CustomerStatus.CUSTOMERSTATUS_NORMAL?0:1);
            map.put("uid",user.getUid());
            return map;

        } else {
            //代理商账户
            Agency agency = agencyService.getAgencyInfo(user.getUid());
            if (agency == null) {
                return null;
            }
            Map map = new HashMap<>(4);
            map.put("balanceCash", agency.getBalanceCash()/user.getRate());
            map.put("balanceVirtual", agency.getBalanceVirtual()/user.getRate());
            map.put("isAgency", true);
            map.put("uid",user.getUid());

            return map;
        }
    }
}
