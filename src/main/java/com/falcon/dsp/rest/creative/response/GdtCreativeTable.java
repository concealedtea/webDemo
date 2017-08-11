package com.falcon.dsp.rest.creative.response;

import com.falcon.dsp.rest.common.Conf;
import com.falcon.dsp.rest.creative.request.GdtCreative;

import java.util.List;

/**
 * Created by zhangyujuan on 2016/5/6.
 */
public class GdtCreativeTable {

    private List<GdtCreative> list;
    private Conf conf;

    public List<GdtCreative> getList() {
        return list;
    }

    public void setList(List<GdtCreative> list) {
        this.list = list;
    }

    public Conf getConf() {
        return conf;
    }

    public void setConf(Conf conf) {
        this.conf = conf;
    }
}
