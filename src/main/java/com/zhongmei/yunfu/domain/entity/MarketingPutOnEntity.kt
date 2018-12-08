package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 优惠券投放
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("marketing_put_on")
class MarketingPutOnEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 投放方案ID  1：进入小程序推送优惠券  2：参与砍价活动推送优惠券 3：注册为新会员推送优惠券 4：支付交易完成推送优惠券 5：预约完成推送优惠券
     */
    var palnId: Int? = null
    /**
     * 推送优惠信息ID
     */
    var couponId: String? = null
    /**
     * 推送优惠名称
     */
    var couponName: String? = null
    /**
     * 投放状态 1：投放中 2：未启用
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


    override fun toString(): String {
        return "MarketingPutOnEntity{" +
        ", id=" + id +
        ", palnId=" + palnId +
        ", couponId=" + couponId +
        ", couponName=" + couponName +
        ", status=" + status +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}"
    }
}
