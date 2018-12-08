package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class CutDownModel extends WebBaseModel {

    private Long id;
    /**
     * 砍价活动名称
     */
    private String name;
    /**
     * 砍价活动简介
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
     * 活动使用有效期
     */
    private String validityPeriod;
    /**
     * 砍价起始价格
     */
    private BigDecimal startPrice;
    /**
     * 砍价结束价格
     */
    private BigDecimal endPrice;
    /**
     * 砍价随机最小金额
     */
    private BigDecimal randomPriceMini;
    /**
     * 砍价随机最大金额
     */
    private BigDecimal randomPriceMax;
    /**
     * 商品Id
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 活动图片
     */
    private String imgUrl;
    /**
     * 拼团描述
     */
    private String describe;
    /**
     * 同一活动同一会员参与砍价次数
     */
    private Integer customerCutCount;
    /**
     * 活动售卖数量
     */
    private Integer salesCount;
    /**
     * 已售卖活动数量
     */
    private Integer soldCount;
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

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
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

    public BigDecimal getRandomPriceMini() {
        return randomPriceMini;
    }

    public void setRandomPriceMini(BigDecimal randomPriceMini) {
        this.randomPriceMini = randomPriceMini;
    }

    public BigDecimal getRandomPriceMax() {
        return randomPriceMax;
    }

    public void setRandomPriceMax(BigDecimal randomPriceMax) {
        this.randomPriceMax = randomPriceMax;
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

    public Integer getCustomerCutCount() {
        return customerCutCount;
    }

    public void setCustomerCutCount(Integer customerCutCount) {
        this.customerCutCount = customerCutCount;
    }

    public Integer getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(Integer salesCount) {
        this.salesCount = salesCount;
    }

    public Integer getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(Integer soldCount) {
        this.soldCount = soldCount;
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
}
