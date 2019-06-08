package com.zhongmei.yunfu.controller.model;

import java.math.BigDecimal;

public class CustomerPrivilegeRuleModel {

    private Long id;
    /**
     * 会员等级
     */
    private Integer customerLevel;
    /**
     * 权益类型集
     */
    private String privilegeTypes;
    /**
     * 权益类型： 1：会员等级折扣，2：会员等级让价，3：储值支付折扣，4：储值支付让价
     */
    private Integer privilegeType;
    /**
     * 权益额度值
     */
    private BigDecimal privilegeValue;
    /**
     * 储值金额，用于记录储值满多少给予对应的优惠
     */
    private BigDecimal saveAmount;
    /**
     * 满额限制
     */
    private BigDecimal fullAmount;
    /**
     * 是否需要储值支付 0：不需要  1：需要
     */
    private Integer isNeedSavePayment;
    /**
     * 系统全局设置Id
     */
    private Long systemSettingId;
    /**
     * 商户id
     */
    private Long shopIdenty;
    /**
     * 品牌id
     */
    private Long brandIdenty;
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

    private String successOrfail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(Integer customerLevel) {
        this.customerLevel = customerLevel;
    }

    public String getPrivilegeTypes() {
        return privilegeTypes;
    }

    public void setPrivilegeTypes(String privilegeTypes) {
        this.privilegeTypes = privilegeTypes;
    }

    public BigDecimal getPrivilegeValue() {
        return privilegeValue;
    }

    public void setPrivilegeValue(BigDecimal privilegeValue) {
        this.privilegeValue = privilegeValue;
    }

    public BigDecimal getFullAmount() {
        return fullAmount;
    }

    public void setFullAmount(BigDecimal fullAmount) {
        this.fullAmount = fullAmount;
    }

    public Integer getIsNeedSavePayment() {
        return isNeedSavePayment;
    }

    public void setIsNeedSavePayment(Integer isNeedSavePayment) {
        this.isNeedSavePayment = isNeedSavePayment;
    }

    public Long getShopIdenty() {
        return shopIdenty;
    }

    public void setShopIdenty(Long shopIdenty) {
        this.shopIdenty = shopIdenty;
    }

    public Long getBrandIdenty() {
        return brandIdenty;
    }

    public void setBrandIdenty(Long brandIdenty) {
        this.brandIdenty = brandIdenty;
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

    public String getSuccessOrfail() {
        return successOrfail;
    }

    public void setSuccessOrfail(String successOrfail) {
        this.successOrfail = successOrfail;
    }

    public BigDecimal getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(BigDecimal saveAmount) {
        this.saveAmount = saveAmount;
    }

    public Long getSystemSettingId() {
        return systemSettingId;
    }

    public void setSystemSettingId(Long systemSettingId) {
        this.systemSettingId = systemSettingId;
    }

    public Integer getPrivilegeType() {
        return privilegeType;
    }

    public void setPrivilegeType(Integer privilegeType) {
        this.privilegeType = privilegeType;
    }
}
