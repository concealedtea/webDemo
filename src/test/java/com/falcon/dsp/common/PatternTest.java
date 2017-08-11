package com.falcon.dsp.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dongbin.yu
 * @from 2016-04-15
 * @since V1.0
 */
public class PatternTest {

    private static Pattern pattern = Pattern.compile("^(data:.*;base64,)");

    @Test
    public void testBase64Image() {

        Matcher matcher = pattern.matcher("data:png/jpeg;base64,11122222");
        while (matcher.find()) {
            matcher.group(1);
        }
    }

    @Test
    public void testSha1() {

        System.out.println(DigestUtils.sha1Hex("agency"));
        System.out.println(UUID.randomUUID());
    }
}
