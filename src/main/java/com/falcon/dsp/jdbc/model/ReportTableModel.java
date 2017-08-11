package com.falcon.dsp.jdbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Zhouchunhui
 * @from 2016-04-27
 * @since V1.0
 */
public class ReportTableModel {

    private int cid;

    private int uid;

    private String uname;

    private String cname;

    @JsonProperty("delivery_time")
    private String deliveryTime;

    @JsonProperty("view_count")
    private String viewCount;

    @JsonProperty("click_count")
    private String clickCount;

    private String cost;

    @JsonProperty("click_rate")
    private String clickRate;

    private String cpc;

    private String cpm;


    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getViewCount() {
        return StringUtils.isEmpty(this.viewCount)?"0":this.viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getClickCount() {
        return StringUtils.isEmpty(this.clickCount)?"0":this.clickCount;
    }

    public void setClickCount(String clickCount) {
        this.clickCount = clickCount;
    }

    public String getCost() {
        return StringUtils.isEmpty(this.cost)?"0":this.cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getClickRate() {
        return StringUtils.isEmpty(this.clickRate)?"0":this.clickRate;
    }

    public void setClickRate(String clickRate) {
        this.clickRate = clickRate;
    }

    public String getCpc() {
        return StringUtils.isEmpty(this.cpc)?"0":this.cpc;
    }

    public void setCpc(String cpc) {
        this.cpc = cpc;
    }

    public String getCpm() {
        return StringUtils.isEmpty(this.cpm)?"0":this.cpm;
    }

    public void setCpm(String cpm) {
        this.cpm = cpm;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReportTableModel))
            return false;
        ReportTableModel rt = (ReportTableModel) o;
        if(rt.cid == this.cid){
            this.viewCount = StringUtils.isEmpty(rt.viewCount)?"0":rt.viewCount;
            this.clickCount = StringUtils.isEmpty(rt.clickCount)?"0":rt.clickCount;
            this.clickRate = StringUtils.isEmpty(rt.clickRate)?"0":rt.clickRate;
            this.cost =  StringUtils.isEmpty(rt.cost)?"0":rt.cost;
            this.cpc = StringUtils.isEmpty(rt.cpc)?"0":rt.cpc;
            this.cpm = StringUtils.isEmpty(rt.cpm)?"0":rt.cpm;
            return true;
        }else{
            return false;
        }

    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
