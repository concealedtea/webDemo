package com.falcon.dsp.jdbc.entity;

/**
 * @author dongbin.yu
 * @from 2016-04-07
 * @since V1.0
 */
public class ImageRule {

    private int id;

    private int width;

    private int height;

    private String title;

    private String description;

    private Integer imageUrlId;

    private Integer image2UrlId;

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

    public Integer getImageUrlId() {
        return imageUrlId;
    }

    public void setImageUrlId(Integer imageUrlId) {
        this.imageUrlId = imageUrlId;
    }

    public Integer getImage2UrlId() {
        return image2UrlId;
    }

    public void setImage2UrlId(Integer image2UrlId) {
        this.image2UrlId = image2UrlId;
    }
}
