package com.falcon.dsp.enumration;

/**
 * Created by zhangyujuan on 2016/4/22.
 */
public enum UserStatus {

    NORMAL_STATUS(1, "有效"),
    FROZEN_STATUS(-1, "无效");

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    private int status;
    private String description;

    UserStatus(int status, String description) {
        this.status = status;
        this.description = description;
    }
}
