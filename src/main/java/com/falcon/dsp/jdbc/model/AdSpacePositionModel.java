package com.falcon.dsp.jdbc.model;

import com.falcon.dsp.jdbc.entity.ImageDemo;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @author dongbin.yu
 * @from 2016-04-07
 * @since V1.0
 */
@Alias("adSpacePositionModel")
public class AdSpacePositionModel {

    private int id;

    @JsonProperty("platform_id")
    private int platformId;

    private String platform;

    @JsonProperty("ad_format_id")
    private int adFormatId;

    @JsonProperty("ad_format")
    private String adFormat;

    private String description;

    private List<ImageRuleModel> specifics;

    private List<ImageDemo> imageDemos;

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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAdFormat() {
        return adFormat;
    }

    public void setAdFormat(String adFormat) {
        this.adFormat = adFormat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ImageRuleModel> getSpecifics() {
        return specifics;
    }

    public void setSpecifics(List<ImageRuleModel> specifics) {
        this.specifics = specifics;
    }

    public List<ImageDemo> getImageDemos() {
        return imageDemos;
    }

    public void setImageDemos(List<ImageDemo> imageDemos) {
        this.imageDemos = imageDemos;
    }
}
