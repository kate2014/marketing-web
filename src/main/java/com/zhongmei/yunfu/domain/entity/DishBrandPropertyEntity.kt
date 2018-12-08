package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 菜品属性关联表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("dish_brand_property")
class DishBrandPropertyEntity : BaseEntity() {

    /**
     * 自增主键 : 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 属性类型 : 属性类型 1：口味，做法 2：标签 3：备注 4：菜品属性
     */
    var propertyKind: Int? = null
    /**
     * 属性关联id : 属性关联id
     */
    var propertyId: Long? = null
    /**
     * 属性类别关联id  : 属性类别关联id 
     */
    var propertyTypeId: Long? = null
    /**
     * 菜品id : 菜品id
     */
    var dishId: Long? = null
    /**
     * 菜品名称 : 菜品名称
     */
    var dishName: String? = null
    /**
     * 品牌标识 : 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 是否设为默认属性（做法）：1，默认；2，非默认
     */
    var isDefault: Int? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null


    override fun toString(): String {
        return "DishBrandPropertyEntity{" +
        ", id=" + id +
        ", propertyKind=" + propertyKind +
        ", propertyId=" + propertyId +
        ", propertyTypeId=" + propertyTypeId +
        ", dishId=" + dishId +
        ", dishName=" + dishName +
        ", brandIdenty=" + brandIdenty +
        ", isDefault=" + isDefault +
        ", shopIdenty=" + shopIdenty +
        "}"
    }
}
