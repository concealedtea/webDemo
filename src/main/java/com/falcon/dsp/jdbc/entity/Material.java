package com.falcon.dsp.jdbc.entity;

import com.falcon.dsp.rest.common.BasicEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * @author dongbin.yu
 * @from 2016-04-08
 * @since V1.0
 */
@Alias("material")
public class Material extends BasicEntity {

    private Integer id;

    private Integer gdtCreativeId;

    private Integer creativeId;

    @JsonProperty("crt_size")
    private int crtSize;

    private String title;

    private String description;

    private String imageUrl;

    private String image2Url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGdtCreativeId() {
        return gdtCreativeId;
    }

    public void setGdtCreativeId(Integer gdtCreativeId) {
        this.gdtCreativeId = gdtCreativeId;
    }

    public Integer getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Integer creativeId) {
        this.creativeId = creativeId;
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

    @Override
    public boolean equals(Object obj) {
        return this.id.equals(((Material)obj).getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
