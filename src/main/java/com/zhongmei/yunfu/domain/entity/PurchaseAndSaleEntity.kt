package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

@TableName("purchase_and_sale")
class PurchaseAndSaleEntity{

    /**
     * 服务端自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 1:进货  2:销货
     */
    var type: Int? = null
    /**
     * 进货来源ID
     */
    var sourceId: Long? = null
    /**
     * 货源名称
     */
    var sourceName: String? = null
    /**
     * 品项id
     */
    var dishShopId: Long? = null
    /**
     * 进货/销货数量
     */
    var number: BigDecimal? = null
    /**
     * 进货单价
     */
    var purchasePrice: BigDecimal? = null
    /**
     * 进货总价
     */
    var totalPurchasePrice: BigDecimal? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null

    var serverCreateTime: Date? = null


    override fun toString(): String {
        return "PurchaseAndSaleEntity{" +
                ", id=" + id +
                ", type=" + type +
                ", sourceId=" + sourceId +
                ", sourceName=" + sourceName +
                ", dishShopId=" + dishShopId +
                ", number=" + number +
                ", purchasePrice=" + purchasePrice +
                ", totalPurchasePrice=" + totalPurchasePrice +
                ", brandIdenty=" + brandIdenty +
                ", shopIdenty=" + shopIdenty +
                ", serverCreateTime=" + serverCreateTime +

                "}"
    }
}
