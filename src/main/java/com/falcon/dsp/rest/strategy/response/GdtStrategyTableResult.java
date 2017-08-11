package com.falcon.dsp.rest.strategy.response;

import com.falcon.dsp.rest.common.GdtBaseResult;

/**
 * @author Zhouchunhui
 * @from 2016-05-06
 * @since V1.0
 */
public class GdtStrategyTableResult  extends GdtBaseResult{

    private GdtStrategyTable data;

    public GdtStrategyTable getData() {
        return data;
    }

    public void setData(GdtStrategyTable data) {
        this.data = data;
    }
}
