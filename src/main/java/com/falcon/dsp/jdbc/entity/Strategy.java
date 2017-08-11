package com.falcon.dsp.jdbc.entity;

import com.falcon.dsp.rest.common.BasicEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author dongbin.yu
 * @from 2016-04-05
 * @since V1.0
 */
@Alias("strategy")
public class Strategy extends BasicEntity {

    private Integer sid;
    private String sname;
    private Date start;
    private Date end;
    private String description;
    private Integer uid;
    @JsonProperty(value = "campaign_id")
    private Integer campaignId;
    @JsonProperty(value = "target_id")
    private Integer targetId;
    @JsonProperty(value = "creative_id")
    private Integer creativeId;
    private Double bid;

    private Creative creative;

    @JsonProperty(value = "audit_msg")
    private String auditMsg;

    @JsonProperty(value = "ad_space_position")
    private AdSpacePosition adSpacePosition;

    @JsonProperty(value = "strategy_platform")
    private StrategyPlatform strategyPlatform;

    private Target target = new Target();

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Integer creativeId) {
        this.creativeId = creativeId;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Creative getCreative() {
        return creative;
    }

    public void setCreative(Creative creative) {
        this.creative = creative;
    }

    public AdSpacePosition getAdSpacePosition() {
        return adSpacePosition;
    }

    public void setAdSpacePosition(AdSpacePosition adSpacePosition) {
        this.adSpacePosition = adSpacePosition;
    }

    public StrategyPlatform getStrategyPlatform() {
        return strategyPlatform;
    }

    public void setStrategyPlatform(StrategyPlatform strategyPlatform) {
        this.strategyPlatform = strategyPlatform;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public String getAuditMsg() {
        return auditMsg;
    }

    public void setAuditMsg(String auditMsg) {
        this.auditMsg = auditMsg;
    }

    @Override
    public String toString() {
        return "Strategy{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", description='" + description + '\'' +
                ", uid=" + uid +
                ", campaignId=" + campaignId +
                ", targetId=" + targetId +
                ", creativeId=" + creativeId +
                ", bid=" + bid +
                ", creative=" + creative +
                ", adSpacePosition=" + adSpacePosition +
                ", strategyPlatform=" + strategyPlatform +
                ", target=" + target +
                '}';
    }
}
