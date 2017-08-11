package com.falcon.dsp.exception;

/**
 * Created by falcon-zhangxg on 2016/4/5.
 */
public class FalDspException extends RuntimeException {

    private int ret;

    public FalDspException(int ret, String message, Throwable cause) {
        super(message, cause);
        this.ret = ret;
    }

    public FalDspException(int ret, String message) {
        super(message);
        this.ret = ret;
    }

    public int getRet() {
        return ret;
    }
}
