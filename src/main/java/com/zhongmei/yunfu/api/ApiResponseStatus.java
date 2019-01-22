package com.zhongmei.yunfu.api;

public enum ApiResponseStatus {

    SUCCESS(1000, "执行成功"),
    FOUND(302, "Found"),

    CUSTOMER_FOUND(2106, "顾客不存在"),
    CUSTOMER_INVALID(2017, "顾客无效"),
    CUSTOMER_DISABLED(2018, "顾客已禁用"),
    CUSTOMER_ERROR_PASSWORD(2018, "顾客密码出错"),

    CUSTOMER_INTEGRAL_INSUFFICIENT(2017, "剩余积分不够"),

    CUSTOMER_CARD_TIME_INVALID(2017, "顾客无效"),
    CUSTOMER_CARD_TIME_INSUFFICIENT(2017, "剩余次数不够"),
    CUSTOMER_CARD_TIME_EXPIRED(2017, "次卡已过期"),

    SHOP_FOUND(2106, "门店不存在"),
    SHOP_INVALID(2017, "门店无效"),
    SHOP_DISABLED(2017, "门店不可用"),

    UNKNOWN(-1, "Continue");

    private final int value;
    private final String reason;

    ApiResponseStatus(int value, String reason) {
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
