package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 付款明细
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("payment_item")
class PaymentItemEntity : BaseEntity() {

    /**
     * 服务端自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 服务端自增ID
     */
    var paymentId: Long? = null
    var paymentUuid: String? = null
    /**
     * 支付方式ID(cashTypeId)：1:会员余额, 2:现金,3:银行卡,4:微信支付,5:支付宝 
     */
    var payModeId: Long? = null
    /**
     * 支付方式名称，冗余字段
     */
    var payModeName: String? = null
    /**
     * 票面金额
     */
    var faceAmount: BigDecimal? = null
    /**
     * 抵扣金额，即实际用于支付的金额
     */
    var usefulAmount: BigDecimal? = null
    /**
     * 找零金额
     */
    var changeAmount: BigDecimal? = null
    /**
     * 关联ID，随支付方式类型而定。
如：会员余额支付时为会员ID
     */
    var relateId: String? = null
    /**
     * 支付状态：  1:UNPAID:未支付  2:PAYING:支付中，微信下单选择了在线支付但实际上未完成支付的  (删了)  3:PAID:已支付  4:REFUNDING:退款中  5:REFUNDED:已退款  6:REFUND_FAILED:退款失败  7:PREPAID:预支付(现在都没用)  8:WAITING_REFUND:等待退款 9:PAY_FAIL支付失败 10:REPEAT_PAID重复支付 11:异常支付
     */
    var payStatus: Int? = null
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
     * 是否支持退款  1：是；2：否
     */
    var isRefund: Int? = null
    /**
     * 交易描述
     */
    var payMemo: String? = null
    /**
     * 退款方式，1：无需退款 2：自动退款 3：手动退款
     */
    var refundWay: Int? = null
    /**
     * 支付来源， 1：pos, 2:美业小程序
     */
    var paySource: Int? = null


    override fun toString(): String {
        return "PaymentItemEntity{" +
        ", id=" + id +
        ", paymentId=" + paymentId +
        ", paymentUuid=" + paymentUuid +
        ", payModeId=" + payModeId +
        ", payModeName=" + payModeName +
        ", faceAmount=" + faceAmount +
        ", usefulAmount=" + usefulAmount +
        ", changeAmount=" + changeAmount +
        ", relateId=" + relateId +
        ", payStatus=" + payStatus +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", deviceIdenty=" + deviceIdenty +
        ", uuid=" + uuid +
        ", clientCreateTime=" + clientCreateTime +
        ", clientUpdateTime=" + clientUpdateTime +
        ", isRefund=" + isRefund +
        ", payMemo=" + payMemo +
        ", refundWay=" + refundWay +
        ", paySource=" + paySource +
        "}"
    }
}
