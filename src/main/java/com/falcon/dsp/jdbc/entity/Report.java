package com.falcon.dsp.jdbc.entity;

import com.falcon.dsp.common.MathUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * Created by falcon-zhangxg on 2016/4/13.
 */
@Alias("report")
public class Report {

    @JsonProperty("settled_time")
    private Date settledTime;
    private Integer cid;
    private Integer sid;
    private Integer hour;

    private String name;

    @JsonProperty("state_id")
    private Integer stateId;
    @JsonProperty("state_time")
    private String stateName;

    @JsonProperty("gender_id")
    private Integer genderId;
    @JsonProperty("gender_name")
    private String genderName;

    private Integer ages;
    @JsonProperty("age_name")
    private String ageName;

    private long viewCount;
    private long clickCount;
    private double cost;

    private double ctr;
    private double ecpm;
    private double ecpc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCtr() {
        return MathUtil.ctrFormat(((double) clickCount) / viewCount);
    }

    public double getEcpm() {
        return MathUtil.ecpmFormat(cost / viewCount);
    }

    public double getEcpc() {
        return MathUtil.doubleFormat(cost / clickCount);
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public Integer getAges() {
        return ages;
    }

    public void setAges(Integer ages) {
        this.ages = ages;
    }

    public String getAgeName() {
        return ageName;
    }

    public void setAgeName(String ageName) {
        this.ageName = ageName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public long getClickCount() {
        return clickCount;
    }

    public void setClickCount(long clickCount) {
        this.clickCount = clickCount;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public Date getSettledTime() {
        return settledTime;
    }

    public void setSettledTime(Date settledTime) {
        this.settledTime = settledTime;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }
}
