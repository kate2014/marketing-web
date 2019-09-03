package com.zhongmei.yunfu.controller.api.model;

public class WxPhoneReq {

    private Long brandIdenty;
    private Long shopIdenty;
    private String encryptedData;
    private String seesionKey;
    private String iv;
    private String wxOpenId;

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

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getSeesionKey() {
        return seesionKey;
    }

    public void setSeesionKey(String seesionKey) {
        this.seesionKey = seesionKey;
    }
}
