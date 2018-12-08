package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 支付明细扩展表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("payment_item_extra")
class PaymentItemExtraEntity : BaseEntity() {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * UUID
     */
    var uuid: String? = null
    /**
     * 支付明细ID
     */
    var paymentItemId: Long? = null
    /**
     * 支付交易号
     */
    var payTranNo: String? = null
    /**
     * 支付模式：1:手机网页，2:主扫，3:被扫，4:app支付
     */
    var payType: Int? = null
    /**
     * 结算方, 0 未知 1 现金 2 翼支付 3 银联(记账)
     */
    var payChannel: Int? = null
    /**
     * 手续费
     */
    var handlingCharge: BigDecimal? = null
    /**
     * 手续费费率
     */
    var handlingChargeRate: BigDecimal? = null
    /**
     * 付款账号
     */
    var buyerAccount: String? = null
    /**
     * 支付回调时间
     */
    var payCallbackTime: Date? = null
    /**
     * 收款账号
     */
    var sellerAccount: String? = null
    /**
     * 收款帐号类型，1：众美云服帐号   2：商家帐号 3: 第三方帐号
     */
    var sellerAccountType: Int? = null
    /**
     * 退款单号
     */
    var refundTradeNo: String? = null
    /**
     * 退款回调时间
     */
    var refundCallbackTime: Date? = null
    /**
     * 会员账号ID
     */
    var customerId: Long? = null
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


    override fun toString(): String {
        return "PaymentItemExtraEntity{" +
        ", id=" + id +
        ", uuid=" + uuid +
        ", paymentItemId=" + paymentItemId +
        ", payTranNo=" + payTranNo +
        ", payType=" + payType +
        ", payChannel=" + payChannel +
        ", handlingCharge=" + handlingCharge +
        ", handlingChargeRate=" + handlingChargeRate +
        ", buyerAccount=" + buyerAccount +
        ", payCallbackTime=" + payCallbackTime +
        ", sellerAccount=" + sellerAccount +
        ", sellerAccountType=" + sellerAccountType +
        ", refundTradeNo=" + refundTradeNo +
        ", refundCallbackTime=" + refundCallbackTime +
        ", customerId=" + customerId +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", deviceIdenty=" + deviceIdenty +
        "}"
    }
}
