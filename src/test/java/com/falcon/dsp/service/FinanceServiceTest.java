package com.falcon.dsp.service;

import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.interceptor.PageParameter;
import com.falcon.dsp.jdbc.model.FinanceFlowModel;
import com.falcon.dsp.jdbc.model.FinanceFlowParam;
import com.falcon.dsp.jdbc.model.TableData;
import com.falcon.dsp.rest.common.TimeRange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhouchunhui
 * @from 2016-04-19
 * @since V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class FinanceServiceTest {
    @Autowired
    private FinanceService financeService;

    @Test
    public void testAccountInvoiceList(){
        Map map  = new HashMap();
        map.put("startDate","2016-04-19");
        map.put("endDate","2016-04-19");
        map.put("accountType","VIRTUAL");
        TimeRange tr = new TimeRange();
        tr.setStartDate(DateUtils.stringtoDate("2016-04-19",DateUtils.LONG_DATE_FORMAT));
        tr.setEndDate(DateUtils.stringtoDate("2016-04-19",DateUtils.LONG_DATE_FORMAT));
        PageParameter page = new PageParameter();
        page.setCurrentPage(1);
        page.setPageSize(10);
        map.put("agencyId", 1);
        TableData<Map> data = financeService.getAccountInvoiceList(map, page);

        System.out.println("sds");
    }

    @Test
    public void testgetAdminFinanceFlow(){
        FinanceFlowParam f = new FinanceFlowParam();
        f.setStartDate("2016-04-19");
        f.setEndDate("2016-05-30");
        f.setAccountType("CASH");
        f.setStart(0);
        f.setLength(10);

        TableData<FinanceFlowModel> data = financeService.getAdminFinanceFlow(f);
    }
}
