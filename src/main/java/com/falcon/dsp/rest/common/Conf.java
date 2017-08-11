package com.falcon.dsp.rest.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2016/4/1.
 */
public class Conf {

    @JsonProperty(value = "total_num")
    private int totalNum;

    @JsonProperty(value = "total_page")
    private int totalPage;

    @JsonProperty(value = "page_size")
    private int pageSize;

    private int page;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
