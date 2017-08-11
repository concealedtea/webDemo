package com.falcon.dsp.rest.campaign.request;

import com.falcon.dsp.common.MathUtil;
import com.falcon.dsp.enumration.CampaignType;
import com.falcon.dsp.enumration.FalAdStatus;
import com.falcon.dsp.enumration.GdtAdStatus;
import com.falcon.dsp.enumration.SpeedMode;
import com.falcon.dsp.jdbc.entity.Campaign;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author dongbin.yu
 * @from 2016-03-29
 * @since V1.0
 */
public class GdtCampaign {

    private Integer uid;

    private Integer cid;

    private String cname;

    private Date start;

    private Date end;

    private CampaignType ctype;

    @JsonIgnore
    private Long budget;

    @JsonProperty(value = "day_budget")
    private Integer dayBudget;

    @JsonProperty(value = "speed_mode")
    private SpeedMode speedMode;

    private GdtAdStatus status;

    private Integer strategies;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
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

    public CampaignType getCtype() {
        return ctype;
    }

    public void setCtype(CampaignType ctype) {
        this.ctype = ctype;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public Integer getDayBudget() {
        return dayBudget;
    }

    public void setDayBudget(Integer dayBudget) {
        this.dayBudget = dayBudget;
    }

    public SpeedMode getSpeedMode() {
        return speedMode;
    }

    public void setSpeedMode(SpeedMode speedMode) {
        this.speedMode = speedMode;
    }

    public GdtAdStatus getStatus() {
        return status;
    }

    public void setStatus(GdtAdStatus status) {
        this.status = status;
    }

    public Integer getStrategies() {
        return strategies;
    }

    public void setStrategies(Integer strategies) {
        this.strategies = strategies;
    }

    public GdtCampaign convert(Campaign campaign) {
        if (campaign.getCid() != null) this.setCid(campaign.getCid());
        if (campaign.getCname() != null) this.setCname(campaign.getCname());
        if (campaign.getUid() != null) this.setUid(campaign.getUid());
        if (campaign.getEnd() != null) this.setEnd(campaign.getEnd());
        if (campaign.getStart() != null) this.setStart(campaign.getStart());
        if (campaign.getCtype() != null) this.setCtype(CampaignType.forValue(campaign.getCtype()));
        if (campaign.getSpeedMode() != null) this.setSpeedMode(SpeedMode.forValue(campaign.getSpeedMode()));
        if (campaign.getStatus() != null) this.setStatus(FalAdStatus.forValue(campaign.getStatus()).getGdtAdStatus());
        if (campaign.getDayBudget() != null) this.setDayBudget(MathUtil.getRoundNumberMultiply(campaign.getDayBudget(),1));
        return this;
    }

    @Override
    public String toString() {
        return "GdtCampaign{" +
                "uid=" + uid +
                ", cid=" + cid +
                ", cname='" + cname + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", ctype=" + ctype +
                ", budget=" + budget +
                ", dayBudget=" + dayBudget +
                ", speedMode=" + speedMode +
                ", status=" + status +
                ", strategies=" + strategies +
                '}';
    }
}
