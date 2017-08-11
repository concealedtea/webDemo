package com.falcon.dsp.jdbc.model;

/**
 * @author Zhouchunhui
 * @from 2016-04-28
 * @since V1.0
 */

public class AgencyTradeResultModel{

    private String account_type;

    private int agency_id;

    private long amount;

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public int getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(int agency_id) {
        this.agency_id = agency_id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
