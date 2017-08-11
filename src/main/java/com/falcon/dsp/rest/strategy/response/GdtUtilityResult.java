package com.falcon.dsp.rest.strategy.response;

import com.falcon.dsp.rest.common.GdtBaseResult;
import com.falcon.dsp.rest.strategy.request.GdtUtility;

/**
 * Created by falcon-zhangxg on 2016/4/18.
 */
public class GdtUtilityResult extends GdtBaseResult {

    private GdtUtility data;

    public GdtUtility getData() {
        return data;
    }

    public void setData(GdtUtility data) {
        this.data = data;
    }

}
