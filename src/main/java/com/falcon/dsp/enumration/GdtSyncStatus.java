package com.falcon.dsp.enumration;

/**
 * @author dongbin.yu
 * @from 2016-04-09
 * @since V1.0
 */
public enum GdtSyncStatus {

    SYNC_INIT(0,"未同步"),
    SYNC_SUCCESS(1,"同步成功"),
    SYNC_FAILED(-1,"同步失败"),
    SYNC_ABANDON(-2,"资源丢弃");

    private int status;
    private String description;

    private GdtSyncStatus(int status, String description) {
        this.status = status;
        this.description = description;
    }

    public int getStatus() {
        return this.status;
    }

}
