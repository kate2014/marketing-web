package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;
import com.zhongmei.yunfu.domain.entity.base.SupperEntity
import com.zhongmei.yunfu.domain.enums.StatusFlag
import com.zhongmei.yunfu.service.LoginManager

/**
 * <p>
 * 特价活动赠送礼品规则
 * </p>
 */
@TableName("activity_red_packets_rule")
class RedPacketsRecordEntity : SupperEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 活动Id
     */
    var activityId: Long? = null

    var customerId: Long? = null

    var wxOpenId: String? = null

    var customerName: String? = null

    var wxPhoto: String? = null

    /**
     * 红包金额
     */
    var amount: BigDecimal? = null
    /**
     * 对应订单
     */
    var tradeId: Long? = null
    /**
     * 红包发放来源
     */
    var source: Int? = null

    override fun toString(): String {
        return "CouponEntity{" +
                ", id=" + id +
                ", brandIdenty=" + brandIdenty +
                ", shopIdenty=" + shopIdenty +
                ", activityId=" + activityId +
                ", customerId=" + customerId +
                ", wxOpenId=" + wxOpenId +
                ", customerName=" + customerName +
                ", wxPhoto=" + wxPhoto +
                ", amount=" + amount +
                ", tradeId=" + tradeId +
                ", source=" + source +
                "}"
    }


}
