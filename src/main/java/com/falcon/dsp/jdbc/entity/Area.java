package com.falcon.dsp.jdbc.entity;

import org.apache.ibatis.type.Alias;

/**
 * @author dongbin.yu
 * @from 2016-04-12
 * @since V1.0
 */
@Alias("area")
public class Area {

    private int id;

    private String name;

    private int parentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
