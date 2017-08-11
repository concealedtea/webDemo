package com.falcon.dsp.rest.strategy.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by falcon-zhangxg on 2016/4/18.
 */
public class GdtUtility {

    private Long user;
    private Long exposure;
    @JsonProperty("min_bid")
    private Integer minBid;
    @JsonProperty("max_bid")
    private Integer maxBid;

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getExposure() {
        return exposure;
    }

    public void setExposure(Long exposure) {
        this.exposure = exposure;
    }

    public Integer getMinBid() {
        return minBid;
    }

    public void setMinBid(Integer minBid) {
        this.minBid = minBid;
    }

    public Integer getMaxBid() {
        return maxBid;
    }

    public void setMaxBid(Integer maxBid) {
        this.maxBid = maxBid;
    }
}
