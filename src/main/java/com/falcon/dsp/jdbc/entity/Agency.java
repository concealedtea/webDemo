package com.falcon.dsp.jdbc.entity;

import com.falcon.dsp.rest.common.BasicEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * @author Zhouchunhui
 * @from 2016-04-14
 * @since V1.0
 */
@Alias("agency")
public class Agency extends BasicEntity {

    private int id;
    private String name;

    @JsonProperty(value = "balance_cash")
    private Double balanceCash;

    @JsonProperty(value = "balance_virtual")
    private Double balanceVirtual;

    @JsonProperty(value = "phone_no")
    private String phoneNo;

    private String email;

    @JsonProperty(value = "contact_name")
    private String contactName;

    private int commission;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalanceCash() {
        return balanceCash;
    }

    public void setBalanceCash(Double balanceCash) {
        this.balanceCash = balanceCash;
    }

    public Double getBalanceVirtual() {
        return balanceVirtual;
    }

    public void setBalanceVirtual(Double balanceVirtual) {
        this.balanceVirtual = balanceVirtual;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }
}
