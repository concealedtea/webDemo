package com.falcon.dsp.enumration;

/**
 * @author dongbin.yu
 * @from 2016-04-05
 * @since V1.0
 */
public enum FalAdStatus {

    ADSTATUS_NORMAL(0, GdtAdStatus.ADSTATUS_NORMAL, "待运行"),    //通过时间判断
    ADSTATUS_RUNNING(1, GdtAdStatus.ADSTATUS_NORMAL, "运行中"),   //通过时间判断
    ADSTATUS_FINISH(2, GdtAdStatus.ADSTATUS_NORMAL, "已结束"),    //通过时间判断
    ADSTATUS_SUSPEND(3, GdtAdStatus.ADSTATUS_SUSPEND, "已暂停"),  //
    ADSTATUS_DELETED(-1, GdtAdStatus.ADSTATUS_DELETED, "删除");   //

    private int value;
    private String description;
    private GdtAdStatus gdtAdStatus;
    private static java.util.HashMap<Integer, FalAdStatus> mappings;

    private FalAdStatus(int value, GdtAdStatus gdtAdStatus, String description) {
        this.value = value;
        this.description = description;
        this.gdtAdStatus = gdtAdStatus;
        FalAdStatus.getMappings().put(value, this);
    }

    private synchronized static java.util.HashMap<Integer, FalAdStatus> getMappings() {
        if (mappings == null) {
            mappings = new java.util.HashMap<Integer, FalAdStatus>();
        }
        return mappings;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public GdtAdStatus getGdtAdStatus(){
        return this.gdtAdStatus;
    }

    public static FalAdStatus forValue(int value) {
        return getMappings().get(value);
    }

}
