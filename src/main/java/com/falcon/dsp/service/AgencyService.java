package com.falcon.dsp.service;

import org.apache.commons.lang3.StringUtils;
import com.falcon.dsp.common.Constant;
import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.common.GdtAction;
import com.falcon.dsp.common.MathUtil;
import com.falcon.dsp.enumration.FinanceFlowType;
import com.falcon.dsp.enumration.FinanceType;
import com.falcon.dsp.exception.FalDspParamException;
import com.falcon.dsp.exception.FalconDspSqlException;
import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.interceptor.PageParameter;
import com.falcon.dsp.jdbc.entity.AccountInvoice;
import com.falcon.dsp.jdbc.entity.Advertiser;
import com.falcon.dsp.jdbc.entity.Agency;
import com.falcon.dsp.jdbc.entity.Response;
import com.falcon.dsp.jdbc.model.*;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.agency.request.TransferParam;
import com.falcon.dsp.rest.agency.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/11.
 */
@Service
public class AgencyService {

    @Autowired
    private GdtRestHandler gdtRestHandler;

    @Resource(name = "sqlHandler")
    private SqlSessionHandler sqlSessionTemplate;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 获取代理商资金账户列表
     * @return
     */
    public List<GdtAgency> getAgencyList() {
        GdtAgencyTableResult obj = gdtRestHandler.get(GdtAction.AGENCY_GET_ACCOUNTS, GdtAgencyTableResult.class, null);
        return obj.getData().getList();
    }

    /**
     * 获取代理商财务流水 ,暂时用不到
     * @return
     */
    public GdtAgencyInvoiceTypeTable getAgencyAccountInvoice() {
        GdtAgencyInvoiceTypeTableResult object = gdtRestHandler.get(GdtAction.AGENCY_ACCOUNT_INVOICE, GdtAgencyInvoiceTypeTableResult.class, null);
        return object.getData();
    }


    /**
     * 生成流水单号
     * @param uid
     * @return
     */
    private String createBillNo(long uid) {
        //猎鹰简称+广告主Id+时间（201604天操作时间的时间戳）
        String dayString = DateUtils.currDay();
        Date dayDate = DateUtils.stringtoDate(dayString, DateUtils.LONG_DATE_FORMAT);
        long stamp = System.currentTimeMillis() - dayDate.getTime();
        StringBuffer sb = new StringBuffer(Constant.LIEING_SHORT_NAME).append("-").append(uid).append("-").append(dayString.replaceAll("-", "")).append(stamp);
        return sb.toString();
    }

    /**
     * 拼写转入转出地logger日志
     * @param accountInvoice
     * @param acy
     */
    private void getLoggerMessage(AccountInvoice accountInvoice,Agency acy){

        if(accountInvoice.getTransType().equals(FinanceFlowType.TRANSFER_IN)){
            logger.info("代理商{}({})向广告主{}({})账户转入{}元", acy.getName(), acy.getId(), accountInvoice.getUname(), accountInvoice.getUid(), accountInvoice.getAmount() / 100);
        }else{
            logger.info("代理商{}({})从广告主{}({})账户转出{}元", acy.getName(), acy.getId(), accountInvoice.getUname(), accountInvoice.getUid(), accountInvoice.getAmount() / 100);
        }

    }

    /**
     * 代理商操作广告主账户的转入转出
     * @param accountInvoice
     * @return
     */
    @Transactional
    public boolean transferOrRecoverToAdvertiser(AccountInvoice accountInvoice, UserModel user) {
        /*{uid: 79643, uname: "aaa 中午", account_type: "CASH", trans_type: "TRANSFER_IN", amount: "8"}
         * 1、展示广告主打算转入的资金、（为100的整数、），前台传过来的计算需要操作的资金（virtual_amount） ，验证是否正常
         * 2、操作广告主转入需要验证 amount 最低划账金额为50元，最高划账金额为2000万
         * 3、计算出真实的需要向广告主转入的资金，验证是否符合转账的要求
         * 4、转出时不需要判断限额
         */
        if(accountInvoice.getVirtualAmount()==0||accountInvoice.getVirtualAmount()%100!=0){
            throw new FalconDspSqlException("溢价金额只能为百的倍数!");
        }

        Advertiser adver = sqlSessionTemplate.selectOne("advertiserMapper.advertiser:selectone",accountInvoice.getUid());
        long va = accountInvoice.getVirtualAmount();
        try {
            if(MathUtil.mul(MathUtil.div(MathUtil.sub(100,adver.getCommission()),100.0),va)!=accountInvoice.getAmount()){
                throw new FalconDspSqlException("溢价金额与实际金额计算存在异常！");
            }
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            throw new FalconDspSqlException(e.getMessage());
        }
        int id = accountInvoice.getAgencyAccount();
        Agency agency = sqlSessionTemplate.selectOne("agencyMapper.agency:one:data", id);
        if (accountInvoice.getTransType() == FinanceFlowType.TRANSFER_IN) {
            //判断代理商资金是否充足
            if (accountInvoice.getAccountType() == FinanceType.CASH) {
                if(agency.getBalanceCash()<MathUtil.mul(accountInvoice.getAmount(),user.getRate())){
                    throw new FalconDspSqlException("资金不足，不能进行该操作!");
                }
            } else {
                if(agency.getBalanceVirtual()<MathUtil.mul(accountInvoice.getAmount(),user.getRate())){
                    throw new FalconDspSqlException("资金不足，不能进行该操作!");
                }
            }
            String msg = MathUtil.booleanTransferFinance(50,20000000,accountInvoice.getAmount(),user.getRate());
            if(!StringUtils.equals("SUCCESS",msg)){
                throw new FalconDspSqlException(msg);
            }
        }

        //查询当前代理商

        int ccAmount = MathUtil.getRoundNumberMultiply(accountInvoice.getAmount() * 100,user.getRate());
        accountInvoice.setBillNo(createBillNo(accountInvoice.getUid()));
        TransferParam tp = new TransferParam();
        tp.setUid(accountInvoice.getUid());
        tp.setAccount_type(accountInvoice.getAccountType());
        tp.setAmount(ccAmount);
        tp.setMemo(accountInvoice.getDescription());
        //到期日，暂时设置为空
        tp.setExpire_date(null);

        //设置交易时间戳
        accountInvoice.setTransTime(new Date().getTime() / 1000);
        //操作的是广告主，设置targetType=1
        accountInvoice.setTargetType(1);



        //写入到account_invoice表记录
        if (accountInvoice.getTransType() == FinanceFlowType.TRANSFER_IN) {
            accountInvoice.setRemark(String.format("向广告主 %s(%d) 转入 %.2f 元", accountInvoice.getUname(), accountInvoice.getUid(), ccAmount/100.0));
            accountInvoice.setDescription(String.format("向广告主 %s(%d) 转入 %d 元", accountInvoice.getUname(), accountInvoice.getUid(), accountInvoice.getAmount()));
            accountInvoice.setBalance(accountInvoice.getCurrentBalance() + ccAmount);
        } else {
            accountInvoice.setRemark(String.format("向广告主 %s(%d) 转出 %.2f 元", accountInvoice.getUname(), accountInvoice.getUid(), ccAmount/100.0));
            accountInvoice.setDescription(String.format("从广告主 %s(%d) 转出 %d 元", accountInvoice.getUname(), accountInvoice.getUid(), accountInvoice.getAmount()));
            accountInvoice.setBalance(accountInvoice.getCurrentBalance() - ccAmount);
        }

        //对代理商Agency表进行金额的更新
        AgencyTradeResultModel agencyModel = new AgencyTradeResultModel();
        agencyModel.setAgency_id(accountInvoice.getAgencyAccount());
        if (accountInvoice.getAccountType() == FinanceType.CASH) {
            agencyModel.setAccount_type(FinanceType.CASH.toString());
        } else {
            agencyModel.setAccount_type(FinanceType.VIRTUAL.toString());
        }

        if (accountInvoice.getTransType() == FinanceFlowType.TRANSFER_IN) {
            agencyModel.setAmount(- ccAmount);
        } else {
            agencyModel.setAmount(ccAmount);
        }

        sqlSessionTemplate.update("agencyMapper.agency:update:balance", agencyModel);

        accountInvoice.setAmount(ccAmount);
        //一条是对应代理商账户的流水记录
        sqlSessionTemplate.insert("accountInvoice:insert", accountInvoice);

        GdtTransferResult gdtTransferResult;
        if (accountInvoice.getTransType() == FinanceFlowType.TRANSFER_IN) {
            gdtTransferResult = gdtRestHandler.post(GdtAction.AGENCY_ACCOUNT_TRANSFER, tp, GdtTransferResult.class, null);
        }else{
            gdtTransferResult = gdtRestHandler.post(GdtAction.AGENCY_ACCOUNT_RECOVER, tp, GdtTransferResult.class, null);
        }
        GdtTransferModel gm = gdtTransferResult.getData();
        //接口处理成功，并且是否重复字段为（不重复）正常
        if(gm.getBillRepeated() == 1){
           throw new FalDspParamException("重复转账");
        }

        getLoggerMessage(accountInvoice,agency);
        return true;
    }

    /**
     * 查询代理商下的广告主列表
     * @return
     */
    public TableData<AdvertiserTableModel> listTable( PageParameter pageParameter, Map<String, Object> whereCondition) {
        List<AdvertiserTableModel> list = sqlSessionTemplate.selectList("advertiserMapper.advertiser:list:page", whereCondition,pageParameter);
        TableData<AdvertiserTableModel> result = new TableData<>();
        result.setData(list);
        result.setRecordsTotal(pageParameter.getTotalCount());
        result.setRecordsFiltered(pageParameter.getTotalCount());
        return result;
    }

    /**
     * 根据代理商的id查询代理商信息
     * @param id
     * @return
     */
    public Agency getAgencyInfo(int id) {

        return sqlSessionTemplate.selectOne("agency:one:data", id);
    }

    /**
     * 获取代理商列表
     * @param param
     * @return
     */
    public TableData<AgencyModel> getAgencyModelList(TableParam param) {
        PageParameter pageParameter = new PageParameter();
        pageParameter.setPageSize(param.getPageSize());
        pageParameter.setCurrentPage(param.getPage());
        Map<String, Object> whereCondition = new HashMap();


        if(param.getSearchKeyword()!=null&&!param.getSearchKeyword().equals("")){
            whereCondition.put("search_key",param.getSearchKeyword());
        }

        if (param.getOrderby() != null) {
            whereCondition.put("orderby", param.getOrderby());
        }

        if (param.getOrdercolumns() != null) {
            whereCondition.put("orderbyColumn", param.getOrdercolumns());
        }
        List<AgencyModel> list = sqlSessionTemplate.selectList("agencyMapper.agency:list:page",whereCondition,pageParameter);
        TableData<AgencyModel> result = new TableData<>();
        result.setData(list);
        result.setRecordsTotal(pageParameter.getTotalCount());
        result.setRecordsFiltered(pageParameter.getTotalCount());
        return result;
    }

    /**
     * 添加代理商
     * @param agency
     * @return
     */
    public Response addAgency(Agency agency) {
        if(checkAgencyName(agency)){
            return new Response().failure("代理商名称已存在！");
        }
        sqlSessionTemplate.insert("agency:add",agency);
        logger.info("添加了代理商{}",agency.getName());
        return new Response().success("ok!");
    }

    /**
     * 获取未绑定的代理商列表
     * @return
     */
    public List<Agency> getAgencySelectList() {
        return sqlSessionTemplate.selectList("agency:list:select",null);
    }

    /**
     * 修改代理商信息
     * @param agency
     */
    public Response updateAgency(Agency agency) {
        if(checkAgencyName(agency)){
            return new Response().failure("代理商名称已存在！");
        }
        sqlSessionTemplate.update("agency:update",agency);
        logger.info("更新代理商{}({})信息",agency.getName(),agency.getId());
        Agency agencyNew = getAgencyInfo(agency.getId());
        return new Response<Agency>().success(agencyNew);
    }

    public Agency getAgencyFinance() {
        Agency agency = sqlSessionTemplate.selectOne("agency:select:sum:finance");
        return agency;
    }

    /**
     * 验证代理商是否存在同名
     * @param agency
     * @return
     */
    public boolean checkAgencyName(Agency agency){
        int val = sqlSessionTemplate.selectOne("agency:check:name",agency);
        return val>0;
    }
}
