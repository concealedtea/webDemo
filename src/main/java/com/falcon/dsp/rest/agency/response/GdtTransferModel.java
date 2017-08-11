package com.falcon.dsp.rest.agency.response;

import com.falcon.dsp.enumration.FinanceType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Zhouchunhui
 * @from 2016-04-14
 * @since V1.0
 * 代理商划账给子客户返回数据模型 与子客户回划账与代理商（recover）通用
 */
public class GdtTransferModel {

    @JsonProperty(value = "account_type")
    private FinanceType accountType;//转出账户类型

    private long amount;//转账金额T

    @JsonProperty(value = "bill_no")
    private String billNo;//推广计划id

    @JsonProperty(value = "trans_time")
    private long transTime;//划账时间戳

    @JsonProperty(value = "bill_repeated")
    private int billRepeated;//是否重复

    public FinanceType getAccountType() {
        return accountType;
    }

    public void setAccountType(FinanceType accountType) {
        this.accountType = accountType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public long getTransTime() {
        return transTime;
    }

    public void setTransTime(long transTime) {
        this.transTime = transTime;
    }

    public int getBillRepeated() {
        return billRepeated;
    }

    public void setBillRepeated(int billRepeated) {
        this.billRepeated = billRepeated;
    }
}
