package com.falcon.dsp.common;

/**
 * @author wei.wang
 * @from 2016-04-15
 * @since V1.0
 */
public interface MessageDigest {

	byte[] encrypt(String data);

	String decryption(byte[] digest) throws Exception;
}
