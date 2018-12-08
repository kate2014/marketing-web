package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 套餐内菜品分组 : dish_type表只能存在两个级别的分类
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("dish_setmeal_group")
class DishSetmealGroupEntity : BaseEntity() {

    /**
     * 自增id : 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 套餐菜品id : 套餐菜品id
     */
    var setmealDishId: Long? = null
    /**
     * 套餐内分组名称 : 套餐内分组名称
     */
    var name: String? = null
    /**
     * 套餐内分组别名 : 套餐内分组别名
     */
    var aliasName: String? = null
    /**
     * 最少选几份 : 最少选几份
     */
    var orderMin: BigDecimal? = null
    /**
     * 最多选几份 : 最多选几份
     */
    var orderMax: BigDecimal? = null
    /**
     * 品牌id : 品牌id
     */
    var brandIdenty: Long? = null
    /**
     * 排序 : 排序
     */
    var sort: Int? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null


    override fun toString(): String {
        return "DishSetmealGroupEntity{" +
        ", id=" + id +
        ", setmealDishId=" + setmealDishId +
        ", name=" + name +
        ", aliasName=" + aliasName +
        ", orderMin=" + orderMin +
        ", orderMax=" + orderMax +
        ", brandIdenty=" + brandIdenty +
        ", sort=" + sort +
        ", shopIdenty=" + shopIdenty +
        "}"
    }
}
