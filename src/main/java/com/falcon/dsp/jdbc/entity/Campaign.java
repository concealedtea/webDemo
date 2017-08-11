package com.falcon.dsp.jdbc.entity;

import com.falcon.dsp.rest.campaign.request.GdtCampaign;
import com.falcon.dsp.rest.common.BasicEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 广点通-推广计划模块
 */
@Alias("campaign")
public class Campaign extends BasicEntity {

    /**
     * 推广计划 Id，从广点通获取
     */
    private Integer cid;

    /**
     * 广告主 Id
     */
    private Integer uid;

    /**
     * 推广计划名称，不可与相同 uid 下其他推广计划重名
     */
    private String cname;

    /**
     * 开始结束时间
     */
    private Date start;
    private Date end;

    /**
     * 推广计划类型
     */
    private Integer ctype;

    /**
     * 日消耗限额，单位为分
     */
    @JsonProperty("day_budget")
    private Double dayBudget;

    /**
     * 投放速度控制
     */
    @JsonProperty("speed_mode")
    private Integer speedMode;

    @JsonProperty("strategy_count")
    private int strategyCount;

    private String uname;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Double getDayBudget() {
        return dayBudget;
    }

    public void setDayBudget(Double dayBudget) {
        this.dayBudget = dayBudget;
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

    public Integer getCtype() {
        return ctype;
    }

    public void setCtype(Integer ctype) {
        this.ctype = ctype;
    }

    public Integer getSpeedMode() {
        return speedMode;
    }

    public void setSpeedMode(Integer speedMode) {
        this.speedMode = speedMode;
    }

    public int getStrategyCount() {
        return strategyCount;
    }

    public void setStrategyCount(int strategyCount) {
        this.strategyCount = strategyCount;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Campaign convert(GdtCampaign gdtCampaign) {
        if (gdtCampaign.getCid() != null) this.setCid(gdtCampaign.getCid());
        if (gdtCampaign.getUid() != null) this.setUid(gdtCampaign.getUid());
        if (gdtCampaign.getCname() != null) this.setCname(gdtCampaign.getCname());
        if (gdtCampaign.getCtype() != null) this.setCtype(gdtCampaign.getCtype().getValue());
        if (gdtCampaign.getStart() != null) this.setStart(gdtCampaign.getStart());
        if (gdtCampaign.getEnd() != null) this.setEnd(gdtCampaign.getEnd());
        if (gdtCampaign.getDayBudget() != null) this.setDayBudget(gdtCampaign.getDayBudget() / 100.0);
        if (gdtCampaign.getSpeedMode() != null) this.setSpeedMode(gdtCampaign.getSpeedMode().getValue());
//        if (gdtCampaign.getStatus() != null) this.setStatus(gdtCampaign.getStatus());
        if (gdtCampaign.getCtype() != null) this.setCreateTime(new Date());
        return this;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", cname='" + cname + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", ctype=" + ctype +
                ", dayBudget=" + dayBudget +
                ", speedMode=" + speedMode +
                ", strategyCount=" + strategyCount +
                ", uname='" + uname + '\'' +
                '}';
    }
}
