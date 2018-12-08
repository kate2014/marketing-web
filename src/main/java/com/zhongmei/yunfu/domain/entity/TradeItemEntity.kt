package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 交易明细
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("trade_item")
class TradeItemEntity : BaseEntity() {

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
     * 商品是组合明细时，指向该明细的组合商品(即指向套餐主餐)
     */
    var parentId: Long? = null
    /**
     * 指向父记录的uuid，如果是子菜才有值，单菜此字段为空
     */
    var parentUuid: String? = null
    /**
     * 商品ID
     */
    var dishId: String? = null
    /**
     * 套餐内菜品分组 id
     */
    var dishSetmealGroupId: Long? = null
    /**
     * 商品名称
     */
    var dishName: String? = null
    /**
     * 菜品类型 : 菜品种类 1:单菜 2:套餐
     */
    var type: Int? = null
    /**
     * 排序位
     */
    var sort: Int? = null
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
     * 各种特征的金额合计
     */
    var propertyAmount: BigDecimal? = null
    /**
     * 售价，等于 AMOUNT + FEATURE_AMOUNT
     */
    var actualAmount: BigDecimal? = null
    /**
     * 备注
     */
    var tradeMemo: String? = null
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
     * 桌台id
     */
    var tradeTableId: Long? = null
    /**
     * 桌台UUID
     */
    var tradeTableUuid: String? = null
    /**
     * 批次号
     */
    var batchNo: String? = null
    /**
     * 是否参与整单折扣 1 是 2 否
     */
    var enableWholePrivilege: Int? = null
    /**
     * 单位名称
     */
    var unitName: String? = null
    /**
     * 当此记录是修改其他品项而来时记录被修改的品项ID
     */
    var relateTradeItemId: Long? = null
    /**
     * 当此记录是修改其他品项而来时记录被修改的品项UUID
     */
    var relateTradeItemUuid: String? = null
    /**
     * 加项总金额
     */
    var feedsAmount: BigDecimal? = null
    /**
     *  无效类型 1:退品项，2:被拆单，3：被删除 , 4:改品项,5:品项被撤回，6:改品项被撤回,7移品项 ，8联台虚拟占位品项
     */
    var invalidType: Int? = null
    /**
     * 1=正常  2=在回收站
     */
    var recycleStatus: Int? = null
    /**
     * 退品项数量
     */
    var returnQuantity: BigDecimal? = null
    /**
     * PAD本地创建时间
     */
    var clientCreateTime: Date? = null
    /**
     * PAD本地最后修改时间
     */
    var clientUpdateTime: Date? = null


    override fun toString(): String {
        return "TradeItemEntity{" +
        ", id=" + id +
        ", tradeId=" + tradeId +
        ", tradeUuid=" + tradeUuid +
        ", parentId=" + parentId +
        ", parentUuid=" + parentUuid +
        ", dishId=" + dishId +
        ", dishSetmealGroupId=" + dishSetmealGroupId +
        ", dishName=" + dishName +
        ", type=" + type +
        ", sort=" + sort +
        ", price=" + price +
        ", quantity=" + quantity +
        ", amount=" + amount +
        ", propertyAmount=" + propertyAmount +
        ", actualAmount=" + actualAmount +
        ", tradeMemo=" + tradeMemo +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", deviceIdenty=" + deviceIdenty +
        ", uuid=" + uuid +
        ", tradeTableId=" + tradeTableId +
        ", tradeTableUuid=" + tradeTableUuid +
        ", batchNo=" + batchNo +
        ", enableWholePrivilege=" + enableWholePrivilege +
        ", unitName=" + unitName +
        ", relateTradeItemId=" + relateTradeItemId +
        ", relateTradeItemUuid=" + relateTradeItemUuid +
        ", feedsAmount=" + feedsAmount +
        ", invalidType=" + invalidType +
        ", recycleStatus=" + recycleStatus +
        ", returnQuantity=" + returnQuantity +
        ", clientCreateTime=" + clientCreateTime +
        ", clientUpdateTime=" + clientUpdateTime +
        "}"
    }
}
