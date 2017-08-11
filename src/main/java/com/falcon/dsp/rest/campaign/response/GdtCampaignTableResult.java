package com.falcon.dsp.rest.campaign.response;

import com.falcon.dsp.rest.common.GdtBaseResult;

/**
 * Created by falcon-zhangxg on 2016/4/5.
 */
public class GdtCampaignTableResult extends GdtBaseResult {

    private GdtCampaignTable data;

    public GdtCampaignTable getData() {
        return data;
    }

    public void setData(GdtCampaignTable data) {
        this.data = data;
    }
}
