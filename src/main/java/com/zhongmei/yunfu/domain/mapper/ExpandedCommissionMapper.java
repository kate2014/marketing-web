package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.controller.model.CommissionSearchModel;
import com.zhongmei.yunfu.domain.entity.ExpandedCommissionEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 推广提成 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-09-16
 */
public interface ExpandedCommissionMapper extends BaseMapper<ExpandedCommissionEntity> {

    @Select("select id,customer_id,total_amount,change_sales_amount,commission_ratio,commission_amount,total_commission,can_exchange,exchange_amount from expanded_commission  ${ew.sqlSegment} order by id DESC  LIMIT  1")
    ExpandedCommissionEntity queryCustomerCommission(@Param("ew") Condition wrapper);

    @Select("select e.id,e.customer_id,c.`name` ,e.total_amount,e.change_sales_amount,e.commission_ratio,e.commission_amount,e.total_commission,e.can_exchange,e.exchange_amount,e.`server_create_time` \n" +
            "from expanded_commission e  LEFT JOIN `customer` c on e.`customer_id` = c.`id` ${ew.sqlSegment} order by id DESC ")
    List<CommissionSearchModel> qeryListCommission(RowBounds rowBounds, @Param("ew") Condition wrapper);
}
