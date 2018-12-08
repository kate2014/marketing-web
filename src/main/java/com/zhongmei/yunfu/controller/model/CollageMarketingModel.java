package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class CollageMarketingModel extends WebBaseModel {

    private Long id;
    /**
     * 拼团名称
     */
    private String name;
    /**
     * 拼团简介
     */
    private String profile;
    /**
     * 活动开始时间
     */
    private String beginTime;
    /**
     * 活动结束时间
     */
    private String endTime;
    /**
     * 商品Id
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 成团人数
     */
    private Integer collagePeopleCount;
    /**
     * 已参团人数
     */
    private Integer joinCount;
    /**
     * 成团价格
     */
    private String collagePrice;
    /**
     * 商品原价
     */
    private String originalPrice;
    /**
     * 活动图片
     */
    private String imgUrl;
    /**
     * 拼团描述
     */
    private String describe;
    /**
     * 最大开团数
     */
    private Integer maxOpenGroup;
    /**
     * 启用停用标识 : 1:启用;2:停用
     */
    private Integer enabledFlag;
    /**
     * 开团数量
     */
    private Integer openGroupCount;
    /**
     * 成团数量
     */
    private Integer finishGroupCount;
    /**
     * 活动使用有效期
     */
    private String validityPeriod;
    /**
     * 品牌id
     */
    private Long brandIdentity;
    /**
     * 门店id
     */
    private Long shopIdentity;
    /**
     * 状态标识1:有效 2:无效
     */
    private Integer statusFlag;
    /**
     * 创建者id
     */
    private Long creatorId;
    /**
     * 创建者名称
     */
    private String creatorName;
    /**
     * 更新者id
     */
    private Long updatorId;
    /**
     * 最后修改者姓名
     */
    private String updatorName;
    /**
     * 服务器创建时间
     */
    private Date serverCreateTime;
    /**
     * 服务器更新时间
     */
    private Date serverUpdateTime;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getCollagePeopleCount() {
        return collagePeopleCount;
    }

    public void setCollagePeopleCount(Integer collagePeopleCount) {
        this.collagePeopleCount = collagePeopleCount;
    }

    public String getCollagePrice() {
        return collagePrice;
    }

    public void setCollagePrice(String collagePrice) {
        this.collagePrice = collagePrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getMaxOpenGroup() {
        return maxOpenGroup;
    }

    public void setMaxOpenGroup(Integer maxOpenGroup) {
        this.maxOpenGroup = maxOpenGroup;
    }

    public Integer getOpenGroupCount() {
        return openGroupCount;
    }

    public void setOpenGroupCount(Integer openGroupCount) {
        this.openGroupCount = openGroupCount;
    }

    public Integer getFinishGroupCount() {
        return finishGroupCount;
    }

    public void setFinishGroupCount(Integer finishGroupCount) {
        this.finishGroupCount = finishGroupCount;
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

    public Long getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Long updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
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

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
    }

    public Integer getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Integer joinCount) {
        this.joinCount = joinCount;
    }



    public Date getServerCreateTime() {
        return serverCreateTime;
    }

    public void setServerCreateTime(Date serverCreateTime) {
        this.serverCreateTime = serverCreateTime;
    }

    public Date getServerUpdateTime() {
        return serverUpdateTime;
    }

    public void setServerUpdateTime(Date serverUpdateTime) {
        this.serverUpdateTime = serverUpdateTime;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
}
