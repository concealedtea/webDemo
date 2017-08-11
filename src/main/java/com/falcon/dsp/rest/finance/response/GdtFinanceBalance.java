package com.falcon.dsp.rest.finance.response;

import com.falcon.dsp.enumration.FinanceStatus;
import com.falcon.dsp.enumration.FinanceType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by xm on 2016/4/8.
 */
public class GdtFinanceBalance {

    @JsonProperty("account_type")
    private FinanceType financeType;

    @JsonProperty("balance")
    private int balance;

    @JsonProperty("fund_status")
    private FinanceStatus financeStatus;

    @JsonProperty("daily_cost")
    private int dailyCost;

    public FinanceType getFinanceType() {
        return financeType;
    }

    public void setFinanceType(FinanceType financeType) {
        this.financeType = financeType;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public FinanceStatus getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(FinanceStatus financeStatus) {
        this.financeStatus = financeStatus;
    }

    public int getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(int dailyCost) {
        this.dailyCost = dailyCost;
    }
}
