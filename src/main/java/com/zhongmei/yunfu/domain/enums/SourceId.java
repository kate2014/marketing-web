package com.zhongmei.yunfu.domain.enums;

/**
 * 会员来源 1：pos本地  2：小程序
 */
public enum SourceId implements ValueEnum<Integer> {

    BUY(1, "POS"),
    EXPENSE(2, "小程序"),
    UNKNOWN(-1, "未知");

    private final Helper<Integer> helper;
    private String desc;

    SourceId(Integer value, String desc) {
        helper = Helper.valueHelper(value);
        this.desc = desc;
    }

    @Override
    public Integer value() {
        return helper.value();
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public boolean equalsValue(Integer value) {
        return helper.equalsValue(this, value);
    }

    @Override
    public boolean isUnknownEnum() {
        return helper.isUnknownEnum();
    }

    @Override
    public void setUnknownValue(Integer value) {
        helper.setUnknownValue(value);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
