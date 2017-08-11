package com.falcon.dsp.param;

import java.util.List;

/**
 * @author dongbin.yu
 * @from 2016-04-06
 * @since V1.0
 */
public class CreativeParam extends BaseParam {

    /**
     * 本地的id
     */
    private Integer id;

    private Integer uid;

    /**
     * 广点通的创意id
     */
    private Integer cid;

    private Integer strategyId;

    private String name;

    private String remark;

    private String destUrl;

    private Integer adSpacePositionId;

    private List<MaterialParam> materials;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public String getDestUrl() {
        return destUrl;
    }

    public void setDestUrl(String destUrl) {
        this.destUrl = destUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAdSpacePositionId() {
        return adSpacePositionId;
    }

    public void setAdSpacePositionId(Integer adSpacePositionId) {
        this.adSpacePositionId = adSpacePositionId;
    }

    public List<MaterialParam> getMaterials() {
        return materials;
    }

    public void setMaterials(List<MaterialParam> materials) {
        this.materials = materials;
    }
}
