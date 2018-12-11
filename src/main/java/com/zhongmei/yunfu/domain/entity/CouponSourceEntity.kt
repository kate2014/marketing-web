package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("coupon_source")
class CouponSourceEntity : BaseEntity() {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 来源id
     */
    var sourceId: Int? = null
    /**
     * 来源名称
     */
    var sourceName: String? = null


    override fun toString(): String {
        return "CouponSourceEntity{" +
        ", id=" + id +
        ", sourceId=" + sourceId +
        ", sourceName=" + sourceName +
        "}"
    }
}
