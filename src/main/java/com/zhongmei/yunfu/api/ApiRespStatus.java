package com.zhongmei.yunfu.api;

public enum ApiRespStatus {

    SUCCESS(1000, "执行成功"),
    FOUND(302, "Found"),

    CUSTOMER_FOUND(2106, "顾客不存在"),
    CUSTOMER_INVALID(2017, "顾客无效"),
    CUSTOMER_DISABLED(2018, "顾客已禁用"),
    CUSTOMER_ERROR_PASSWORD(2018, "顾客密码出错"),

    CUSTOMER_INTEGRAL_INSUFFICIENT(2017, "剩余积分不够"),

    CUSTOMER_MOBILE_INVALID(2017, "顾客手机号已存在"),
    CUSTOMER_CARD_TIME_INVALID(2017, "顾客无效"),
    CUSTOMER_CARD_TIME_INSUFFICIENT(2017, "剩余次数不够"),
    CUSTOMER_CARD_TIME_EXPIRED(2017, "次卡已过期"),
    CUSTOMER_ENTITY_CARD_BINDED(2017, "此卡号已经绑定过"),

    CUSTOMER_STORED_BALANCE_RECHARGE_GIVE_ERR(2017, "充值赠送的金额不匹配"),

    SHOP_FOUND(2106, "门店不存在"),
    SHOP_INVALID(2017, "门店无效"),
    SHOP_DISABLED(2017, "门店不可用"),

    UNKNOWN(-1, "Continue");

    private final int value;
    private final String reason;

    ApiRespStatus(int value, String reason) {
        this.value = value;
        this.reason = reason;
    }

    public int getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }
}
