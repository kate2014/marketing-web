package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhongmei.yunfu.domain.entity.CustomerCardTimeEntity;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 会员次卡表 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
public interface CustomerCardTimeMapper extends BaseMapper<CustomerCardTimeEntity> {

    @Select("SELECT * FROM customer_card_time WHERE id = " +
            "(SELECT MAX(id) FROM customer_card_time WHERE customer_id = #{customerId})")
    CustomerCardTimeEntity getByLastId(Long customerId);

    @Select("SELECT * FROM customer_card_time WHERE id = " +
            "(SELECT MAX(id) FROM customer_card_time WHERE customer_id = #{customerId} AND dish_id = #{dishId})")
    CustomerCardTimeEntity getDishByLastId(Long customerId, Long dishId);
}
