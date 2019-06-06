package com.zhongmei.yunfu.domain

import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.annotations.TableName
import com.baomidou.mybatisplus.enums.IdType
import com.zhongmei.yunfu.domain.entity.base.BaseEntity
import java.math.BigDecimal

@TableName("customer_dish_privilege")
class CustomerDishPrivilegeEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 会员等级
     */
    var levelId: Int? = null
    /**
     * 商品Id
     */
    var dishId: Long? = null
    /**
     * 优惠类型：1：折扣 2：让价  3：特价
     */
    var privilegeType: Int? = null
    /**
     * 优惠额度：优惠值/特价金额
     */
    var privilegeValue: BigDecimal? = null
    /**
     * 门店编号
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识 : 品牌标识
     */
    var brandIdenty: Long? = null

    override fun toString(): String {
        return "CustomerDishPrivilegeEntity{" +
                ", id=" + id +
                ", levelId=" + levelId +
                ", dishId=" + dishId +
                ", privilegeType=" + privilegeType +
                ", privilegeValue=" + privilegeValue +
                ", shopIdenty=" + shopIdenty +
                ", brandIdenty=" + brandIdenty +
                "}"
    }

}