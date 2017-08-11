package com.falcon.dsp.rest.advertiser.response;

import com.falcon.dsp.rest.common.GdtBaseResult;

/**
 * @author Zhouchunhui
 * @from 2016-04-12
 * @since V1.0
 */
public class GdtAdvertiserResult extends GdtBaseResult {

    private GdtAdvertiser data;

    public GdtAdvertiser getData() {
        return data;
    }

    public void setData(GdtAdvertiser data) {
        this.data = data;
    }
}
