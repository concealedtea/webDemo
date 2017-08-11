package com.falcon.dsp.jdbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Zhouchunhui
 * @from 2016-05-03
 * @since V1.0
 */
public class FinanceFlowModel {
    @JsonProperty(value = "trans_time")
    private String transTime;

    @JsonProperty(value = "trans_in")
    private String transIn;

    @JsonProperty(value = "trans_out")
    private String transOut;

    private String description;

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getTransIn() {
        return transIn;
    }

    public void setTransIn(String transIn) {
        this.transIn = transIn;
    }

    public String getTransOut() {
        return transOut;
    }

    public void setTransOut(String transOut) {
        this.transOut = transOut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
