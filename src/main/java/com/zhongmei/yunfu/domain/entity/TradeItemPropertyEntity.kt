package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 交易明细特征
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("trade_item_property")
class TradeItemPropertyEntity : BaseEntity() {

    /**
     * 服务端自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 服务端自增ID
     */
    var tradeId: Long? = null
    /**
     * 订单桌台表UUID
     */
    var tradeUuid: String? = null
    /**
     * 服务端自增ID
     */
    var tradeItemId: Long? = null
    /**
     * 关联TRADE_ITEM的UUID
     */
    var tradeItemUuid: String? = null
    /**
     * 加项：(待口味、做法等统一后可以考虑取消此字段)  1.加项
     */
    var propertyType: Int? = null
    /**
     * 属性UUID，对应口味或做法的主键id（uuid）
     */
    var propertyUuid: String? = null
    /**
     * 属性名称
     */
    var propertyName: String? = null
    /**
     * 单价
     */
    var price: BigDecimal? = null
    /**
     * 数量
     */
    var quantity: BigDecimal? = null
    /**
     * 金额，等于 PRICE * QTY
     */
    var amount: BigDecimal? = null
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
     * 1=正常  2=在回收站
     */
    var recycleStatus: Int? = null


    override fun toString(): String {
        return "TradeItemPropertyEntity{" +
        ", id=" + id +
        ", tradeId=" + tradeId +
        ", tradeUuid=" + tradeUuid +
        ", tradeItemId=" + tradeItemId +
        ", tradeItemUuid=" + tradeItemUuid +
        ", propertyType=" + propertyType +
        ", propertyUuid=" + propertyUuid +
        ", propertyName=" + propertyName +
        ", price=" + price +
        ", quantity=" + quantity +
        ", amount=" + amount +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", deviceIdenty=" + deviceIdenty +
        ", uuid=" + uuid +
        ", clientCreateTime=" + clientCreateTime +
        ", clientUpdateTime=" + clientUpdateTime +
        ", recycleStatus=" + recycleStatus +
        "}"
    }
}
