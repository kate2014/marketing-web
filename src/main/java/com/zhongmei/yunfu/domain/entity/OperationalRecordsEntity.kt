package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;
import com.zhongmei.yunfu.domain.entity.base.SupperEntity
import com.zhongmei.yunfu.domain.enums.StatusFlag
import com.zhongmei.yunfu.service.LoginManager

/**
 * <p>
 * 会员操作记录
 * </p>
 */
@TableName("operational_records")
class OperationalRecordsEntity : SupperEntity() {

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

    var customerId: Long? = null

    var customerPhone: String? = null

    var customerName: String? = null

    var wxOpenId: String? = null

    var wxPhoto: String? = null

    var wxName: String? = null

    var activityId: Long? = null

    var operationalCount: Int? = null

    /**
     * 类型：1：查看  2：分享  3：购买
     */
    var type: Int? = null
    /**
     * 1：门店操作、2：活动操作、3：新品操作、4：拼团、5：秒杀、6：砍价、7：特价活动
     */
    var source: Int? = null

    override fun toString(): String {
        return "CouponEntity{" +
                ", id=" + id +
                ", brandIdenty=" + brandIdenty +
                ", shopIdenty=" + shopIdenty +
                ", customerId=" + customerId +
                ", customerPhone=" + customerPhone +
                ", customerName=" + customerName +
                ", wxOpenId=" + wxOpenId +
                ", wxPhoto=" + wxPhoto +
                ", wxName=" + wxName +
                ", activityId=" + activityId +
                ", operationalCount=" + operationalCount +
                ", type=" + type +
                ", source=" + source +
                "}"
    }


}
