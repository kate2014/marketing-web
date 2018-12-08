package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.CutDownCustomerEntity;

import java.math.BigDecimal;
import java.util.Date;

public class CutDownCustomerModel {

    /**
     * 发起砍价活动id
     */
    private Long id;
    /**
     * 砍价活动名称
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
     * 砍价起始价格
     */
    private BigDecimal startPrice;
    /**
     * 砍价结束价格
     */
    private BigDecimal endPrice;
    /**
     * 活动使用有效期
     */
    private Date validityPeriod;
    /**
     * 活动图片
     */
    private String imgUrl;
    /**
     * 关联砍价活动id
     */
    private Long cutDownId;
    /**
     * 会员id
     */
    private Long customerId;
    /**
     * 砍价会员名称
     */
    private String wxName;
    /**
     * 砍价会员头像
     */
    private String wxPhoto;
    /**
     * 砍价状态 1：砍价中  2：砍价完成  3：砍价失败
     */
    private Integer state;
    /**
     * 已砍价格
     */
    private BigDecimal currentPrice;
    /**
     * 参与人数
     */
    private Integer joinCount;
    /**
     * 品牌id
     */
    private Long brandIdentity;
    /**
     * 门店id
     */
    private Long shopIdentity;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(BigDecimal endPrice) {
        this.endPrice = endPrice;
    }

    public Date getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Date validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCutDownId() {
        return cutDownId;
    }

    public void setCutDownId(Long cutDownId) {
        this.cutDownId = cutDownId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getWxPhoto() {
        return wxPhoto;
    }

    public void setWxPhoto(String wxPhoto) {
        this.wxPhoto = wxPhoto;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
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
}
