package com.falcon.dsp.jdbc.model;

import com.falcon.dsp.enumration.FinanceFlowType;
import com.falcon.dsp.enumration.FinanceType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 代理商交易model
 * @author Zhouchunhui
 * @from 2016-04-26
 * @since V1.0
 */
public class AgencyTradeModel {

    @JsonProperty(value = "finance_type")
    private FinanceType financeType;

    @JsonProperty(value = "trans_type")
    private FinanceFlowType transType;

    private int id;

    @JsonProperty(value = "virtual_amount")
    private int virtualAmount;

    private int amount;

    public FinanceType getFinanceType() {
        return financeType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setFinanceType(FinanceType financeType) {
        this.financeType = financeType;
    }

    public FinanceFlowType getTransType() {
        return transType;
    }

    public void setTransType(FinanceFlowType transType) {
        this.transType = transType;
    }

    public int getVirtualAmount() {
        return virtualAmount;
    }

    public void setVirtualAmount(int virtualAmount) {
        this.virtualAmount = virtualAmount;
    }
}
