package com.falcon.dsp.jdbc.entity;

import org.apache.ibatis.type.Alias;

/**
 * @author dongbin.yu
 * @from 2016-04-07
 * @since V1.0
 */
@Alias("platform")
public class StrategyPlatform {

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
