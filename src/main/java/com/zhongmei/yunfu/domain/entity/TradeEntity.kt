package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 交易记录主单（相当于ORDERS）。
主单及其所有子单中的数量、金额在退货时都取反
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("trade")
class TradeEntity : BaseEntity() {

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
     * 所属领域:
 1: beauty:美业
     */
    var domainType: Int? = null
    /**
     * 业务形态：业务形态1.美业销货，2，余额储值，3，次卡充值 4,小程序服务购买
     */
    var businessType: Int? = null
    /**
     * 交易类型 1:SELL:售货 2:REFUND:退货 3:REPAY:反结账 4:REPAY_FOR_REFUND:反结账退货
     */
    var tradeType: Int? = null
    /**
     * 流水号
     */
    var serialNumber: String? = null
    /**
     * 交易时间
     */
    var tradeTime: Date? = null
    /**
     * 交易状态(订单状态)  1:UNPROCESSED:未处理  2:TEMPORARY:挂单，不需要厨房打印(客户端本地的.)  3:CONFIRMED:已确认  4:FINISH:已完成(全部支付)  5:RETURNED:已退货  6:INVALID:已作废  7:REFUSED:已拒绝,  8:已取消 10:已反结账 11:已挂账 12:已销账 13:待清账,14:被合单
     */
    var tradeStatus: Int? = null
    /**
     * 支付状态：  1:UNPAID:未支付  2:PAYING:支付中(目前组合支付使用)  3:PAID:已支付  4:REFUNDING:退款中  5:REFUNDED:已退款  6:REFUND_FAILED:退款失败  7:PREPAID:预支付  8:WAITING_REFUND:等待退款 9:PAID_FAIL:支付失败  
     */
    var tradePayStatus: Int? = null
    /**
     * 订单形式： 1:HERE:内用，2,上门服务 
     */
    var deliveryType: Int? = null
    /**
     * 来源：1:pos,2:美业小程序
     */
    var source: Int? = null
    /**
     * 单号,生成规则:固定编码(3)+年月日时分秒(yyMMddHHmmSS)+erp后台设置硬件编号(6)
     */
    var tradeNo: String? = null
    /**
     * 商品种数，每种商品计1，组合套餐明细不计
     */
    var dishKindCount: Int? = null
    /**
     * 销售金额，明细SALE_AMOUNT之和
     */
    var saleAmount: BigDecimal? = null
    /**
     * 各种优惠折扣的减免金额，销货时为负数，退货时为正数
     */
    var privilegeAmount: BigDecimal? = null
    /**
     * 交易金额，等于SALE_AMOUNT与PRIVILEGE_AMOUNT之和
     */
    var tradeAmount: BigDecimal? = null
    /**
     * 进位处理之前金额
     */
    var tradeAmountBefore: BigDecimal? = null
    /**
     * 备注
     */
    var tradeMemo: String? = null
    /**
     * 1、退货所对应的销货单
2、拆单时对应的原单
     */
    var relateTradeId: Long? = null
    var relateTradeUuid: String? = null
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
     * 订单就餐人数订单实际就餐人数，放在总表里面,是方便后台根据总金额做统计
     */
    var tradePeopleCount: Int? = null


    override fun toString(): String {
        return "TradeEntity{" +
        ", id=" + id +
        ", bizDate=" + bizDate +
        ", domainType=" + domainType +
        ", businessType=" + businessType +
        ", tradeType=" + tradeType +
        ", serialNumber=" + serialNumber +
        ", tradeTime=" + tradeTime +
        ", tradeStatus=" + tradeStatus +
        ", tradePayStatus=" + tradePayStatus +
        ", deliveryType=" + deliveryType +
        ", source=" + source +
        ", tradeNo=" + tradeNo +
        ", dishKindCount=" + dishKindCount +
        ", saleAmount=" + saleAmount +
        ", privilegeAmount=" + privilegeAmount +
        ", tradeAmount=" + tradeAmount +
        ", tradeAmountBefore=" + tradeAmountBefore +
        ", tradeMemo=" + tradeMemo +
        ", relateTradeId=" + relateTradeId +
        ", relateTradeUuid=" + relateTradeUuid +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", deviceIdenty=" + deviceIdenty +
        ", uuid=" + uuid +
        ", clientCreateTime=" + clientCreateTime +
        ", clientUpdateTime=" + clientUpdateTime +
        ", tradePeopleCount=" + tradePeopleCount +
        "}"
    }
}
