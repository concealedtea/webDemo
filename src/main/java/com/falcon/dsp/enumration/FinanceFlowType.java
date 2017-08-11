package com.falcon.dsp.enumration;

/**
 * Created by xm on 2016/4/7.
 */

public enum FinanceFlowType{
    UNKNOWN , //未知
    TRANSACTION_CHARGE , //充值
    TRANSACTION_CLEAR, //退款
    TRANSFER_IN, //转入
    TRANSFER_OUT,//转出
    EXPIRE, //过期
    TRANSACTION_REPAYMENT,//还款
    TRANSACTION_PAY //消费
}