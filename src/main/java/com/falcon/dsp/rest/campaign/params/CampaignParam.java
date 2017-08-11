package com.falcon.dsp.rest.campaign.params;

import com.falcon.dsp.rest.common.BasicParam;

/**
 * Created by falcon-zhangxg on 2016/4/5.
 */
public class CampaignParam extends BasicParam {

    private Integer uid;
    private Integer cid;
    private Integer agencyId;
    private Double markup;

    public CampaignParam() {
    }

    public CampaignParam(Integer uid, Integer cid, Integer agencyId,Double markup) {
        this.uid = uid;
        this.cid = cid;
        this.agencyId = agencyId;
        this.markup = markup==null?1:markup;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public Double getMarkup() {
        return markup;
    }

    public void setMarkup(double markup) {
        this.markup = markup;
    }
}
