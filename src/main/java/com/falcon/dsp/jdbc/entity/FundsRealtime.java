package com.falcon.dsp.jdbc.entity;

import com.falcon.dsp.rest.finance.response.GdtFinanceBalance;

import java.sql.Timestamp;

/**
 * @author Zhouchunhui
 * @from 2016-04-18
 * @since V1.0
 */
public class FundsRealtime {

    private int uid;

    private Timestamp createTime;

    private String accountType;

    private int balance;

    private String fundStatus;

    private int dailyCost;

    public FundsRealtime() {
    }

    public FundsRealtime(int uid, GdtFinanceBalance gdtFinanceBalance) {
        this.uid = uid;
        this.setBalance(gdtFinanceBalance.getBalance());
        this.setDailyCost(gdtFinanceBalance.getDailyCost());
        this.setFundStatus(gdtFinanceBalance.getFinanceStatus().name());
        this.setAccountType(gdtFinanceBalance.getFinanceType().name());
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(String fundStatus) {
        this.fundStatus = fundStatus;
    }

    public int getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(int dailyCost) {
        this.dailyCost = dailyCost;
    }
}
