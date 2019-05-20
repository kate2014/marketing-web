package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import java.util.List;

public class ShopSearchModel extends WebBaseModel {

    private Long  relatedId;
    /**
     * 品牌标识
     */
    private Long brandIdenty;
    /**
     * 门店标识
     */
    private Long shopIdenty;

    private Long creatorId;

    private String creatorName;

    private String commercialName;

    private Integer invalidStatus;

    private String province;

    private String city;

    private String area;

    private List<Long> selectShop;

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
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

    public String getCommercialName() {
        return commercialName;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public Integer getInvalidStatus() {
        return invalidStatus;
    }

    public void setInvalidStatus(Integer invalidStatus) {
        this.invalidStatus = invalidStatus;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<Long> getSelectShop() {
        return selectShop;
    }

    public void setSelectShop(List<Long> selectShop) {
        this.selectShop = selectShop;
    }
}
