package com.falcon.dsp.rest.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author dongbin.yu
 * @from 2016-04-07
 * @since V1.0
 */
public class BasicEntity {

    private Integer status;

    /**
     * 创建时间
     */
    @JsonProperty(value = "create_time")
    private Date createTime;

    /**
     * 最近一次修改时间
     */
    @JsonProperty(value = "last_mod_time")
    private Date lastModTime;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModTime() {
        return lastModTime;
    }

    public void setLastModTime(Date lastModTime) {
        this.lastModTime = lastModTime;
    }
}
