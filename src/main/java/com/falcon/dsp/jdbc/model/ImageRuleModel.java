package com.falcon.dsp.jdbc.model;

import com.falcon.dsp.jdbc.entity.ImageUrl;

/**
 * @author dongbin.yu
 * @from 2016-04-07
 * @since V1.0
 */
public class ImageRuleModel {

    private int id;

    private int width;

    private int height;

    private String title;

    private String description;

    private ImageUrl imageUrl;

    private ImageUrl image2Url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public ImageUrl getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ImageUrl imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ImageUrl getImage2Url() {
        return image2Url;
    }

    public void setImage2Url(ImageUrl image2Url) {
        this.image2Url = image2Url;
    }
}
