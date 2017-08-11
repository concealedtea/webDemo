package com.falcon.dsp.jdbc.entity;

import org.apache.ibatis.type.Alias;

/**
 * @author dongbin.yu
 * @from 2016-04-11
 * @since V1.0
 */
@Alias("imageDemo")
public class ImageDemo {

    private int id;

    private String url;

    private int adSpacePositionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAdSpacePositionId() {
        return adSpacePositionId;
    }

    public void setAdSpacePositionId(int adSpacePositionId) {
        this.adSpacePositionId = adSpacePositionId;
    }
}
