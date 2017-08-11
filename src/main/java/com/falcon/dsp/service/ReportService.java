package com.falcon.dsp.service;

import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.enumration.DateType;
import com.falcon.dsp.enumration.ReportType;
import com.falcon.dsp.handler.SqlSessionHandler;
import com.falcon.dsp.interceptor.PageParameter;
import com.falcon.dsp.jdbc.entity.Campaign;
import com.falcon.dsp.jdbc.entity.Report;
import com.falcon.dsp.jdbc.model.DateTableParam;
import com.falcon.dsp.jdbc.model.ReportTableModel;
import com.falcon.dsp.jdbc.model.TableData;
import com.falcon.dsp.jdbc.model.UserModel;
import com.falcon.dsp.param.ReportParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by falcon-zhangxg on 2016/4/13.
 */
@Service
public class ReportService {

    @Resource(name = "reportSqlHandler")
    private SqlSessionHandler reportSqlSessionHandler;

    @Resource(name = "sqlHandler")
    private SqlSessionHandler sqlSessionTemplate;

    @Autowired
    private StragegyService stragegyService;

    public Report campaignTotal(final Integer campaignId, UserModel user) {
        Map<String, Object> param = new HashMap();
        param.put("cid", campaignId);
        param.put("settled_time", DateUtils.today("yyyy-MM-dd 00:00:00"));
        // 设置markup值
        param.put("markup", user.getRate());
        List<Report> result = reportSqlSessionHandler.selectList("rptCampaign.rpt:campaign:daily:select:total", param);
        return result.get(0);
    }

    public List<Report> campaignDailyReport(ReportParam reportParam, UserModel user) {
        Map<String, Object> map = reportParam.toMap();
        // 设置markup值
        map.put("markup", user.getRate());

        List<Report> result = null;
        if (DateType.forValue(reportParam.getDateType()) == DateType.TODAY) {
            result = reportSqlSessionHandler.selectList("rptCampaign.rpt:campaign:hourly:select:today", map);
        } else {
            result = reportSqlSessionHandler.selectList("rptCampaign.rpt:campaign:daily:select", map);
        }
        return result;
    }

    public List<Report> campaignHourlyReport(ReportParam reportParam, UserModel user) {
        Map<String, Object> map = reportParam.toMap();
        // 设置markup值
        map.put("markup",user.getRate());
        return reportSqlSessionHandler.selectList("rptCampaign.rpt:campaign:hourly:select", map);
    }

    public Report campaignHourlyTotal(ReportParam reportParam, UserModel user) {
        Map<String, Object> map = reportParam.toMap();
        // 设置markup值
        map.put("markup",user.getRate());
        return reportSqlSessionHandler.selectOne("rptCampaign.rpt:campaign:hourly:select:total", map);
    }

    public List<Report> campaignStrategy(ReportParam reportParam, UserModel user) {
        List<Report> result = new ArrayList<>();
        Map<Integer, String> names = stragegyService.nameList(reportParam.getCampaignId());
        if (names != null && names.size() > 0) {
            Map<String, Object> params = new HashMap();
            params.put("endDate", reportParam.getEnd());
            params.put("startDate", reportParam.getStart());
            Date startDate = reportParam.getStart();
            Date endDate = reportParam.getEnd();
            params = resizeMap(params, startDate, endDate);
            params.put("sids", StringUtils.join(names.keySet(), ","));
            // 设置markup值
            params.put("markup", user.getRate());

            result = reportSqlSessionHandler.selectList("rptStrategy.rpt:campaign:strategy:daily:list", params);

            if (result != null && result.size() > 0) {
                for (Report item : result) {

                    if (names.containsKey(item.getSid())) {
                        item.setName(names.get(item.getSid()));
                    } else {
                        item.setName("unknow");
                    }
                }
            }
        }
        return result;
    }

    public Report campaignStrategyTotal(ReportParam reportParam, UserModel user) {
        Report result = new Report();
        Map<Integer, String> names = stragegyService.nameList(reportParam.getCampaignId());
        if (names != null && names.size() > 0) {
            Map<String, Object> params = new HashMap();
            params.put("endDate", reportParam.getEnd());
            params.put("startDate", reportParam.getStart());
            Date startDate = reportParam.getStart();
            Date endDate = reportParam.getEnd();
            params = resizeMap(params,startDate,endDate);
            params.put("sids", StringUtils.join(names.keySet(), ","));
            // 设置markup值
            params.put("markup", user.getRate());

            result = reportSqlSessionHandler.selectOne("rptStrategy.rpt:campaign:strategy:daily:total", params);
        }
        return result;
    }

    public List<Report> strategyDailyReport(ReportParam reportParam,double rate) {
        List<Report> result = null;
        Map map =reportParam.toMap();
        map.put("markup",rate);
        if (DateType.forValue(reportParam.getDateType()) == DateType.TODAY) {
            result = reportSqlSessionHandler.selectList("rptStrategy.rpt:strategy:daily:select:today", map);
        } else {
            result = reportSqlSessionHandler.selectList("rptStrategy.rpt:strategy:daily:select", map);
        }
        return result;
    }

    public List<Report> strategyHourlyReport(ReportParam reportParam,double rate) {
        List<Report> result = null;
        Map map = reportParam.toMap();
        map.put("markup",rate);
        result = reportSqlSessionHandler.selectList("rptStrategy.rpt:strategy:hourly:select",map);
        return result;
    }

    public List<Report> userSummaryReport(Integer dateType, UserModel user) {
        List<Report> result = null;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", user.getUid());
        // 设置markup值
        params.put("markup", user.getRate());
        if (dateType != null) {
            if (DateType.forValue(dateType) == DateType.LAST_7) {
                params.put("start", DateUtils.last_7_day("yyyy-MM-dd"));
                params.put("end", DateUtils.today("yyyy-MM-dd"));
                result = reportSqlSessionHandler.selectList("rptUser.rpt:user:daily:summary:select", params);
            }

            if (DateType.forValue(dateType) == DateType.LAST_30) {
                params.put("start", DateUtils.last_30_day("yyyy-MM-dd"));
                params.put("end", DateUtils.today("yyyy-MM-dd"));
                result = reportSqlSessionHandler.selectList("rptUser.rpt:user:daily:summary:select", params);
            }

            if (DateType.forValue(dateType) == DateType.TODAY) {
                params.put("current", DateUtils.today("yyyy-MM-dd"));
                result = reportSqlSessionHandler.selectList("rptUser.rpt:user:hourly:summary:select:total", params);
            }

            if (DateType.forValue(dateType) == DateType.YESTERDAY) {
                params.put("current", DateUtils.yesterday("yyyy-MM-dd"));
                result = reportSqlSessionHandler.selectList("rptUser.rpt:user:hourly:summary:select:total", params);
            }
        }
        return result;
    }

    public List<Report> userReport(Integer dateType, UserModel user) {
        List<Report> result = null;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", user.getUid());
        // 设置markup值
        params.put("markup", user.getRate());
        if (dateType != null) {
            if (DateType.forValue(dateType) == DateType.LAST_7) {
                params.put("start", DateUtils.last_7_day("yyyy-MM-dd"));
                params.put("end", DateUtils.today("yyyy-MM-dd"));
                result = reportSqlSessionHandler.selectList("rptUser.rpt:user:daily:select", params);
            }

            if (DateType.forValue(dateType) == DateType.LAST_30) {
                params.put("start", DateUtils.last_30_day("yyyy-MM-dd"));
                params.put("end", DateUtils.today("yyyy-MM-dd"));
                result = reportSqlSessionHandler.selectList("rptUser.rpt:user:daily:select", params);
            }

            if (DateType.forValue(dateType) == DateType.TODAY) {
                params.put("current", DateUtils.today("yyyy-MM-dd"));
                result = reportSqlSessionHandler.selectList("rptUser.rpt:user:hourly:select", params);
            }

            if (DateType.forValue(dateType) == DateType.YESTERDAY) {
                params.put("current", DateUtils.yesterday("yyyy-MM-dd"));
                result = reportSqlSessionHandler.selectList("rptUser.rpt:user:hourly:select", params);
            }
        }
        return result;
    }

    public List<Report> genderReport(Integer dateType, UserModel user) {
        List<Report> result = null;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", user.getUid());
        // 设置markup值
        params.put("markup", user.getRate());
        if (dateType != null) {
            if (DateType.forValue(dateType) == DateType.LAST_7) {
                params.put("start", DateUtils.last_7_day("yyyy-MM-dd"));
                params.put("end", DateUtils.today("yyyy-MM-dd"));
            }

            if (DateType.forValue(dateType) == DateType.LAST_30) {
                params.put("start", DateUtils.last_30_day("yyyy-MM-dd"));
                params.put("end", DateUtils.today("yyyy-MM-dd"));
            }

            if (DateType.forValue(dateType) == DateType.TODAY) {
                params.put("current", DateUtils.today("yyyy-MM-dd"));
            }

            if (DateType.forValue(dateType) == DateType.YESTERDAY) {
                params.put("current", DateUtils.yesterday("yyyy-MM-dd"));
            }
            result = reportSqlSessionHandler.selectList("rptUser.rpt:user:gender:daily:select", params);
        }
        return result;
    }

    public List<Report> ageReport(Integer dateType, UserModel user) {
        List<Report> result = null;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", user.getUid());
        // 设置markup值
        params.put("markup", user.getRate());
        if (dateType != null) {
            if (DateType.forValue(dateType) == DateType.LAST_7) {
                params.put("start", DateUtils.last_7_day("yyyy-MM-dd"));
                params.put("end", DateUtils.today("yyyy-MM-dd"));
            }

            if (DateType.forValue(dateType) == DateType.LAST_30) {
                params.put("start", DateUtils.last_30_day("yyyy-MM-dd"));
                params.put("end", DateUtils.today("yyyy-MM-dd"));
            }

            if (DateType.forValue(dateType) == DateType.TODAY) {
                params.put("current", DateUtils.today("yyyy-MM-dd"));
            }

            if (DateType.forValue(dateType) == DateType.YESTERDAY) {
                params.put("current", DateUtils.yesterday("yyyy-MM-dd"));
            }
            result = reportSqlSessionHandler.selectList("rptUser.rpt:user:age:daily:select", params);
        }
        return result;
    }

    public List<Report> areaReport(Integer dateType, UserModel user) {
        List<Report> result = null;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", user.getUid());
        // 设置markup值
        params.put("markup", user.getRate());
        if (dateType != null) {
            if (DateType.forValue(dateType) == DateType.LAST_7) {
                params.put("start", DateUtils.last_7_day("yyyy-MM-dd"));
                params.put("end", DateUtils.today("yyyy-MM-dd"));
            }

            if (DateType.forValue(dateType) == DateType.LAST_30) {
                params.put("start", DateUtils.last_30_day("yyyy-MM-dd"));
                params.put("end", DateUtils.today("yyyy-MM-dd"));
            }

            if (DateType.forValue(dateType) == DateType.TODAY) {
                params.put("current", DateUtils.today("yyyy-MM-dd"));
            }

            if (DateType.forValue(dateType) == DateType.YESTERDAY) {
                params.put("current", DateUtils.yesterday("yyyy-MM-dd"));
            }
            result = reportSqlSessionHandler.selectList("rptUser.rpt:user:geo:daily:select", params);
        }
        return result;
    }


    public Report campaignTotal(ReportParam reportParam,double rate) {
        Map<String, Object> param = new HashMap();
        param.put("cid", reportParam.getCampaignId());
        param.put("endDate", reportParam.getEnd());
        param.put("startDate", reportParam.getStart());
        param.put("markup",rate);
        Date startDate = reportParam.getStart();
        Date endDate = reportParam.getEnd();
        param = resizeMap(param,startDate,endDate);
        if(param==null) return null;
        Report result = reportSqlSessionHandler.selectOne("rptCampaign.rpt:campaign:daily:select:total:date", param);
        return result;
    }

    /**
     * 判断时间，放入到map中。
     * @param map
     * @param startDate
     * @param endDate
     * @return
     */
    private Map resizeMap(Map map,Date startDate,Date endDate){
        Date now = DateUtils.stringtoDate(DateUtils.currDay(), DateUtils.LONG_DATE_FORMAT);
        if (startDate.getTime() == now.getTime()) {
            map.put("todayOnly",true);
        }else if (startDate.getTime() < now.getTime() && endDate.getTime() >= now.getTime()) {
            map.put("allHave",true);
        }else if (endDate.getTime() < now.getTime()) {
            map.put("agoOnly",true);
        }else{
            return null;
        }
        return map;
    }

    //获取
    public List<Report> getRptCampaignDailyListByCid(ReportParam reportParam,double rate) {
        Date startDate = reportParam.getStart();
        Date endDate = reportParam.getEnd();
        Date now = DateUtils.stringtoDate(DateUtils.currDay(), DateUtils.LONG_DATE_FORMAT);
        List<Report> result = new ArrayList<>();
        Map paramMap = reportParam.toMap();
        paramMap.put("markup",rate);
        if (startDate.getTime() == now.getTime()) {
            paramMap.put("current", DateUtils.today("yyyy-MM-dd 00:00:00"));
            result = reportSqlSessionHandler.selectList("rptCampaign.rpt:campaign:hourly:select:today",paramMap );
        } else if (startDate.getTime() < now.getTime() && endDate.getTime() >= now.getTime()) {
            Map rp = new HashMap();
            rp.put("current", DateUtils.today("yyyy-MM-dd 00:00:00"));
            rp.put("cid", reportParam.getCampaignId());
            rp.put("markup",rate);
            reportParam.setEnd(DateUtils.stringtoDate(DateUtils.befoDay(), DateUtils.LONG_DATE_FORMAT));
            result = reportSqlSessionHandler.selectList("rptCampaign.rpt:campaign:daily:select", paramMap);
            Report report = reportSqlSessionHandler.selectOne("rptCampaign.rpt:campaign:hourly:select:today", rp);
            if (report != null) {
                result.add(0,report);
            }
        } else if (endDate.getTime() < now.getTime()) {
            result = reportSqlSessionHandler.selectList("rptCampaign.rpt:campaign:daily:select", paramMap);
        }

        return result;
    }

    /**
     * 查询订单日报表的数据
     * @param tableParam
     * @return
     */
    public TableData<ReportTableModel> getRptCampaignList(DateTableParam tableParam) {
        PageParameter page = new PageParameter();
        page.setCurrentPage(tableParam.getCurrentPage());
        page.setPageSize(tableParam.getLength());
        TableData<ReportTableModel> result = new TableData<>();
        Map map = tableParam.toMap();
        List<ReportTableModel> listTable = sqlSessionTemplate.selectList("usingForReportMapper.for:report:campaign：list:page",map,page);
        if(listTable!=null&&listTable.size()>0){
            Date startDate = DateUtils.stringtoDate(tableParam.getStartDate(), DateUtils.LONG_DATE_FORMAT);
            Date endDate = DateUtils.stringtoDate(tableParam.getEndDate(), DateUtils.LONG_DATE_FORMAT);
            map = resizeMap(map,startDate,endDate);
            if(map!=null){
                StringBuffer cids = new StringBuffer();
                for(ReportTableModel rm:listTable){
                    cids.append("'").append(rm.getCid()).append("',");
                }
                cids.deleteCharAt(cids.length()-1);
                map.put("cids",cids.toString());

                List<ReportTableModel> listTableSum = reportSqlSessionHandler.selectList("rptCampaign.report:campaign:daily:select:total:for:this", map);
                for(ReportTableModel rt:listTable){
                    for(ReportTableModel rtm:listTableSum){
                        if(rt.equals(rtm)) break;
                    }
                }

                //按照印象数倒序排列
                /*Collections.sort(listTable, new Comparator<ReportTableModel>() {
                    @Override
                    public int compare(ReportTableModel o1, ReportTableModel o2) {
                        return Integer.parseInt(o2.getViewCount().replace(",","")) - Integer.parseInt(o1.getViewCount().replace(",",""));
                    }
                });*/

                result.setData(listTable);
                result.setRecordsTotal(page.getTotalCount());
                result.setRecordsFiltered(page.getTotalCount());
            }else{
                result = getNullTableData(result);
            }
        }else{
            result = getNullTableData(result);
        }
        return result;
    }

    /**
     * 组成空数据格式
     * @param result
     * @return
     */
    private TableData getNullTableData(TableData result){
        result.setData(new ArrayList());
        result.setRecordsTotal(0);
        result.setRecordsFiltered(0);
        return result;
    }

    // 报告详情-获取指定订单地域总体报告
    public List<Report> getRptCampaignGeoTotal(ReportParam params, UserModel user) {
        Map<String, Object> map = params.toMap();
        // 设置markup值
        map.put("markup", user.getRate());
        List<Report> result = reportSqlSessionHandler.selectList("rptDetail.rpt:campaign:geo:daily:total:select", map);
        return result;
    }

    // 报告详情-获取指定订单地域详情报告
    public List<Report> getRptCampaignGeoDetail(ReportParam params, UserModel user) {
        Map<String, Object> map = params.toMap();
        // 设置markup值
        map.put("markup", user.getRate());
        List<Report> result = reportSqlSessionHandler.selectList("rptDetail.rpt:campaign:geo:daily:detail:select", map);
        return result;
    }

    // 报告详情-获取指定订单性别总体报告
    public List<Report> getRptCampaignGenderTotal(ReportParam params, UserModel user) {
        Map<String, Object> map = params.toMap();
        // 设置markup值
        map.put("markup", user.getRate());
        List<Report> result = reportSqlSessionHandler.selectList("rptDetail.rpt:campaign:gender:daily:total:select", map);
        return result;
    }

    // 报告详情-获取指定订单性别详情报告
    public List<Report> getRptCampaignGenderDetail(ReportParam params, UserModel user) {
        Map<String, Object> map = params.toMap();
        // 设置markup值
        map.put("markup", user.getRate());
        List<Report> result = reportSqlSessionHandler.selectList("rptDetail.rpt:campaign:gender:daily:detail:select", map);
        return result;
    }

    // 报告详情-获取指定订单年龄总体报告
    public List<Report> getRptCampaignAgeTotal(ReportParam params, UserModel user) {
        Map<String, Object> map = params.toMap();
        // 设置markup值
        map.put("markup", user.getRate());
        List<Report> result = reportSqlSessionHandler.selectList("rptDetail.rpt:campaign:age:daily:total:select", map);
        return result;
    }

    // 报告详情-获取指定订单年龄详情报告
    public List<Report> getRptCampaignAgeDetail(ReportParam params, UserModel user) {
        Map<String, Object> map = params.toMap();
        // 设置markup值
        map.put("markup", user.getRate());
        List<Report> result = reportSqlSessionHandler.selectList("rptDetail.rpt:campaign:age:daily:detail:select", map);
        return result;
    }

    // 导出Excel数据报告
    public void exportExcelReport(Campaign campaign, ReportParam params, Report total, List<Report> reportList, ReportType reportType, HttpServletResponse response) throws Exception {
        String dateRange = DateUtils.dateToString(params.getStart(), "yyyy-MM-dd") + " - " + DateUtils.dateToString(params.getEnd(), "yyyy-MM-dd");;
        if (params.getStart() != null && params.getStart().compareTo(params.getEnd()) == 0) {
            dateRange = DateUtils.dateToString(params.getStart(), "yyyy-MM-dd");
        }

        String dataDimension = "时报";
        switch (reportType) {
            case HOURLY:
                dataDimension = "时报";
                dateRange = DateUtils.dateToString(params.getCurrent(), "yyyy-MM-dd");
                break;
            case DAILY:
                dataDimension = "日报";
                break;
            case STRATEGY:
                dataDimension = "策略";
                break;
            case GEO:
                dataDimension = "地域";
                break;
            case GENDER:
                dataDimension = "性别";
                break;
            case AGE:
                dataDimension = "年龄";
                break;
        }

        StringBuffer fileName = new StringBuffer(campaign.getCname()).append("_数据纬度（").append(dataDimension).append("）_时间（").append(dateRange).append("）.xlsx");

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-Disposition", "attachment;filename=" + new String(fileName.toString().getBytes("UTF-8"), "ISO-8859-1"));
        OutputStream out = response.getOutputStream();

        // 创建一个workbook，对应一个Excel文件
        SXSSFWorkbook wb = new SXSSFWorkbook();
        // 在workbook中添加一个sheet,对应Excel文件中的sheet,sheet页的标题为纬度
        SXSSFSheet sheet = wb.createSheet(dataDimension);

        // 订单概况(B2:F8) 填充白色
        XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);

        // 订单概况(B2:F8)
        setCampaignGeneralInfo(1, "订单ID", params.getCampaignId().toString(), sheet, cellStyle);
        setCampaignGeneralInfo(2, "订单名称", campaign.getCname(), sheet, cellStyle);
        setCampaignGeneralInfo(3, "投放时间", DateUtils.dateToString(campaign.getStart(), "yyyy/MM/dd") + " - " + DateUtils.dateToString(campaign.getEnd(), "yyyy/MM/dd"), sheet, cellStyle);
        setCampaignGeneralInfo(4, "每日限额", Integer.toString((int) Math.round(campaign.getDayBudget())), sheet, cellStyle);
        setCampaignGeneralInfo(5, "所属广告主", campaign.getUname(), sheet, cellStyle);
        setCampaignGeneralInfo(6, "报告纬度", dataDimension, sheet, cellStyle);
        setCampaignGeneralInfo(7, "报告时间范围", dateRange, sheet, cellStyle);

        // 订单概况(B2:F8), 区域 设置边框
        CellRangeAddress campaignInfoRegion = new CellRangeAddress(1, 7, 1, 5);
        setRegionBorder(1, campaignInfoRegion, sheet, wb);

        // 数据信息 设置边框
        XSSFCellStyle dataCellStyle = (XSSFCellStyle) wb.createCellStyle();
        dataCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        dataCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        dataCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        dataCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);

        //  数据信息 表头(C10:H10) 设置居右 背景色为蓝色 有边框
        XSSFCellStyle titleCellStyle = (XSSFCellStyle) wb.createCellStyle();
        titleCellStyle.cloneStyleFrom(dataCellStyle);
        titleCellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        titleCellStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte) 149,(byte)179,(byte)215,}));
        titleCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        // 数据信息 表头(B10) 设置背景色为蓝色 有边框
        XSSFCellStyle cellStyleB10 = (XSSFCellStyle) wb.createCellStyle();
        cellStyleB10.cloneStyleFrom(titleCellStyle);
        cellStyleB10.setAlignment(XSSFCellStyle.ALIGN_LEFT);

        // 数据信息 columnC和columnD 设置千分位使用
        CellStyle dataCellStyleColumnCD = (XSSFCellStyle) wb.createCellStyle();
        dataCellStyleColumnCD.cloneStyleFrom(dataCellStyle);
        dataCellStyleColumnCD.setDataFormat(wb.createDataFormat().getFormat("#,##0"));

        // 数据信息 columnEFGH 设置千分位使用 两位小数
        CellStyle dataCellStyleColumnEFGH = (XSSFCellStyle) wb.createCellStyle();
        dataCellStyleColumnEFGH.cloneStyleFrom(dataCellStyle);
        dataCellStyleColumnEFGH.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));

        // 数据信息
        // 数据信息 表头
        SXSSFRow row10 = sheet.createRow(9);
        Cell cellB10 = row10.createCell(1);
        cellB10.setCellValue(dataDimension);
        cellB10.setCellStyle(cellStyleB10);
        Cell cellC10 = row10.createCell(2);
        cellC10.setCellStyle(titleCellStyle);
        cellC10.setCellValue("印象数");
        cellC10.setCellStyle(titleCellStyle);
        Cell cellD10 = row10.createCell(3);
        cellD10.setCellValue("点击数");
        cellD10.setCellStyle(titleCellStyle);
        Cell cellE10 = row10.createCell(4);
        cellE10.setCellValue("点击率（%）");
        cellE10.setCellStyle(titleCellStyle);
        Cell cellF10 = row10.createCell(5);
        cellF10.setCellValue("花费（￥）");
        cellF10.setCellStyle(titleCellStyle);
        Cell cellG10 = row10.createCell(6);
        cellG10.setCellValue("eCPC（￥）");
        cellG10.setCellStyle(titleCellStyle);
        Cell cellH10 = row10.createCell(7);
        cellH10.setCellValue("eCPM（￥）");
        cellH10.setCellStyle(titleCellStyle);

        if (total != null) {
            // 如果存在数据信息，将数据信息写入Excel
            setDetailDataInfoToExcel(total, reportList, reportType, sheet, dataCellStyle, dataCellStyleColumnCD, dataCellStyleColumnEFGH);
        } else {
            // 如果不存在数据信息，第11行 居中显示 暂无数据
            SXSSFRow row11 = sheet.createRow(10);
            Cell cellB11 = row11.createCell(1);
            cellB11.setCellValue("暂无数据");
            // C11:H11 设置边框
            for (int i = 2; i < 8; i++) {
                Cell cell = row11.createCell(i);
                cell.setCellStyle(dataCellStyle);
            }
            // B11:H11 合并单元格 并居中
            sheet.addMergedRegion(new CellRangeAddress(10,10,1,7));
            XSSFCellStyle cellStyleAlignCenter = (XSSFCellStyle) wb.createCellStyle();
            cellStyleAlignCenter.cloneStyleFrom(dataCellStyle);
            cellStyleAlignCenter.setAlignment(HorizontalAlignment.CENTER);
            cellB11.setCellStyle(cellStyleAlignCenter);
        }

        // 设置列宽
        sheet.setColumnWidth(1, 20*256);
        sheet.setColumnWidth(2, 14*256);
        sheet.setColumnWidth(3, 14*256);
        sheet.setColumnWidth(4, 14*256);
        sheet.setColumnWidth(5, 14*256);
        sheet.setColumnWidth(6, 14*256);
        sheet.setColumnWidth(7, 14*256);

        // 设置行高
        sheet.setDefaultRowHeight((short)(16.5*20));

        wb.write(out);
        out.flush();
        out.close();
    }

    // 将数据信息写入Excel
    private void setDetailDataInfoToExcel(Report total, List<Report> reportList, ReportType reportType, SXSSFSheet sheet, XSSFCellStyle dataCellStyle, CellStyle dataCellStyleColumnCD, CellStyle dataCellStyleColumnEFGH) {
        // 数据信息 总计
        SXSSFRow row11 = sheet.createRow(10);
        Cell cellB11 = row11.createCell(1);
        cellB11.setCellValue("总计");
        cellB11.setCellStyle(dataCellStyle);
        Cell cellC11 = row11.createCell(2);
        cellC11.setCellValue(total.getViewCount());
        cellC11.setCellStyle(dataCellStyleColumnCD);
        Cell cellD11 = row11.createCell(3);
        cellD11.setCellValue(total.getClickCount());
        cellD11.setCellStyle(dataCellStyleColumnCD);
        Cell cellE11 = row11.createCell(4);
        cellE11.setCellValue(total.getCtr());
        cellE11.setCellStyle(dataCellStyleColumnEFGH);
        Cell cellF11 = row11.createCell(5);
        cellF11.setCellValue(total.getCost());
        cellF11.setCellStyle(dataCellStyleColumnEFGH);
        Cell cellG11 = row11.createCell(6);
        cellG11.setCellValue(total.getEcpc());
        cellG11.setCellStyle(dataCellStyleColumnEFGH);
        Cell cellH11 = row11.createCell(7);
        cellH11.setCellValue(total.getEcpm());
        cellH11.setCellStyle(dataCellStyleColumnEFGH);

        // 详细数据信息
        for (int i = 0; i < reportList.size(); i++) {
            SXSSFRow rowData = sheet.createRow(11 + i);
            Cell columnB = rowData.createCell(1);
            switch (reportType) {
                case HOURLY:
                    if (reportList.get(i).getHour() < 10) {
                        columnB.setCellValue("0" + reportList.get(i).getHour() + ":00 - " + (reportList.get(i).getHour() + 1) + ":00");
                    } else {
                        columnB.setCellValue(reportList.get(i).getHour() + ":00 - " + (reportList.get(i).getHour() + 1) + ":00");
                    }
                    break;
                case DAILY:
                    columnB.setCellValue(DateUtils.dateToString(reportList.get(i).getSettledTime(), "yyyy-MM-dd"));
                    break;
                case STRATEGY:
                    columnB.setCellValue(reportList.get(i).getSid().toString() + " - " + reportList.get(0).getName());
                    break;
                case GEO:
                    columnB.setCellValue(reportList.get(i).getStateName());
                    break;
                case GENDER:
                    columnB.setCellValue(reportList.get(i).getGenderName());
                    break;
                case AGE:
                    columnB.setCellValue(reportList.get(i).getAgeName());
                    break;
            }
            columnB.setCellStyle(dataCellStyle);

            Cell columnC = rowData.createCell(2);
            columnC.setCellValue(reportList.get(i).getViewCount());
            columnC.setCellStyle(dataCellStyleColumnCD);
            Cell columnD = rowData.createCell(3);
            columnD.setCellValue(reportList.get(i).getClickCount());
            columnD.setCellStyle(dataCellStyleColumnCD);
            Cell columnE = rowData.createCell(4);
            columnE.setCellValue(reportList.get(i).getCtr());
            columnE.setCellStyle(dataCellStyleColumnEFGH);
            Cell columnF = rowData.createCell(5);
            columnF.setCellValue(reportList.get(i).getCost());
            columnF.setCellStyle(dataCellStyleColumnEFGH);
            Cell columnG = rowData.createCell(6);
            columnG.setCellValue(reportList.get(i).getEcpc());
            columnG.setCellStyle(dataCellStyleColumnEFGH);
            Cell columnH = rowData.createCell(7);
            columnH.setCellValue(reportList.get(i).getEcpm());
            columnH.setCellStyle(dataCellStyleColumnEFGH);
        }
    }

    // 区域单元格 设置边框
    private void setRegionBorder(int border, CellRangeAddress region, Sheet sheet, Workbook wb) {
        RegionUtil.setBorderBottom(border, region, sheet, wb);
        RegionUtil.setBorderLeft(border, region, sheet, wb);
        RegionUtil.setBorderRight(border, region, sheet, wb);
        RegionUtil.setBorderTop(border, region, sheet, wb);
    }

    // 订单概况(D2:F8) 填充白色
    private void fillColorForCampaignGeneralInfoCell(SXSSFRow row, CellStyle cellStyle) {
        for (int column = 3; column < 6; column++) {
            Cell cell = row.createCell(column);
            cell.setCellStyle(cellStyle);
        }
    }

    // 订单概况 （B2:F8）
    private void setCampaignGeneralInfo(int rowNum, String valueColumnB, String valueColumnC, SXSSFSheet sheet, CellStyle cellStyle){
        SXSSFRow row = sheet.createRow(rowNum);
        Cell columnB = row.createCell(1);
        columnB.setCellValue(valueColumnB);
        columnB.setCellStyle(cellStyle);
        Cell columnC = row.createCell(2);
        columnC.setCellValue(valueColumnC);
        columnC.setCellStyle(cellStyle);
        // D2:F8 填充白色
        fillColorForCampaignGeneralInfoCell(row,cellStyle);
    }
}
