package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 菜品属性表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("dish_time_charging_rule")
class DishTimeChargingRuleEntity : BaseEntity() {

    /**
     * 自增主键 : 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 服务品项Id
     */
    var dishId: Long? = null
    /**
     * 起始计费时间
     */
    var startChargingTimes: BigDecimal? = null
    /**
     * 起始计费金额
     */
    var startChargingPrice: BigDecimal? = null
    /**
     * 计费单位
     */
    var chargingUnit: BigDecimal? = null
    /**
     * 计费单位价格
     */
    var chargingPrice: BigDecimal? = null
    /**
     * 满计时设置
     */
    var fullUnit: BigDecimal? = null
    /**
     * 满计时额度计时规则
     */
    var fullUnitCharging: BigDecimal? = null
    /**
     * 不满计时额度设置
     */
    var noFullUnit: BigDecimal? = null
    /**
     * 不满额度计时规则
     */
    var noFullUnitCharging: BigDecimal? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null

    var shopIdenty: Long? = null


    override fun toString(): String {
        return "DishTimeChargingRuleEntity{" +
                ", id=" + id +
                ", dishId=" + dishId +
                ", startChargingTimes=" + startChargingTimes +
                ", startChargingPrice=" + startChargingPrice +
                ", chargingUnit=" + chargingUnit +
                ", chargingPrice=" + chargingPrice +
                ", fullUnit=" + fullUnit +
                ", fullUnitCharging=" + fullUnitCharging +
                ", noFullUnit=" + noFullUnit +
                ", noFullUnitCharging=" + noFullUnitCharging +
                ", brandIdenty=" + brandIdenty +
                ", shopIdenty=" + shopIdenty +
                "}"
    }
}
