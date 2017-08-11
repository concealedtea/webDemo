package com.falcon.dsp.rest.finance.response;

import com.falcon.dsp.rest.common.GdtBaseResult;

/**
 * Created by falcon-zhangxg on 2016/4/5.
 */
public class GdtFinanceBalanceTableResult extends GdtBaseResult {

    private GdtFinanceBalanceTable data;

    public GdtFinanceBalanceTable getData() {
        return data;
    }

    public void setData(GdtFinanceBalanceTable data) {
        this.data = data;
    }
}
