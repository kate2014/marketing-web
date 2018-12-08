package com.zhongmei.yunfu.domain.enums;

public enum EnabledFlag implements ValueEnum<Integer> {
    /**
     * 有效的
     */
    ENABLED(1),

    /**
     * 无效的
     */
    DISABLED(2);


    private final Helper<Integer> helper;

    private EnabledFlag(Integer value) {
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
