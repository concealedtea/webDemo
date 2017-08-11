package com.falcon.dsp.rest.agency.response;

import com.falcon.dsp.rest.common.GdtBaseResult;

/**
 * @author Zhouchunhui
 * @from 2016-04-12
 * @since V1.0
 */
public class GdtAgencyInvoiceTypeTableResult extends GdtBaseResult{

    private GdtAgencyInvoiceTypeTable data;

    public GdtAgencyInvoiceTypeTable getData() {
        return data;
    }

    public void setData(GdtAgencyInvoiceTypeTable data) {
        this.data = data;
    }
}
