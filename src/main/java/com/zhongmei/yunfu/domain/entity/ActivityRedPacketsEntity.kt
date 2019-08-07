package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;
import com.zhongmei.yunfu.domain.enums.StatusFlag
import com.zhongmei.yunfu.service.LoginManager

/**
 * <p>
 * 特价活动赠送礼品规则
 * </p>
 */
@TableName("activity_red_packets_rule")
class ActivityRedPacketsEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 品牌Id
     */
    var brandIdenty: Long? = null
    /**
     * 门店Id
     */
    var shopIdenty: Long? = null
    /**
     * 活动Id
     */
    var activityId: Long? = null
    /**
     * 直接推荐随机最小红包
     */
    var firstMinAmount: BigDecimal? = null
    /**
     * 直接推荐随机最大红包
     */
    var firstMaxAmount: BigDecimal? = null
    /**
     * 间接推荐随机最小红包
     */
    var secondMinAmount: BigDecimal? = null
    /**
     * 间接推荐随机最大红包
     */
    var secondMaxAmount: BigDecimal? = null



    override fun toString(): String {
        return "CouponEntity{" +
                ", id=" + id +
                ", brandIdenty=" + brandIdenty +
                ", shopIdenty=" + shopIdenty +
                ", activityId=" + activityId +
                ", firstMinAmount=" + firstMinAmount +
                ", firstMaxAmount=" + firstMaxAmount +
                ", secondMinAmount=" + secondMinAmount +
                ", secondMaxAmount=" + secondMaxAmount +
                "}"
    }


}
