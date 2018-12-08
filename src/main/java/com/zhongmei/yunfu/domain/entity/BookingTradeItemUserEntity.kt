package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 预定订单销售员与订单商品关系
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("booking_trade_item_user")
class BookingTradeItemUserEntity : BaseEntity() {

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 预定id
     */
    var bookingId: Long? = null
    /**
     * 预定订单单品id
     */
    var bookingTradeItemId: Long? = null
    /**
     * 订单商品表uuid
     */
    var bookingTradeItemUuid: String? = null
    /**
     * 技师id
     */
    var userId: Long? = null
    /**
     * 技师名称
     */
    var userName: String? = null
    /**
     * 角色类型id 
     */
    var roleId: Int? = null
    /**
     * 角色类型名称 
     */
    var roleName: Int? = null
    /**
     * 品牌id
     */
    var brandIdenty: Long? = null
    /**
     * 商户id
     */
    var shopIdenty: Long? = null
    /**
     * 是否是指定技师(技师专属) 1 指定 2 非指定
     */
    var isAssign: Int? = null


    override fun toString(): String {
        return "BookingTradeItemUserEntity{" +
        ", id=" + id +
        ", bookingId=" + bookingId +
        ", bookingTradeItemId=" + bookingTradeItemId +
        ", bookingTradeItemUuid=" + bookingTradeItemUuid +
        ", userId=" + userId +
        ", userName=" + userName +
        ", roleId=" + roleId +
        ", roleName=" + roleName +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", isAssign=" + isAssign +
        "}"
    }
}
