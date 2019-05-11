package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhongmei.yunfu.controller.model.CustomerSaleModel;
import com.zhongmei.yunfu.controller.model.ReportSalesExportModel;
import com.zhongmei.yunfu.domain.entity.*;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhongmei.yunfu.domain.entity.bean.ShopSalesReport;
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

    @Select("SELECT t.`trade_amount` as salesAmount, tc.`customer_name` as customerName,t.`server_create_time` as createDate  FROM `trade_customer` tc LEFT JOIN `trade` t on tc.`trade_id` = t.`id` \n" +
            "${ew.sqlSegment} \n" +
            "ORDER BY trade_time desc;\n")
    List<CustomerSaveReport> customerSaveDetailReport(@Param("ew") Condition wrapper);

    @Select("SELECT \n" +
            "t.`shop_identy` AS shopIdenty,\n" +
            "t.`server_create_time` AS tradeDate,\n" +
            "CASE t.trade_type \n" +
            "WHEN 1 THEN '销货单' \n" +
            "WHEN 2 THEN '退货单' END AS tradeType,\n" +
            "CASE t.`business_type` \n" +
            "WHEN 1 THEN '销货'\n" +
            "WHEN 2 THEN '余额储值'\n" +
            "WHEN 3 THEN '次卡购买'\n" +
            "WHEN 4 THEN '小程序服务购买' END AS businessType,\n" +
            "t.`trade_amount` AS tradeAmount,\n" +
            "CASE t.`source` \n" +
            "WHEN 1 THEN '门店POS端'\n" +
            "WHEN 2 THEN '小程序端' END AS tradeSource,\n" +
            "CASE t.`trade_pay_status`  \n" +
            "WHEN 1 THEN '未支付'\n" +
            "WHEN 2 THEN '支付中'\n" +
            "WHEN 3 THEN '已支付'\n" +
            "WHEN 4 THEN '退款中'\n" +
            "WHEN 5 THEN '已退款'\n" +
            "WHEN 6 THEN '退款失败'\n" +
            "WHEN 7 THEN '预支付'\n" +
            "WHEN 8 THEN '等待退款'\n" +
            "WHEN 9 THEN '支付失败' END AS tradeState,\n" +
            "i.pay_mode_name AS tradeMode FROM trade t, `payment` p ,`payment_item` i \n" +
            "WHERE t.`id` = p.`relate_id` and p.`id` = i.`payment_id` \n" +
            "${ew.sqlSegment} " +
            "ORDER BY t.id desc;")
    List<ReportSalesExportModel> querySalseExportExcel(@Param("ew") Condition wrapper);

    @Select("SELECT sum(t.`trade_amount`) as saleAmount, tc.`customer_id` as customerId ,c.`name` as customerName , c.`gender` as gender,c.`birthday`  as birthday\n" +
            "FROM `trade_customer` tc ,`trade` t, `customer` c\n" +
            "WHERE tc.`trade_id` = t.`id`and t.`shop_identy` = 1 and tc.`customer_id` = c.id ${ew.sqlSegment}\n" +
            "GROUP BY tc.`customer_id` ;")
    List<CustomerSaleModel> queryCustomerSale(@Param("ew") Condition wrapper);


    @Select("SELECT t.`trade_amount` as saleAmount ,ti.`dish_id` as dishId , ti.`dish_name` as dishName,ti.`quantity`  as quantity, tc.`customer_id` as customerId ,c.`name` as customerName , c.`gender` as gender,c.`birthday`  as birthday\n" +
            "FROM `trade_customer` tc ,`trade` t, `customer` c, `trade_item` ti\n" +
            "WHERE tc.`trade_id` = t.`id`and t.`shop_identy` = 1 and tc.`customer_id` = c.id and t.`id` = ti.`trade_id` ${ew.sqlSegment}")
    List<CustomerSaleModel> queryCustomerSaleDetail(@Param("ew") Condition wrapper);

    @Select("SELECT sum(t.`trade_amount`) as tradeAmount,count(t.id) as tradeCount,p.`privilege_name` as privilageName ,p.`promo_id` as promoId  FROM `trade_privilege`  p, trade t\n" +
            "WHERE p.`trade_id`  = t.`id` ${ew.sqlSegment} \n" +
            "GROUP BY p.`coupon_id` \n" +
            "ORDER BY sum(t.`trade_amount`) desc;")
    List<TradePrivilageReport> queryTradePrivilage(@Param("ew") Condition wrapper);

    @Select("SELECT c.`commercial_name` as shopName, c.`commercial_id` as shopIdenty,SUM(t.`trade_amount`) as salesAmount,count(t.id) as salesCount \n" +
            "FROM `commercial` c LEFT JOIN `trade` t on c.`commercial_id` = t.`shop_identy` \n" +
            "where t.id not in (SELECT relate_trade_id FROM trade WHERE brand_identy = ${brandIdenty} AND status_flag = 1 AND trade_status = 5 AND trade_type = 2 AND server_create_time BETWEEN '${startDate}' AND '${endDate}')  \n" +
            "${ew.sqlSegment} \n" +
            "GROUP BY c.`commercial_id` \n" +
            "ORDER BY SUM(t.`trade_amount`) desc limit 20")
    List<ShopSalesReport> queryShopOrderSales(@Param("ew") Condition wrapper, @Param("brandIdenty") Long brandIdenty, @Param("startDate") String startDate, @Param("endDate") String endDate);


    @Select("SELECT c.`commercial_name` as shopName, c.`commercial_id` as shopIdenty,SUM(t.`trade_amount`) as salesAmount,count(t.id) as salesCount , t.`business_type` as businessType\n" +
            "FROM `commercial` c LEFT JOIN `trade` t on c.`commercial_id` = t.`shop_identy` \n" +
            "where t.id not in (SELECT relate_trade_id FROM trade WHERE brand_identy = ${brandIdenty} AND status_flag = 1 AND trade_status = 5 AND trade_type = 2 AND server_create_time BETWEEN '${startDate}' AND '${endDate}') \n" +
            "${ew.sqlSegment} \n" +
            "GROUP BY c.`commercial_id` , t.`business_type` \n" +
            "ORDER BY SUM(t.`trade_amount`) desc ;")
    List<ShopSalesReport> queryShopSalesData(@Param("ew") Condition wrapper,@Param("brandIdenty") Long brandIdenty,@Param("startDate") String startDate,@Param("endDate") String endDate);
}
