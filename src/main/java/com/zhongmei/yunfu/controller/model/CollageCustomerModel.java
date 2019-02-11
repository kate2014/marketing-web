package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.CollageCustomerEntity;

import java.math.BigDecimal;
import java.util.Date;

public class CollageCustomerModel {

    /**
     * 会员发起拼团id
     */
    private Long id;
    /**
     * 拼团名称
     */
    private String name;
    /**
     * 活动开始时间
     */
    private Date beginTime;
    /**
     * 活动结束时间
     */
    private Date endTime;
    /**
     * 活动使用有效期
     */
    private Date validityPeriod;
    /**
     * 成团人数
     */
    private Integer collagePeopleCount;
    /**
     * 成团价格
     */
    private BigDecimal collagePrice;
    /**
     * 原价格
     */
    private BigDecimal originalPrice;
    /**
     * 已参团人数
     */
    private Integer joinCount;
    /**
     * 活动图片
     */
    private String imgUrl;
    /**
     * 关联拼团活动id
     */
    private Long collageId;
    /**
     * 关联会员发起的拼团活动id
     */
    private Long relationId;
    /**
     * 拼团批次code,一个团一个码，全局唯一
     */
    private String collageCode;
    /**
     * 参团类型：1：发起拼团  2：参与拼团
     */
    private int type;
    /**
     * 会员id
     */
    private Long customerId;
    /**
     * 拼团状态 1：拼团中  2：拼团完成  3：拼团失败
     */
    private Integer state;
    /**
     * 状态 1未使用、2已使用
     */
    private Integer status;
    /**
     * 启用停用标识 : 1:启用;2:停用
     */
    private Integer enabledFlag;
    /**
     * 品牌id
     */
    private Long brandIdentity;
    /**
     * 门店id
     */
    private Long shopIdentity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollageId() {
        return collageId;
    }

    public void setCollageId(Long collageId) {
        this.collageId = collageId;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getCollageCode() {
        return collageCode;
    }

    public void setCollageCode(String collageCode) {
        this.collageCode = collageCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Integer joinCount) {
        this.joinCount = joinCount;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Date validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public Integer getCollagePeopleCount() {
        return collagePeopleCount;
    }

    public void setCollagePeopleCount(Integer collagePeopleCount) {
        this.collagePeopleCount = collagePeopleCount;
    }

    public BigDecimal getCollagePrice() {
        return collagePrice;
    }

    public void setCollagePrice(BigDecimal collagePrice) {
        this.collagePrice = collagePrice;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }
}
