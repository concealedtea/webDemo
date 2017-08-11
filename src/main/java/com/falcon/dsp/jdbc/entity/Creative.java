package com.falcon.dsp.jdbc.entity;


import com.falcon.dsp.rest.common.BasicEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * @author dongbin.yu
 * @from 2016-03-29
 * @since V1.0
 */
@Alias("creative")
public class Creative extends BasicEntity {

    private Integer id;

    /**
     * 广告主 Id
     */
    private Integer uid;

    /**
     * 广告推广计划 Id
     */
    private Integer cid;

    private Integer strategyId;

    /**
     * 创意名称，同一账户下名称不允许重复
     */
    @JsonProperty(value = "creative_name")
    private String creativeName;

    /**
     * 创意备注
     */
    private String remark;

    /**
     * 投放位置id
     */
    private Integer adSpacePositionId;

    /**
     * 创意目标 url
     */
    private String destUrl;

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

    public String getCreativeName() {
        return creativeName;
    }

    public void setCreativeName(String creativeName) {
        this.creativeName = creativeName;
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

    public String getDestUrl() {
        return destUrl;
    }

    public void setDestUrl(String destUrl) {
        this.destUrl = destUrl;
    }
}
