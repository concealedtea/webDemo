package com.falcon.dsp.exception;

/**
 * @author dongbin.yu
 * @from 2016-04-11
 * @since V1.0
 */
public class FalconDspSqlException extends RuntimeException {

    public FalconDspSqlException() {
    }

    public FalconDspSqlException(String message) {
        super(message);
    }

    public FalconDspSqlException(String message, Throwable cause) {
        super(message, cause);
    }
}
