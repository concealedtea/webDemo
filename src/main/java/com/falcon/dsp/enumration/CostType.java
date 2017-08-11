package com.falcon.dsp.enumration;

/**
 * @author dongbin.yu
 * @from 2016-04-05
 * @since V1.0
 */
public enum CostType {

    COSTTYPE_CPC(0, "CPC扣费"),
    COSTTYPE_CPA(1, "CPA扣费"),
    COSTTYPE_CPS(2, "CPS扣费"),
    COSTTYPE_CPM(3, "CPM扣费"),
    COSTTYPE_CPD(4, "CPD扣费");

    private int value;
    private String desc;

    private CostType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
