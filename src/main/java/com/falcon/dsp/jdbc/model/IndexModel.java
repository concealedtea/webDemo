package com.falcon.dsp.jdbc.model;

import com.falcon.dsp.common.DateUtils;
import com.falcon.dsp.jdbc.entity.Report;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyujuan on 2016/4/19.
 */
public class IndexModel {
    private DataItem total;

    private List<DataItem> gender;
    private List<DataItem> area;
    private List<DataItem> age;
    private List<DataItem> list;

    public IndexModel() {
        this.total = new DataItem();
        this.gender = new ArrayList<>();
        this.area = new ArrayList<>();
        this.age = new ArrayList<>();
        this.list = new ArrayList<>();
    }

    public void init(Report totalReport, List<Report> list, List<Report> gender, List<Report> area, List<Report> ages, boolean isHour) {
        if(totalReport!=null) {
            this.total.setViewCount(totalReport.getViewCount());
            this.total.setClickCount(totalReport.getClickCount());
            this.total.setCtr(totalReport.getCtr());
            this.total.setCost(totalReport.getCost());
            this.total.setEcpc(totalReport.getEcpc());
            this.total.setEcpm(totalReport.getEcpm());
        }
        for (Report item : list) {
            String key = null;
            if (isHour) {
                key = item.getHour().toString();
            } else {
                key = DateUtils.dateToString(item.getSettledTime(), "yyyy-MM-dd");
            }
            DataItem dataItem = new DataItem(
                    key,
                    item.getViewCount(),
                    item.getClickCount(),
                    item.getCtr(),
                    item.getCost(),
                    item.getEcpm(),
                    item.getEcpc()
            );
            this.list.add(dataItem);

        }

        for (Report item : gender) {
            DataItem dataItem = new DataItem(
                    item.getGenderName(),
                    item.getViewCount(),
                    item.getClickCount(),
                    item.getCtr(),
                    item.getCost(),
                    item.getEcpm(),
                    item.getEcpc()
            );
            this.gender.add(dataItem);
        }

        for (Report item : ages) {
            DataItem dataItem = new DataItem(
                    item.getAgeName(),
                    item.getViewCount(),
                    item.getClickCount(),
                    item.getCtr(),
                    item.getCost(),
                    item.getEcpm(),
                    item.getEcpc()
            );
            this.age.add(dataItem);
        }

        for (Report item : area) {
            DataItem dataItem = new DataItem(
                    item.getStateName(),
                    item.getViewCount(),
                    item.getClickCount(),
                    item.getCtr(),
                    item.getCost(),
                    item.getEcpm(),
                    item.getEcpc()
            );
            this.area.add(dataItem);
        }

    }

    public DataItem getTotal() {
        return total;
    }

    public void setTotal(DataItem total) {
        this.total = total;
    }

    public List<DataItem> getGender() {
        return gender;
    }

    public void setGender(List<DataItem> gender) {
        this.gender = gender;
    }

    public List<DataItem> getArea() {
        return area;
    }

    public void setArea(List<DataItem> area) {
        this.area = area;
    }

    public List<DataItem> getAge() {
        return age;
    }

    public void setAge(List<DataItem> age) {
        this.age = age;
    }

    public List<DataItem> getList() {
        return list;
    }

    public void setList(List<DataItem> list) {
        this.list = list;
    }

    class DataItem {

        private String key;

        private String value;

        @JsonProperty("view_count")
        private long viewCount;

        @JsonProperty("click_count")
        private long clickCount;
        private double ctr;
        private double cost;
        private double ecpm;
        private double ecpc;

        public DataItem (){

        }

        public DataItem(String key, Long viewCount, Long clickCount, double ctr, double cost, double ecpm, double ecpc) {
            this.key = key;
            this.viewCount = viewCount;
            this.clickCount = clickCount;
            this.ctr = ctr;
            this.cost = cost;
            this.ecpm = ecpm;
            this.ecpc = ecpc;
        }

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

        public long getViewCount() {
            return viewCount;
        }

        public void setViewCount(long viewCount) {
            this.viewCount = viewCount;
        }

        public long getClickCount() {
            return clickCount;
        }

        public void setClickCount(long clickCount) {
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

}
