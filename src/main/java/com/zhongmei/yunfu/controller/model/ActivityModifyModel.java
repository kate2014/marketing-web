package com.zhongmei.yunfu.controller.model;

import java.util.List;

/**
 * 活动修改
 */
public class ActivityModifyModel {

    private Long id;
    /**
     * 品牌id
     */
    private Long brandIdentity;
    /**
     * 门店id
     */
    private Long shopIdentity;
    /**
     * 方案名称
     */
    private String name;
    /**
     * 活动简介
     */
    private String planDesc;
    /**
     * 1, 进行中;2, 停止;
     */
    private Integer planState;
    /**
     * 活动开始时间
     */
    private String beginTime;
    /**
     * 活动结束时间
     */
    private String endTime;
    /**
     * 活动描述
     */
    private String describe;
    /**
     * 活动图片
     */
    private String imgUrl;

    private Long sourceId;

    private String selectShopList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandIdentity() {
        return brandIdentity;
    }

    public void setBrandIdentity(Long brandIdentity) {
        this.brandIdentity = brandIdentity;
    }

    public Long getShopIdentity() {
        return shopIdentity;
    }

    public void setShopIdentity(Long shopIdentity) {
        this.shopIdentity = shopIdentity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanDesc() {
        return planDesc;
    }

    public void setPlanDesc(String planDesc) {
        this.planDesc = planDesc;
    }

    public Integer getPlanState() {
        return planState;
    }

    public void setPlanState(Integer planState) {
        this.planState = planState;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSelectShopList() {
        return selectShopList;
    }

    public void setSelectShopList(String selectShopList) {
        this.selectShopList = selectShopList;
    }
}
