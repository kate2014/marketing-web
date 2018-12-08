package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 交易的顾客信息
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("trade_customer")
class TradeCustomerEntity : BaseEntity() {

    /**
     * 服务端自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 交易ID
     */
    var tradeId: Long? = null
    /**
     * 关联TRADE表的UUID
     */
    var tradeUuid: String? = null
    /**
     * 来客类型: 1:预约人, 2:支付人, 3:MEMBER:登录的会员 
     */
    var customerType: Int? = null
    /**
     * 顾客ID
     */
    var customerId: Long? = null
    /**
     * 顾客电话
     */
    var customerPhone: String? = null
    /**
     * 顾客姓名
     */
    var customerName: String? = null
    /**
     * 顾客性别:
1男,0女,-1未知
     */
    var customerSex: Int? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null
    /**
     * 设备标识
     */
    var deviceIdenty: String? = null
    /**
     * UUID，本笔记录唯一值
     */
    var uuid: String? = null
    /**
     * PAD本地创建时间
     */
    var clientCreateTime: Date? = null
    /**
     * PAD本地最后修改时间
     */
    var clientUpdateTime: Date? = null


    override fun toString(): String {
        return "TradeCustomerEntity{" +
        ", id=" + id +
        ", tradeId=" + tradeId +
        ", tradeUuid=" + tradeUuid +
        ", customerType=" + customerType +
        ", customerId=" + customerId +
        ", customerPhone=" + customerPhone +
        ", customerName=" + customerName +
        ", customerSex=" + customerSex +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", deviceIdenty=" + deviceIdenty +
        ", uuid=" + uuid +
        ", clientCreateTime=" + clientCreateTime +
        ", clientUpdateTime=" + clientUpdateTime +
        "}"
    }
}
