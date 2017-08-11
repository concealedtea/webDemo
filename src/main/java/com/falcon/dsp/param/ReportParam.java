package com.falcon.dsp.param;

import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.enumration.DateType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by falcon-zhangxg on 2016/4/13.
 */
public class ReportParam {

    private Date start;
    private Date end;
    private Date current;
    @JsonProperty("date_type")
    private Integer dateType;
    private Integer uid;
    @JsonProperty("campaign_id")
    private Integer campaignId;
    @JsonProperty("strategy_id")
    private Integer strategyId;

    private Integer page;
    @JsonProperty("page_size")
    private Integer pageSize;

    public ReportParam() {

    }

    public ReportParam(Integer campaignId, Integer dateType) {
        this.campaignId = campaignId;
        this.dateType = dateType;
    }

    /**
     * @param start
     * @param end
     * @param dateType
     * @param campaignId
     * @param strategyId
     * @param page
     * @param pageSize
     */
    public ReportParam(Date start, Date end, Integer dateType, Integer campaignId, Integer strategyId, Integer page, Integer pageSize) {
        this.start = start;
        this.end = end;
        this.dateType = dateType;
        this.campaignId = campaignId;
        this.strategyId = strategyId;
        this.page = page;
        this.pageSize = pageSize;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getCurrent() {
        return current;
    }

    public void setCurrent(Date current) {
        this.current = current;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Object> toMap() {

        Map<String, Object> params = new HashMap<>();

        params.put("cid", this.getCampaignId());
        params.put("sid", this.getStrategyId());

        if (this.getStart() != null || this.getEnd() != null) {
            params.put("start", this.getStart());
            params.put("end", this.getEnd());
        }

        if (this.getCurrent() != null) {
            params.put("current", this.getCurrent());
        }

        if (this.getDateType() != null) {
            if (DateType.forValue(this.getDateType()) == DateType.TODAY) {
                params.put("current", DateUtils.today("yyyy-MM-dd"));
            }
            if (DateType.forValue(this.getDateType()) == DateType.YESTERDAY) {
                params.put("current", DateUtils.yesterday("yyyy-MM-dd"));
            }
            if (DateType.forValue(this.getDateType()) == DateType.LAST_7) {
                params.put("start", DateUtils.last_7_day("yyyy-MM-dd"));
                params.put("end", DateUtils.today("yyyy-MM-dd"));
            }
            if (DateType.forValue(this.getDateType()) == DateType.LAST_30) {
                params.put("start", DateUtils.last_7_day("yyyy-MM-dd"));
                params.put("end", DateUtils.today("yyyy-MM-dd"));
            }
        }
        return params;
    }
}
