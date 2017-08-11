package com.falcon.dsp.rest.finance.response;

import com.falcon.dsp.rest.common.GdtBaseResult;

/**
 * Created by falcon-zhangxg on 2016/4/5.
 */
public class GdtFinanceTradeTableResult extends GdtBaseResult {

    private GdtFinanceTradeTable data;

    public GdtFinanceTradeTable getData() {
        return data;
    }

    public void setData(GdtFinanceTradeTable data) {
        this.data = data;
    }
}
