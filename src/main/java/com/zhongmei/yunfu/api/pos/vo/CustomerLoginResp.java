package com.zhongmei.yunfu.api.pos.vo;

import java.math.BigDecimal;

/**

 * @version: 1.0
 * @date 2015年5月13日
 */
public class CustomerLoginResp {

    private Long customerId;//顾客id
    private String customerName;// 顾客名字 （新的登录接口使用）
    private Integer sex; //性别
    private String mobile;//联系电话 （新接口使用）
    private String phoneNumber;//手机号码(老接口使用)
    private String address;//顾客地址
    private Long levelId;
    private String level; //会员等级
    private BigDecimal valueCardBalance; //储值余额
    private BigDecimal remainValue; //储值余额
    private String openId;//微信openID
    private Integer integral;//当前积分
    private Integer cardCount;//实体卡（有/无）
    private Integer coupCount;//优惠券（有/无）
    private Integer isDisable;//是否停用 1.是停用; 2.否
    private Long brandId;//品牌id
    private Long commercialId;//顾客所属门店id
    private String commercialName;//顾客所属门店名称
    private Boolean isStoredPaymentCheck; //是否做储值支付验证
    private Integer storedPrivilegeType; //储值支付优惠类型
    private BigDecimal storedPrivilegeValue; //储值支付优惠值
    private BigDecimal storedFullAmount; //储值消费满额限制

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public BigDecimal getValueCardBalance() {
        return valueCardBalance;
    }

    public void setValueCardBalance(BigDecimal valueCardBalance) {
        this.valueCardBalance = valueCardBalance;
    }

    public BigDecimal getRemainValue() {
        return remainValue;
    }

    public void setRemainValue(BigDecimal remainValue) {
        this.remainValue = remainValue;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getCardCount() {
        return cardCount;
    }

    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }

    public Integer getCoupCount() {
        return coupCount;
    }

    public void setCoupCount(Integer coupCount) {
        this.coupCount = coupCount;
    }

    public Integer getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Integer isDisable) {
        this.isDisable = isDisable;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

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

    public Boolean isStoredPaymentCheck() {
        return isStoredPaymentCheck;
    }

    public void setStoredPaymentCheck(Boolean storedPaymentCheck) {
        isStoredPaymentCheck = storedPaymentCheck;
    }

    public Integer getStoredPrivilegeType() {
        return storedPrivilegeType;
    }

    public void setStoredPrivilegeType(Integer storedPrivilegeType) {
        this.storedPrivilegeType = storedPrivilegeType;
    }

    public BigDecimal getStoredPrivilegeValue() {
        return storedPrivilegeValue;
    }

    public void setStoredPrivilegeValue(BigDecimal storedPrivilegeValue) {
        this.storedPrivilegeValue = storedPrivilegeValue;
    }

    public BigDecimal getStoredFullAmount() {
        return storedFullAmount;
    }

    public void setStoredFullAmount(BigDecimal storedFullAmount) {
        this.storedFullAmount = storedFullAmount;
    }
}
