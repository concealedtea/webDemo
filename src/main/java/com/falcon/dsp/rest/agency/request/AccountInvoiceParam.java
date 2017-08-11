package com.falcon.dsp.rest.agency.request;

import com.falcon.dsp.enumration.FinanceType;
import com.falcon.dsp.rest.common.TimeRange;

/**
 * @author Zhouchunhui
 * @from 2016-04-12
 * @since V1.0
 */
public class AccountInvoiceParam {
    private FinanceType accountType;//账户类型

    private TimeRange time_range;//{"start_date":"2014-03-01",end_date":"2014-04-02"}

    private int page; //搜索页码，不传视为1

    private int page_size; //一页显示的数据，如果不传默认为10

    private int no_page;//如果传递这个参数，则忽略分页，获取时间范围内的所有数据

    public FinanceType getAccountType() {
        return accountType;
    }

    public void setAccountType(FinanceType accountType) {
        this.accountType = accountType;
    }

    public TimeRange getTime_range() {
        return time_range;
    }

    public void setTime_range(TimeRange time_range) {
        this.time_range = time_range;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getNo_page() {
        return no_page;
    }

    public void setNo_page(int no_page) {
        this.no_page = no_page;
    }
}
