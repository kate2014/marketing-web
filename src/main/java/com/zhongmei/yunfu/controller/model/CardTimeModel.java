package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import java.math.BigDecimal;
import java.util.List;

public class CardTimeModel extends WebBaseModel {

    private Long id;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 名称
     */
    private String name;

    private BigDecimal marketPrice;

    private BigDecimal dishIncreaseUnit;

    private Integer minNum;

    private Integer maxNum;

    private BigDecimal dishQty;

    private Integer isAllDish;

    private List<Long> dishId;

    private BigDecimal originalPrice;

    /**
     * 状态
     */
    private Integer enabledFlag;
    /**
     * 品牌id
     */
    private Long brandIdenty;
    /**
     * 门店id
     */
    private Long shopIdenty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getDishIncreaseUnit() {
        return dishIncreaseUnit;
    }

    public void setDishIncreaseUnit(BigDecimal dishIncreaseUnit) {
        this.dishIncreaseUnit = dishIncreaseUnit;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public BigDecimal getDishQty() {
        return dishQty;
    }

    public void setDishQty(BigDecimal dishQty) {
        this.dishQty = dishQty;
    }

    public Integer getIsAllDish() {
        return isAllDish;
    }

    public void setIsAllDish(Integer isAllDish) {
        this.isAllDish = isAllDish;
    }

    public List<Long> getDishId() {
        return dishId;
    }

    public void setDishId(List<Long> dishId) {
        this.dishId = dishId;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }
}
