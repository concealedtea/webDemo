package com.falcon.dsp.rest.finance.response;

import com.falcon.dsp.rest.common.Conf;

import java.util.List;

/**
 * Created by falcon-zhangxg on 2016/4/5.
 */
public class GdtFinanceTradeTable {

    private List<GdtTrade> list;
    private Conf conf;

    public List<GdtTrade> getList() {
        return list;
    }

    public void setList(List<GdtTrade> list) {
        this.list = list;
    }

    public Conf getConf() {
        return conf;
    }

    public void setConf(Conf conf) {
        this.conf = conf;
    }

}
