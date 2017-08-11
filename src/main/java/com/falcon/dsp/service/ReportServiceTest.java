package com.falcon.dsp.service;

import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.jdbc.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.falcon.dsp.jdbc.model.PhoneModel;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thatq on 8/10/2017.
 */
@Service
public class ReportServiceTest {

    @Resource(name = "reportSqlHandlerTest")
    private SqlSessionHandler reportSqlSessionHandler;


    public List<PhoneModel> phoneTotals(PhoneModel phone) {
        Map<String, Object> param = new HashMap();
        param.put("phoneName", phone.getName());
        param.put("count", phone.getCount());
        List<PhoneModel> result = reportSqlSessionHandler.selectList("rptTest.rpt:test:daily:select", param);
        return result;
    }
}
