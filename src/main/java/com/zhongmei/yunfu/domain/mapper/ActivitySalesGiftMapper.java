package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.controller.api.model.ActivityGiftEffectResp;
import com.zhongmei.yunfu.controller.model.CustomerGiftModel;
import com.zhongmei.yunfu.domain.entity.ActivitySalesGiftEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 特价活动 Mapper 接口
 * </p>
 *
 */
public interface ActivitySalesGiftMapper extends BaseMapper<ActivitySalesGiftEntity> {

    @Select("SELECT c.customer_id, c.`coupon_name` as giftName ,c.`coupon_id` as giftId ,o.`wx_photo` as wxPhoto," +
            "o.`wx_name` as wxName ,o.`wx_open_id` as wxOpenId , o.`customer_name` as customerName," +
            "o.`customer_phone` as customerPhone, c.`server_create_time` as serverCreateTime " +
            "FROM `customer_coupon` c ,`operational_records` o \n" +
            "where c.`wx_customer_openid`  = o.`wx_open_id` ${ew.sqlSegment} \n" +
            "ORDER BY c.`server_create_time` desc;")
    List<CustomerGiftModel> queryActivityGift(@Param("ew") Condition wrapper);

    @Select("SELECT a.gift_price as giftPrice,a.image_url as imageUrl,a.order_count as orderCount ,a.gift_id as giftId,a.gift_name as giftName,r.count as finishCount,r.activity_id as activityId, a.brand_identy as brandIdenty ,a.shop_identy as shopIdenty \n" +
            "FROM `activity_sales_gift_rule`  a,(\n" +
            "    SELECT COUNT(r.id) as count ,r.`activity_id`,r.`main_customer_id` as customer_id ,r.`brand_identy` ,r.`shop_identy`  \n" +
            "    FROM recommendation_association r \n" +
            "    ${ew.sqlSegment}\n" +
            "    GROUP BY r.`activity_id` \n" +
            ") r\n" +
            "WHERE a.activity_id = r.activity_id and a.brand_identy = r.`brand_identy` and a.shop_identy = r.`shop_identy` \n" +
            "ORDER BY r.`activity_id`  desc;")
    List<ActivityGiftEffectResp> queryCustomerGift(@Param("ew") Condition wrapper);
}
