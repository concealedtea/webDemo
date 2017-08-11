package com.falcon.dsp.common;

/**
 * Created by Administrator on 2016/3/24.
 */
public class TokenUtil {
    //获取对应的token
    public static void main(String[] args){

        String uid = "1021415";
        String appid = "1021415";
        String appkey = "e5d64f627caa82703af7a5033e3c97a4";

        String time = String.valueOf(System.currentTimeMillis() / 1000);
        System.out.println(time);

        String signStr = appid + appkey + time;
        String sign = EncodeUtil.SHA1(signStr);

        String tokenStr = uid + "," + appid + "," + time + "," + sign;
        System.out.println(EncodeUtil.getBase64(tokenStr));

    }
}
