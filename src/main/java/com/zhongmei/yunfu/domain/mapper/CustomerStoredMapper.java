package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.domain.entity.CustomerSaveReport;
import com.zhongmei.yunfu.domain.entity.CustomerStoredEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    @Select("SELECT * FROM customer_stored WHERE record_type IN (1, 2)" +
            " AND shop_identy = #{shopId} AND trade_id = #{tradeId} AND payment_item_id = #{paymentItemId} LIMIT 1")
    CustomerStoredEntity getRefundByPaymentItemId(@Param("shopId") Long shopId, @Param("tradeId") Long tradeId, @Param("paymentItemId") Long paymentItemId);

    @Select("SELECT COUNT(id) as tradeCount,sum(trade_amount) as tradeAmount,sum(give_amount) as giveAmount,date_format(s.`server_create_time`, '%Y-%m-%d') as createDate ,record_type as recordype \n" +
            "FROM `customer_stored` s " +
            "${ew.sqlSegment} " +
            "GROUP BY date_format(s.`server_create_time`, '%Y-%m-%d'),record_type " +
            "ORDER BY date_format(s.`server_create_time`, '%Y-%m-%d') asc;")
    List<CustomerSaveReport> querySaveData(@Param("ew") Condition wrapper);
}
