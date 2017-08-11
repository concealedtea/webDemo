package com.falcon.dsp.rest.common;

/**
 * Created by falcon-zhangxg on 2016/4/5.
 */
public abstract class GdtBaseResult {

    private int ret;
    private String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
