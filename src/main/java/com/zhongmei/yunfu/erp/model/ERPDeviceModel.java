package com.zhongmei.yunfu.erp.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

public class ERPDeviceModel extends WebBaseModel {

    private Long id;
    /**
     * 设备mac地址
     */
    private String deviceMac;
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 设备类型 1.pos收银机
     */
    private String deviceType;
    /**
     * 品牌标识
     */
    private Long brandIdenty;
    /**
     * 门店标识
     */
    private Long shopIdenty;

    private Long creatorId;

    private String creatorName;

    private Integer statusFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
    }
}
