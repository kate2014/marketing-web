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
 * 特价活动
 * </p>
 */
@TableName("activity_sales")
class ActivitySalesEntity : BaseEntity() {

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
     * 活动名称
     */
    var name: String? = null
    /**
     * 活动图片
     */
    var imageUrl: String? = null
    /**
     * 活动简介
     */
    var profile: String? = null
    /**
     * 售卖价格
     */
    var saleAmount: BigDecimal? = null
    /**
     * 品项Id
     */
    var productId: Long? = null
    /**
     * 品项名称
     */
    var productName: String? = null
    /**
     * 每人限购数
     */
    var customerBuyCount: Int? = null
    /**
     * 品项原价
     */
    var originalPrice: BigDecimal? = null
    /**
     * 活动描述
     */
    var describe: String? = null
    /**
     * 活动结束时间
     */
    var endTime: Date? = null
    /**
     * 活动使用有效期
     */
    var validityPeriod: Date? = null
    /**
     * 活动参与售卖总数
     */
    var joinCount: Int? = null

    var enabledFlag: Int? = null

    var browseCount: Int? = null

    var shareCount: Int? = null

    var buyCount: Int? = null

    override fun toString(): String {
        return "CouponEntity{" +
                ", id=" + id +
                ", brandIdenty=" + brandIdenty +
                ", shopIdenty=" + shopIdenty +
                ", name=" + name +
                ", imageUrl=" + imageUrl +
                ", profile=" + profile +
                ", saleAmount=" + saleAmount +
                ", productId=" + productId +
                ", productName=" + productName +
                ", customerBuyCount=" + customerBuyCount +
                ", originalPrice=" + originalPrice +
                ", describe=" + describe +
                ", validityPeriod=" + validityPeriod +
                ", joinCount=" + joinCount +
                ", enabledFlag=" + enabledFlag +
                ", browseCount=" + browseCount +
                ", shareCount=" + shareCount +
                ", buyCount=" + buyCount +
                "}"
    }


}
