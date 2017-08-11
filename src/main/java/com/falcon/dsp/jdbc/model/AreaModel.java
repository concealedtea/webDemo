package com.falcon.dsp.jdbc.model;

import org.apache.ibatis.type.Alias;

/**
 * @author dongbin.yu
 * @from 2016-04-12
 * @since V1.0
 */
@Alias("areaModel")
public class AreaModel {

    private String id;

    private String text;

    private String parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
