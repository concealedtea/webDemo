package com.falcon.dsp.common;

/**
 * @author Zhouchunhui
 * @from 2016-04-14
 * @since V1.0
 */
public class Constant {
    public static final int LIEING_AGENCY_UID = 1;

    public static final String LIEING_SHORT_NAME = "LIEY";

    public static final String USER_INFO_SESSION = "USER_INFO_SESSION";

    //最小值
    public static final Integer MIN_DAY_BUDGET_VALUE = 50; //单位元

    //日限额最大值
    public static final Integer MAX_ADVERTISER_DAY_BUDGET_VALUE = 10000000;//广告主日限额最大值  单位元

    //单个日限额的最大值
    public static final Integer MAX_CAMPAIGN_DAY_BUDGET_VALUE = 4000000; //单位元

    //最小变动金额
    public static final Integer MIN_CHANGE_VALUE = 50; //单位元


    /**
     * 权限控制参数
     */
    public static final String PERMISSION_PARAM_UID = "uid";
    /**
     * 权限控制参数
     */
    public static final String PERMISSION_PARAM_CID = "cid";
    /**
     * 权限控制参数
     */
    public static final String PERMISSION_PARAM_CAMPAIGN_ID = "campaign_id";
}
