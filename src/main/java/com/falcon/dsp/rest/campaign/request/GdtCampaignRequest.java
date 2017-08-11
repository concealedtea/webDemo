package com.falcon.dsp.rest.campaign.request;

/**
 * Created by falcon-zhangxg on 2016/4/9.
 */
public class GdtCampaignRequest {
    private Integer uid;              //int32 广告主Id 有访问权限的广告主id 是
    private Integer time_range;     //Array of datetime object 时间范围 日期格式YY-MM-DD，如：{"start_date":"2014-03-01","end_date","2014-04-02"} 详见附录【报表日期限制】 是
    private Integer cid_list;       //json(array of int32)如[2001,2002,2003,2004]，可不填，数量不能不超过200 个 否
    private Integer order_by;       //json(array ofstring)规则定义】否
    private Integer group_by;       //json(array ofstring)聚合参数 聚合规则 见附录 【报表聚合规则定义】否
    private Integer page;           //int32 搜索页码，若不传则视为1 大于等于1 小于等于实际总页数 否
    private Integer page_size;     //int32 一页显示的数据条数，若不传则视为10 1-100 否
}
