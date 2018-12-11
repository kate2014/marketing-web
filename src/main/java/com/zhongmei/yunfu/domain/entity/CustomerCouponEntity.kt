package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 会员优惠券关联表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("customer_coupon")
class CustomerCouponEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 优惠券id
     */
    var couponId: Long? = null
    /**
     * 优惠券来源 1：商户会员模块触发 2：进入小程序触发 3：参与砍价获取 4：注册新会员获取 5：完成交易获取 6：预约成功获取 7：分析门店获取 8：分析活动获取 9：分析新品获取 10：全员推广获取 11：同行特惠获取
     */
    var sourceId: Int? = null
    /**
     * 优惠券类型
     */
    var couponType: Int? = null
    /**
     * 优惠券名称
     */
    var couponName: String? = null
    /**
     * 会员编号
     */
    var customerId: Long? = null
    /**
     * 会员编号
     */
    var wxCustomerOpenid: String? = null
    /**
     * 使用状态 : 1:未使用;2:已使用
     */
    var status: Int? = null
    /**
     * 门店id 
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 启用停用标识 : 1:启用;2:停用
     */
    var enabledFlag: Int? = null


    override fun toString(): String {
        return "CustomerCouponEntity{" +
        ", id=" + id +
        ", couponId=" + couponId +
        ", sourceId=" + sourceId +
        ", couponType=" + couponType +
        ", couponName=" + couponName +
        ", customerId=" + customerId +
        ", wxCustomerOpenid=" + wxCustomerOpenid +
        ", status=" + status +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        ", enabledFlag=" + enabledFlag +
        "}"
    }
}
