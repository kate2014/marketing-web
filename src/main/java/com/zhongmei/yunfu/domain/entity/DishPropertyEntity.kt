package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 菜品属性表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("dish_property")
class DishPropertyEntity : BaseEntity() {

    /**
     * 自增主键 : 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 属性类别id : 属性类别id
     */
    var propertyTypeId: Long? = null
    /**
     * 属性类型 : 属性类型 1：口味，做法 2：标签 3：备注 4：菜品属性
     */
    var propertyKind: Int? = null
    /**
     * 属性名称 : 属性名称1
     */
    var name: String? = null
    /**
     * 别名 : 别名
     */
    var aliasName: String? = null
    /**
     * 属性变价 : 属性变价
     */
    var reprice: BigDecimal? = null
    /**
     * 排序 : 排序
     */
    var sort: Int? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null
    var shopIdenty: Long? = null
    /**
     * uuid : uuid
     */
    var uuid: String? = null
    /**
     * 是否被固化：1 是，固化后不允许被修改 2否  
     */
    var isCure: Int? = null
    /**
     * 1:启用;2:停用
     */
    var enabledFlag: Int? = null
    var dishShopId: Long? = null


    override fun toString(): String {
        return "DishPropertyEntity{" +
        ", id=" + id +
        ", propertyTypeId=" + propertyTypeId +
        ", propertyKind=" + propertyKind +
        ", name=" + name +
        ", aliasName=" + aliasName +
        ", reprice=" + reprice +
        ", sort=" + sort +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", uuid=" + uuid +
        ", isCure=" + isCure +
        ", enabledFlag=" + enabledFlag +
        ", dishShopId=" + dishShopId +
        "}"
    }
}
