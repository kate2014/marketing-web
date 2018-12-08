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
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("booking_trade_item")
class BookingTradeItemEntity : BaseEntity() {

    /**
     * 服务端自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 预定 ID
     */
    var bookingId: Long? = null
    /**
     *  预定 UUID
     */
    var bookingUuid: String? = null
    /**
     * 商品id
     */
    var dishId: String? = null
    /**
     * 商品名称
     */
    var dishName: String? = null
    /**
     * 菜品类型 : 菜品种类 0:单菜 1:套餐
     */
    var type: Int? = null
    /**
     * 排序位
     */
    var sort: Int? = null
    /**
     * 售价，等于 AMOUNT + FEATURE_AMOUNT
     */
    var actualAmount: BigDecimal? = null
    /**
     * 备注
     */
    var memo: String? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null
    /**
     * 单位名称
     */
    var unitName: String? = null
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
        return "BookingTradeItemEntity{" +
        ", id=" + id +
        ", bookingId=" + bookingId +
        ", bookingUuid=" + bookingUuid +
        ", dishId=" + dishId +
        ", dishName=" + dishName +
        ", type=" + type +
        ", sort=" + sort +
        ", actualAmount=" + actualAmount +
        ", memo=" + memo +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", unitName=" + unitName +
        ", uuid=" + uuid +
        ", clientCreateTime=" + clientCreateTime +
        ", clientUpdateTime=" + clientUpdateTime +
        "}"
    }
}
