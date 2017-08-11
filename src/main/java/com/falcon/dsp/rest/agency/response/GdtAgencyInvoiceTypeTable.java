package com.falcon.dsp.rest.agency.response;

import com.falcon.dsp.rest.common.Conf;

import java.util.List;

/**
 * @author Zhouchunhui
 * @from 2016-04-12
 * @since V1.0
 */
public class GdtAgencyInvoiceTypeTable {

    private List<GdtAgencyInvoiceType> list;

    private Conf conf;

    public List<GdtAgencyInvoiceType> getList() {
        return list;
    }

    public void setList(List<GdtAgencyInvoiceType> list) {
        this.list = list;
    }

    public Conf getConf() {
        return conf;
    }

    public void setConf(Conf conf) {
        this.conf = conf;
    }
}
