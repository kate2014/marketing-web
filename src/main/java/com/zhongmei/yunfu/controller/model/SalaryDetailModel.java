package com.zhongmei.yunfu.controller.model;

public class SalaryDetailModel {

    /**
     * 提成类型  1：比例提成  2：固定提成
     */
    private Integer type;

    private String baseAmount;

    private String commissionsValue;

    private String commissionsAmount;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(String baseAmount) {
        this.baseAmount = baseAmount;
    }

    public String getCommissionsValue() {
        return commissionsValue;
    }

    public void setCommissionsValue(String commissionsValue) {
        this.commissionsValue = commissionsValue;
    }

    public String getCommissionsAmount() {
        return commissionsAmount;
    }

    public void setCommissionsAmount(String commissionsAmount) {
        this.commissionsAmount = commissionsAmount;
    }
}
