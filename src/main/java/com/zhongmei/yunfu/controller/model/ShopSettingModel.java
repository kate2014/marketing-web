package com.zhongmei.yunfu.controller.model;

public class ShopSettingModel {

    /**
     * 品牌标识
     */
    private Long brandIdenty;
    /**
     * 门店标识
     */
    private Long shopIdenty;
    /**
     * 商家id
     */
    private Long commercialId;
    /**
     * 商家名称
     */
    private String commercialName;
    /**
     * 联系人
     */
    private String commercialContact;
    /**
     * 联系电话
     */
    private String commercialPhone;
    /**
     * 商家地址
     */
    private String commercialAdress;
    /**
     * 商户描述
     */
    private String commercialDesc;
    /**
     * LOGO对应的URL
     */
    private String imgUrl;
    /**
     * 小程序应用密钥
     */
    private String wxAppsecret;

    /**
     * 商家支付api秘钥
     */
    private String apiSecret;
    /**
     * 密钥文件名称
     */
    private String secretFilepathTitle;
    /**
     * 密钥文件路径
     */
    private String secretFilepath;
    /**
     * 小程序商户标示
     */
    private String wxAppid;
    /**
     * 应用密钥
     */
    private String appsecret;
    /**
     * 商户标示
     */
    private String appid;
    /**
     * 小程序支付商户编号
     */
    private String wxShopId;
    /**
     * 翼支付appid
     */
    private String yzfAppid;
    /**
     * 翼支付AppSecret
     */
    private String yzfAppsecret;
    /**
     * 数据类型：1 小程序公众号信息  2商户支付信息
     */
    private int type;

    private String successOrfail;

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getSecretFilepathTitle() {
        return secretFilepathTitle;
    }

    public void setSecretFilepathTitle(String secretFilepathTitle) {
        this.secretFilepathTitle = secretFilepathTitle;
    }

    public String getSecretFilepath() {
        return secretFilepath;
    }

    public void setSecretFilepath(String secretFilepath) {
        this.secretFilepath = secretFilepath;
    }

    public String getWxAppsecret() {
        return wxAppsecret;
    }

    public void setWxAppsecret(String wxAppsecret) {
        this.wxAppsecret = wxAppsecret;
    }

    public String getWxAppid() {
        return wxAppid;
    }

    public void setWxAppid(String wxAppid) {
        this.wxAppid = wxAppid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getCommercialContact() {
        return commercialContact;
    }

    public void setCommercialContact(String commercialContact) {
        this.commercialContact = commercialContact;
    }

    public String getCommercialPhone() {
        return commercialPhone;
    }

    public void setCommercialPhone(String commercialPhone) {
        this.commercialPhone = commercialPhone;
    }

    public String getCommercialAdress() {
        return commercialAdress;
    }

    public void setCommercialAdress(String commercialAdress) {
        this.commercialAdress = commercialAdress;
    }

    public String getCommercialDesc() {
        return commercialDesc;
    }

    public void setCommercialDesc(String commercialDesc) {
        this.commercialDesc = commercialDesc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSuccessOrfail() {
        return successOrfail;
    }

    public void setSuccessOrfail(String successOrfail) {
        this.successOrfail = successOrfail;
    }

    public String getYzfAppid() {
        return yzfAppid;
    }

    public void setYzfAppid(String yzfAppid) {
        this.yzfAppid = yzfAppid;
    }

    public String getYzfAppsecret() {
        return yzfAppsecret;
    }

    public void setYzfAppsecret(String yzfAppsecret) {
        this.yzfAppsecret = yzfAppsecret;
    }

    public String getWxShopId() {
        return wxShopId;
    }

    public void setWxShopId(String wxShopId) {
        this.wxShopId = wxShopId;
    }
}
