package com.falcon.dsp.rest.advertiser.request;

/**
 * @author Zhouchunhui
 * @from 2016-04-12
 * @since V1.0
 */
public class GdtAdvertiserParam {

    private String uid;//广告主id

    private String fields;//json(array of string)需要获取的字段。若不传则查询所有字段

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
