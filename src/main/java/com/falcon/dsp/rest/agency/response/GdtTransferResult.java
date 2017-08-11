package com.falcon.dsp.rest.agency.response;

import com.falcon.dsp.rest.common.GdtBaseResult;

/**
 * @author Zhouchunhui
 * @from 2016-04-14
 * @since V1.0
 * 该结果与recover通用
 */
public class GdtTransferResult extends GdtBaseResult {

    private GdtTransferModel data;

    public GdtTransferModel getData() {
        return data;
    }

    public void setData(GdtTransferModel data) {
        this.data = data;
    }
}
