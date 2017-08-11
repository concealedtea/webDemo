package com.falcon.dsp.enumration;

/**
 * @author dongbin.yu
 * @from 2016-03-29
 * @since V1.0
 */
public enum SpeedMode {

    SPEEDMODE_UNIFORM(0, "标准投放"),
    SPEEDMODE_NORMAL(1, "加速投放");

    private int value;
    private String description;
    private static java.util.HashMap<Integer, SpeedMode> mappings;

    private SpeedMode(int value, String description) {
        this.value = value;
        this.description = description;
        SpeedMode.getMappings().put(value, this);
    }

    private synchronized static java.util.HashMap<Integer, SpeedMode> getMappings() {
        if (mappings == null) {
            mappings = new java.util.HashMap<Integer, SpeedMode>();
        }
        return mappings;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static SpeedMode forValue(int value) {
        return getMappings().get(value);
    }

}
