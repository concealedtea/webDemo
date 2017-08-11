package com.falcon.dsp.common;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author dongbin.yu
 * @from 2016-04-09
 * @since V1.0
 */
public class EncodeUtilTest {

    @Test
    public void testEncode() {

        try {
            System.out.println(URLEncoder.encode("湖","UTF-8"));
            System.out.println(URLDecoder.decode("http%3A%2F%2Fpgdt.gtimg.cn%2Fgdt%2F0%2FBAAAE92BeBTC\n" +
                    "yTUsQcNLw.jpg%2F0%3Fck%3D3d943125f3aeac22e0faa8a25842ebd3","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTimeSet() {

        int[][] timeSet = new int[][]{{1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
    }

    @Test
    public void testJsonToString() {

        String[] s = new String[]{"测试","测试A"};
        System.out.println(JsonUtil.toJson(s));

    }
}
