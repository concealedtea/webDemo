package com.falcon.dsp.service;

import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.enumration.DateType;
import com.falcon.dsp.enumration.UserRoleType;
import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.jdbc.entity.Campaign;
import com.falcon.dsp.jdbc.entity.Report;
import com.falcon.dsp.jdbc.model.UserModel;
import com.falcon.dsp.param.ReportParam;
import com.falcon.dsp.rest.GdtRestHandler;
import com.falcon.dsp.rest.campaign.request.GdtCampaign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CampaignServiceTest {

    @Autowired
    private GdtRestHandler gdtRestHandler;

    @Resource(name = "sqlHandler")
    private SqlSessionHandler sqlSessionHandler;

    @Resource(name = "reportSqlHandler")
    private SqlSessionHandler reportSqlSessionHandler;

    @Autowired
    private ReportService reportService;

    @Autowired
    private CampaignService campaignService;

    @Test
    public void test() {
        Campaign campaign = new Campaign();
        campaign.setCname("zhang test campaign 0");
        campaign.setUid(51967);
        int result = sqlSessionHandler.selectOne("campaignMapper.campaign:checkname", campaign);
        System.out.println("result count" + result + "");
    }

    @Test
    public void test1() {

        Map<String, Object> params = new HashMap<>();
        params.put("uid", 1164220);
        params.put("cid", 1107552);
        params.put("current", DateUtils.stringtoDate("2016-04-02", "yyyy-MM-dd"));
        Report result = reportSqlSessionHandler.selectOne("rptCampaignDaily.rpt:campaign:daily:select:day", params);
        System.out.printf(result.toString());
    }

    @Test
    public void test2() {
        Map<String, Object> params = new HashMap<>();
        params.put("uid", 1164220);
        params.put("cid", 1107552);
        params.put("settledTime", DateUtils.stringtoDate("2016-04-03", "yyyy-MM-dd"));
        List<Report> result = reportSqlSessionHandler.selectList("rptCampaignHourly.rpt:campaign:hourly:select:day", params);
        System.out.printf(result.toString());
    }

    @Test
    public void test3(){
        UserModel user = new UserModel();
        user.setId(555555564);
        user.setUserName("report");
        user.setUserRole(UserRoleType.ADVERTISER);
        user.setUid(146907);
        user.setRate(1.0);
        ReportParam reportParam = new ReportParam();
        reportParam.setCampaignId(1107552);
        reportParam.setUid(1164220);
        reportParam.setDateType(DateType.LAST_7.getValue());
        reportService.campaignDailyReport(reportParam, user);
    }

    @Test
    public void test4(){
        int uid = 51967;
        int cid = 184310;
        GdtCampaign gc = campaignService.getGdtCampaign(uid,cid);
        System.out.printf(gc.toString());
    }

    @Test
    public void test5(){
        int uid = 51967;
        int cid = 184310;
        List<GdtCampaign> list = campaignService.getGdtCampaignList(uid,false);

    }
}
