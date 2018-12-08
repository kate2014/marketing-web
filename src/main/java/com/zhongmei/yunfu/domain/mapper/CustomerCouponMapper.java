package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhongmei.yunfu.controller.model.CustomerCouponModel;
import com.zhongmei.yunfu.domain.entity.CouponEntity;
import com.zhongmei.yunfu.domain.entity.CustomerCouponEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhongmei.yunfu.domain.entity.bean.CustomerCouponReport;
import com.zhongmei.yunfu.service.vo.CustomerCouponVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 会员优惠券关联表 Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-29
 */
public interface CustomerCouponMapper extends BaseMapper<CustomerCouponEntity> {

    @Select("select count(id) as couponCount, source_id as sourceId from customer_coupon ${ew.sqlSegment} GROUP BY `source_id`")
    List<CustomerCouponReport> selectCouponReport(@Param("ew") Condition wrapper);

    @Select("SELECT source_id as sourceId, source_name as sourceName FROM `coupon_source` ;")
    List<CustomerCouponReport> selectCouponSource(@Param("ew") Condition wrapper);

    @Select("select m.id,m.coupon_id,m.coupon_name,m.source_id,m.customer_id,m.wx_customer_openid,m.status,c.`coupon_type`,c.`content` ,c.`end_time` \n" +
            "from `customer_coupon` m LEFT JOIN `coupon` c  on m.`coupon_id` =c.`id` ${ew.sqlSegment} and unix_timestamp(c.`end_time`) >= unix_timestamp(NOW()) ORDER BY  m.`server_create_time` DESC")
    List<CustomerCouponModel> selectCouponByCustomer(@Param("ew") Condition wrapper);

    @Select("SELECT b.id customer_coupon_id,a.* FROM coupon a INNER JOIN customer_coupon b ON a.id = b.coupon_id ${ew.sqlSegment} AND b.status_flag = 1  AND b.status = 1  AND a.end_time >= NOW()") //b.customer_id in(${customerIds}) AND
    List<CustomerCouponVo> selectCouponEntity(@Param("ew") Wrapper wrapper);

    @Select("SELECT count(1) FROM coupon a INNER JOIN customer_coupon b ON a.id = b.coupon_id ${ew.sqlSegment} AND b.status_flag = 1  AND b.status = 1  AND a.end_time >= NOW()") //b.customer_id in(${customerIds}) AND
    int selectCouponEntityCount(@Param("ew") Wrapper wrapper);
}
