package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 推广提成
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("expanded_commission")
class ExpandedCommissionEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    var customerId: Long? = null
    /**
     * 累计消费金额
     */
    var totalAmount: BigDecimal? = null
    /**
     * 单次消费金额
     */
    var changeSalesAmount: BigDecimal? = null
    /**
     * 提成比例
     */
    var commissionRatio: BigDecimal? = null
    /**
     * 单次提成金额
     */
    var commissionAmount: BigDecimal? = null
    /**
     * 合计提成金额
     */
    var totalCommission: BigDecimal? = null
    /**
     * 可兑换提成金额
     */
    var canExchange: BigDecimal? = null
    /**
     * 单次兑换提成金额
     */
    var exchangeAmount: BigDecimal? = null
    /**
     * 业务类型 1：提成累加  2：兑换 3:扣减（退货时需扣减）
     */
    var type: Int? = null
    /**
     * 交易订单Id
     */
    var tradeId: Long? = null
    /**
     * 门店id
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null


    override fun toString(): String {
        return "ExpandedCommissionEntity{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", totalAmount=" + totalAmount +
        ", changeSalesAmount=" + changeSalesAmount +
        ", commissionRatio=" + commissionRatio +
        ", commissionAmount=" + commissionAmount +
        ", totalCommission=" + totalCommission +
        ", canExchange=" + canExchange +
        ", exchangeAmount=" + exchangeAmount +
        ", type=" + type +
        ", tradeId=" + tradeId +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}"
    }
}
