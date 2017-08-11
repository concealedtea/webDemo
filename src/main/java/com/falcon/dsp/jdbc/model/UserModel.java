package com.falcon.dsp.jdbc.model;

import com.falcon.dsp.jdbc.entity.AccessParam;
import com.falcon.dsp.jdbc.entity.UserInfo;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class UserModel extends UserInfo{


    private double rate;

    private AccessParam accessParam;

    private boolean inLieyingAccount;


    public boolean getInLieyingAccount() {
        return inLieyingAccount;
    }

    public void setInLieyingAccount(boolean inLieyingAccount) {
        this.inLieyingAccount = inLieyingAccount;
    }

    public AccessParam getAccessParam() {
        return accessParam;
    }

    public void setAccessParam(AccessParam accessParam) {
        this.accessParam = accessParam;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void settingValus(UserInfo userInfo) {
        this.setUid(userInfo.getUid());
        this.setUserRole(userInfo.getUserRole());
        this.setId(userInfo.getId());
        this.setStatus(userInfo.getStatus());
        this.setUserName(userInfo.getUserName());
    }
}
