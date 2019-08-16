package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.domain.entity.RecommendationAssociationEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 会员操作记录 Mapper 接口
 * </p>
 *
 */
public interface OperationalRecordsMapper extends BaseMapper<OperationalRecordsEntity> {

    @Select("SELECT a.customer_id as customerId,b.customer_name as customerName, b.customer_phone as customerPhone,b.wx_open_id as wxOpenId,b.wx_photo as wxPhoto,b.wx_name as wxName, a.count as operationalCount,a.server_create_time as serverCreateTime FROM \n" +
            "(SELECT count(w.id) as count,w.`customer_id` as customer_id ,w.`customer_name`,w.`server_create_time`  FROM `wx_trade_customer` w \n" +
            "where w.brand_identy = ${brandIdenty} and w.shop_identy = ${shopIdenty} and w.marketing_id = ${activityId} and w.enabled_flag = 1 and w.status_flag = 1 and w.type = ${type} " +
            "GROUP BY `customer_id` \n" +
            "ORDER BY count(id) desc) a \n" +
            "LEFT JOIN \n" +
            "(SELECT o.`customer_id` as customer_id ,o.`wx_open_id` ,o.`wx_name` ,o.`wx_photo` ,o.`customer_name` ,o.`customer_phone`  FROM `operational_records` o \n" +
            "where o.brand_identy = ${brandIdenty} and o.shop_identy = ${shopIdenty} and o.activity_id = ${activityId} and o.status_flag = 1 and o.type = 1 \n" +
            "GROUP BY o.`customer_id` \n" +
            "ORDER BY id desc) b \n" +
            "on a.customer_id = b.customer_id ${ew.sqlSegment} \n" +
            "ORDER BY a.count desc ;\n")
    List<OperationalRecordsEntity> querySalesEffect(@Param("ew") Condition wrapper,@Param("brandIdenty") Long brandIdenty,@Param("shopIdenty") Long shopIdenty,@Param("activityId") Long activityId,@Param("type") Integer type);


    @Select("SELECT a.customer_id as customerId,b.customer_name as customerName, b.customer_phone as customerPhone,b.wx_open_id as wxOpenId,b.wx_photo as wxPhoto,b.wx_name as wxName, a.count as operationalCount,a.server_create_time as serverCreateTime FROM \n" +
            "(SELECT count(w.id) as count,w.`customer_id` as customer_id ,w.`customer_name`,w.`server_create_time`  FROM `wx_trade_customer` w \n" +
            "where w.brand_identy = ${brandIdenty} and w.shop_identy = ${shopIdenty} and w.marketing_id = ${activityId} and w.status_flag = 1 and w.type = ${type} \n" +
            "GROUP BY `customer_id` \n" +
            "ORDER BY count(id) desc) a \n" +
            "LEFT JOIN \n" +
            "(SELECT o.`customer_id` as customer_id ,o.`wx_open_id` ,o.`wx_name` ,o.`wx_photo` ,o.`customer_name` ,o.`customer_phone`  FROM `operational_records` o \n" +
            "where o.brand_identy = ${brandIdenty} and o.shop_identy = ${shopIdenty} and o.activity_id = ${activityId} and o.status_flag = 1 and o.type = 1 \n" +
            "GROUP BY o.`customer_id` \n" +
            "ORDER BY id desc) b \n" +
            "on a.customer_id = b.customer_id ${ew.sqlSegment} \n" +
            "ORDER BY a.count desc ;\n")
    List<OperationalRecordsEntity> queryJoinEffect(@Param("ew") Condition wrapper,@Param("brandIdenty") Long brandIdenty,@Param("shopIdenty") Long shopIdenty,@Param("activityId") Long activityId,@Param("type") Integer type);
}

