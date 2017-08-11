package com.falcon.dsp.enumration;

/**
 * Created by falcon-zhangxg on 2016/4/8.
 */
public enum FalStrategyStatus {

    ADSTATUS_NORMAL(0, GdtAdStatus.ADSTATUS_NORMAL, "待运行"),    //通过时间判断
    ADSTATUS_RUNNING(1, GdtAdStatus.ADSTATUS_NORMAL, "运行中"),   //通过时间判断
    ADSTATUS_FINISH(2, GdtAdStatus.ADSTATUS_NORMAL, "已结束"),    //通过时间判断
    ADSTATUS_PENDING(3, GdtAdStatus.ADSTATUS_PENDING, "待审核"),   // 待审核
    ADSTATUS_DENIED(4, GdtAdStatus.ADSTATUS_DENIED, "审核不通过"),    // 审核不通过
//    ADSTATUS_FROZEN(5, GdtAdStatus.ADSTATUS_FROZEN, "冻结"),    // 冻结
    ADSTATUS_SUSPEND(6, GdtAdStatus.ADSTATUS_SUSPEND, "已暂停"),   // 挂起
//    ADSTATUS_PREPARE(7, GdtAdStatus.ADSTATUS_PREPARE, "准备状态"),   // 准备状态
    ADSTATUS_DELETED(-1, GdtAdStatus.ADSTATUS_DELETED, "已删除");

    private int value;
    private String description;
    private GdtAdStatus gdtAdStatus;
    private static java.util.HashMap<Integer, FalStrategyStatus> mappings;

    private FalStrategyStatus(int value, GdtAdStatus gdtAdStatus, String description) {
        this.value = value;
        this.description = description;
        this.gdtAdStatus = gdtAdStatus;
        FalStrategyStatus.getMappings().put(value, this);
    }

    private synchronized static java.util.HashMap<Integer, FalStrategyStatus> getMappings() {
        if (mappings == null) {
            mappings = new java.util.HashMap<Integer, FalStrategyStatus>();
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

    public static FalStrategyStatus forValue(int value) {
        return getMappings().get(value);
    }

}
