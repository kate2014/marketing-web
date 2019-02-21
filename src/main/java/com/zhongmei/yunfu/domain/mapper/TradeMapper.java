package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhongmei.yunfu.domain.entity.CustomerSaveReport;
import com.zhongmei.yunfu.domain.entity.SalesReport;
import com.zhongmei.yunfu.domain.entity.TradeEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交易记录主单（相当于ORDERS）。
 * 主单及其所有子单中的数量、金额在退货时都取反 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
public interface TradeMapper extends BaseMapper<TradeEntity> {

    @Select("select sum(trade_amount) as salesAmount,sum(trade_people_count) as peopleCount,date_format(trade_time,'%Y-%m-%d') as createDate\n" +
            "from trade \n" +
            "${ew.sqlSegment}\n" +
            "group by date_format(trade_time,'%Y-%m-%d') \n" +
            "order by date_format(trade_time,'%Y-%m-%d') ;")
    List<SalesReport> queryTradeSales(@Param("ew") Condition wrapper);

    @Select("select sum(trade_amount) as salesAmount from trade ${ew.sqlSegment}")
    BigDecimal querySalesAmount(@Param("ew") Condition wrapper);

    @Select("SELECT\n" +
            "  t.server_create_time,\n" +
            "  SUM(t.trade_amount) trade_amount\n" +
            "FROM\n" +
            "  trade t\n" +
            "  INNER JOIN trade_customer tc\n" +
            "    ON t.id = tc.trade_id\n" +
            //"WHERE tc.customer_id IN (51)\n" +
            "${ew.sqlSegment}\n" +
            "  AND t.trade_status = 4\n" +
            "  AND t.status_flag = 1\n" +
            "GROUP BY YEAR(t.server_create_time),\n" +
            "  MONTH(t.server_create_time),\n" +
            "  DAY(t.server_create_time)")
    List<TradeEntity> queryCustomerExpenseList(@Param("ew") Wrapper wrapper);

    @Select("SELECT t.* FROM trade t INNER JOIN trade_customer tc ON t.id = tc.trade_id WHERE t.shop_identy = ${shopId} AND tc.customer_id = ${customerId} ORDER BY t.server_update_time DESC LIMIT 0,100")
    List<TradeEntity> getTradeEntityBy(@Param("customerId") Long customerId, @Param("shopId") Long shopId);

    @Select("SELECT count(id) as tradeCount, sum(trade_amount) as salesAmount,date_format(trade_time,'%Y-%m-%d') as createDate\n" +
            "FROM trade \n" +
            "${ew.sqlSegment} \n" +
            "group by date_format(trade_time,'%Y-%m-%d')\n" +
            "order by date_format(trade_time,'%Y-%m-%d'); ")
    List<CustomerSaveReport> queryCustomerSave(@Param("ew") Condition wrapper);
}
