package com.falcon.dsp.enumration;

/**
 * Created by xm on 2016/4/7.
 */
public enum FinanceType {
    CASH("CASH","现金"), //现金
    DEVIDE("DEVIDE","分成"), //分成
    VIRTUAL("VIRTUAL","虚拟"),//虚拟
    UNKNOWN("UNKNOWN(8)","未知"), //
    MYAPP_CONSUME("MYAPP_CONSUME","手机支付");

    private String name;
    private String description;

    private FinanceType(String name, String description) {
        this.name = name;
        this.description = description;
    }


    @Override
    public String toString() {
        return name;
    }
}


