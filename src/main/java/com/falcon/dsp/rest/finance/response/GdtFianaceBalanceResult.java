package com.falcon.dsp.rest.finance.response;

import com.falcon.dsp.rest.common.GdtBaseResult;

/**
 * Created by falcon-zhangxg on 2016/4/5.
 */
//应该用不上，暂时留着
public class GdtFianaceBalanceResult extends GdtBaseResult {

    private GdtFinanceBalance data;

    public GdtFinanceBalance getData() {
        return data;
    }

    public void setData(GdtFinanceBalance data) {
        this.data = data;
    }
}
