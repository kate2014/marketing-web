package com.zhongmei.yunfu.domain.enums;

/**
 * 交易类型
 */
public enum TradeTypeEnum implements ValueEnum<Integer> {

    SELL(1, "售货"), //售货

    REFUND(2, "退货"), //退货

    REPAY(3, "反结账"),//反结账

    REPAY_FOR_REFUND(4, "反结账退货");//反结账退货

    private String desc;

    private final Helper<Integer> helper;

    private TradeTypeEnum(Integer value, String desc) {
        helper = Helper.valueHelper(value);
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
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
