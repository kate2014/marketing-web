package com.zhongmei.yunfu.domain.enums;

public enum RecordType implements ValueEnum<Integer> {
    /**
     * 购买，买入的
     */
    BUY(1, "购买"),

    /**
     * 消费
     */
    EXPENSE(2, "消费"),

    /**
     * 退货
     */
    REFUND(3, "购买退货"),

    REFUND_EXPENSE(4, "消费退货");


    private final Helper<Integer> helper;
    private String desc;

    private RecordType(Integer value, String desc) {
        helper = Helper.valueHelper(value);
        this.desc = desc;
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
