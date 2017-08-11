package com.falcon.dsp.jdbc.entity;

import com.falcon.dsp.enumration.FinanceFlowType;
import com.falcon.dsp.enumration.FinanceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * @author Zhouchunhui
 * @from 2016-04-14
 * @since V1.0
 */
@Alias("accountInvoice")
public class AccountInvoice {

    /**
     * 广告主名称
     */
    private String uname;

    private int id;

    /**
     * 广告主id
     */
    private int uid;
    /**
     * 账户类型
     */
    @JsonProperty(value = "account_type")
    private FinanceType accountType;

    /**
     * 当前金额
     */
    @JsonProperty(value = "current_balance")
    private long currentBalance;

    /**
     * 操作后的余额
     */
    private long balance;

    /**
     * 变动的代理商的账户（）
     */
    @JsonProperty(value = "agency_account")
    private int agencyAccount;

    /**
     * 财务流水类型  （TRANSFER_IN 转入,TRANSFER_OUT 转出）
     */
    @JsonProperty(value = "trans_type")
    private FinanceFlowType transType;

    /**
     * 交易时间戳
     */
    @JsonProperty(value = "trans_time")
    private long transTime;

    /**
     * 交易单号
     */
    @JsonProperty(value = "bill_no")
    private String billNo;

    @JsonProperty(value = "virtual_amount")
    private long virtualAmount;

    /**
     * 交易金额
     */
    private long amount;

    /**
     * 描述
     */
    private String description;

    /**
     * 1 - 广告主
     * 2 - 代理商
     */
    private int targetType;

    //备注，真实的描述信息
    private String remark;

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public int  getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(long currentBalance) {
        this.currentBalance = currentBalance;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public int getAgencyAccount() {
        return agencyAccount;
    }

    public void setAgencyAccount(int agencyAccount) {
        this.agencyAccount = agencyAccount;
    }

    public FinanceFlowType getTransType() {
        return transType;
    }

    public void setTransType(FinanceFlowType transType) {
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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getVirtualAmount() {
        return virtualAmount;
    }

    public void setVirtualAmount(long virtualAmount) {
        this.virtualAmount = virtualAmount;
    }
}
