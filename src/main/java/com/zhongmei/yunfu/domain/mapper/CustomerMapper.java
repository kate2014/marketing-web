package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerExtraEntity;
import com.zhongmei.yunfu.domain.entity.CustomerReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-29
 */
public interface CustomerMapper extends BaseMapper<CustomerEntity> {

    //List<CustomerExtra> selectUserList(Pagination page, String state);

    @Select("SELECT * FROM customer LEFT JOIN customer_extra ON id = customer_id ${ew.sqlSegment}")
    List<CustomerExtraEntity> findByPage(RowBounds rowBounds, @Param("ew") Condition wrapper);

    @Select("SELECT * FROM customer LEFT JOIN customer_extra ON id = customer_id ${ew.sqlSegment}")
    CustomerExtraEntity getCustomerExtra(@Param("ew") Wrapper wrapper);

    @Select("select count(id) as count,DATE_FORMAT(server_create_time,'%Y-%m-%d') as create_date  FROM customer ${ew.sqlSegment} GROUP BY DATE_FORMAT(server_create_time,'%Y-%m-%d') ORDER BY DATE_FORMAT(server_create_time,'%Y-%m-%d')")
    List<CustomerReport> queryCustomerGroup(@Param("ew") Condition wrapper);

    @Select("select count(id) as count FROM customer ${ew.sqlSegment}")
    Integer queryCustomerCount(@Param("ew") Condition wrapper);

    /*@Select("select * as count FROM customer ${ew.sqlSegment}")
    CustomerEntity loginMobile(@Param("ew") Condition condition);*/

    /**
     * 统计最近一段时间内交易次数大于等于目标值或交易金额大于等于目标金额
     *
     * @param shop_identy
     * @param server_create_time
     * @param tradeCount
     * @param tradeAmountSum
     * @return
     */
    //@Select("SELECT tc.customer_id, COUNT(1), SUM(t.trade_amount) FROM trade t INNER JOIN trade_customer tc ON t.id = tc.trade_id WHERE t.shop_identy = 1 AND t.server_create_time >= '' GROUP BY tc.customer_id\n")
    @Select("SELECT\n" +
            "  count(*)\n" +
            "FROM\n" +
            "  customer\n" +
            "WHERE shop_identy = ${shopId}\n" +
            "  AND relate_id = 0\n" +
            "  AND id IN\n" +
            "  (SELECT\n" +
            "    tc.customer_id\n" +
            "  FROM\n" +
            "    trade t\n" +
            "    INNER JOIN trade_customer tc\n" +
            "      ON t.id = tc.trade_id\n" +
            "  WHERE t.shop_identy = ${shopId}\n" +
            "    AND t.server_create_time >= #{serverCreateTime}\n" +
            "  GROUP BY tc.customer_id\n" +
            "  HAVING COUNT(1) > ${tradeCount}\n" +
            "    OR SUM(t.trade_amount) > ${tradeAmountSum})")
    Integer selectCountByTrade(@Param("shopId") Long shop_identy, @Param("serverCreateTime") String server_create_time, @Param("tradeCount") Integer tradeCount, @Param("tradeAmountSum") Integer tradeAmountSum);

    @Select("SELECT\n" +
            " c.*, wc.third_id wx_open_id\n" +
            "FROM customer c LEFT JOIN customer wc ON c.id = wc.relate_id\n" +
            "WHERE c.id IN\n" +
            "  (SELECT\n" +
            "    tc.customer_id\n" +
            "  FROM\n" +
            "    trade t\n" +
            "    INNER JOIN trade_customer tc\n" +
            "      ON t.id = tc.trade_id\n" +
            "  WHERE t.shop_identy = ${shopId}\n" +
            "    AND t.server_create_time >= #{serverCreateTime}\n" +
            "  GROUP BY tc.customer_id\n" +
            "  HAVING COUNT(1) > ${tradeCount}\n" +
            "    OR SUM(t.trade_amount) > ${tradeAmountSum}) ${ew.sqlSegment}")
    List<CustomerEntity> selectByTrade(RowBounds page, @Param("ew") Wrapper wrapper, @Param("shopId") Long shop_identy, @Param("serverCreateTime") String server_create_time, @Param("tradeCount") Integer tradeCount, @Param("tradeAmountSum") Integer tradeAmountSum);

    /**
     * 统计最近几天将要过生日的会员
     *
     * @param shop_identy
     * @param betweenRight1
     * @param betweenRight2
     * @return
     */
    @Select("SELECT\n" +
            "  COUNT(1)\n" +
            "FROM\n" +
            "  customer\n" +
            "WHERE shop_identy = ${shopId}\n" +
            "  AND relate_id = 0\n" +
            "  AND DATEDIFF(\n" +
            "    CAST(CONCAT('0000', DATE_FORMAT(birthday, '-%m-%d')) AS DATE),\n" +
            "    CAST(CONCAT('0000', DATE_FORMAT(NOW(), '-%m-%d')) AS DATE)\n" +
            "  ) BETWEEN 0 AND ${betweenRight1}\n" +
            "  OR DATEDIFF(\n" +
            "    CAST(CONCAT('0000', DATE_FORMAT(birthday, '-%m-%d')) AS DATE),\n" +
            "    CAST(CONCAT('0000', DATE_FORMAT(NOW(), '-%m-%d')) AS DATE)\n" +
            "  ) BETWEEN ${betweenRight1} AND ${betweenRight2}")
    Integer selectCountByBirthday(@Param("shopId") Long shop_identy, @Param("betweenRight1") Integer betweenRight1, @Param("betweenRight2") Integer betweenRight2);

    @Select("SELECT\n" +
            " c.*, wc.third_id wx_open_id\n" +
            "FROM customer c LEFT JOIN customer wc ON c.id = wc.relate_id\n" +
            "WHERE DATEDIFF(\n" +
            "    CAST(CONCAT('0000', DATE_FORMAT(c.birthday, '-%m-%d')) AS DATE),\n" +
            "    CAST(CONCAT('0000', DATE_FORMAT(NOW(), '-%m-%d')) AS DATE)\n" +
            "  ) BETWEEN 0 AND ${betweenRight1}\n" +
            "  OR DATEDIFF(\n" +
            "    CAST(CONCAT('0000', DATE_FORMAT(c.birthday, '-%m-%d')) AS DATE),\n" +
            "    CAST(CONCAT('0000', DATE_FORMAT(NOW(), '-%m-%d')) AS DATE)\n" +
            "  ) BETWEEN ${betweenRight1} AND ${betweenRight2} ${ew.sqlSegment}")
    List<CustomerEntity> selectByBirthday(RowBounds page, @Param("ew") Wrapper wrapper, @Param("betweenRight1") Integer betweenRight1, @Param("betweenRight2") Integer betweenRight2);


    /**
     * 统计最近几天注册的新会员
     *
     * @param shop_identy
     * @param server_create_time
     * @return
     */
    @Select("SELECT COUNT(1) FROM customer WHERE shop_identy = ${shopId} AND relate_id = 0 AND server_create_time >= #{serverCreateTime}")
    Integer selectCountByNewMember(@Param("shopId") Long shop_identy, @Param("serverCreateTime") String server_create_time);

    @Select("SELECT c.*, wc.third_id wx_open_id FROM customer c LEFT JOIN customer wc ON c.id = wc.relate_id WHERE c.shop_identy = ${shopId} AND c.relate_id = 0 AND c.server_create_time >= #{serverCreateTime} ${ew.sqlSegment}")
    List<CustomerEntity> selectByNewMember(RowBounds page, @Param("ew") Wrapper wrapper, @Param("shopId") Long shop_identy, @Param("serverCreateTime") String server_create_time);

    /**
     * 统计最近将要满周年的会员
     *
     * @param shop_identy
     * @param startTime
     * @return
     */
    @Select("SELECT COUNT(1) FROM customer WHERE shop_identy = ${shopId} AND relate_id = 0 AND server_create_time >= #{startTime} AND server_create_time <= #{endTime}")
    Integer selectCountByAnniversary(@Param("shopId") Long shop_identy, @Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select("SELECT c.*, wc.third_id wx_open_id FROM customer c LEFT JOIN customer wc ON c.id = wc.relate_id WHERE c.shop_identy = ${shopId} AND c.relate_id = 0 AND c.server_create_time >= #{startTime} AND c.server_create_time <= #{endTime} ${ew.sqlSegment}")
    List<CustomerEntity> selectByAnniversary(RowBounds page, @Param("ew") Wrapper wrapper, @Param("shopId") Long shop_identy, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
