package com.falcon.dsp.enumration;

/**
 * @author Zhouchunhui
 * @from 2016-04-13
 * @since V1.0
 */
public enum CustomerRegistionType {

    CUSTOMERREGISTIONTYPE_UNKNOW("CUSTOMERREGISTIONTYPE_UNKNOW","未知"),
    CUSTOMERREGISTIONTYPE_CORPORATION("CUSTOMERREGISTIONTYPE_CORPORATION","企业用户"),
    CUSTOMERREGISTIONTYPE_INDIVIDUAL("CUSTOMERREGISTIONTYPE_INDIVIDUAL","个人用户"),
    UNKNOWN0("UNKNOWN(0)","未知0");
    private String name;
    private String description;

    private CustomerRegistionType(String name, String description) {
        this.name = name;
        this.description = description;
    }


    @Override
    public String toString() {
        return name;
    }


}
