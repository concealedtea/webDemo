package com.falcon.dsp.rest.strategy.response;

import com.falcon.dsp.rest.common.GdtBaseResult;
import com.falcon.dsp.rest.strategy.request.GdtStrategy;

/**
 * Created by falcon-zhangxg on 2016/4/14.
 */
public class GdtStrategyResult extends GdtBaseResult {

    private GdtStrategy data;

    public GdtStrategy getData() {
        return data;
    }

    public void setData(GdtStrategy data) {
        this.data = data;
    }
}
