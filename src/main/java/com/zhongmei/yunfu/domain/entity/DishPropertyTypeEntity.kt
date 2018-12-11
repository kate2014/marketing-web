package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 菜品属性类别表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("dish_property_type")
class DishPropertyTypeEntity : BaseEntity() {

    /**
     * 自增主键 : 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 属性类别名称 : 属性类别名称
     */
    var name: String? = null
    /**
     * 套餐内分组别名 : 套餐内分组别名
     */
    var aliasName: String? = null
    /**
     * 属性类型 : 属性类型 1：口味，做法 4：菜品属性
     */
    var propertyKind: Int? = null
    /**
     * 排序 : 排序
     */
    var sort: Int? = null
    /**
     * 品牌标识 : 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 1:启用;2:停用
     */
    var enabledFlag: Int? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null


    override fun toString(): String {
        return "DishPropertyTypeEntity{" +
        ", id=" + id +
        ", name=" + name +
        ", aliasName=" + aliasName +
        ", propertyKind=" + propertyKind +
        ", sort=" + sort +
        ", brandIdenty=" + brandIdenty +
        ", enabledFlag=" + enabledFlag +
        ", shopIdenty=" + shopIdenty +
        "}"
    }
}
