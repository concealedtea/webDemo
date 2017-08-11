package com.falcon.dsp.jdbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhouchunhui
 * @from 2016-05-04
 * @since V1.0
 */
public class FinanceFlowParam extends TableParam{

    @JsonProperty(value = "account_type")
    private String accountType;

    @JsonProperty(value = "start_date")
    private String startDate;

    @JsonProperty(value = "end_date")
    private String endDate;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

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

    public Map<String,Object> toMap(){
        Map map = new HashMap();
        if(!StringUtils.isEmpty(this.startDate)){
            map.put("startDate",this.startDate);
        }
        if(!StringUtils.isEmpty(this.endDate)){
            map.put("endDate",this.endDate);
        }
        if(!StringUtils.isEmpty(this.accountType)){
            map.put("accountType",this.accountType);
        }
        return map;

    }
}
