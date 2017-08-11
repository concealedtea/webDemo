package com.falcon.dsp.common;

/**
 * Created by Administrator on 2016/4/1.
 */
public class GdtAction {
    public final static String CAMPAIGN_SELECT = "campaign/select";
    public final static String CAMPAIGN_DELETE = "campaign/delete";
    public final static String CAMPAIGN_CREATE = "campaign/create";
    public final static String CAMPAIGN_UPDATE = "campaign/update";
    public final static String CAMPAIGN_READ = "campaign/read";
    public final static String CAMPAIGN_SET_DAY_BUDGET = "campaign/set_day_budget";
    public final static String CAMPAIGN_GET_DAY_COST= "campaign/get_day_cost";

    public final static String AD_GROUP_CREATE = "ad_group/create";
    public final static String AD_GROUP_UPDATE = "ad_group/update";
    public final static String AD_GROUP_SELECT = "ad_group/select";
    public final static String AD_GROUP_DELETE = "ad_group/delete";

    public final static String RPT_CAMPAIGN_SELECT= "campaign_rpt/select";
    public final static String RPT_AD_TIME= "ad_time_rpt/select";

    public final static String FINANCE_SELECT= "account/select";
    public final static String FINANCE_FINSTAMENT= "account/finstament";
    public final static String FINANCE_SET_DAY_BUDGET= "account/set_day_budget";
    public final static String FINANCE_GET_DAY_BUDGET= "account/get_day_cost";


    public static final String ADVERTISER_READ="advertiser/read";

    public static final String  AGENCY_GET_ACCOUNTS = "agency/get_accounts";
    public static final String  AGENCY_ACCOUNT_INVOICE = "agency/account_invoice";
    public static final String  AGENCY_ACCOUNT_TRANSFER = "agency/transfer";
    public static final String  AGENCY_ACCOUNT_RECOVER = "agency/recover";

    public static final String UTILITIES_ESTIMATE = "utilities/estimate";

    // 获取广点通创意列表(对应Falcon素材列表)
    public static final String CREATIVE_SELECT = "creative/select";

    // 从广点通获取定向信息
    public static final String AD_TARGET_READ = "ad_target/read";
    
}

