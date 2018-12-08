package com.zhongmei.yunfu.domain.entity.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DishType implements Serializable {
    private Long id;
    private String name;
    private String aliasName;
    private Long brandIdenty;
    private Long shopIdenty;
    private Long creatorId;
    private String creatorName;
    private String dishTypeDesc;
    private Integer enabledFlag;
    private Long parentId;
    private Long serverCreateTime;
    private Long serverUpdateTime;
    private Integer sort;
    private Integer statusFlag;
    private String typeCode;
    private List<DishType> dishBrandTypeBoList;

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

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
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

    public String getDishTypeDesc() {
        return dishTypeDesc;
    }

    public void setDishTypeDesc(String dishTypeDesc) {
        this.dishTypeDesc = dishTypeDesc;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getServerCreateTime() {
        return serverCreateTime;
    }

    public void setServerCreateTime(Long serverCreateTime) {
        this.serverCreateTime = serverCreateTime;
    }

    public Long getServerUpdateTime() {
        return serverUpdateTime;
    }

    public void setServerUpdateTime(Long serverUpdateTime) {
        this.serverUpdateTime = serverUpdateTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public List<DishType> getDishBrandTypeBoList() {
        return dishBrandTypeBoList;
    }

    public void setDishBrandTypeBoList(List<DishType> dishBrandTypeBoList) {
        this.dishBrandTypeBoList = dishBrandTypeBoList;
    }
}
