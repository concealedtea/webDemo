package com.falcon.dsp.enumration;

/**
 * Created by falcon-zhangxg on 2016/4/7.
 */
public enum FalAdKpi {

    CPM(1, "CPM"),
    CPC(2, "CPC");

    private int value;
    private String description;
    private static java.util.HashMap<Integer, FalAdKpi> mappings;

    private FalAdKpi(int value, String description) {
        this.value = value;
        this.description = description;
        FalAdKpi.getMappings().put(value, this);
    }

    private synchronized static java.util.HashMap<Integer, FalAdKpi> getMappings() {
        if (mappings == null) {
            mappings = new java.util.HashMap<Integer, FalAdKpi>();
        }
        return mappings;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }


    public static FalAdKpi forValue(int value) {
        return getMappings().get(value);
    }
}
