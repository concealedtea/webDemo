package com.falcon.dsp.rest.strategy.request;

import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.enumration.CostType;
import com.falcon.dsp.enumration.GdtAdStatus;
import com.falcon.dsp.enumration.PlayMode;
import com.falcon.dsp.enumration.ProductType;
import com.falcon.dsp.jdbc.entity.Strategy;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author dongbin.yu
 * @from 2016-04-05
 * @since V1.0
 */
public class GdtStrategy {

    private Integer uid;

    private Integer cid;

    private Integer mid;

    private Integer aid;

    private String aname;

    private GdtAdStatus status;

    @JsonProperty(value = "audit_msg")
    private String auditMsg;

    @JsonProperty(value = "cost_type")
    private CostType costType = CostType.COSTTYPE_CPC;

    private Integer bid;

    @JsonProperty(value = "begin_time")
    private Integer beginTime;

    @JsonProperty(value = "end_time")
    private Integer endTime;

    @JsonProperty(value = "created_time")
    private int createdTime;

    private String[] siteset;

    private String timeset;

    @JsonProperty(value = "product_type")
    private ProductType productType;

    @JsonProperty(value = "product_id")
    private String productId;

    @JsonProperty(value = "play_mode")
    private PlayMode playMode;

    @JsonProperty(value = "industry_id")
    private Long industryId;

    @JsonProperty(value = "custom_category")
    private String customCategory;

    public Strategy getStrategy(int creativeId,int uid){
        Strategy s= new Strategy();
        s.setBid(this.bid/100.00);
        s.setCampaignId(this.cid);
        s.setCreativeId(creativeId);
        s.setDescription("同步数据");
        s.setEnd(DateUtils.formatIntToDate(this.endTime));
        s.setSid(this.aid);
        s.setSname(this.aname);
        s.setStart(DateUtils.formatIntToDate(this.beginTime));
        s.setTargetId(this.mid);
        s.setUid(uid);
        s.setStatus(getStatusValue());
        s.setCreateTime(new Date());
        return s;
    }

    private Integer getStatusValue(){
        switch (this.status) {
            case ADSTATUS_NORMAL:
                int now = (int) (new Date().getTime()/1000);
                if(now<beginTime){
                    return 0;
                }else if(now>=beginTime&&now<=endTime){
                    return 1;
                }else{
                    return 2;
                }
            case ADSTATUS_PENDING:
                return 3;
            case ADSTATUS_DENIED:
                return 4;
            case ADSTATUS_SUSPEND:
                return 6;
            case ADSTATUS_DELETED:
                return -1;
            default: return 0;
        }

    }

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

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public GdtAdStatus getStatus() {
        return status;
    }

    public void setStatus(GdtAdStatus status) {
        this.status = status;
    }

    public String getAuditMsg() {
        return auditMsg;
    }

    public void setAuditMsg(String auditMsg) {
        this.auditMsg = auditMsg;
    }

    public CostType getCostType() {
        return costType;
    }

    public void setCostType(CostType costType) {
        this.costType = costType;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Integer beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }

    public String[] getSiteset() {
        return siteset;
    }

    public void setSiteset(String[] siteset) {
        this.siteset = siteset;
    }

    public String getTimeset() {
        return timeset;
    }

    public void setTimeset(String timeset) {
        this.timeset = timeset;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public PlayMode getPlayMode() {
        return playMode;
    }

    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public String getCustomCategory() {
        return customCategory;
    }

    public void setCustomCategory(String customCategory) {
        this.customCategory = customCategory;
    }
}
