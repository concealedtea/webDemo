package com.falcon.dsp.rest.finance.request;

import com.falcon.dsp.enumration.FinanceType;
import com.falcon.dsp.rest.common.TimeRange;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wanxm
 * @from 2016-04-07
 * @since V1.0
 */
public class GdtFinance {

    private int uid;

    @JsonProperty("account_type")
    private FinanceType financeType;

    private TimeRange[] timeRange;

    @JsonProperty("page")
    private  int pageIndex;

    @JsonProperty("page_size")
    private  int pageSize;

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

    public TimeRange[] getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange[] timeRange) {
        this.timeRange = timeRange;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
