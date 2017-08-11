package com.falcon.dsp.jdbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by falcon-zhangxg on 2016/4/18.
 */
public class UtilityModel {

    private long user;
    private long exposure;

    @JsonProperty("min_bid")
    private double minBid;

    @JsonProperty("max_bid")
    private double maxBid;

    public UtilityModel(long user, long exposure, double minBid, double maxBid) {
        this.user = user;
        this.exposure = exposure;
        this.minBid = minBid;
        this.maxBid = maxBid;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getExposure() {
        return exposure;
    }

    public void setExposure(long exposure) {
        this.exposure = exposure;
    }

    public double getMinBid() {
        return minBid;
    }

    public void setMinBid(double minBid) {
        this.minBid = minBid;
    }

    public double getMaxBid() {
        return maxBid;
    }

    public void setMaxBid(double maxBid) {
        this.maxBid = maxBid;
    }
}
