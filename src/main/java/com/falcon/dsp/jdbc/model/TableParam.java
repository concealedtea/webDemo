package com.falcon.dsp.jdbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by falcon-zhangxg on 2016/4/6.
 */
public class TableParam {

    @JsonProperty(value = "campaign_id")
    private Integer campaignId;

    private Integer uid;

    @JsonProperty(value = "strategy_id")
    private Integer strategyId;

    private Integer draw;

    private Integer start;

    private Integer length;

    private String orderby;

    private String ordercolumns;

    private int currentPage;

    public int getCurrentPage() {
        return start/length+1;
    }

    @JsonProperty(value = "search_keyword")
    private String searchKeyword;

    @JsonProperty(value = "search_status")
    private Integer searchStatus;

    @JsonProperty(value = "search_advertiser")
    private Integer searchAdvertiser;

    public int getPage() {
        return start / length + 1;
    }

    public int getPageSize() {
        return length;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getOrdercolumns() {
        return ordercolumns;
    }

    public void setOrdercolumns(String ordercolumns) {
        this.ordercolumns = ordercolumns;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public Integer getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(Integer searchStatus) {
        this.searchStatus = searchStatus;
    }

    public Integer getSearchAdvertiser() {
        return searchAdvertiser;
    }

    public void setSearchAdvertiser(Integer searchAdvertiser) {
        this.searchAdvertiser = searchAdvertiser;
    }
}

