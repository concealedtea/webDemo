package com.falcon.dsp.jdbc.model;

import com.falcon.dsp.enumration.CustomerStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Zhouchunhui
 * @from 2016-04-13
 * @since V1.0
 */
public class AdvertiserTableModel {

    private int uid; //广告主id

    private String uname; //客户名称

    private CustomerStatus customerStatus; //用户状态

    private int commission;//服务费率

    private double dayBudget; //日消耗限额

    private double dayCost;//今日花费

    private double balance;//账户余额

    @JsonProperty(value="audit_msg")
    private String auditMsg;

    private int status;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getCustomerStatus() {
        return customerStatus.getDescription();
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public double getDayBudget() {
        return dayBudget;
    }

    public void setDayBudget(double dayBudget) {
        this.dayBudget = dayBudget;
    }

    public double getDayCost() {
        return dayCost;
    }

    public void setDayCost(double dayCost) {
        this.dayCost = dayCost;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAuditMsg() {
        return auditMsg;
    }

    public void setAuditMsg(String auditMsg) {
        this.auditMsg = auditMsg;
    }

    public int getStatus() {
        switch (customerStatus){
            case CUSTOMERSTATUS_NORMAL:
                return 0;
            case CUSTOMERSTATUS_PENDING:
                return 1;
            case CUSTOMERSTATUS_DENIED:
                return 2;
            case CUSTOMERSTATUS_FROZEN:
                return 3;
            case CUSTOMERSTATUS_TOBE_ACCEPTED:
                return 4;
            case CUSTOMERSTATUS_TOBE_ACTIVATED:
                return 5;
            case CUSTOMERSTATUS_SUSPEND:
                return 6;
            case CUSTOMERSTATUS_MATERIAL_PREPARED:
                return 7;
            case CUSTOMERSTATUS_DELETED:
                return 8;
            case CUSTOMERSTATUS_UNREGISTERED:
                return 9;
            case UNKNOWN0:
                return 10;
            default:
                return 0;
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
