package com.falcon.dsp.jdbc.model;

import com.falcon.dsp.common.MathUtil;
import com.falcon.dsp.jdbc.entity.Report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by falcon-zhangxg on 2016/4/13.
 */
public class ReportModel {

    private int cid;
    private int sid;
    private long viewCount;
    private long clickCount;
    private double cost;
    private double ctr;
    private double ecpc;

    private Map<String, Object[]> hour;

    public ReportModel() {
        hour = new HashMap<>();
        hour.put("view_count", new Object[24]);
        initHourData("view_count");
        hour.put("click_count", new Object[24]);
        initHourData("click_count");
        hour.put("cost", new Object[24]);
        initHourData("cost");
        hour.put("ctr", new Object[24]);
        initHourData("ctr");
        hour.put("ecpc", new Object[24]);
        initHourData("ecpc");
    }

    private void initHourData(String column) {
        for (int i = 0; i < 24; i++) {
            hour.get(column)[i] = 0;
        }
    }

    public ReportModel initData(List<Report> list) {
        for (Report item : list) {
            if(item.getCid()!=null) this.cid = item.getCid();
            if(item.getSid()!=null) this.sid = item.getSid();
            this.viewCount = this.viewCount+item.getViewCount();
            this.clickCount = this.clickCount+item.getClickCount();
            this.cost = this.cost+item.getCost();
            hour.get("view_count")[item.getHour()] = item.getViewCount();
            hour.get("click_count")[item.getHour()] = item.getClickCount();
            hour.get("cost")[item.getHour()] = item.getCost();
            hour.get("ctr")[item.getHour()] = item.getCtr();
            hour.get("ecpc")[item.getHour()] = item.getEcpc();
        }
        this.ctr = MathUtil.ctrFormat(((double) this.clickCount) / this.viewCount);
        this.ecpc = MathUtil.doubleFormat(cost / clickCount);
        return this;
    }


    public ReportModel initData(Report report, List<Report> list) {
        if (report.getCid() != null) this.cid = report.getCid();
        if (report.getSid() != null) this.sid = report.getSid();
        this.viewCount = report.getViewCount();
        this.clickCount = report.getClickCount();
        this.cost = report.getCost();
        this.ctr = report.getCtr();
        this.ecpc = report.getEcpc();
        for (Report item : list) {
            hour.get("view_count")[item.getHour()] = item.getViewCount();
            hour.get("click_count")[item.getHour()] = item.getClickCount();
            hour.get("cost")[item.getHour()] = item.getCost();
            hour.get("ctr")[item.getHour()] = item.getCtr();
            hour.get("ecpc")[item.getHour()] = item.getEcpc();
        }
        return this;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCtr() {
        return ctr;
    }

    public void setCtr(double ctr) {
        this.ctr = ctr;
    }

    public double getEcpc() {
        return ecpc;
    }

    public void setEcpc(double ecpc) {
        this.ecpc = ecpc;
    }

    public Map<String, Object[]> getHour() {
        return hour;
    }

    public void setHour(Map<String, Object[]> hour) {
        this.hour = hour;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }
}
