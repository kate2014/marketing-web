package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.IShopIdenty;
import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

public class NewDishPushSearchModel extends WebBaseModel implements IShopIdenty {
    private Integer planState = 0; //0:全部 1:进行中; 2:停止;
    private String keyWord;//查询关键字
    private Integer sourceType;
    private Long shopIdenty;
    private Long brandIdenty;
    private Long activityId;
    private Integer type;//1：浏览   2：分享
    private String customerName;
    private String customerPhone;
    private Integer operationalCount;

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public int getPlanState() {
        return planState;
    }

    public void setPlanState(int planState) {
        this.planState = planState;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setPlanState(Integer planState) {
        this.planState = planState;
    }

    public Long getShopIdenty() {
        return shopIdenty;
    }

    public void setShopIdenty(Long shopIdenty) {
        this.shopIdenty = shopIdenty;
    }

    public Long getBrandIdenty() {
        return brandIdenty;
    }

    public void setBrandIdenty(Long brandIdenty) {
        this.brandIdenty = brandIdenty;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Integer getOperationalCount() {
        return operationalCount;
    }

    public void setOperationalCount(Integer operationalCount) {
        this.operationalCount = operationalCount;
    }
}
