package com.falcon.dsp.rest.creative.response;

import com.falcon.dsp.rest.common.GdtBaseResult;
import com.falcon.dsp.rest.creative.request.GdtCreative;

/**
 * @author dongbin.yu
 * @from 2016-04-05
 * @since V1.0
 */
public class GdtCreativeResult extends GdtBaseResult {

    private GdtCreative data;

    public GdtCreative getData() {
        return data;
    }

    public void setData(GdtCreative data) {
        this.data = data;
    }
}
