package com.falcon.dsp.param;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author dongbin.yu
 * @from 2016-04-07
 * @since V1.0
 */
public class MaterialParam {

    private Integer id;

    @JsonProperty("crt_size")
    private int crtSize;

    private String title;

    private String description;

    private String imageUrl;

    private String image2Url;

    private int status;

    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCrtSize() {
        return crtSize;
    }

    public void setCrtSize(int crtSize) {
        this.crtSize = crtSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImage2Url() {
        return image2Url;
    }

    public void setImage2Url(String image2Url) {
        this.image2Url = image2Url;
    }
}
