package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 优惠券使用约束菜品
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("coupon_rule_dish")
class CouponRuleDishEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 优惠券Id
     */
    var couponId: Long? = null
    /**
     * 菜品Id
     */
    var dishId: Long? = null
    /**
     * 菜品名称
     */
    var dishName: String? = null
    /**
     * 品牌id
     */
    var brandIdentity: Long? = null


    override fun toString(): String {
        return "CouponRuleDishEntity{" +
        ", id=" + id +
        ", couponId=" + couponId +
        ", dishId=" + dishId +
        ", dishName=" + dishName +
        ", brandIdentity=" + brandIdentity +
        "}"
    }
}
