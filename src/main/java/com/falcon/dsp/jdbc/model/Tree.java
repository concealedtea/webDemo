package com.falcon.dsp.jdbc.model;

/**
 * Created by falcon-zhangxg on 2016/4/7.
 */
public class Tree {

    private Integer id;
    private String name;

    public Tree() {

    }

    public Tree(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
