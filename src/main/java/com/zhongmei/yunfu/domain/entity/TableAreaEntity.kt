package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 区域表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("table_area")
class TableAreaEntity : BaseEntity() {

    /**
     * 区域id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 区域名称
     */
    var areaName: String? = null
    /**
     * 品牌id
     */
    var brandIdenty: Long? = null
    /**
     * 门店id
     */
    var shopIdenty: Long? = null
    /**
     * 区域编号
     */
    var areaCode: String? = null
    /**
     * 备注
     */
    var memo: String? = null
    var brandIdentity: Long? = null
    var shopIdentity: Long? = null


    override fun toString(): String {
        return "TableAreaEntity{" +
        ", id=" + id +
        ", areaName=" + areaName +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", areaCode=" + areaCode +
        ", memo=" + memo +
        ", brandIdentity=" + brandIdentity +
        ", shopIdentity=" + shopIdentity +
        "}"
    }
}
