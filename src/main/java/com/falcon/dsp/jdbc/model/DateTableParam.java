package com.falcon.dsp.jdbc.model;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhouchunhui
 * @from 2016-04-20
 * @since V1.0
 */
public class DateTableParam extends TableParam{
    private Integer agencyId;//代理商id，通过session获取 ，只有是代理商用户的时候需要设置该值

    private String startDate;

    private String endDate;

    private double rate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Map<String,Object> toMap(){
        Map map = new HashMap();
        if(!StringUtils.isEmpty(this.startDate)){
            map.put("startDate",this.startDate);
        }
        if(!StringUtils.isEmpty(this.endDate)){
            map.put("endDate",this.endDate);
        }
        if(!StringUtils.isEmpty(this.agencyId)){
            map.put("agencyId",this.agencyId);
        }
        if(!StringUtils.isEmpty(this.getUid())){
            map.put("uid",this.getUid());
        }
        if(!StringUtils.isEmpty(this.getOrderby())){
            map.put("orderby",this.getOrderby());
        }
        if(!StringUtils.isEmpty(this.getOrdercolumns())){
            map.put("ordercolumns",this.getOrdercolumns());
        }
        if(!StringUtils.isEmpty(this.getSearchKeyword())){
            map.put("searchKeyword",this.getSearchKeyword());
        }
        map.put("markup", this.getRate());

        return map;

    }
}
