package com.zhongmei.yunfu.domain.entity

import com.baomidou.mybatisplus.enums.IdType
import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity

/**
 *
 * @author pigeon88
 * @since 2018-12-19
 */
@TableName("commercial_group")
class CommercialGroupEntity : BaseEntity() {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 品牌编号
     */
    var brandId: Long? = null
    /**
     * 门店编号
     */
    var commercialId: Long? = null

    override fun toString(): String {
        return "CommercialGroupEntity{" +
                ", id=" + id +
                ", brandId=" + brandId +
                ", commercialId=" + commercialId +
                "}"
    }
}
