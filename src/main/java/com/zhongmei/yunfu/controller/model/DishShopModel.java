package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;
import com.zhongmei.yunfu.domain.entity.DishBrandTypeEntity;

import java.util.List;

public class DishShopModel extends WebBaseModel {

    private String keyWord;//查询关键字

    private Long shopIdentity;

    private Long dishTypeId;

    private Long creatorId;

    private String creatorName;

    private Long shopIdenty;

    private Long brandIdenty;

    private DishBrandTypeEntity dishBrandTypeEntity;

    private List<DishBrandTypeEntity> listType;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Long getShopIdentity() {
        return shopIdentity;
    }

    public void setShopIdentity(Long shopIdentity) {
        this.shopIdentity = shopIdentity;
    }

    public Long getDishTypeId() {
        return dishTypeId;
    }

    public void setDishTypeId(Long dishTypeId) {
        this.dishTypeId = dishTypeId;
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

    public DishBrandTypeEntity getDishBrandTypeEntity() {
        return dishBrandTypeEntity;
    }

    public void setDishBrandTypeEntity(DishBrandTypeEntity dishBrandTypeEntity) {
        this.dishBrandTypeEntity = dishBrandTypeEntity;
    }

    public List<DishBrandTypeEntity> getListType() {
        return listType;
    }

    public void setListType(List<DishBrandTypeEntity> listType) {
        this.listType = listType;
    }
}
