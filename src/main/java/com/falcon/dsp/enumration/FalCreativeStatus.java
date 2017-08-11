package com.falcon.dsp.enumration;

/**
 * Created by ZhouChunhui on 2016/7/4 0004.
 */
public enum FalCreativeStatus {

    ADSTATUS_NORMAL(0, GdtAdStatus.ADSTATUS_NORMAL, "正常"),
    ADSTATUS_PENDING(3, GdtAdStatus.ADSTATUS_PENDING, "待审核"),
    ADSTATUS_DENIED(4, GdtAdStatus.ADSTATUS_DENIED, "审核不通过"),
    //ADSTATUS_FROZEN(3, GdtAdStatus.ADSTATUS_FROZEN, "冻结"),
    ADSTATUS_SUSPEND(6, GdtAdStatus.ADSTATUS_SUSPEND, "暂停"),
    //ADSTATUS_PREPARE(5, GdtAdStatus.ADSTATUS_PREPARE, "准备状态"),
    ADSTATUS_DELETED(-1, GdtAdStatus.ADSTATUS_DELETED, "删除");

    private int value;
    private String description;
    private GdtAdStatus gdtAdStatus;
    private static java.util.HashMap<Integer, FalCreativeStatus> mappings;

    private FalCreativeStatus(int value, GdtAdStatus gdtAdStatus, String description) {
        this.value = value;
        this.description = description;
        this.gdtAdStatus = gdtAdStatus;
        FalCreativeStatus.getMappings().put(value, this);
    }

    private synchronized static java.util.HashMap<Integer, FalCreativeStatus> getMappings() {
        if (mappings == null) {
            mappings = new java.util.HashMap<Integer, FalCreativeStatus>();
        }
        return mappings;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public GdtAdStatus getGdtAdStatus() {
        return this.gdtAdStatus;
    }

    public static FalCreativeStatus forValue(int value) {
        return getMappings().get(value);
    }
}
