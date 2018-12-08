package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 菜品类型
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("dish_brand_type")
class DishBrandTypeEntity : BaseEntity() {

    /**
     * 自增长主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 父类型id : 如果父类型id为空，则表示当前类别为顶级类型
     */
    var parentId: Long? = null
    /**
     * 类别编码 : 类别编码
     */
    var typeCode: String? = null
    /**
     * 菜品类型名称 : 菜品类型名称
     */
    var name: String? = null
    /**
     * 菜品类型别名
     */
    var aliasName: String? = null
    /**
     * 顺序 : 顺序
     */
    var sort: Int? = null
    /**
     * 菜品分类描述 : 菜品分类描述
     */
    var dishTypeDesc: String? = null
    /**
     * 是否可以点菜 : 是否可以点菜(1：是，2：否)
     */
    var isOrder: Int? = null
    /**
     * 同步标识 : 同步标识. 32位的唯一值. 
     */
    var uuid: String? = null
    /**
     * 品牌id : 品牌id
     */
    var brandIdenty: Long? = null
    /**
     * 1:启用;2:停用
     */
    var enabledFlag: Int? = null
    /**
     * 是否固化，固化的大类下的商品不允许编辑删除，1-是（不允许编辑删除），2否（允许编辑删除）
     */
    var isCure: Int? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null


    override fun toString(): String {
        return "DishBrandTypeEntity{" +
        ", id=" + id +
        ", parentId=" + parentId +
        ", typeCode=" + typeCode +
        ", name=" + name +
        ", aliasName=" + aliasName +
        ", sort=" + sort +
        ", dishTypeDesc=" + dishTypeDesc +
        ", isOrder=" + isOrder +
        ", uuid=" + uuid +
        ", brandIdenty=" + brandIdenty +
        ", enabledFlag=" + enabledFlag +
        ", isCure=" + isCure +
        ", shopIdenty=" + shopIdenty +
        "}"
    }
}
