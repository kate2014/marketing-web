package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhongmei.yunfu.domain.entity.CustomerStoredEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 会员储值、储值消费记录表 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
public interface CustomerStoredMapper extends BaseMapper<CustomerStoredEntity> {

    /**
     * 获取最后一条记录
     *
     * @return
     */
    @Select("SELECT * FROM customer_stored WHERE server_update_time = " +
            "(SELECT MAX(server_update_time) FROM customer_stored WHERE customer_id = #{customerId})")
    CustomerStoredEntity getByLastServerUpdateTime(Long customerId);

    @Select("SELECT * FROM customer_stored WHERE shop_identy = #{shopId} AND trade_id = #{tradeId} AND payment_item_id = #{paymentItemId}")
    CustomerStoredEntity getByPaymentItemId(@Param("shopId") Long shopId, @Param("tradeId") Long tradeId, @Param("paymentItemId") Long paymentItemId);
}
