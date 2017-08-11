package com.falcon.dsp.jdbc.model;

import com.falcon.dsp.jdbc.entity.Agency;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Zhouchunhui
 * @from 2016-04-25
 * @since V1.0
 */
public class AgencyModel extends Agency{

    private String advertiserNumber;

    private String uid;

    @JsonProperty(value = "user_name")
    private String userName;

    @JsonProperty(value = "status_name")
    private String statusName;

    private int commission;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAdvertiserNumber() {
        return advertiserNumber;
    }

    public void setAdvertiserNumber(String advertiserNumber) {
        this.advertiserNumber = advertiserNumber;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }


    @Override
    public int getCommission() {
        return commission;
    }

    @Override
    public void setCommission(int commission) {
        this.commission = commission;
    }
}
