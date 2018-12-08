package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 套餐
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("dish_setmeal")
class DishSetmealEntity : BaseEntity() {

    /**
     * 自增id : 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 子菜id : 子菜id/加料id。引用dish_brand id
     */
    var childDishId: Long? = null
    /**
     * 菜品id : 套餐id/菜品id。引用dish_brand id
     */
    var dishId: Long? = null
    /**
     * 套餐菜品分组id : 套餐菜品分组id
     */
    var comboDishTypeId: Long? = null
    /**
     * 子菜类型 : 1 套餐子菜  2 加料
     */
    var childDishType: Int? = null
    /**
     * 子菜变价
     */
    var price: BigDecimal? = null
    /**
     * 起卖份数 : 起卖份数
     */
    var leastCellNum: BigDecimal? = null
    /**
     * 是否必选 : 1必点,2选点
     */
    var isReplace: Int? = null
    /**
     * 是否默认 : 是否默认 1 是 2否
     */
    var isDefault: Int? = null
    /**
     * 是否复选 : 是否复选  1是 2否
     */
    var isMulti: Int? = null
    /**
     * 品牌编号 : 品牌编号
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
        return "DishSetmealEntity{" +
        ", id=" + id +
        ", childDishId=" + childDishId +
        ", dishId=" + dishId +
        ", comboDishTypeId=" + comboDishTypeId +
        ", childDishType=" + childDishType +
        ", price=" + price +
        ", leastCellNum=" + leastCellNum +
        ", isReplace=" + isReplace +
        ", isDefault=" + isDefault +
        ", isMulti=" + isMulti +
        ", brandIdenty=" + brandIdenty +
        ", sort=" + sort +
        ", shopIdenty=" + shopIdenty +
        "}"
    }
}
