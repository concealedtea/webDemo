package com.falcon.dsp.jdbc.model;

/**
 * @author dongbin.yu
 * @from 2016-04-14
 * @since V1.0
 */
public class MaterialPreviewModel {

    private Integer id;

    private String img;

    private Integer status;

    private String statusName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
