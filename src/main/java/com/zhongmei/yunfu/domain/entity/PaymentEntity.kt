package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 付款主单
主单及其子单的金额在退货时都取反
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("payment")
class PaymentEntity : BaseEntity() {

    /**
     * 服务端自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 营业日期，由服务端生成
     */
    var bizDate: Date? = null
    /**
     * 付款时间
     */
    var paymentTime: Date? = null
    /**
     * 付款类型：1:trade_sell:交易支付，2:trade_refund:交易退款， 3:会员储值(pad支付/微信上支付,没有mac地址)， 4:会员储值退款,5次卡购买,6次卡退卡，7:调账
     */
    var paymentType: Int? = null
    /**
     * 与此付款有关联的记录ID，如TRADE.ID。与PAYMENT_TYPE配合使用
     */
    var relateId: Long? = null
    /**
     * 与此付款有关联的记录UUID，如TRADE.UUID。与PAYMENT_TYPE配合使用
     */
    var relateUuid: String? = null
    /**
     * 可收金额，抹零前顾客应该支付的金额
     */
    var receivableAmount: BigDecimal? = null
    /**
     * 抹零金额
     */
    var exemptAmount: BigDecimal? = null
    /**
     * 顾客实付金额
     */
    var actualAmount: BigDecimal? = null
    /**
     * 交班UUID
     */
    var handoverUuid: String? = null
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
    /**
     * 是否已付款 1 已付款  2 未付款
     */
    var isPaid: Int? = null
    /**
     * 调账原因备注
     */
    var memo: String? = null
    /**
     * 1=正常  2=在回收站
     */
    var recycleStatus: Int? = null
    /**
     * 商户实收金额
     */
    var shopActualAmount: BigDecimal? = null


    override fun toString(): String {
        return "PaymentEntity{" +
        ", id=" + id +
        ", bizDate=" + bizDate +
        ", paymentTime=" + paymentTime +
        ", paymentType=" + paymentType +
        ", relateId=" + relateId +
        ", relateUuid=" + relateUuid +
        ", receivableAmount=" + receivableAmount +
        ", exemptAmount=" + exemptAmount +
        ", actualAmount=" + actualAmount +
        ", handoverUuid=" + handoverUuid +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", deviceIdenty=" + deviceIdenty +
        ", uuid=" + uuid +
        ", clientCreateTime=" + clientCreateTime +
        ", clientUpdateTime=" + clientUpdateTime +
        ", isPaid=" + isPaid +
        ", memo=" + memo +
        ", recycleStatus=" + recycleStatus +
        ", shopActualAmount=" + shopActualAmount +
        "}"
    }
}
