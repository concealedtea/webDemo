package com.falcon.dsp.rest.agency.request;

import com.falcon.dsp.enumration.FinanceType;

/**
 * @author Zhouchunhui
 * @from 2016-04-14
 * @since V1.0
 * 与recover通用
 */
public class TransferParam {

    private long uid;
    private FinanceType account_type;
    private long amount;
    private String outer_bill_no;
    private String memo;
    private String expire_date;//格式YYYY-MM-dd 为空则永不过期，现金、内部领用、银证账户的划账不能使用该字段

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public FinanceType getAccount_type() {
        return account_type;
    }

    public void setAccount_type(FinanceType account_type) {
        this.account_type = account_type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getOuter_bill_no() {
        return outer_bill_no;
    }

    public void setOuter_bill_no(String outer_bill_no) {
        this.outer_bill_no = outer_bill_no;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }
}
