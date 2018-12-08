package com.zhongmei.yunfu.domain.enums;

/**
 * 会员来源 1：pos本地  2：小程序
 */
public enum CustomerSourceId implements ValueEnum<Integer> {
    /**
     * pos本地
     */
    POS(1),

    /**
     * 小程序
     */
    WX_APP(2);


    private final Helper<Integer> helper;

    private CustomerSourceId(Integer value) {
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
