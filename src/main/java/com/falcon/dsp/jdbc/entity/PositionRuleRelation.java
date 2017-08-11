package com.falcon.dsp.jdbc.entity;

/**
 * @author dongbin.yu
 * @from 2016-04-07
 * @since V1.0
 */
public class PositionRuleRelation {

    private int id;

    private int platFormId;

    private int imageRuleId;

    public int getPlatFormId() {
        return platFormId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlatFormId(int platFormId) {
        this.platFormId = platFormId;
    }

    public int getImageRuleId() {
        return imageRuleId;
    }

    public void setImageRuleId(int imageRuleId) {
        this.imageRuleId = imageRuleId;
    }
}
