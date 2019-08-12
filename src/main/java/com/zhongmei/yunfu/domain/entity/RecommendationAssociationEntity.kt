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
 * 推荐成单关联表
 * </p>
 */
@TableName("recommendation_association")
class RecommendationAssociationEntity : SupperEntity() {

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

    var mainCustomerId: Long? = null

    var mainWxOpenId: String? = null

    var mainCustomerName: String? = null

    var acceptWxOpenId: String? = null

    var tradeId: Long? = null

    var activityId: Long? = null

    /**
     * 推荐状态：1：已下单  2：已支付
     */
    var transactionStatus: Int? = null

    override fun toString(): String {
        return "CouponEntity{" +
                ", id=" + id +
                ", brandIdenty=" + brandIdenty +
                ", shopIdenty=" + shopIdenty +
                ", mainCustomerId=" + mainCustomerId +
                ", mainWxOpenId=" + mainWxOpenId +
                ", mainCustomerName=" + mainCustomerName +
                ", acceptWxOpenId=" + acceptWxOpenId +
                ", tradeId=" + tradeId +
                ", activityId=" + activityId +
                ", transactionStatus=" + transactionStatus +
                "}"
    }


}
