package com.falcon.dsp.common;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author wei.wang
 * @from 2016-04-15
 * @since V1.0
 */
public class MessageDigestSHA implements MessageDigest {

	private java.security.MessageDigest msgDigest;

	// 默认Sha1
	public MessageDigestSHA() {
		super();
		// msgDigest = DigestUtils.getSha256Digest();//sha256
		msgDigest = DigestUtils.getSha1Digest();
		// msgDigest = DigestUtils.getMd5Digest();
	}

	public java.security.MessageDigest getMsgDigest() {
		return msgDigest;
	}

	public void setMsgDigest(java.security.MessageDigest msgDigest) {
		this.msgDigest = msgDigest;
	}

	@Override
	public byte[] encrypt(String data) {
		return sha(StringUtils.getBytesUtf8(data));
	}

	@Override
	public String decryption(byte[] digest) throws Exception {
		throw new Exception("算法不可逆");
	}

	public byte[] sha(final byte[] data) {
		byte[] bb = msgDigest.digest(data);
		return bb;
	}

}
