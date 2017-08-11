package com.falcon.dsp.jdbc.model;

import com.falcon.dsp.jdbc.entity.Material;

import java.util.List;

/**
 * @author dongbin.yu
 * @from 2016-04-09
 * @since V1.0
 */
public class CreativeModel {

    private Integer creativeId;

    private String creativeName;

    private Integer campaignId;

    private String destUrl;

    private String remark;

    private Integer strategyId;

    private int adSpacePositionId;

    private List<Material> materials;

    public Integer getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Integer creativeId) {
        this.creativeId = creativeId;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public String getCreativeName() {
        return creativeName;
    }

    public void setCreativeName(String creativeName) {
        this.creativeName = creativeName;
    }

    public String getDestUrl() {
        return destUrl;
    }

    public void setDestUrl(String destUrl) {
        this.destUrl = destUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public int getAdSpacePositionId() {
        return adSpacePositionId;
    }

    public void setAdSpacePositionId(int adSpacePositionId) {
        this.adSpacePositionId = adSpacePositionId;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }
}
