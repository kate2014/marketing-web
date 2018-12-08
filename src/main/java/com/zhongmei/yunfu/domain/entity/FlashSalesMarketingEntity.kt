package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 秒杀活动
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("flash_sales_marketing")
class FlashSalesMarketingEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 秒杀活动名称
     */
    var name: String? = null
    /**
     * 秒杀活动简介
     */
    var profile: String? = null
    /**
     * 活动开始时间
     */
    var beginTime: Date? = null
    /**
     * 活动结束时间
     */
    var endTime: Date? = null
    /**
     * 活动使用有效期
     */
    var validityPeriod: Date? = null
    /**
     * 原价格
     */
    var originalPrice: BigDecimal? = null
    /**
     * 秒杀价格
     */
    var flashPrice: BigDecimal? = null
    /**
     * 商品Id
     */
    var productId: Long? = null
    /**
     * 商品名称
     */
    var productName: String? = null
    /**
     * 活动图片
     */
    var imgUrl: String? = null
    /**
     * 秒杀描述
     */
    var describe: String? = null
    /**
     * 同一活动同一会员购买数量
     */
    var customerBuyCount: Int? = null
    /**
     * 已售卖活动数量
     */
    var soldCount: Int? = null
    /**
     * 活动售卖数量
     */
    var salesCount: Int? = null
    /**
     * 启用停用标识 : 1:启用;2:停用
     */
    var enabledFlag: Int? = null
    /**
     * 品牌id
     */
    var brandIdentity: Long? = null
    /**
     * 门店id
     */
    var shopIdentity: Long? = null


    override fun toString(): String {
        return "FlashSalesMarketingEntity{" +
        ", id=" + id +
        ", name=" + name +
        ", profile=" + profile +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", validityPeriod=" + validityPeriod +
        ", originalPrice=" + originalPrice +
        ", flashPrice=" + flashPrice +
        ", productId=" + productId +
        ", productName=" + productName +
        ", imgUrl=" + imgUrl +
        ", describe=" + describe +
        ", customerBuyCount=" + customerBuyCount +
        ", soldCount=" + soldCount +
        ", salesCount=" + salesCount +
        ", enabledFlag=" + enabledFlag +
        ", brandIdentity=" + brandIdentity +
        ", shopIdentity=" + shopIdentity +
        "}"
    }
}
