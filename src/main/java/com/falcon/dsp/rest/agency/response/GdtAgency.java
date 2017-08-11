package com.falcon.dsp.rest.agency.response;

import com.falcon.dsp.enumration.FinanceStatus;
import com.falcon.dsp.enumration.FinanceType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2016/4/12.
 */
public class GdtAgency {

    @JsonProperty(value = "account_type")
    private FinanceType accountType;
    @JsonProperty(value = "balance")
    private int balance;
    @JsonProperty(value = "fund_status")
    private FinanceStatus fundStatus;
    @JsonProperty(value = "daily_cost")
    private int dailyCost;

    public FinanceType getAccountType() {
        return accountType;
    }

    public void setAccountType(FinanceType accountType) {
        this.accountType = accountType;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public FinanceStatus getFundStatus() {
        return fundStatus;
    }

    public void setFundStatus(FinanceStatus fundStatus) {
        this.fundStatus = fundStatus;
    }

    public int getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(int dailyCost) {
        this.dailyCost = dailyCost;
    }
}
