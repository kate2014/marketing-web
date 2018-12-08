package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;
import java.util.*

/**
 * <p>
 * 微信小程序购买使用记录
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("wx_trade_customer")
class WxTradeCustomerEntity : BaseEntity() {

    /**
     * 自增主键 : 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 活动id
     */
    var marketingId: Long? = null
    /**
     * 活动关联id：拼团活动id  发起的砍价活动id
     */
    var relateId: Long? = null
    /**
     * 会员编号
     */
    var customerId: Long? = null
    /**
     * 状态 1未使用、2已使用
     */
    var status: Int? = null
    /**
     * 类型 1 拼团、2 砍价、 3 秒杀
     */
    var type: Int? = null
    /**
     * 订单id 
     */
    var tradeId: Long? = null
    /**
     * 品项id 
     */
    var dishId: Long? = null
    /**
     * 品项名称
     */
    var dishName: String? = null

    var validityPeriod: Date? = null
    /**
     * 门店id 
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识 : 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 启用停用标识 : 1:启用;2:停用
     */
    var enabledFlag: Int? = null


    override fun toString(): String {
        return "WxTradeCustomerEntity{" +
        ", id=" + id +
        ", marketingId=" + marketingId +
        ", relateId=" + relateId +
        ", customerId=" + customerId +
        ", status=" + status +
        ", type=" + type +
        ", tradeId=" + tradeId +
        ", dishId=" + dishId +
        ", dishName=" + dishName +
        ", validityPeriod=" + validityPeriod +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        ", enabledFlag=" + enabledFlag +
        "}"
    }
}
