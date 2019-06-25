package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;
import com.zhongmei.yunfu.domain.entity.DishBrandTypeEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DishShopModel extends WebBaseModel {

    private String keyWord;//查询关键字

    private Long dishShopId;

    private String dishCode;

    private BigDecimal marketPrice;

    private String name;

    private String unitName;

    private Long dishTypeId;

    private String typeCode;

    private String typeName;

    private Long parentId;

    private Long creatorId;

    private String creatorName;

    private Long shopIdenty;

    private Long brandIdenty;

    private String addition;

    private BigDecimal dishQty;

    private LinkedList<String> additionName;

    private LinkedList<String> additionPrice;

    private DishBrandTypeEntity dishBrandTypeEntity;

    private List<DishBrandTypeEntity> listType;

    private String successOrfail;

    private Long supplierId;

    private String supplierName;

    private BigDecimal supplierCount;

    private BigDecimal supplierPrice;

    private Long salesDishShopId;

    private Long salesDishTypeId;

    private String salesSupplierName;

    private BigDecimal salesSupplierCount;

    private Integer type;

    private List<String> setmealTypeId;

    private List<String> setmealTypeName;

    private List<String> typeOrderMin;

    private List<String> typeOrderMax;

    private List<String> comboDishTypeId;

    private List<String> setmealId;

    private List<String> childDishId;

    private List<String> isReplace;

    private List<String> isDefault;

    private List<String> isMulti;

    private List<String> leastCellNum;

    private List<String> setmealPrice;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Long getDishShopId() {
        return dishShopId;
    }

    public void setDishShopId(Long dishShopId) {
        this.dishShopId = dishShopId;
    }

    public String getDishCode() {
        return dishCode;
    }

    public void setDishCode(String dishCode) {
        this.dishCode = dishCode;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getDishTypeId() {
        return dishTypeId;
    }

    public void setDishTypeId(Long dishTypeId) {
        this.dishTypeId = dishTypeId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public BigDecimal getDishQty() {
        return dishQty;
    }

    public void setDishQty(BigDecimal dishQty) {
        this.dishQty = dishQty;
    }

    public LinkedList<String> getAdditionName() {
        return additionName;
    }

    public void setAdditionName(LinkedList<String> additionName) {
        this.additionName = additionName;
    }

    public LinkedList<String> getAdditionPrice() {
        return additionPrice;
    }

    public void setAdditionPrice(LinkedList<String> additionPrice) {
        this.additionPrice = additionPrice;
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

    public String getSuccessOrfail() {
        return successOrfail;
    }

    public void setSuccessOrfail(String successOrfail) {
        this.successOrfail = successOrfail;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getSupplierCount() {
        return supplierCount;
    }

    public void setSupplierCount(BigDecimal supplierCount) {
        this.supplierCount = supplierCount;
    }

    public BigDecimal getSupplierPrice() {
        return supplierPrice;
    }

    public void setSupplierPrice(BigDecimal supplierPrice) {
        this.supplierPrice = supplierPrice;
    }

    public Long getSalesDishShopId() {
        return salesDishShopId;
    }

    public void setSalesDishShopId(Long salesDishShopId) {
        this.salesDishShopId = salesDishShopId;
    }

    public Long getSalesDishTypeId() {
        return salesDishTypeId;
    }

    public void setSalesDishTypeId(Long salesDishTypeId) {
        this.salesDishTypeId = salesDishTypeId;
    }

    public String getSalesSupplierName() {
        return salesSupplierName;
    }

    public void setSalesSupplierName(String salesSupplierName) {
        this.salesSupplierName = salesSupplierName;
    }

    public BigDecimal getSalesSupplierCount() {
        return salesSupplierCount;
    }

    public void setSalesSupplierCount(BigDecimal salesSupplierCount) {
        this.salesSupplierCount = salesSupplierCount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getSetmealTypeId() {
        return setmealTypeId;
    }

    public void setSetmealTypeId(List<String> setmealTypeId) {
        this.setmealTypeId = setmealTypeId;
    }

    public List<String> getSetmealTypeName() {
        return setmealTypeName;
    }

    public void setSetmealTypeName(List<String> setmealTypeName) {
        this.setmealTypeName = setmealTypeName;
    }

    public List<String> getTypeOrderMin() {
        return typeOrderMin;
    }

    public void setTypeOrderMin(List<String> typeOrderMin) {
        this.typeOrderMin = typeOrderMin;
    }

    public List<String> getTypeOrderMax() {
        return typeOrderMax;
    }

    public void setTypeOrderMax(List<String> typeOrderMax) {
        this.typeOrderMax = typeOrderMax;
    }

    public List<String> getSetmealId() {
        return setmealId;
    }

    public void setSetmealId(List<String> setmealId) {
        this.setmealId = setmealId;
    }

    public List<String> getComboDishTypeId() {
        return comboDishTypeId;
    }

    public void setComboDishTypeId(List<String> comboDishTypeId) {
        this.comboDishTypeId = comboDishTypeId;
    }

    public List<String> getChildDishId() {
        return childDishId;
    }

    public void setChildDishId(List<String> childDishId) {
        this.childDishId = childDishId;
    }

    public List<String> getIsReplace() {
        return isReplace;
    }

    public void setIsReplace(List<String> isReplace) {
        this.isReplace = isReplace;
    }

    public List<String> getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(List<String> isDefault) {
        this.isDefault = isDefault;
    }

    public List<String> getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(List<String> isMulti) {
        this.isMulti = isMulti;
    }

    public List<String> getLeastCellNum() {
        return leastCellNum;
    }

    public void setLeastCellNum(List<String> leastCellNum) {
        this.leastCellNum = leastCellNum;
    }

    public List<String> getSetmealPrice() {
        return setmealPrice;
    }

    public void setSetmealPrice(List<String> setmealPrice) {
        this.setmealPrice = setmealPrice;
    }

}
