package com.zhongmei.yunfu.service.vo

import java.math.BigDecimal
import java.util.*

class CustomerCouponVo {

    /**
     * 自增主键
     */
    var id: Long? = null
    /**
     * 品牌Id
     */
    var brandIdenty: Long? = null
    /**
     * 门店Id
     */
    var shopIdenty: Long? = null
    /**
     * 优惠券名称
     */
    var name: String? = null
    /**
     * 券类型(1：折扣、2：代金、3：礼品、)
     */
    var couponType: Int? = null
    /**
     * 消费满额
     */
    var fullValue: Int? = null
    /**
     * 1：折扣数值、2：代金金额
     */
    var discountValue: BigDecimal? = null
    /**
     * 品项id
     */
    var dishId: Long? = null
    /**
     * 品项名称
     */
    var dishName: String? = null
    /**
     * 备注信息
     */
    var remark: String? = null
    /**
     * 券内容
     */
    var content: String? = null
    /**
     * 适用商品：1.全部商品可用；2.部分商品可用；3.部分商品不可用
     */
    var applyDish: Int? = null
    /**
     * 是否限制使用门店（0：否、1：是）
     */
    var restrictUseCommercial: Int? = null
    /**
     * 优惠叠加类型（1可叠加同享|2不可叠加同享|3部分可叠加）
     */
    var sharingPrivilegeType: Int? = null
    /**
     * 优惠卷状态（1:使用中，2:停用中）
     */
    var couponState: Int? = null
    /**
     * 活动结束时间
     */
    var endTime: Date? = null

    /**
     * 会员优惠券关联表id
     */
    var customerCouponId: Long? = null

}
