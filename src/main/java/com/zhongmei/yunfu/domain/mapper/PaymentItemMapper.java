package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.controller.model.PaymentItemModel;
import com.zhongmei.yunfu.domain.entity.PaymentEntity;
import com.zhongmei.yunfu.domain.entity.PaymentItemEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 预订表 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-09-14
 */
public interface PaymentItemMapper extends BaseMapper<PaymentItemEntity> {


    @Select("SELECT sum(`useful_amount`) as totalAmount, count(id) as count, `pay_mode_name` as payModeName,pay_mode_id as payModeId from `payment_item` ${ew.sqlSegment} GROUP BY `pay_mode_id`")
    List<PaymentItemModel> querySalesAmount(@Param("ew") Condition wrapper);

    @Select("SELECT i.* FROM payment p LEFT JOIN `payment_item` i on p.`id` = i.`payment_id` ${ew.sqlSegment}")
    PaymentItemEntity queryPaymentItemByTradeId(@Param("ew") Condition wrapper);

    @Select("SELECT i.* FROM payment p LEFT JOIN `payment_item` i on p.`id` = i.`payment_id` ${ew.sqlSegment}")
    List<PaymentItemEntity> queryPaymentItemsByTradeId(@Param("ew") Condition wrapper);
}
