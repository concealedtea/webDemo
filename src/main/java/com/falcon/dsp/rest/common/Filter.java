package com.falcon.dsp.rest.common;

/**
 * 调用接口时filter 高级查询条件
 * @author Zhouchunhui
 * @from 2016-05-06
 * @since V1.0
 */
public class Filter {

    private String field;//字段名

    private String operator;//操作符  EQUALS 等于，CONTAINS 包含

    private String value;

    public Filter() {
    }

    public Filter(String field, String operator, String value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
