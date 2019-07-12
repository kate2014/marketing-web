package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhongmei.yunfu.domain.bean.CustomerDrain;
import com.zhongmei.yunfu.domain.bean.CustomerMobile;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerExtraEntity;
import com.zhongmei.yunfu.domain.entity.CustomerReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
public interface CustomerMapper extends BaseMapper<CustomerEntity> {

    @Select("SELECT c.* FROM customer c" +
            "  LEFT JOIN customer_entity_card ce ON c.id = ce.customer_id AND ce.status_flag = 1" +
            "  WHERE c.status_flag = 1 AND c.shop_identy = #{shopId} AND ce.card_no = #{cardNo}")
    CustomerEntity loginCardNoEntity(@Param("cardNo") String cardNo, @Param("shopId") Long shopId);

    //CustomerInfo selectByKey(Serializable key);

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
    @Select("SELECT\n" +
            "  COUNT(*)\n" +
            "FROM\n" +
            "  customer c\n" +
            "  LEFT JOIN customer wc\n" +
            "    ON c.id = wc.relate_id\n" +
            "  LEFT JOIN\n" +
            "    (SELECT\n" +
            "      tc.customer_id,\n" +
            "      COUNT(1) _count,\n" +
            "      SUM(t.trade_amount) _trade_amount\n" +
            "    FROM\n" +
            "      trade t\n" +
            "      INNER JOIN trade_customer tc\n" +
            "        ON t.id = tc.trade_id\n" +
            "    WHERE t.status_flag = 1\n" +
            "      AND t.shop_identy = ${shopId}\n" +
            "      AND t.server_create_time >= #{serverCreateTime}\n" +
            "    GROUP BY tc.customer_id) t\n" +
            "    ON c.id = t.customer_id\n" +
            "WHERE c.status_flag = 1\n" +
            "  AND c.relate_id = 0\n" +
            "  AND c.shop_identy = ${shopId}\n" +
            "  AND IFNULL(t._count, 0) >= ${tradeCount}\n" +
            "  AND IFNULL(t._count, 0) < ${tradeCountMax}\n" +
            "  AND IFNULL(t._trade_amount, 0) >= ${tradeAmountSum}\n" +
            "  AND IFNULL(t._trade_amount, 0) < ${tradeAmountSumMax}")
    Integer selectCountByTrade(@Param("shopId") Long shop_identy, @Param("serverCreateTime") String server_create_time, @Param("tradeCount") Integer tradeCount, @Param("tradeCountMax") Integer tradeCountMax, @Param("tradeAmountSum") Integer tradeAmountSum, @Param("tradeAmountSumMax") Integer tradeAmountSumMax);

    @Select("SELECT\n" +
            "  c.*,\n" +
            "  wc.third_id wx_open_id\n" +
            "FROM\n" +
            "  customer c\n" +
            "  LEFT JOIN customer wc\n" +
            "    ON c.id = wc.relate_id\n" +
            "  LEFT JOIN\n" +
            "    (SELECT\n" +
            "      tc.customer_id,\n" +
            "      COUNT(1) _count,\n" +
            "      SUM(t.trade_amount) _trade_amount\n" +
            "    FROM\n" +
            "      trade t\n" +
            "      INNER JOIN trade_customer tc\n" +
            "        ON t.id = tc.trade_id\n" +
            "    WHERE t.status_flag = 1\n" +
            "      AND t.shop_identy = ${shopId}\n" +
            "      AND t.server_create_time >= #{serverCreateTime}\n" +
            "    GROUP BY tc.customer_id) t\n" +
            "    ON c.id = t.customer_id\n" +
            "WHERE c.status_flag = 1\n" +
            "  AND c.relate_id = 0\n" +
            "  AND c.shop_identy = ${shopId}\n" +
            "  AND IFNULL(t._count, 0) >= ${tradeCount}\n" +
            "  AND IFNULL(t._count, 0) < ${tradeCountMax}\n" +
            "  AND IFNULL(t._trade_amount, 0) >= ${tradeAmountSum}\n" +
            "  AND IFNULL(t._trade_amount, 0) < ${tradeAmountSumMax}\n" +
            "  ${ew.sqlSegment}")
    List<CustomerEntity> selectByTrade(RowBounds page, @Param("ew") Wrapper wrapper, @Param("shopId") Long shopId, @Param("serverCreateTime") String serverCreateTime, @Param("tradeCount") Integer tradeCount, @Param("tradeCountMax") Integer tradeCountMax, @Param("tradeAmountSum") Integer tradeAmountSum, @Param("tradeAmountSumMax") Integer tradeAmountSumMax);

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
            "WHERE status_flag = 1 AND shop_identy = ${shopId}\n" +
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
            "WHERE c.status_flag = 1 AND c.shop_identy = ${shopId}\n" +
            "  AND c.relate_id = 0\n" +
            "  AND DATEDIFF(\n" +
            "    CAST(CONCAT('0000', DATE_FORMAT(c.birthday, '-%m-%d')) AS DATE),\n" +
            "    CAST(CONCAT('0000', DATE_FORMAT(NOW(), '-%m-%d')) AS DATE)\n" +
            "  ) BETWEEN 0 AND ${betweenRight1}\n" +
            "  OR DATEDIFF(\n" +
            "    CAST(CONCAT('0000', DATE_FORMAT(c.birthday, '-%m-%d')) AS DATE),\n" +
            "    CAST(CONCAT('0000', DATE_FORMAT(NOW(), '-%m-%d')) AS DATE)\n" +
            "  ) BETWEEN ${betweenRight1} AND ${betweenRight2} ${ew.sqlSegment}")
    List<CustomerEntity> selectByBirthday(RowBounds page, @Param("ew") Wrapper wrapper, @Param("shopId") Long shop_identy, @Param("betweenRight1") Integer betweenRight1, @Param("betweenRight2") Integer betweenRight2);


    /**
     * 统计最近几天注册的新会员
     *
     * @param shop_identy
     * @param server_create_time
     * @return
     */
    @Select("SELECT COUNT(1) FROM customer WHERE status_flag = 1 AND shop_identy = ${shopId} AND relate_id = 0 AND server_create_time >= #{serverCreateTime}")
    Integer selectCountByNewMember(@Param("shopId") Long shop_identy, @Param("serverCreateTime") String server_create_time);

    @Select("SELECT c.*, wc.third_id wx_open_id FROM customer c LEFT JOIN customer wc ON c.id = wc.relate_id WHERE c.status_flag = 1 AND c.shop_identy = ${shopId} AND c.relate_id = 0 AND c.server_create_time >= #{serverCreateTime} ${ew.sqlSegment}")
    List<CustomerEntity> selectByNewMember(RowBounds page, @Param("ew") Wrapper wrapper, @Param("shopId") Long shop_identy, @Param("serverCreateTime") String server_create_time);

    /**
     * 统计最近将要满周年的会员
     *
     * @param shop_identy
     * @param startTime
     * @return
     */
    @Select("SELECT COUNT(1) FROM customer WHERE status_flag = 1 AND shop_identy = ${shopId} AND relate_id = 0 AND server_create_time >= #{startTime} AND server_create_time <= #{endTime}")
    Integer selectCountByAnniversary(@Param("shopId") Long shop_identy, @Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select("SELECT c.*, wc.third_id wx_open_id FROM customer c LEFT JOIN customer wc ON c.id = wc.relate_id WHERE c.status_flag = 1 AND c.shop_identy = ${shopId} AND c.relate_id = 0 AND c.server_create_time >= #{startTime} AND c.server_create_time <= #{endTime} ${ew.sqlSegment}")
    List<CustomerEntity> selectByAnniversary(RowBounds page, @Param("ew") Wrapper wrapper, @Param("shopId") Long shop_identy, @Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select("SELECT count(tc.id) as count, sum(t.`trade_amount`) as tradeAmount ,date_format(t.`trade_time`, '%Y-%m-%d') as tradeDate\n" +
            "FROM trade t, `trade_customer` tc\n" +
            "WHERE t.`id` = tc.`trade_id` ${ew.sqlSegment} \n" +
            "group by date_format(trade_time,'%Y-%m-%d')\n" +
            "order by date_format(trade_time,'%Y-%m-%d') ;")
    List<CustomerReport> customerShopReport(@Param("ew") Condition wrapper);

    @Select("SELECT t.`id`,t.`business_type`  ,t.`trade_amount` ,tc.`customer_name`, ti.`dish_name`,t.`trade_time` as tradeDate\n" +
            "FROM `trade_customer`  tc LEFT JOIN `trade` t on t.`id` = tc.`trade_id` LEFT JOIN `trade_item` ti on t.`id` = ti.`trade_id` \n" +
            "${ew.sqlSegment} \n" +
            "ORDER BY t.`trade_time` desc;")
    List<CustomerReport> customerShopDetailReport(@Param("ew") Condition wrapper);

    @Select("SELECT * from ((SELECT * FROM `customer`  WHERE `source_id` = 2 and `relate_id` = 0  ${ew.sqlSegment} )\n" +
            "UNION \n" +
            "(SELECT * from `customer` WHERE `source_id` = 1 ${ew.sqlSegment})) a")
    List<CustomerEntity> selectAllCustomer(@Param("ew") Condition wrapper);

    @Select("SELECT c.*, ce.stored_amount, ce.stored_used, ce.stored_balance FROM customer c" +
            " LEFT JOIN customer_extra ce ON ce.customer_id = c.id" +
            " WHERE c.relate_id = 0 AND c.status_flag = 1 ${ew.sqlSegment}")
    List<CustomerDrain> findCustomerByDrain(RowBounds rowBounds, @Param("ew") Wrapper wrapper);

    /*@Select("<script>" +
            "SELECT\n" +
            "  c.*,\n" +
            "  ce.stored_amount,\n" +
            "  ce.stored_used,\n" +
            "  ce.stored_balance\n" +
            "FROM\n" +
            "  customer c\n" +
            "  LEFT JOIN customer_extra ce\n" +
            "    ON ce.customer_id = c.id\n" +
            "WHERE c.relate_id = 0\n" +
            "  AND c.status_flag = 1\n" +
            "  AND c.shop_identy = #{shopId}\n" +
            "<if test='cardExpireDateLe != null and cardExpireDateGe != null'>\n" +
            "  AND c.card_expire_date &lt;= #{cardExpireDateLe}\n" +
            "  AND c.card_expire_date &gt;= #{cardExpireDateGe}\n" +
            "</if>\n" +
            "<if test='consumptionLastTime != null'>\n" +
            "  AND c.consumption_last_time &gt;= #{consumptionLastTime}\n" +
            "</if>\n" +
            "<if test='opType == null or opType == \"0\"'>\n" +
            "  <if test='storedBalance != null'>\n" +
            "    AND ce.stored_balance &gt;= #{storedBalance}\n" +
            "  </if>\n" +
            "  <if test='cardResidueCount != null'>\n" +
            "    AND c.card_residue_count &gt;= #{cardResidueCount}\n" +
            "  </if>\n" +
            "</if>\n" +
            "<if test='opType != null and opType != \"0\"'>\n" +
            "  <if test='storedBalance != null'>\n" +
            "    AND ce.stored_balance &lt;= #{storedBalance}\n" +
            "  </if>\n" +
            "  <if test='cardResidueCount != null'>\n" +
            "    AND c.card_residue_count &lt;= #{cardResidueCount}\n" +
            "  </if>\n" +
            "</if>" +
            "  ORDER BY IFNULL(c.card_expire_date, &apos;9999-99-99&apos;)" +
            "</script>")*/
    List<CustomerDrain> findCustomerByDrainExample(RowBounds rowBounds,
                                                   @Param("shopId") Long shopId,
                                                   @Param("consumptionLastTime") String consumptionLastTime,
                                                   @Param("cardExpireDateLe") String cardExpireDateLe,
                                                   @Param("cardExpireDateGe") String cardExpireDateGe,
                                                   @Param("opType") Integer opType,
                                                   @Param("storedBalance") BigDecimal storedBalance,
                                                   @Param("cardResidueCount") Integer cardResidueCount);

    /**
     * 获取当前门店下所有手机号
     *
     * @param shopId
     * @return
     */
    List<CustomerMobile> getMobileAll(@Param("shopId") Long shopId);
}
