package com.falcon.dsp.rest.agency.response;

import com.falcon.dsp.rest.common.GdtBaseResult;

/**
 * Created by Zhouchunhui on 2016/4/12.
 */
public class GdtAgencyTableResult extends GdtBaseResult{

    private GdtAgencyTable data;

    public GdtAgencyTable getData() {
        return data;
    }

    public void setData(GdtAgencyTable data) {
        this.data = data;
    }
}
