package com.falcon.dsp.rest.campaign.response;

import com.falcon.dsp.rest.campaign.request.GdtCampaign;
import com.falcon.dsp.rest.common.Conf;

import java.util.List;

/**
 * Created by falcon-zhangxg on 2016/4/5.
 */
public class GdtCampaignTable {

    private List<GdtCampaign> list;
    private Conf conf;

    public List<GdtCampaign> getList() {
        return list;
    }

    public void setList(List<GdtCampaign> list) {
        this.list = list;
    }

    public Conf getConf() {
        return conf;
    }

    public void setConf(Conf conf) {
        this.conf = conf;
    }

}
