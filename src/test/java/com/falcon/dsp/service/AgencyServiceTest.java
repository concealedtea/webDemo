package com.falcon.dsp.service;

import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.enumration.FinanceFlowType;
import com.falcon.dsp.enumration.FinanceType;
import com.falcon.dsp.jdbc.entity.AccountInvoice;
import com.falcon.dsp.jdbc.entity.Agency;
import com.falcon.dsp.jdbc.entity.Response;
import com.falcon.dsp.jdbc.model.AgencyModel;
import com.falcon.dsp.jdbc.model.TableData;
import com.falcon.dsp.jdbc.model.TableParam;
import com.falcon.dsp.param.AdvertiserParam;
import com.falcon.dsp.rest.agency.request.AccountInvoiceParam;
import com.falcon.dsp.rest.agency.response.GdtAgency;
import com.falcon.dsp.rest.agency.response.GdtAgencyInvoiceTypeTable;
import com.falcon.dsp.rest.common.TimeRange;
import com.falcon.dsp.rest.finance.response.GdtFinanceBalance;
import com.falcon.dsp.rest.finance.response.GdtFinanceBalanceTableResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zhouchunhui
 * @from 2016-04-12
 * @since V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AgencyServiceTest {
    @Autowired
    private AgencyService agencyService;
    @Autowired
    AdvertiserService advertiserService;
    @Autowired
    private FinanceService financeService;
    @Test
    public void testGetAgencyList(){

        int uid = 79571;
        List<GdtAgency> list = agencyService.getAgencyList();
        Map map = new HashMap<String,String>();
        map.put("uid",uid);
        GdtFinanceBalanceTableResult sd = financeService.listBalance(uid);
        List<GdtFinanceBalance> gb = sd.getData().getList();

        int id = 1;//代理商id
        Agency age = agencyService.getAgencyInfo(id);

        for(GdtAgency agency:list){
            if(agency.getAccountType().equals(FinanceType.CASH)){
                System.out.println("猎鹰===》》》现金金额："+agency.getBalance()/100+";现金消耗："+agency.getDailyCost()/100);
            }else if(agency.getAccountType().equals(FinanceType.VIRTUAL)){
                System.out.println("猎鹰===》》》虚拟金金额："+agency.getBalance()/100+";虚拟金消耗："+agency.getDailyCost()/100);
            }
        }
        System.out.println("----------------------------------------------------------------------");
        for(GdtFinanceBalance gfb:gb){
            if(gfb.getFinanceType().equals(FinanceType.CASH)){
                System.out.println("广告主===》》》现金金额："+gfb.getBalance()/100+";现金消耗："+gfb.getDailyCost()/100);
            }else if(gfb.getFinanceType().equals(FinanceType.VIRTUAL)){
                System.out.println("广告主===》》》虚拟金金额："+gfb.getBalance()/100+";虚拟金消耗："+gfb.getDailyCost()/100);
            }
        }
        System.out.println("----------------------------------------------------------------------");
        System.out.println("代理商===》》》现金金额："+age.getBalanceCash());
        System.out.println("代理商===》》》虚拟金金额："+age.getBalanceVirtual());

        //

    }

    @Test
    public void testGetAgencyAccountInvoice(){
        AccountInvoiceParam ap = new AccountInvoiceParam();
        ap.setAccountType(FinanceType.CASH);
        ap.setPage(1);

        TimeRange time = new TimeRange();
        String time1 = "2015-01-01";
        String time2 = "2016-04-12";
        time.setStartDate(DateUtils.stringtoDate(time1,DateUtils.LONG_DATE_FORMAT));
        time.setEndDate(DateUtils.stringtoDate(time2,DateUtils.LONG_DATE_FORMAT));
        ap.setTime_range(time);
        GdtAgencyInvoiceTypeTable list = agencyService.getAgencyAccountInvoice();
        System.out.print(list);
    }

    @Test
    public void testlistTable(){
        AdvertiserParam ap = new AdvertiserParam();
        ap.setAgencyId(1);
        ap.setPage(1);
        ap.setPageSize(10);
        //TableData<AdvertiserTableModel> testTableData =  agencyService.listTable(ap);
       // System.out.print(testTableData);
    }

    @Test
    public void testGetAgency(){
        int id = 1;
        Agency ag = agencyService.getAgencyInfo(id);
        System.out.print("");
    }

    @Test
    public void getAgency() {
        Agency agency = agencyService.getAgencyInfo(1);
        System.out.println();
    }


    @Test
    public void testTransferAndRecover(){
        AccountInvoice ai = new AccountInvoice();
        ai.setAccountType(FinanceType.CASH);
        ai.setAgencyAccount(1);
        ai.setAmount(100);
        ai.setBalance(20000);
        ai.setBillNo("123111111111111111");
        ai.setCurrentBalance(210000);
        ai.setTransType(FinanceFlowType.TRANSFER_OUT);
        ai.setUid(51967);
        //agencyService.transferOrRecoverToAdvertiser(ai);
    }

    @Test
    public void testAdvertiserList(){
        int agencyId = 1; //代理商id，需要修改为从session中获取
        int start = 1;
        int page =10;

        AdvertiserParam advertiserParam = new AdvertiserParam();
        advertiserParam.setPage(start / page + 1);
        advertiserParam.setPageSize(page);
        advertiserParam.setAgencyId(agencyId);
        advertiserParam.setSearchKey("56452");


        //先从数据库中查询出代理商下对应的广告商。
        //TableData<AdvertiserTableModel> gdtAdvertiserList = agencyService.listTable(advertiserParam);
    }

    @Test
    public void testGetAdvertiserBalance(){
        int uid = 64047;
        Response response= new Response<Map>();
        GdtFinanceBalanceTableResult  balances= financeService.listBalance(uid);
        if(balances.getRet()!=0){
            response.failure("用户状态已暂停!不能进行操作");
        }

        List<GdtFinanceBalance> listBalance =  balances.getData().getList();
        long balance_cash=0,balance_virtual=0;
        Map map = new HashMap<>();
        map.put("balance_cash",balance_cash);
        map.put("balance_virtual",balance_virtual);
        response.success(map);
        System.out.print("13");
    }

    @Test
    public void testUpdateFundsList(){
        int uid= 59704;
        GdtFinanceBalanceTableResult  result= financeService.listBalance(uid);
        //更新广告主实时账户信息
        List<GdtFinanceBalance> list1 = result.getData().getList();
        if(list1!=null){
            financeService.updateFundsRealTime(uid,list1);
        }

    }

    @Test
    public void TestAgencyAdd(){
        Agency agency = new Agency();
        agency.setName("测试服务商");
        agency.setBalanceCash(1000d);
        agency.setBalanceVirtual(1000d);
        agencyService.addAgency(agency);
        System.out.print(0);
    }

    @Test
    public void getAgencySelectList(){
        List<Agency> i = agencyService.getAgencySelectList();
        System.out.print(i);
    }

    @Test
    public void testGetAgencyPageList(){
        TableParam tp = new TableParam();
        tp.setSearchKeyword("1");
        tp.setOrdercolumns("uid,id");
        tp.setLength(10);
        tp.setStart(0);
        TableData<AgencyModel> table = agencyService.getAgencyModelList(tp);
        System.out.print(table);
    }
}
