package com.falcon.dsp.rest.campaign.response;

import com.falcon.dsp.rest.campaign.request.GdtCampaign;
import com.falcon.dsp.rest.common.GdtBaseResult;

/**
 * Created by falcon-zhangxg on 2016/4/5.
 */
public class GdtCampaignResult extends GdtBaseResult {

    private GdtCampaign data;

    public GdtCampaign getData() {
        return data;
    }

    public void setData(GdtCampaign data) {
        this.data = data;
    }
}
