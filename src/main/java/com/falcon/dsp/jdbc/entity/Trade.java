package com.falcon.dsp.jdbc.entity;

import java.util.Date;

/**
 * Created by xm on 2016/4/8.
 */
public class Trade {
    /*
    *      "time":"2016/03/29",
           "type":"现金",
           "amount":20000,
           "remark":"test备注"
    * */
    private Date time;
    private String type;
    private  int amount;
    private String remark;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
