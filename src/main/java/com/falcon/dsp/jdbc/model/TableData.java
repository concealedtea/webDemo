package com.falcon.dsp.jdbc.model;

import java.util.List;

/**
 * Created by falcon-zhangxg on 2016/4/6.
 */
public class TableData<T> {
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private List<T> data;

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}