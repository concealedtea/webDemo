package com.falcon.dsp.jdbc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * @author dongbin.yu
 * @from 2016-04-19
 * @since V1.0
 */
@Alias("industry")
public class Industry {

    private long id;

    private String name;

    @JsonProperty("parent_id")
    private long parentId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
