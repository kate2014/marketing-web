package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 单位表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("dish_unit_dictionary")
class DishUnitDictionaryEntity : BaseEntity() {

    /**
     * 自增id : 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 单位名称 : 单位名称
     */
    var name: String? = null
    /**
     * 别名 : 别名
     */
    var aliasName: String? = null
    /**
     * 品牌id : 品牌id
     */
    var brandIdenty: Long? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null


    override fun toString(): String {
        return "DishUnitDictionaryEntity{" +
        ", id=" + id +
        ", name=" + name +
        ", aliasName=" + aliasName +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        "}"
    }
}
