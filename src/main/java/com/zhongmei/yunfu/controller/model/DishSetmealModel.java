package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;
import com.zhongmei.yunfu.domain.entity.DishBrandTypeEntity;
import com.zhongmei.yunfu.domain.entity.DishSetmealEntity;
import com.zhongmei.yunfu.domain.entity.DishSetmealGroupEntity;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class DishSetmealModel extends WebBaseModel {

    private Long id;
    /**
     * 子菜id : 子菜id/加料id。引用dish_brand id
     */
    private Long childDishId;
    /**
     * 菜品id : 套餐id/菜品id。引用dish_brand id
     */
    private Long dishId;
    /**
     * 套餐菜品分组id : 套餐菜品分组id
     */
    private Long comboDishTypeId;
    /**
     * 子菜类型 : 1 套餐子菜  2 加料
     */
    private Integer childDishType;
    /**
     * 菜品名称
     */
    private String dishName;
    /**
     * 菜品编码
     */
    private String dishCode;
    /**
     * 子菜变价
     */
    private BigDecimal price;
    /**
     * 起卖份数 : 起卖份数
     */
    private BigDecimal leastCellNum;
    /**
     * 是否必选 : 1必点,2选点
     */
    private Integer isReplace;
    /**
     * 是否默认 : 是否默认 1 是 2否
     */
    private Integer isDefault;
    /**
     * 是否复选 : 是否复选  1是 2否
     */
    private Integer isMulti;
    /**
     * 排序 : 排序
     */
    private Integer sort;
    /**
     * 品牌id : 品牌id
     */
    private Long brandIdenty;
    /**
     * 门店标识
     */
    private Long shopIdenty;

    DishSetmealGroupEntity mDishSetmealGroup;

    List<DishSetmealModel> listSetmeal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChildDishId() {
        return childDishId;
    }

    public void setChildDishId(Long childDishId) {
        this.childDishId = childDishId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Long getComboDishTypeId() {
        return comboDishTypeId;
    }

    public void setComboDishTypeId(Long comboDishTypeId) {
        this.comboDishTypeId = comboDishTypeId;
    }

    public Integer getChildDishType() {
        return childDishType;
    }

    public void setChildDishType(Integer childDishType) {
        this.childDishType = childDishType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getLeastCellNum() {
        return leastCellNum;
    }

    public void setLeastCellNum(BigDecimal leastCellNum) {
        this.leastCellNum = leastCellNum;
    }

    public Integer getIsReplace() {
        return isReplace;
    }

    public void setIsReplace(Integer isReplace) {
        this.isReplace = isReplace;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(Integer isMulti) {
        this.isMulti = isMulti;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public DishSetmealGroupEntity getmDishSetmealGroup() {
        return mDishSetmealGroup;
    }

    public void setmDishSetmealGroup(DishSetmealGroupEntity mDishSetmealGroup) {
        this.mDishSetmealGroup = mDishSetmealGroup;
    }

    public List<DishSetmealModel> getListSetmeal() {
        return listSetmeal;
    }

    public void setListSetmeal(List<DishSetmealModel> listSetmeal) {
        this.listSetmeal = listSetmeal;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishCode() {
        return dishCode;
    }

    public void setDishCode(String dishCode) {
        this.dishCode = dishCode;
    }
}
