package com.falcon.dsp.service;

import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.enumration.DateType;
import com.falcon.dsp.enumration.UserRoleType;
import com.falcon.dsp.jdbc.entity.Report;
import com.falcon.dsp.jdbc.model.DateTableParam;
import com.falcon.dsp.jdbc.model.IndexModel;
import com.falcon.dsp.jdbc.model.UserModel;
import com.falcon.dsp.param.ReportParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Zhouchunhui
 * @from 2016-04-20
 * @since V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ReportServiceTest {
    @Autowired
    private ReportService reportService;

    @Test
    public void testIndexReport() {
        int dateType = 2;
        UserModel user = new UserModel();
        user.setId(555555564);
        user.setUserName("report");
        user.setUserRole(UserRoleType.ADVERTISER);
        user.setUid(146907);
        user.setRate(1.0);
        boolean isHour = DateType.forValue(dateType) == DateType.TODAY || DateType.forValue(dateType) == DateType.YESTERDAY ? true : false;

        List<Report> userReport = reportService.userReport(dateType, user);
        List<Report> summaryReport = reportService.userSummaryReport(dateType, user);
        List<Report> genderReport = reportService.genderReport(dateType, user);
        List<Report> ageReport = reportService.ageReport(dateType, user);
        List<Report> areaReport = reportService.areaReport(dateType, user);

        IndexModel indexModel = new IndexModel();

        indexModel.init(summaryReport.get(0),userReport, genderReport, areaReport, ageReport, isHour);
        System.out.println(indexModel);
    }

    //测试获取数据报告报表
    @Test
    public void testGetRptCampaignList() {
        DateTableParam dp = new DateTableParam();
        //dp.setAgencyId(1);
        dp.setUid(1164220);
        dp.setStartDate("2016-04-15");
        dp.setEndDate("2016-04-27");
        dp.setStart(0);
        dp.setLength(10);
        //dp.setSearchKeyword("");
        reportService.getRptCampaignList(dp);
    }

    @Test
    public void test1() {
        UserModel user = new UserModel();
        user.setId(555555564);
        user.setUserName("report");
        user.setUserRole(UserRoleType.ADVERTISER);
        user.setUid(146907);
        user.setRate(1.0);
        ReportParam reportParam = new ReportParam();
        reportParam.setCampaignId(1230488);
        reportParam.setStart(DateUtils.stringtoDate("2016-04-01", "yyyy-MM-dd"));
        reportParam.setEnd(DateUtils.stringtoDate("2016-04-29", "yyyy-MM-dd"));
        List<Report> reports = reportService.campaignStrategy(reportParam, user);
        System.out.printf(reports.toString());
    }

}
