package com.zhongmei.yunfu.controller.model;

import java.util.Date;

public class BookingModel {

    /**
     * 顾客id
     */
    private Long commercialId;
    /**
     * 顾客名称
     */
    private String commercialName;
    /**
     * 顾客性别 1:男 2:女
     */
    private Long commercialGender;
    /**
     * 顾客联系方式
     */
    private String commercialPhone;
    /**
     * 预定时间段开始时间
     */
    private String startTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 预订确认状态. 0:未确认 1:已确认
     */
    private Integer confirmed;
    /**
     * 商品id
     */
    private String dishId;
    /**
     * 商品名称
     */
    private String dishName;
    /**
     * 订单状态,-1:用户未到店,1:用户到店,2:用户离店,9:已取消,-2:未处理,-3:已拒绝,-4:逾期未到店
     */
    private Integer orderStatus;
    /**
     * 品牌标识
     */
    private Long brandIdenty;
    /**
     * 门店标识
     */
    private Long shopIdenty;

    public Long getCommercialId() {
        return commercialId;
    }

    public void setCommercialId(Long commercialId) {
        this.commercialId = commercialId;
    }

    public String getCommercialName() {
        return commercialName;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public Long getCommercialGender() {
        return commercialGender;
    }

    public void setCommercialGender(Long commercialGender) {
        this.commercialGender = commercialGender;
    }

    public String getCommercialPhone() {
        return commercialPhone;
    }

    public void setCommercialPhone(String commercialPhone) {
        this.commercialPhone = commercialPhone;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Long getBrandIdenty() {
        return brandIdenty;
    }

    public void setBrandIdenty(Long brandIdenty) {
        this.brandIdenty = brandIdenty;
    }

    public Long getShopIdenty() {
        return shopIdenty;
    }

    public void setShopIdenty(Long shopIdenty) {
        this.shopIdenty = shopIdenty;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
