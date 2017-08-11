package com.falcon.dsp.rest.finance.response;

import com.falcon.dsp.enumration.FinanceTradeType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by xm on 2016/4/8.
 */
public class GdtTrade {
    private  String date;
    @JsonProperty("trade_time")
    private  int tradteTime;
    @JsonProperty("trade_type")
    private FinanceTradeType financeTradeType;
    private int  amount;
    private String desc;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTradteTime() {
        return tradteTime;
    }

    public void setTradteTime(int tradteTime) {
        this.tradteTime = tradteTime;
    }

    public FinanceTradeType getFinanceTradeType() {
        return financeTradeType;
    }

    public void setFinanceTradeType(FinanceTradeType financeTradeType) {
        this.financeTradeType = financeTradeType;
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
