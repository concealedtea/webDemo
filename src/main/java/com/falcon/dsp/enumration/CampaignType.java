package com.falcon.dsp.enumration;

/**
 * @author dongbin.yu
 * @from 2016-03-29
 * @since V1.0
 */
public enum CampaignType {

    CAMPAIGNTYPE_DISPLAY(0, "展示广告");

    private int value;
    private String description;
    private static java.util.HashMap<Integer, CampaignType> mappings;

    private CampaignType(int value, String description) {
        this.value = value;
        this.description = description;
        CampaignType.getMappings().put(value, this);
    }

    private synchronized static java.util.HashMap<Integer, CampaignType> getMappings() {
        if (mappings == null) {
            mappings = new java.util.HashMap<Integer, CampaignType>();
        }
        return mappings;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public static CampaignType forValue(int value) {
        return getMappings().get(value);
    }

}
