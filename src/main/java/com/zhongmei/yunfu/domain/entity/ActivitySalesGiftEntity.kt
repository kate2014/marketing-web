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
@TableName("activity_sales_gift_rule")
class ActivitySalesGiftEntity : BaseEntity() {

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
     * 礼品图片
     */
    var imageUrl: String? = null
    /**
     * 成单数
     */
    var orderCount: Int? = null
    /**
     * 礼品id
     */
    var giftId: Long? = null
    /**
     * 礼品名称
     */
    var giftName: String? = null
    /**
     * 礼品类型
     */
    var giftType: Int? = null



    override fun toString(): String {
        return "CouponEntity{" +
                ", id=" + id +
                ", brandIdenty=" + brandIdenty +
                ", shopIdenty=" + shopIdenty +
                ", activityId=" + activityId +
                ", imageUrl=" + imageUrl +
                ", orderCount=" + orderCount +
                ", giftId=" + giftId +
                ", giftName=" + giftName +
                ", giftType=" + giftType +
                "}"
    }


}
