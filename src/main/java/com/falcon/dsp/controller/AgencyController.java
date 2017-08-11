package com.falcon.dsp.controller;

import com.falcon.dsp.annotation.UserAttribute;
import com.falcon.dsp.common.MathUtil;
import com.falcon.dsp.enumration.FinanceFlowType;
import com.falcon.dsp.enumration.FinanceType;
import com.falcon.dsp.enumration.UserRoleType;
import com.falcon.dsp.exception.FalconDspServerException;
import com.falcon.dsp.handler.RoleAndView;
import com.falcon.dsp.interceptor.PageParameter;
import com.falcon.dsp.jdbc.entity.AccountInvoice;
import com.falcon.dsp.jdbc.entity.Agency;
import com.falcon.dsp.jdbc.entity.Response;
import com.falcon.dsp.jdbc.entity.UserInfo;
import com.falcon.dsp.jdbc.model.*;
import com.falcon.dsp.rest.agency.response.GdtAgency;
import com.falcon.dsp.rest.finance.response.GdtFinanceBalance;
import com.falcon.dsp.rest.finance.response.GdtFinanceBalanceTableResult;
import com.falcon.dsp.service.AgencyService;
import com.falcon.dsp.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代理商
 * @author Zhouchunhui
 * @from 2016-04-11
 * @since V1.0
 */
@Controller
@RequestMapping("/agency")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private FinanceService financeService;

    /**
     * 获取猎鹰账户的资金状况
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "getBaseAccountBalance", method = RequestMethod.GET)
    @ResponseBody
    public Response<Map> view() {
        return new Response<Map>().success(getLieYingAcountFinance());
    }


    /**
     * 跳转到代理商管理界面
     * @param user
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public RoleAndView list(@UserAttribute UserModel user) {

        return new RoleAndView("AgencyList", user);
    }

    /**
     * 跳转到代理商财务管理页面
     * @param user
     * @return
     */
    @RequestMapping(value = "agencyFinanceList", method = RequestMethod.GET)
    public RoleAndView agencyFinanceList(@UserAttribute UserModel user, Model model) {
        model.addAllAttributes(getLieYingAcountFinance());
        return new RoleAndView("AgencyFinance", user);
    }

    /**
     * 获取猎鹰账户下的现金虚拟金，组成map后返回
     * @return 715445  436.35
     */
    private Map getLieYingAcountFinance(){
        List<GdtAgency> list = agencyService.getAgencyList();
        Agency agency = agencyService.getAgencyFinance();
        Map map = new HashMap(2);
        for(GdtAgency ga:list){
            if(ga.getAccountType().equals(FinanceType.CASH)){
                map.put("balance_cash", MathUtil.doubleFormat(ga.getBalance()/100.0-agency.getBalanceCash()));
            }else if(ga.getAccountType().equals(FinanceType.VIRTUAL)){
                map.put("balance_virtual",MathUtil.doubleFormat(ga.getBalance()/100.0-agency.getBalanceVirtual()));
            }
        }
        return map;
    }

    /**
     * 获取猎鹰账户操作代理商的财务流水
     * @param tp
     * @return
     */
    @RequestMapping(value = "financeFlowList", method = RequestMethod.POST)
    @ResponseBody
    public TableData<FinanceFlowModel> getAdminFinanceFlow(@RequestBody FinanceFlowParam tp) {

        return financeService.getAdminFinanceFlow(tp);
    }

    /**
     * 查询代理商列表
     * @param tableParam
     * @param user
     * @return
     */
    @RequestMapping(value = "agencyList", method = RequestMethod.POST)
    @ResponseBody
    public TableData<AgencyModel> agencyList(@RequestBody TableParam tableParam,@UserAttribute UserModel user){
        //booleanUser(user);
        TableData<AgencyModel> data = agencyService.getAgencyModelList(tableParam);
        return data;
    }

    /**
     * 添加代理商
     * @param agency 代理商信息
     * @param user
     * @return
     */
    @RequestMapping(value = "addAgency", method = RequestMethod.POST)
    @ResponseBody
    public Response addAgency(@RequestBody Agency agency, @UserAttribute UserModel user){
        booleanUser(user);
        return agencyService.addAgency(agency);


    }

    /**
     * 修改代理商
     * @param agency 代理商信息
     * @param user
     * @return
     */
    @RequestMapping(value = "updateAgency", method = RequestMethod.POST)
    @ResponseBody
    public Response updateAgency(@RequestBody Agency agency,@UserAttribute UserModel user){
        booleanUser(user);
        return agencyService.updateAgency(agency);


    }

    /**
     * 代理商资金的转入转出
     * @param atm 交易信息  transType :交易方式  id  代理商id  amount  交易金额  ，financeType 资金账户类型
     * @param user
     * @return
     */
    @RequestMapping(value = "tradeBalanceAgency", method = RequestMethod.POST)
    @ResponseBody
    public Response tradeBalanceAgency(@RequestBody AgencyTradeModel atm, @UserAttribute UserModel user){

        booleanUser(user);
        FinanceFlowType transType = atm.getTransType();
        FinanceType financeType = atm.getFinanceType();
        int id= atm.getId();
        long amount = atm.getAmount();
        Agency agency = agencyService.getAgencyInfo(id);
        if(agency==null){
            return  new Response().failure("未查询到该代理商的信息！");
        }
        if(atm.getVirtualAmount()%100!=0){
            return  new Response().failure("转入转出操作金额应为百的倍数！");
        }
        if((atm.getVirtualAmount()*(100-agency.getCommission())/100.0)!=atm.getAmount()){
            return  new Response().failure("资金计算存在异常！");
        }

        AccountInvoice av = new AccountInvoice();
        av.setAccountType(financeType);
        av.setAmount(amount*100);
        av.setCurrentBalance((long) ((agency.getBalanceCash()+agency.getBalanceVirtual())*100));
        av.setUid(id);
        av.setUname(agency.getName());
        av.setTransTime(new Date().getTime());
        av.setTargetType(2);
        //对代理商Agency表进行金额的更新
        AgencyTradeResultModel agencyModel = new AgencyTradeResultModel();
        agencyModel.setAgency_id(id);
        double rate = getAgencyRate(agency);
        if(transType.equals(FinanceFlowType.TRANSFER_IN)){
            List<GdtAgency> list = agencyService.getAgencyList();
            Agency agency1 = agencyService.getAgencyFinance();
            int nowCash = 0,nowVirtual = 0;
            for(GdtAgency ga:list){
                if(ga.getAccountType().equals(FinanceType.CASH)){
                    nowCash = ga.getBalance();
                }else if(ga.getAccountType().equals(FinanceType.VIRTUAL)){
                    nowVirtual = ga.getBalance();
                }
            }
            agencyModel.setAmount(av.getAmount());
            av.setBalance((long) ((agency.getBalanceCash()+agency.getBalanceVirtual()+amount)*100));
            av.setTransType(FinanceFlowType.TRANSFER_IN);

            //代理商转入
            if(financeType.equals(FinanceType.CASH)){
                if((nowCash-agency1.getBalanceCash()*100)<amount*100){
                    return new Response().failure("现金不足，不能进行转账！");
                }
                agencyModel.setAccount_type(FinanceType.CASH.toString());
                av.setRemark("代理商"+agency.getName()+"现金账户充值，金额(元)："+String.format(" %.2f", MathUtil.doubleFormat(amount)));
                av.setDescription("代理商"+agency.getName()+"现金账户充值，金额(元)："+String.format(" %.2f",MathUtil.doubleFormat(amount/rate)));
            }else{
                if((nowVirtual-agency1.getBalanceVirtual()*100)<amount*100){
                    return new Response().failure("猎鹰账户虚拟金不足，不能进行转账！");
                }
                agencyModel.setAccount_type(FinanceType.VIRTUAL.toString());
                av.setRemark("代理商"+agency.getName()+"虚拟金账户充值，金额(元)："+String.format(" %.2f", MathUtil.doubleFormat(amount)));
                av.setDescription("代理商"+agency.getName()+"虚拟金账户充值，金额(元)："+String.format(" %.2f",MathUtil.doubleFormat(amount/rate)));
            }
        }else{
            agencyModel.setAmount(- av.getAmount());
            av.setBalance((long) ((agency.getBalanceCash()+agency.getBalanceVirtual()-amount)*100));
            av.setTransType(FinanceFlowType.TRANSFER_OUT);
            //代理商转出
            if(financeType.equals(FinanceType.CASH)){
                agencyModel.setAccount_type(FinanceType.CASH.toString());
                //现金
                if(agency.getBalanceCash()-amount>=0){
                    av.setRemark("代理商"+agency.getName()+"现金账户转出，金额(元)："+String.format(" %.2f", MathUtil.doubleFormat(amount)));
                    av.setDescription("代理商"+agency.getName()+"现金账户转出，金额(元)："+String.format(" %.2f",MathUtil.doubleFormat(amount/rate)));
                }else{
                    return  new Response().failure("现金不足，不能进行该操作！");
                }
            }else{
                agencyModel.setAccount_type(FinanceType.VIRTUAL.toString());
                if(agency.getBalanceVirtual()-amount<0){
                    return  new Response().failure("虚拟金不足，不能进行该操作！");
                }
                av.setRemark("代理商"+agency.getName()+"虚拟金账户转出，金额(元)："+String.format(" %.2f", MathUtil.doubleFormat(amount)));
                av.setDescription("代理商"+agency.getName()+"虚拟金账户转出，金额(元)："+String.format(" %.2f",MathUtil.doubleFormat(amount/rate)));
            }
        }
        av.setTransTime(new Date().getTime() / 1000);
        financeService.agencyTrade(av,agencyModel,agency);
        Agency agencyNew = agencyService.getAgencyInfo(agency.getId());
        return new Response<Agency>().success(agencyNew);
    }

    /**
     * 获取代理商的服务费系数
     * @param agency
     * @return
     */
    private double getAgencyRate(Agency agency){
        return (100-agency.getCommission())/100.0;
    }

    /**
     * 获取未绑定的代理商 条件：关联到的用户表信息为空
     * @return
     */
    @RequestMapping(value = "agencySelectList", method = RequestMethod.POST)
    @ResponseBody
    public List<Agency> agencySelectList(){
        List<Agency> data = agencyService.getAgencySelectList();
        return data;
    }

    /**
     * 根据uid获取单个代理商
     * @param id
     * @return
     */
    @RequestMapping(value = "getAgencyOne", method = RequestMethod.GET)
    @ResponseBody
    public Response<Agency> getAgencyOne(@RequestParam(name = "id") Integer id) {

        Agency agency = agencyService.getAgencyInfo(id);
        if (agency == null) {
            return new Response().failure("未查询到数据!");
        }
        return new Response<Agency>().success(agency);
    }

    /**
     * 获取代理商下广告主列表
     * @param tableParam
     * @param user
     * @return
     */
    @RequestMapping(value = "advertiserList", method = RequestMethod.POST)
    @ResponseBody
    public TableData<AdvertiserTableModel> advertiserList(@RequestBody TableParam tableParam,@UserAttribute UserModel user) {
        int agencyId = user.getUid();

        PageParameter pageParameter = new PageParameter();
        pageParameter.setPageSize(tableParam.getLength());
        pageParameter.setCurrentPage(tableParam.getCurrentPage());

        Map<String, Object> whereCondition = new HashMap();
        whereCondition.put("agency_id",agencyId);
        whereCondition.put("rate",user.getRate());
        if (tableParam.getSearchKeyword() != null && tableParam.getSearchKeyword() != null) {
            whereCondition.put("search_key",tableParam.getSearchKeyword());
        }
        if (tableParam.getOrderby() != null) {
            whereCondition.put("orderby", tableParam.getOrderby());
        }
        if (tableParam.getOrdercolumns() != null) {
            whereCondition.put("orderbyColumn", tableParam.getOrdercolumns());
        }

        //先从数据库中查询出代理商下对应的广告商。
        TableData<AdvertiserTableModel> gdtAdvertiserList = agencyService.listTable(pageParameter,whereCondition);
        return gdtAdvertiserList;
    }

    /**
     * 获取当前登录代理商的信息
     * @param user
     * @return
     */
    @RequestMapping(value = "getAgencyInfoById", method = RequestMethod.GET)
    @ResponseBody
    public Response<Agency> getAgencyInfoById(@UserAttribute UserModel user) {
        if(user.getUserRole()!= UserRoleType.AGENCY){
            return new Response().failure("该账户不是代理商用户，不能进行该操作！");
        }
        int id = user.getUid();

        Agency agency = agencyService.getAgencyInfo(id);
        agency.setBalanceCash(agency.getBalanceCash()/user.getRate());
        agency.setBalanceVirtual(agency.getBalanceVirtual()/user.getRate());
        if (agency == null) {
            return new Response().failure("未查询到数据!");
        }
        return new Response<Agency>().success(agency);
    }

    /**
     * 根据uid获取广告主下资金信息
     * @param uid
     * @return
     */
    @RequestMapping(value = "getAdvertiserBalanceInfo", method = RequestMethod.GET)
    @ResponseBody
    public Response<Map> getAdvertiserBalanceInfo(@RequestParam(name = "uid", required = false) Integer uid,@UserAttribute UserModel user) {
        GdtFinanceBalanceTableResult balances = financeService.listBalance(uid);

        List<GdtFinanceBalance> listBalance = balances.getData().getList();
        long balance_cash = 0, balance_virtual = 0;
        for (GdtFinanceBalance ge : listBalance) {
            if (ge.getFinanceType() == FinanceType.CASH) {
                balance_cash = ge.getBalance();
            } else if (ge.getFinanceType() == FinanceType.VIRTUAL) {
                balance_virtual = ge.getBalance();
            }
        }
        Map map = new HashMap<>(2);
        map.put("balance_cash", Math.round(balance_cash)/100.0/user.getRate());
        map.put("balance_virtual", Math.round(balance_virtual)/100.0/user.getRate());
        return new Response<Map>().success(map);
    }

    /**
     * 代理商操作广告主账户进行资金的转入转出
     * @param accountInvoice
     * @param user
     * @return
     */
    @RequestMapping(value = "tradeFlow", method = RequestMethod.POST)
    @ResponseBody
    public Response tradeFlow(@RequestBody AccountInvoice accountInvoice,@UserAttribute UserModel user) {

        if(user.getUserRole()!= UserRoleType.AGENCY){
            return new Response().failure("该账户不是代理商用户，不能进行该操作！");
        }

        accountInvoice.setAgencyAccount(user.getUid());

        //查询当前广告主的当前余额(包括现金和虚拟金)
        GdtFinanceBalanceTableResult  result= financeService.listBalance(accountInvoice.getUid());
        List<GdtFinanceBalance> list = result.getData().getList();
        int crruteBalance = 0;
        for (GdtFinanceBalance item : list) {
            crruteBalance = crruteBalance + item.getBalance();
        }

        //设置该广告主在广点通余额到交易流水中
        accountInvoice.setCurrentBalance(crruteBalance);
        //转入或者转出广告主账户
        agencyService.transferOrRecoverToAdvertiser(accountInvoice,user);

        //更新广告主实时账户信息
        result= financeService.listBalance(accountInvoice.getUid());

        list = result.getData().getList();
        if(list!=null&&list.size()>0){
            financeService.updateFundsRealTime(accountInvoice.getUid(),list);
        }
        long sum = 0,sumCost = 0;
        for (GdtFinanceBalance ge : list) {
            if (ge.getFinanceType() == FinanceType.CASH) {
                sum = sum+ge.getBalance();
            } else if (ge.getFinanceType() == FinanceType.VIRTUAL) {
                sum = sum+ge.getBalance();
            }
            sumCost = sumCost+ge.getDailyCost();
        }
        Map map = new HashMap(3);
        map.put("balance",sum/100.0/user.getRate());
        map.put("daily_cost",sumCost/100.0/user.getRate());
        map.put("uid",accountInvoice.getUid());
        return new Response<Map>().success(map);

    }

    /**
     * 判断用户权限
     * @param user
     */
    private void booleanUser(UserInfo user){
        if(!user.getUserRole().equals(UserRoleType.ADMIN)){
            throw new FalconDspServerException("当前用户没有访问该页面的权限！");
        }
    }
}
