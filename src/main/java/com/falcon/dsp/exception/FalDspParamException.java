package com.falcon.dsp.exception;

/**
 * Created by falcon-zhangxg on 2016/4/5.
 */
public class FalDspParamException extends RuntimeException {

    public FalDspParamException() {
    }

    public FalDspParamException(String message) {
        super(message);
    }

    public FalDspParamException(String message, Throwable cause) {
        super(message, cause);
    }

}
