package com.falcon.dsp.enumration;

/**
 * Created by falcon-zhangxg on 2016/4/6.
 */
public enum ErrorType {

    FAL_ERROR_100(100,"数据库访问错误"),
    FAL_ERROR_101(101,"新增策略失败"),
    FAL_ERROR_102(102,""),
    GDT_ERROR_0(0, "没有错误"),
    GDT_ERROR_1004(1004, "余额不足（划账）"),
    GDT_ERROR_1201(1201, "订单号重复（充值）"),
    GDT_ERROR_4000(4000, "用户错误"),
    GDT_ERROR_4001(4001, "参数为空"),
    GDT_ERROR_4002(4002, "参数类型不对"),
    GDT_ERROR_4003(4003, "参数有错"),
    GDT_ERROR_4004(4004, "方法或url错误"),
    GDT_ERROR_4100(4100, "数字错误"),
    GDT_ERROR_4101(4101, "超过上限"),
    GDT_ERROR_4102(4102, "低于下限"),
    GDT_ERROR_4103(4103, "QQ号码错误"),
    GDT_ERROR_4200(4200, "字符串错误"),
    GDT_ERROR_4201(4201, "含有脏词"),
    GDT_ERROR_4202(4202, "格式不符合要求"),
    GDT_ERROR_4203(4203, "不是合法的名称"),
    GDT_ERROR_4204(4204, "字符串过长"),
    GDT_ERROR_4205(4205, "错误的URL地址"),
    GDT_ERROR_4206(4206, "数据结构错误"),
    GDT_ERROR_4250(4250, "请求的账户不存在"),
    GDT_ERROR_4251(4251, "充值通知签名错误"),
    GDT_ERROR_4252(4252, "服务商之间划账，资金类型不支持"),
    GDT_ERROR_4253(4253, "服务商之间划账，资金类型不一致"),
    GDT_ERROR_4254(4254, "划账失败，当前账户已超5笔有效资金"),
    GDT_ERROR_4300(4300, "时间错误"),
    GDT_ERROR_4503(4503, "重复注册"),
    GDT_ERROR_4505(4505, "没有通过审核"),
    GDT_ERROR_4506(4506, "未注册"),
    GDT_ERROR_4511(4511, "没有权限"),
    GDT_ERROR_4512(4512, "防止CSRF的Token验证错误"),
    GDT_ERROR_4513(4513, "不是广点通的注册用户"),
    GDT_ERROR_4632(4632, "频次超限"),
    GDT_ERROR_4901(4901, "token错误"),
    GDT_ERROR_4902(4902, "token过期"),
    GDT_ERROR_4903(4903, "skey错误（登录态过期）"),
    GDT_ERROR_5000(5000, "系统错误"),
    GDT_ERROR_5001(5001, "系统繁忙"),
    GDT_ERROR_5005(5005, "帐户服务异常（AAA异常）"),
    GDT_ERROR_5600(5600, "资金状态变更错误"),
    GDT_ERROR_7001(7001, "日限额不能低于今日消费金额"),
    GDT_ERROR_7201(7201, "仅能删除状态为暂停中的推广计划"),
    GDT_ERROR_7002(7002, "单日修改限额次数已达上限"),
    GDT_ERROR_7003(7003, "操作对象不存"),
    GDT_ERROR_7004(7004, "对象设置名称重复"),
    GDT_ERROR_7005(7005, "每次修改日限额的最小幅度必须大于等于50元"),
    GDT_ERROR_7501(7501, "指定aid不存在，或已删除"),
    GDT_ERROR_7502(7502, "指定cid不存在，或已删除"),
    GDT_ERROR_7503(7503, "指定的cid与aid不匹配"),
	
	GDT_ERROR_9001(9001, "用户名或密码不正确"),
	GDT_ERROR_9002(9002, "用户名已存在"),
    GDT_ERROR_9003(9003, "账户已被冻结，请联系系统管理员！");

    private int intValue;
    private String strDescription;
    private static java.util.HashMap<Integer, ErrorType> mappings;

    private ErrorType(int value, String description) {
        intValue = value;
        strDescription = description;
        ErrorType.getMappings().put(value, this);
    }

    private synchronized static java.util.HashMap<Integer, ErrorType> getMappings() {
        if (mappings == null) {
            mappings = new java.util.HashMap<Integer, ErrorType>();
        }
        return mappings;
    }

    public int getValue() {
        return intValue;
    }

    public String getDescription() {
        return strDescription;
    }

    public static ErrorType forValue(int value) {
        return getMappings().get(value);
    }
}
