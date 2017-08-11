package com.falcon.dsp.rest.strategy.response;

import com.falcon.dsp.rest.common.Conf;
import com.falcon.dsp.rest.strategy.request.GdtStrategy;

import java.util.List;

/**
 * @author Zhouchunhui
 * @from 2016-05-06
 * @since V1.0
 */
public class GdtStrategyTable {
    private List<GdtStrategy> list;

    private Conf conf;

    public List<GdtStrategy> getList() {
        return list;
    }

    public void setList(List<GdtStrategy> list) {
        this.list = list;
    }

    public Conf getConf() {
        return conf;
    }

    public void setConf(Conf conf) {
        this.conf = conf;
    }
}
