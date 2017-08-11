package com.falcon.dsp.jdbc.entity;

import com.falcon.dsp.rest.common.BasicEntity;
import org.apache.ibatis.type.Alias;

/**
 * @author dongbin.yu
 * @from 2016-04-12
 * @since V1.0
 */
@Alias("target")
public class Target extends BasicEntity {

    private Integer tid;

    private Integer uid;

    private String targetValue;

    private String description;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
