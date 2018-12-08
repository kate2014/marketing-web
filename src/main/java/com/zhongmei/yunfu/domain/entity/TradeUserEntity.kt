package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 订单用户关联表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("trade_user")
class TradeUserEntity : BaseEntity() {

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 订单id
     */
    var tradeId: Long? = null
    /**
     * 用户id
     */
    var userId: Long? = null
    /**
     * 用户名称
     */
    var userName: String? = null
    /**
     * 类型，1  售服务  2 售卡  3 储值充值 
     */
    var type: Int? = null
    /**
     * 用户角色id 
     */
    var roleId: Int? = null
    /**
     * 用户名称
     */
    var roleName: String? = null
    /**
     * 订单商品表id
     */
    var tradeItemId: Long? = null
    /**
     * 订单商品表uuid
     */
    var tradeItemUuid: String? = null
    /**
     * 商户id
     */
    var shopIdenty: Long? = null
    /**
     * 品牌id
     */
    var brandIdenty: Long? = null


    override fun toString(): String {
        return "TradeUserEntity{" +
        ", id=" + id +
        ", tradeId=" + tradeId +
        ", userId=" + userId +
        ", userName=" + userName +
        ", type=" + type +
        ", roleId=" + roleId +
        ", roleName=" + roleName +
        ", tradeItemId=" + tradeItemId +
        ", tradeItemUuid=" + tradeItemUuid +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}"
    }
}
