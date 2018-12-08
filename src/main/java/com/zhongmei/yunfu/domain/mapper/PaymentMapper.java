package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.domain.entity.BookingEntity;
import com.zhongmei.yunfu.domain.entity.PaymentEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 预订表 Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-14
 */
public interface PaymentMapper extends BaseMapper<PaymentEntity> {

    @Select("SELECT p.* FROM payment p LEFT JOIN  payment_item i on p.`id` = i.`payment_id` ${ew.sqlSegment}")
    PaymentEntity queryPaymentByItemId(@Param("ew") Condition wrapper);
}
