package com.falcon.dsp.jdbc.entity;

/**
 * @author dongbin.yu
 * @from 2016-04-07
 * @since V1.0
 */
public class AdSpacePosition {

    private int id;

    private int platformId;

    private int adFormatId;

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public int getAdFormatId() {
        return adFormatId;
    }

    public void setAdFormatId(int adFormatId) {
        this.adFormatId = adFormatId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
