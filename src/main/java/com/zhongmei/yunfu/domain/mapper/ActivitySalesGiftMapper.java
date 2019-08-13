package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
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
}
