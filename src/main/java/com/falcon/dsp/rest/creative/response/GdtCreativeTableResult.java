package com.falcon.dsp.rest.creative.response;

import com.falcon.dsp.rest.common.GdtBaseResult;

/**
 * @author Zhouchunhui
 * @from 2016-05-06
 * @since V1.0
 */
public class GdtCreativeTableResult extends GdtBaseResult{

    private GdtCreativeTable data;

    public GdtCreativeTable getData() {
        return data;
    }

    public void setData(GdtCreativeTable data) {
        this.data = data;
    }
}
