package com.falcon.dsp.exception;

/**
 * @author dongbin.yu
 * @from 2016-04-11
 * @since V1.0
 */
public class FalconDspServerException extends RuntimeException {

    public FalconDspServerException() {
    }

    public FalconDspServerException(String message) {
        super(message);
    }

    public FalconDspServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
