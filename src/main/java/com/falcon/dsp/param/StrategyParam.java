package com.falcon.dsp.param;

import com.falcon.dsp.common.JsonUtil;
import com.falcon.dsp.common.MathUtil;
import com.falcon.dsp.common.StringUtil;
import com.falcon.dsp.enumration.CostType;
import com.falcon.dsp.enumration.FalStrategyStatus;
import com.falcon.dsp.enumration.ProductType;
import com.falcon.dsp.jdbc.entity.Strategy;
import com.falcon.dsp.jdbc.entity.Target;
import com.falcon.dsp.rest.strategy.request.GdtStrategy;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by falcon-zhangxg on 2016/4/13.
 */
public class StrategyParam extends BaseParam {

    /**
     * 订单ID
     */
    private Integer cid;

    /**
     * 策略ID
     */
    private Integer id;
    @JsonProperty("sname")
    private String name;
    private Date start;
    private Date end;
    private String description;
    @JsonProperty("creative_id")
    private Integer creativeId;
    private CostType costType = CostType.COSTTYPE_CPC;
    private Double bid;
    private Integer status;
    @JsonProperty("target_param")
    private TargetParam targetParam;

    public Strategy fetchStrategyDBModel() {
        Strategy strategy = new Strategy();
        strategy.setUid(this.getUid());
        strategy.setCampaignId(this.getCid());
        strategy.setSid(this.getId());
        strategy.setBid(this.getBid());
        strategy.setSname(this.getName());
        strategy.setStart(this.getStart());
        strategy.setEnd(this.getEnd());
        strategy.setDescription(this.getDescription());
        strategy.setCreativeId(this.getCreativeId());
        strategy.setBid(this.getBid());
        strategy.setCreateTime(new Date());
        strategy.setStatus(this.getStatus());
        return strategy;
    }

    public GdtStrategy fetchGdtStrategyModel() {
        GdtStrategy gdtStrategy = new GdtStrategy();
        if (this.getUid() != null) gdtStrategy.setUid(this.getUid());
        if (this.getCid() != null) gdtStrategy.setCid(this.getCid());
        if (this.getName() != null) gdtStrategy.setAname(this.getName());
        if (this.getId() != null) gdtStrategy.setAid(this.getId());
        if (this.getCostType() != null) gdtStrategy.setCostType(this.getCostType());
        // 策略出价markup之后四舍五入存到广点通，单位为分，由于在controller层从前台拿到出价数据(单位为元)后已乘以100转化为分，此处不需要再乘以100
        if (this.getBid() != null) gdtStrategy.setBid((int) (MathUtil.doubleFormatRoundUp(this.getBid())));
        if (this.getStart() != null) gdtStrategy.setBeginTime((int) (this.getStart().getTime() / 1000));
        if (this.getEnd() != null) gdtStrategy.setEndTime((int) (this.getEnd().getTime() / 1000));

        if (targetParam!= null &&targetParam.getHours() != null) {
            gdtStrategy.setTimeset(StringUtil.convertTargetDate(targetParam.getHours()));
        }

        gdtStrategy.setProductType(ProductType.PRODUCTTYPE_URL_EXCEPT_TENCENT_DOMAIN);
        gdtStrategy.setProductId(" ");
        if (this.status != null) {
            gdtStrategy.setStatus(FalStrategyStatus.forValue(this.status).getGdtAdStatus());
        }
        return gdtStrategy;
    }

    public void putStrategyDBModel(Strategy strategy) {
        if (strategy.getCampaignId() != null) this.setCid(strategy.getCampaignId());
        if (strategy.getSid() != null) this.setId(strategy.getSid());
        if (strategy.getSname() != null) this.setName(strategy.getSname());
        if (strategy.getStart() != null) this.setStart(strategy.getStart());
        if (strategy.getEnd() != null) this.setEnd(strategy.getEnd());
        if (strategy.getDescription() != null) this.setDescription(strategy.getDescription());
        if (strategy.getCreativeId() != null) this.setCreativeId(strategy.getCreativeId());
        if (strategy.getBid() != null) this.setBid(strategy.getBid());
        if (strategy.getStatus() != null) this.setStatus(strategy.getStatus());
        if (strategy.getTarget() != null) {
            Target target = strategy.getTarget();
            TargetParam targetParam = JsonUtil.fromJson(target.getTargetValue() == null ? "{}" : target.getTargetValue(), TargetParam.class);
            this.setTargetParam(targetParam);
        }
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Integer creativeId) {
        this.creativeId = creativeId;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public CostType getCostType() {
        return costType;
    }

    public void setCostType(CostType costType) {
        this.costType = costType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public TargetParam getTargetParam() {
        return targetParam;
    }

    public void setTargetParam(TargetParam targetParam) {
        this.targetParam = targetParam;
    }

}
