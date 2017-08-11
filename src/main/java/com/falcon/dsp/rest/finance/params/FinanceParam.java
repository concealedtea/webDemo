package com.falcon.dsp.rest.finance.params;


import com.falcon.dsp.enumration.FinanceType;
import com.falcon.dsp.rest.common.BasicParam;
import com.falcon.dsp.rest.common.TimeRange;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by xm on 2016/4/7.
 */
public class FinanceParam extends BasicParam {
    private int uid;

    @JsonProperty("account_type")
    private FinanceType financeType;

    private TimeRange  timeRange;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public FinanceType getFinanceType() {
        return financeType;
    }

    public void setFinanceType(FinanceType financeType) {
        this.financeType = financeType;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

}
