package com.falcon.dsp.rest.creative.request;

import com.falcon.dsp.enumration.FalCreativeStatus;
import com.falcon.dsp.jdbc.entity.Material;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author dongbin.yu
 * @from 2016-03-29
 * @since V1.0
 */
public class GdtCreative {

    private Integer tid;

    /**
     * 广告主 Id
     */
    private Integer uid;

    /**
     * 广告推广计划 Id
     */
    private Integer cid;

    /**
     * 广告组 Id
     */
    private Integer aid;

    /**
     * 创意名称，同一账户下名称不允许重复
     */
    private String tname;

    /**
     * 创意规格 Id
     */
    @JsonProperty(value = "crt_size")
    private int crtSize;

    /**
     * 创意标题
     */
    private String title;

    /**
     * 创意描述
     */
    private String desc;

    /**
     * 创意图片 url
     */
    @JsonProperty(value = "image_url")
    private String imageUrl;

    /**
     * 创意图片 2 image2_url
     */
    @JsonProperty(value = "image2_url")
    private String image2Url;

    /**
     * 图片ID
     */
    @JsonProperty(value = "image_id")
    private String imageId;

    /**
     * 创意目标 url
     */
    @JsonProperty(value = "dest_url")
    private String destUrl;

    /**
     * 曝光监控地址（仅点评调用方允许使用）,且不允许更新为空
     */
    @JsonIgnore
    private String impressionTrackingUrl;

    /**
     * 动态创意模板 id（仅动态创意特性允许使用）template_id
     */
    @JsonProperty(value = "template_id")
    private String templateId;

    /**
     * 动态创意模板物料标 material_label
     */
    @JsonProperty(value = "material_label")
    private String materialLabel;

    private String header;

    /**
     * 状态
     */
    private FalCreativeStatus status;

    public Material getMaterial(int creativeId){
        Material m = new Material();
        m.setGdtCreativeId(this.tid);
        m.setCreativeId(creativeId);
        m.setCrtSize(this.crtSize);
        m.setDescription(this.desc);
        m.setImage2Url(this.image2Url);
        m.setImageUrl(this.imageUrl);
        m.setTitle(this.title);
        m.setStatus(this.status.getValue());
        return m;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getDestUrl() {
        return destUrl;
    }

    public void setDestUrl(String destUrl) {
        this.destUrl = destUrl;
    }

    public String getImpressionTrackingUrl() {
        return impressionTrackingUrl;
    }

    public void setImpressionTrackingUrl(String impressionTrackingUrl) {
        this.impressionTrackingUrl = impressionTrackingUrl;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getMaterialLabel() {
        return materialLabel;
    }

    public void setMaterialLabel(String materialLabel) {
        this.materialLabel = materialLabel;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public FalCreativeStatus getStatus() {
        return status;
    }

    public void setStatus(FalCreativeStatus status) {
        this.status = status;
    }
}
