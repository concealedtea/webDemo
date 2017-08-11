package com.falcon.dsp.rest.agency.response;

import com.falcon.dsp.enumration.FinanceType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Zhouchunhui
 * @from 2016-04-12
 * @since V1.0
 */
public class GdtAgencyInvoiceType {
    @JsonProperty(value = "uid")
    private int uid;
    @JsonProperty(value = "account_type")
    private FinanceType accountType;
    @JsonProperty(value = "balance")
    private int balance;
    @JsonProperty(value = "trans_type")
    private String transType;
    @JsonProperty(value = "trans_time")
    private long transTime;
    @JsonProperty(value = "bill_no")
    private String billNo;
    @JsonProperty(value = "amount")
    private int amount;
    @JsonProperty(value = "desc")
    private String desc;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

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

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public long getTransTime() {
        return transTime;
    }

    public void setTransTime(long transTime) {
        this.transTime = transTime;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
