package com.falcon.dsp.rest.finance.request;

import com.falcon.dsp.enumration.FinanceTradeType;
import com.falcon.dsp.enumration.FinanceType;
import com.falcon.dsp.rest.common.TimeRange;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wanxm
 * @from 2016-04-07
 * @since V1.0
 */
//应该用不上，暂时留着
public class GdtFinanceDetail {

    private int uid;

    @JsonProperty("account_type")
    private FinanceType financeType;

    @JsonProperty("trad_type")
    private FinanceTradeType financeTradeType;

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

    public FinanceTradeType getFinanceTradeType() {
        return financeTradeType;
    }

    public void setFinanceTradeType(FinanceTradeType financeTradeType) {
        this.financeTradeType = financeTradeType;
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
