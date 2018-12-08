package com.zhongmei.yunfu.domain.enums;

public enum StatusFlag implements ValueEnum<Integer> {
    /**
     * 有效的
     */
    VALiD(1),

    /**
     * 无效的
     */
    INVALID(2),

    /**
     * 未知的
     */
    UNKNOWN(null);


    private final Helper<Integer> helper;

    private StatusFlag(Integer value) {
        helper = Helper.valueHelper(value);
    }

    @Override
    public Integer value() {
        return helper.value();
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
