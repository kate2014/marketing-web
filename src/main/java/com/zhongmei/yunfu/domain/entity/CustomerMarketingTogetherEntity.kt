package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 会员关联同行特惠
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("customer_marketing_together")
class CustomerMarketingTogetherEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 会员ID
     */
    var customerId: Long? = null
    /**
     * 会员名称
     */
    var customerName: String? = null
    /**
     * 微信openId
     */
    var openId: String? = null
    /**
     * 邀请的好友会员ID
     */
    var fCustomerId: Long? = null
    /**
     * 邀请的好友微信openId
     */
    var fOpenId: String? = null
    /**
     * 邀请的好友名称
     */
    var fCustomerName: String? = null
    /**
     * 同行优惠券id
     */
    var couponId: Long? = null
    /**
     * 同行优惠券名称
     */
    var couponName: String? = null
    /**
     * 同行特惠方案id
     */
    var togetherId: Long? = null
    /**
     * 同行验证码
     */
    var togetherCode: String? = null
    /**
     * 同行邀请状态  1：结束 2：未接受 3:接受 4：拒绝
     */
    var status: Int? = null
    /**
     * 邀请批次号，邀请人和被邀请人批次号相同，在到店使用时可根据该批次号找的对应的两个同行码进行验证
     */
    var batchCode: String? = null
    /**
     * 活动结束时间
     */
    var validData: Date? = null
    /**
     * 门店id
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null


    override fun toString(): String {
        return "CustomerMarketingTogetherEntity{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", customerName=" + customerName +
        ", openId=" + openId +
        ", fCustomerId=" + fCustomerId +
        ", fOpenId=" + fOpenId +
        ", fCustomerName=" + fCustomerName +
        ", couponId=" + couponId +
        ", couponName=" + couponName +
        ", togetherId=" + togetherId +
        ", togetherCode=" + togetherCode +
        ", status=" + status +
        ", batchCode=" + batchCode +
        ", validData=" + validData +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}"
    }
}
