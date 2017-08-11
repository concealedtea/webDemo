package com.falcon.dsp.jdbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.type.Alias;

/**
 * @author dongbin.yu
 * @from 2016-04-09
 * @since V1.0
 */
@Alias("creativeListModel")
public class CreativeListModel {

    @JsonProperty("id")
    private Integer creativeId;

    @JsonProperty("name")
    private String creativeName;

    private boolean relation;

    @JsonProperty("ad_format")
    private String adFormat;

    @JsonProperty("plat_form")
    private String platForm;

    @JsonProperty("dest_url")
    private String destUrl;

    public Integer getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Integer creativeId) {
        this.creativeId = creativeId;
    }

    public String getCreativeName() {
        return creativeName;
    }

    public void setCreativeName(String creativeName) {
        this.creativeName = creativeName;
    }

    public boolean isRelation() {
        return relation;
    }

    public void setRelation(boolean relation) {
        this.relation = relation;
    }

    public String getAdFormat() {
        return adFormat;
    }

    public void setAdFormat(String adFormat) {
        this.adFormat = adFormat;
    }

    public String getPlatForm() {
        return platForm;
    }

    public void setPlatForm(String platForm) {
        this.platForm = platForm;
    }

    public String getDestUrl() {
        return destUrl;
    }

    public void setDestUrl(String destUrl) {
        this.destUrl = destUrl;
    }
}
