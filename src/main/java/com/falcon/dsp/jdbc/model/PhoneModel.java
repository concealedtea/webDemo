package com.falcon.dsp.jdbc.model;

import org.apache.ibatis.type.Alias;

/**
 * Created by thatq on 8/10/2017.
 */
@Alias("phone")
public class PhoneModel {
    private String name;
    private Integer count;

    public PhoneModel() {

    }

    public PhoneModel(String name, Integer count) {
        this.count = count;
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer id) {
        this.count = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
