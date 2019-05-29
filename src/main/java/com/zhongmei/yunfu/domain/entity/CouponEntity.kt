package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 优惠券
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("coupon")
class CouponEntity : BaseEntity() {

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
     * 优惠券名称
     */
    var name: String? = null
    /**
     * 券类型(1：折扣、2：代金、3：礼品、)
     */
    var couponType: Int? = null
    /**
     * 投放量
     */
    var pushNumber: Int? = null
    /**
     * 使用量
     */
    var useNumber: Int? = null
    /**
     * 消费满额
     */
    var fullValue: Int? = null
    /**
     * 1：折扣数值、2：代金金额
     */
    var discountValue: BigDecimal? = null
    /**
     * 品项id
     */
    var dishId: Long? = null
    /**
     * 品项名称
     */
    var dishName: String? = null
    /**
     * 备注信息
     */
    var remark: String? = null
    /**
     * 券内容
     */
    var content: String? = null
    /**
     * 适用商品：1.全部商品可用；2.部分商品可用；3.部分商品不可用
     */
    var applyDish: Int? = null
    /**
     * 是否限制使用门店（0：否、1：是）
     */
    var restrictUseCommercial: Int? = null
    /**
     * 优惠叠加类型（1可叠加同享|2不可叠加同享|3部分可叠加）
     */
    var sharingPrivilegeType: Int? = null
    /**
     * 优惠卷状态（1:使用中，2:停用中）
     */
    var couponState: Int? = null
    /**
     * 活动结束时间
     */
    var endTime: Date? = null
    /**
     * 活动来源类型 1：品牌下发  2：门店自建
     */
    var sourceType: Int? = null
    /**
     * 活动来源Id
     */
    var sourceId: Long? = null

    override fun toString(): String {
        return "CouponEntity{" +
                ", id=" + id +
                ", brandIdenty=" + brandIdenty +
                ", shopIdenty=" + shopIdenty +
                ", name=" + name +
                ", couponType=" + couponType +
                ", pushNumber=" + pushNumber +
                ", useNumber=" + useNumber +
                ", fullValue=" + fullValue +
                ", discountValue=" + discountValue +
                ", dishId=" + dishId +
                ", dishName=" + dishName +
                ", remark=" + remark +
                ", content=" + content +
                ", applyDish=" + applyDish +
                ", restrictUseCommercial=" + restrictUseCommercial +
                ", sharingPrivilegeType=" + sharingPrivilegeType +
                ", couponState=" + couponState +
                ", endTime=" + endTime +
                ", sourceType=" + sourceType +
                ", sourceId=" + sourceId +
                "}"
    }
}
