package com.falcon.dsp.param;

import com.falcon.dsp.jdbc.model.TableParam;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author dongbin.yu
 * @from 2016-04-14
 * @since V1.0
 */
public class CreativeTableParam extends TableParam {

    private Integer cid;

    private boolean filter;

    @JsonProperty("strategy_id")
    private Integer strategyId;

    @Override
    public Integer getStrategyId() {
        return strategyId;
    }

    @Override
    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}
