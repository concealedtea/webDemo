package com.falcon.dsp.controller;

import com.falcon.dsp.annotation.UserAttribute;
import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.enumration.DateType;
import com.falcon.dsp.enumration.ReportType;
import com.falcon.dsp.enumration.UserRoleType;
import com.falcon.dsp.exception.FalconDspServerException;
import com.falcon.dsp.handler.RoleAndView;
import com.falcon.dsp.jdbc.entity.Advertiser;
import com.falcon.dsp.jdbc.entity.Campaign;
import com.falcon.dsp.jdbc.entity.Report;
import com.falcon.dsp.jdbc.entity.Response;
import com.falcon.dsp.jdbc.model.*;
import com.falcon.dsp.param.ReportParam;
import com.falcon.dsp.rest.campaign.params.CampaignParam;
import com.falcon.dsp.service.AdvertiserService;
import com.falcon.dsp.service.CampaignService;
import com.falcon.dsp.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author dongbin.yu
 * @from 2016-04-01
 * @since V1.0
 */
@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    AdvertiserService advertiserService;
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public RoleAndView view(Model model, @UserAttribute UserModel user) {

        return new RoleAndView("ReportList", user);
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public RoleAndView userReport(Model model,@RequestParam(value = "uid", required = false) Integer uid, @UserAttribute UserModel user) {
        Advertiser advertiser = advertiserService.selectOne(uid);
        model.addAttribute("uid",advertiser.getUid());
        model.addAttribute("uname",advertiser.getUname());
        model.addAttribute("title","广告主报表");
        return new RoleAndView("AdvertiserReport", user);
    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public RoleAndView detail(
            Model model,
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @UserAttribute UserModel user
    ) {

        model.addAttribute("campaign_id", campaignId);
        try {
            Campaign campaign = campaignService.read(new CampaignParam(null, campaignId, null,null),user.getRate());
            if (campaign != null) {
                model.addAttribute("campaign_name", campaign.getCname());
                model.addAttribute("start", DateUtils.dateToString(campaign.getStart(), "yyyy/MM/dd"));
                model.addAttribute("end", DateUtils.dateToString(campaign.getEnd(), "yyyy/MM/dd"));
            }
            Report report = reportService.campaignTotal(campaignId, user);
            model.addAttribute("cost", report == null ? 0 : report.getCost());
        } catch (Exception ex) {
            throw new FalconDspServerException(ex.getMessage(), ex);
        }

        return new RoleAndView("ReportDetail", user);
    }

    @RequestMapping(value = "campaign/single", method = RequestMethod.GET)
    @ResponseBody
    public Response campaignSingle(
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @RequestParam(value = "date_type", required = false) Integer dateType,
            @RequestParam(value = "start", required = false) Date start,
            @RequestParam(value = "end", required = false) Date end,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "page_size", required = false) Integer pageSize,
            @UserAttribute UserModel user) {
        ReportParam reportParam = new ReportParam(start, end, dateType, campaignId, null, page, pageSize);
       // List<Report> dailyList = reportService.campaignDailyReport(reportParam, user);
        List<Report> hourlyList = reportService.campaignHourlyReport(reportParam, user);
        if (hourlyList.size() < 1) {
            return new Response().failure("无报告数据");
        }
        ReportModel result = new ReportModel().initData(hourlyList);
        return new Response().success(result);
    }

    @RequestMapping(value = "strategy/single", method = RequestMethod.GET)
    @ResponseBody
    public Response strategySingle(
            @RequestParam(value = "strategy_id", required = true) Integer strategyId,
            @RequestParam(value = "date_type", required = false) Integer dateType,
            @RequestParam(value = "start", required = false) Date start,
            @RequestParam(value = "end", required = false) Date end,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "page_size", required = false) Integer pageSize,
            @UserAttribute UserModel user
    ) {
        ReportParam reportParam = new ReportParam(start, end, dateType, null, strategyId, page, pageSize);
       // List<Report> dailyList = reportService.strategyDailyReport(reportParam,user.getRate());
        List<Report> hourlyList = reportService.strategyHourlyReport(reportParam,user.getRate());
        if (hourlyList.size() < 1) {
            return new Response().failure("无报告数据");
        }
        ReportModel result = new ReportModel().initData(hourlyList);
        return new Response().success(result);
    }

    @RequestMapping(value = "campaign/dailyList", method = RequestMethod.POST)
    @ResponseBody
    public TableData<ReportTableModel> getRptCampaignList(@RequestBody DateTableParam tableParam, @UserAttribute UserModel user) {
        //查询条件 startDate,endDate, searchKeyword, (订单编号或者订单名称)
        if (user.getUserRole() == UserRoleType.AGENCY) {
            tableParam.setAgencyId(user.getUid());
        } else {
            tableParam.setUid(user.getUid());
        }
        // 设置markup值
        tableParam.setRate(user.getRate());
        TableData<ReportTableModel> data = reportService.getRptCampaignList(tableParam);
        return data;
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResponseBody
    public Response indexReport(@RequestParam(value = "date_type", required = true) Integer dateType,@RequestParam(value = "uid", required = false) Integer uid,@UserAttribute UserModel user, Model modelView) {
        boolean isHour = DateType.forValue(dateType) == DateType.TODAY || DateType.forValue(dateType) == DateType.YESTERDAY ? true : false;
        UserModel model = new UserModel();
        if(uid!=null&&uid!=0){
            model.setUid(uid);
        }else{
           model.setUid(user.getUid());
        }
        model.setRate(user.getRate());

        List<Report> summaryResult = reportService.userSummaryReport(dateType, model);
        List<Report> userResult = reportService.userReport(dateType, model);
        List<Report> genderResult = reportService.genderReport(dateType, model);
        List<Report> ageResult = reportService.ageReport(dateType, model);
        List<Report> areaResult = reportService.areaReport(dateType, model);

        if (summaryResult == null || summaryResult.isEmpty()) {
            return new Response().failure("无统计报告数据");
        }
        IndexModel indexModel = new IndexModel();
        indexModel.init(summaryResult.get(0), userResult, genderResult, areaResult, ageResult, isHour);

        return new Response().success(indexModel);
    }

    @RequestMapping(value = "campaign/hourly", method = RequestMethod.GET)
    @ResponseBody
    public Response getRptCampaignHourly(
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end,
            @UserAttribute UserModel user) {
        ReportParam reportParam = new ReportParam();
        reportParam.setCampaignId(campaignId);
        reportParam.setCurrent(DateUtils.stringtoDate(start,"yyyy-MM-dd"));
        List<Report> report = reportService.campaignHourlyReport(reportParam, user);
        Report total = reportService.campaignHourlyTotal(reportParam, user);
        if (total != null) {
            //如果存在数据的话，将查询到的数据组成ReportDetailModel格式传到前台
            ReportDetailModel reportModel = new ReportDetailModel();
            reportModel.init(total, report, ReportType.HOURLY);
            return new Response().success(reportModel);
        }
        return new Response().failure("无报告数据");
    }

    @RequestMapping(value = "campaign/daily", method = RequestMethod.GET)
    @ResponseBody
    public Response getRptCampaignDaily(
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end,@UserAttribute UserModel user) {

        ReportParam reportParam = new ReportParam();
        reportParam.setCampaignId(campaignId);
        reportParam.setStart(DateUtils.stringtoDate(start, "yyyy-MM-dd"));
        reportParam.setEnd(DateUtils.stringtoDate(end, "yyyy-MM-dd"));
        Report total = reportService.campaignTotal(reportParam,user.getRate());
        List<Report> report = reportService.getRptCampaignDailyListByCid(reportParam,user.getRate());
        if (total != null) {
            //如果存在数据的话，将查询到的数据组成ReportDetailModel格式传到前台
            ReportDetailModel reportModel = new ReportDetailModel();
            reportModel.init(total, report, ReportType.DAILY);
            return new Response().success(reportModel);
        }
        return new Response().failure("无报告数据");
    }

    @RequestMapping(value = "campaign/strategy", method = RequestMethod.GET)
    @ResponseBody
    public Response getRptCampaignStrategy(
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end,
            @UserAttribute UserModel user) {
        ReportParam reportParam = new ReportParam();
        reportParam.setStart(DateUtils.stringtoDate(start, "yyyy-MM-dd"));
        reportParam.setEnd(DateUtils.stringtoDate(end, "yyyy-MM-dd"));
        reportParam.setCampaignId(campaignId);
        List<Report> list = reportService.campaignStrategy(reportParam, user);
        if (list == null || list.size() == 0) {
            return new Response().failure("无报告数据");
        }
        Report report = reportService.campaignStrategyTotal(reportParam, user);
        if (report == null) {
            return new Response().failure("无报告数据");
        }
        ReportDetailModel model = new ReportDetailModel();
        model.init(report, list, ReportType.STRATEGY);
        return new Response().success(model);
    }

    @RequestMapping(value = "campaign/geo", method = RequestMethod.GET)
    @ResponseBody
    public Response getRptCampaignGeo(
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end,
            @UserAttribute UserModel user) {
        ReportParam reportParam = new ReportParam();
        reportParam.setStart(DateUtils.stringtoDate(start, "yyyy-MM-dd"));
        reportParam.setEnd(DateUtils.stringtoDate(end, "yyyy-MM-dd"));
        reportParam.setCampaignId(campaignId);

        List<Report> geoTotalReport = reportService.getRptCampaignGeoTotal(reportParam, user);
        List<Report> geoDetailReport = reportService.getRptCampaignGeoDetail(reportParam, user);

        if (geoTotalReport == null || geoDetailReport.isEmpty()) {
            return new Response().failure("无地域相关的总体数据报告");
        }

        if (geoDetailReport == null || geoDetailReport.isEmpty()){
            return new Response().failure("无地域相关的详细数据报告");
        }

        ReportDetailModel reportDetailModel = new ReportDetailModel();
        reportDetailModel.init(geoTotalReport.get(0), geoDetailReport, ReportType.GEO);
        return new Response().success(reportDetailModel);
    }

    @RequestMapping(value = "campaign/age", method = RequestMethod.GET)
    @ResponseBody
    public Response getRptCampaignAge(
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end,
            @UserAttribute UserModel user) {
        ReportParam reportParam = new ReportParam();
        reportParam.setStart(DateUtils.stringtoDate(start, "yyyy-MM-dd"));
        reportParam.setEnd(DateUtils.stringtoDate(end, "yyyy-MM-dd"));
        reportParam.setCampaignId(campaignId);

        List<Report> ageTotalReport = reportService.getRptCampaignAgeTotal(reportParam, user);
        List<Report> ageDetailReport = reportService.getRptCampaignAgeDetail(reportParam, user);

        if (ageTotalReport == null || ageTotalReport.isEmpty()){
            return new Response().failure("无年龄相关的总体数据报告");
        }
        if (ageDetailReport == null || ageDetailReport.isEmpty()) {
            return new Response().failure("无年龄相关的详细数据报告");
        }

        ReportDetailModel reportDetailModel = new ReportDetailModel();
        reportDetailModel.init(ageTotalReport.get(0), ageDetailReport, ReportType.AGE);
        return new Response().success(reportDetailModel);
    }

    @RequestMapping(value = "campaign/gender", method = RequestMethod.GET)
    @ResponseBody
    public Response getRptCampaignGender(
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end,
            @UserAttribute UserModel user) {
        ReportParam reportParam = new ReportParam();
        reportParam.setStart(DateUtils.stringtoDate(start, "yyyy-MM-dd"));
        reportParam.setEnd(DateUtils.stringtoDate(end, "yyyy-MM-dd"));
        reportParam.setCampaignId(campaignId);

        List<Report> genderTotalReport = reportService.getRptCampaignGenderTotal(reportParam, user);
        List<Report> genderDetailReport = reportService.getRptCampaignGenderDetail(reportParam, user);

        if (genderTotalReport == null || genderTotalReport.isEmpty()) {
            return new Response().failure("无性别相关的总体数据报告");
        }

        if (genderDetailReport == null || genderDetailReport.isEmpty()) {
            return new Response().failure("无性别相关的详细数据报告");
        }

        ReportDetailModel reportDetailModel = new ReportDetailModel();
        reportDetailModel.init(genderTotalReport.get(0), genderDetailReport, ReportType.GENDER);
        return new Response().success(reportDetailModel);
    }

    /***
     * 在详情数据报告页面 导出Excel 时报数据
     *
     * @param campaignId 订单Id
     * @param start      开始时间
     * @param end        结束时间
     * @param response
     * @return ExcelReport
     */
    @RequestMapping(value = "campaign/exportExcel/hourly", method = RequestMethod.GET)
    public void exportExcelHourlyReport(
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end,
            HttpServletResponse response,
            @UserAttribute UserModel user) {

        ReportParam reportParam = new ReportParam();
        reportParam.setCampaignId(campaignId);
        reportParam.setCurrent(DateUtils.stringtoDate(start, "yyyy-MM-dd"));
        List<Report> reportList = reportService.campaignHourlyReport(reportParam, user);
        Report total = reportService.campaignHourlyTotal(reportParam, user);

        //按照hour倒序排列
        Collections.sort(reportList, new Comparator<Report>() {
            @Override
            public int compare(Report o1, Report o2) {
                return o2.getHour() - o1.getHour();
            }
        });

        Campaign campaign = campaignService.read(new CampaignParam(null, campaignId, null,null),user.getRate());
        try {
            if (campaign != null ) {
                // 将查询到的数据导出Excel，如果数据信息不存在，Excel中只显示订单信息，数据详细信息显示暂无数据
                reportService.exportExcelReport(campaign, reportParam, total, reportList, ReportType.HOURLY, response);
            }
        } catch (Exception ex) {
            throw new FalconDspServerException(ex.getMessage());
        }
    }

    /***
     * 在详情数据报告页面 导出Excel 日报数据
     *
     * @param campaignId 订单Id
     * @param start      开始时间
     * @param end        结束时间
     * @param response
     * @return ExcelReport
     */
    @RequestMapping(value = "campaign/exportExcel/daily", method = RequestMethod.GET)
    public void exportExcelDailyReport(
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end,
            HttpServletResponse response,@UserAttribute UserModel user) {

        ReportParam reportParam = new ReportParam();
        reportParam.setCampaignId(campaignId);
        reportParam.setStart(DateUtils.stringtoDate(start, "yyyy-MM-dd"));
        reportParam.setEnd(DateUtils.stringtoDate(end, "yyyy-MM-dd"));
        Report total = reportService.campaignTotal(reportParam,user.getRate());
        List<Report> reportList = reportService.getRptCampaignDailyListByCid(reportParam,user.getRate());

        //按照日期倒序排列
        Collections.sort(reportList, new Comparator<Report>() {
            @Override
            public int compare(Report o1, Report o2) {
                return Integer.parseInt(String.valueOf(o2.getSettledTime().getTime() - o1.getSettledTime().getTime()));
            }
        });

        Campaign campaign = campaignService.read(new CampaignParam(null, campaignId, null,null),user.getRate());
        try {
            if (campaign != null ) {
                // 将查询到的数据导出Excel，如果数据信息不存在，Excel中只显示订单信息，数据详细信息显示暂无数据
                reportService.exportExcelReport(campaign, reportParam, total, reportList, ReportType.DAILY, response);
            }
        } catch (Exception ex) {
            throw new FalconDspServerException(ex.getMessage());
        }
    }

    /***
     * 在详情数据报告页面 导出Excel 策略数据
     *
     * @param campaignId 订单Id
     * @param start      开始时间
     * @param end        结束时间
     * @param response
     * @return ExcelReport
     */
    @RequestMapping(value = "campaign/exportExcel/strategy", method = RequestMethod.GET)
    public void exportExcelStrategyReport(
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end,
            HttpServletResponse response,
            @UserAttribute UserModel user) {
        ReportParam reportParam = new ReportParam();
        reportParam.setStart(DateUtils.stringtoDate(start, "yyyy-MM-dd"));
        reportParam.setEnd(DateUtils.stringtoDate(end, "yyyy-MM-dd"));
        reportParam.setCampaignId(campaignId);
        List<Report> reportList = reportService.campaignStrategy(reportParam, user);
        Report total = reportService.campaignStrategyTotal(reportParam, user);

        //按照印象数倒序排列
        Collections.sort(reportList, new Comparator<Report>() {
            @Override
            public int compare(Report o1, Report o2) {
                return Integer.parseInt(String.valueOf(o2.getViewCount() - o1.getViewCount()));
            }
        });

        Campaign campaign = campaignService.read(new CampaignParam(null, campaignId, null,null),user.getRate());
        try {
            if (campaign != null ) {
                // 将查询到的数据导出Excel，如果数据信息不存在，Excel中只显示订单信息，数据详细信息显示暂无数据
                reportService.exportExcelReport(campaign, reportParam, total, reportList, ReportType.STRATEGY, response);
            }
        } catch (Exception ex) {
            throw new FalconDspServerException(ex.getMessage());
        }
    }

    /**
     * 在详情数据报告页面 导出Excel 地域数据
     *
     * @param campaignId 订单Id
     * @param start      开始时间
     * @param end        结束时间
     * @param response
     * @return ExcelReport
     */
    @RequestMapping(value = "campaign/exportExcel/geo", method = RequestMethod.GET)
    public void exportExcelGEOReport(
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end,
            HttpServletResponse response,
            @UserAttribute UserModel user) {
        ReportParam reportParam = new ReportParam();
        reportParam.setStart(DateUtils.stringtoDate(start, "yyyy-MM-dd"));
        reportParam.setEnd(DateUtils.stringtoDate(end, "yyyy-MM-dd"));
        reportParam.setCampaignId(campaignId);

        List<Report> total = reportService.getRptCampaignGeoTotal(reportParam, user);
        List<Report> reportList = reportService.getRptCampaignGeoDetail(reportParam, user);

        //按照印象数倒序排列
        Collections.sort(reportList, new Comparator<Report>() {
            @Override
            public int compare(Report o1, Report o2) {
                return Integer.parseInt(String.valueOf(o2.getViewCount() - o1.getViewCount()));
            }
        });

        Campaign campaign = campaignService.read(new CampaignParam(null, campaignId, null,null),user.getRate());
        try {
            if (campaign != null ) {
                // 将查询到的数据导出Excel，如果数据信息不存在，Excel中只显示订单信息，数据详细信息显示暂无数据
                reportService.exportExcelReport(campaign, reportParam, total.get(0), reportList, ReportType.GEO, response);
            }
        } catch (Exception ex) {
            throw new FalconDspServerException(ex.getMessage());
        }
    }

    /***
     * 在详情数据报告页面 导出Excel 年龄数据
     *
     * @param campaignId 订单Id
     * @param start      开始时间
     * @param end        结束时间
     * @param response
     * @return ExcelReport
     */
    @RequestMapping(value = "campaign/exportExcel/age", method = RequestMethod.GET)
    public void exportExcelAgeReport(
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end,
            HttpServletResponse response,
            @UserAttribute UserModel user) {
        ReportParam reportParam = new ReportParam();
        reportParam.setStart(DateUtils.stringtoDate(start, "yyyy-MM-dd"));
        reportParam.setEnd(DateUtils.stringtoDate(end, "yyyy-MM-dd"));
        reportParam.setCampaignId(campaignId);

        List<Report> total = reportService.getRptCampaignAgeTotal(reportParam, user);
        List<Report> reportList = reportService.getRptCampaignAgeDetail(reportParam, user);

        //按照印象数倒序排列
        Collections.sort(reportList, new Comparator<Report>() {
            @Override
            public int compare(Report o1, Report o2) {
                return Integer.parseInt(String.valueOf(o2.getViewCount() - o1.getViewCount()));
            }
        });

        Campaign campaign = campaignService.read(new CampaignParam(null, campaignId, null,null),user.getRate());
        try {
            if (campaign != null ) {
                // 将查询到的数据导出Excel，如果数据信息不存在，Excel中只显示订单信息，数据详细信息显示暂无数据
                reportService.exportExcelReport(campaign, reportParam, total.get(0), reportList, ReportType.AGE, response);
            }
        } catch (Exception ex) {
            throw new FalconDspServerException(ex.getMessage());
        }
    }

    /***
     * 在详情数据报告页面 导出Excel 性别数据
     *
     * @param campaignId 订单Id
     * @param start      开始时间
     * @param end        结束时间
     * @param response
     * @return ExcelReport
     */
    @RequestMapping(value = "campaign/exportExcel/gender", method = RequestMethod.GET)
    public void getRptCampaignGender(
            @RequestParam(value = "campaign_id", required = true) Integer campaignId,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end,
            HttpServletResponse response,
            @UserAttribute UserModel user) {

        ReportParam reportParam = new ReportParam();
        reportParam.setStart(DateUtils.stringtoDate(start, "yyyy-MM-dd"));
        reportParam.setEnd(DateUtils.stringtoDate(end, "yyyy-MM-dd"));
        reportParam.setCampaignId(campaignId);

        List<Report> total = reportService.getRptCampaignGenderTotal(reportParam, user);
        List<Report> reportList = reportService.getRptCampaignGenderDetail(reportParam, user);

        //按照印象数倒序排列
        Collections.sort(reportList, new Comparator<Report>() {
            @Override
            public int compare(Report o1, Report o2) {
                return Integer.parseInt(String.valueOf(o2.getViewCount() - o1.getViewCount()));
            }
        });

        Campaign campaign = campaignService.read(new CampaignParam(null, campaignId, null,null),user.getRate());
        try {
            if (campaign != null ) {
                // 将查询到的数据导出Excel，如果数据信息不存在，Excel中只显示订单信息，数据详细信息显示暂无数据
                reportService.exportExcelReport(campaign, reportParam, total.get(0), reportList, ReportType.GENDER, response);
            }
        } catch (Exception ex) {
            throw new FalconDspServerException(ex.getMessage());
        }
    }

}
