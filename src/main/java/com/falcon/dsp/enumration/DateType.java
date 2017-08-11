package com.falcon.dsp.enumration;

/**
 * Created by falcon-zhangxg on 2016/4/13.
 */
public enum  DateType {

    TODAY(0,"今天"),
    YESTERDAY(1,"昨天"),
    LAST_7(2,"过去7天"),
    LAST_30(3,"过去30天");

    private int value;
    private String description;
    private static java.util.HashMap<Integer, DateType> mappings;

    private DateType(int value,String description) {
        this.value = value;
        this.description = description;
        DateType.getMappings().put(value, this);
    }

    private synchronized static java.util.HashMap<Integer, DateType> getMappings() {
        if (mappings == null) {
            mappings = new java.util.HashMap<Integer, DateType>();
        }
        return mappings;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static DateType forValue(int value) {
        return getMappings().get(value);
    }
}
