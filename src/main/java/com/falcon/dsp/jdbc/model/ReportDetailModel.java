package com.falcon.dsp.jdbc.model;

import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.enumration.ReportType;
import com.falcon.dsp.jdbc.entity.Report;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by falcon-zhangxg on 2016/4/21.
 */
public class ReportDetailModel {

    private DataItem total;

    private List<DataItem> list;

    public ReportDetailModel() {
        total = new DataItem();
        list = new ArrayList<>();
    }

    public void init(Report total, List<Report> list, ReportType reportType) {

        this.total.setViewCount(total.getViewCount());
        this.total.setClickCount(total.getClickCount());
        this.total.setCtr(total.getCtr());
        this.total.setCost(total.getCost());
        this.total.setEcpc(total.getEcpc());
        this.total.setEcpm(total.getEcpm());

        for (Report report : list) {
            DataItem item = new DataItem();
            switch (reportType) {
                case HOURLY:
                    item.setKey(report.getHour().toString());
                    break;
                case DAILY:
                    item.setKey(DateUtils.dateToString(report.getSettledTime(), "yyyy-MM-dd"));
                    break;
                case STRATEGY:
                    item.setKey(report.getName());
                    item.setValue(report.getSid().toString());
                    break;
                case GEO:
                    item.setKey(report.getStateName());
                    break;
                case GENDER:
                    item.setKey(report.getGenderName());
                    break;
                case AGE:
                    item.setKey(report.getAgeName());
                    break;
            }
            item.setViewCount(report.getViewCount());
            item.setClickCount(report.getClickCount());
            item.setCtr(report.getCtr());
            item.setCost(report.getCost());
            item.setEcpc(report.getEcpc());
            item.setEcpm(report.getEcpm());
            this.list.add(item);
        }
    }

    class DataItem {

        private String key;
        private String value;

        @JsonProperty("view_count")
        private Long viewCount;

        @JsonProperty("click_count")
        private Long clickCount;

        private double ctr;
        private double cost;
        private double ecpm;
        private double ecpc;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Long getViewCount() {
            return viewCount;
        }

        public void setViewCount(Long viewCount) {
            this.viewCount = viewCount;
        }

        public Long getClickCount() {
            return clickCount;
        }

        public void setClickCount(Long clickCount) {
            this.clickCount = clickCount;
        }

        public double getCtr() {
            return ctr;
        }

        public void setCtr(double ctr) {
            this.ctr = ctr;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public double getEcpm() {
            return ecpm;
        }

        public void setEcpm(double ecpm) {
            this.ecpm = ecpm;
        }

        public double getEcpc() {
            return ecpc;
        }

        public void setEcpc(double ecpc) {
            this.ecpc = ecpc;
        }
    }

    public DataItem getTotal() {
        return total;
    }

    public void setTotal(DataItem total) {
        this.total = total;
    }

    public List<DataItem> getList() {
        return list;
    }

    public void setList(List<DataItem> list) {
        this.list = list;
    }
}
