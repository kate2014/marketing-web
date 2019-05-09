package com.zhongmei.yunfu.domain.entity.bean;


import java.math.BigDecimal;

/**
 *
 *
 * 门店销售业绩
 *
 *
 * @author zhaos
 * @since 2018-09-21
 */
public class ShopSalesReport {

    private Long shopIdenty;

    private String shopName;
    /**
     * 消费额度
     */
    private BigDecimal salesAmount;

    private BigDecimal salesCount;

    private Integer businessType;

    private BigDecimal totalSave;

    private BigDecimal totalSaveCount;

    private BigDecimal totalCard;

    private BigDecimal totalCardCount;

    private BigDecimal totalWeiXin;

    private BigDecimal totalWeiXinCount;

    private BigDecimal totalAmount;

    private BigDecimal totalAmountCount;

    public Long getShopIdenty() {
        return shopIdenty;
    }

    public void setShopIdenty(Long shopIdenty) {
        this.shopIdenty = shopIdenty;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
    }

    public BigDecimal getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(BigDecimal salesCount) {
        this.salesCount = salesCount;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public BigDecimal getTotalSave() {
        return totalSave;
    }

    public void setTotalSave(BigDecimal totalSave) {
        this.totalSave = totalSave;
    }

    public BigDecimal getTotalCard() {
        return totalCard;
    }

    public void setTotalCard(BigDecimal totalCard) {
        this.totalCard = totalCard;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalSaveCount() {
        return totalSaveCount;
    }

    public void setTotalSaveCount(BigDecimal totalSaveCount) {
        this.totalSaveCount = totalSaveCount;
    }

    public BigDecimal getTotalCardCount() {
        return totalCardCount;
    }

    public void setTotalCardCount(BigDecimal totalCardCount) {
        this.totalCardCount = totalCardCount;
    }

    public BigDecimal getTotalAmountCount() {
        return totalAmountCount;
    }

    public void setTotalAmountCount(BigDecimal totalAmountCount) {
        this.totalAmountCount = totalAmountCount;
    }

    public BigDecimal getTotalWeiXin() {
        return totalWeiXin;
    }

    public void setTotalWeiXin(BigDecimal totalWeiXin) {
        this.totalWeiXin = totalWeiXin;
    }

    public BigDecimal getTotalWeiXinCount() {
        return totalWeiXinCount;
    }

    public void setTotalWeiXinCount(BigDecimal totalWeiXinCount) {
        this.totalWeiXinCount = totalWeiXinCount;
    }
}
