package com.falcon.dsp.jdbc.model;

import org.apache.ibatis.type.Alias;

/**
 * @author dongbin.yu
 * @from 2016-04-14
 * @since V1.0
 */
@Alias("materialRuleModel")
public class MaterialRuleModel {

    private int id;

    private Integer width;

    private Integer height;

    private Integer gdtCreativeId;

    private Integer rWidth;

    private Integer rHeight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getGdtCreativeId() {
        return gdtCreativeId;
    }

    public void setGdtCreativeId(Integer gdtCreativeId) {
        this.gdtCreativeId = gdtCreativeId;
    }

    public Integer getrWidth() {
        return rWidth;
    }

    public void setrWidth(Integer rWidth) {
        this.rWidth = rWidth;
    }

    public Integer getrHeight() {
        return rHeight;
    }

    public void setrHeight(Integer rHeight) {
        this.rHeight = rHeight;
    }
}
