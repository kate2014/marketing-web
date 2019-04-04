package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.domain.entity.DishReport;
import com.zhongmei.yunfu.domain.entity.DishSaleReport;
import com.zhongmei.yunfu.domain.entity.TradeItemEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 交易明细 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
public interface TradeItemMapper extends BaseMapper<TradeItemEntity> {

    @Select("select sum(actual_amount) as salesAmount , sum(quantity) as salseCount, dish_name as dishName ,price from trade_item where status_flag = 1 and trade_id in (select id from trade ${ew.sqlSegment}) group by dish_name order by sum(quantity) desc limit 20 ")
    List<DishReport> queryDishSales(@Param("ew") Condition wrapper);

    @Select("select sum(actual_amount) as salesAmount , sum(quantity) as salseCount, dish_name as dishName ,price from trade_item where status_flag = 1 and trade_id in (select id from trade ${ew.sqlSegment}) group by dish_name order by sum(quantity) desc")
    List<DishReport> dishSalesExportExcel(@Param("ew") Condition wrapper);

    @Select("SELECT  i.`dish_id` ,i.`dish_name` ,i.`quantity`,t.`server_create_time`   FROM trade t LEFT JOIN  `trade_item` i on t.`id` = i.`trade_id` \n" +
            "${ew.sqlSegment} " +
            "ORDER BY server_create_time desc;")
    List<TradeItemEntity> dishSaleDetail(@Param("ew") Condition wrapper);



    @Select("SELECT sum(i.`quantity`) as quantity,i.`dish_id` , i.dish_name , sum(i.`actual_amount`) as actual_amount\n" +
            "FROM trade t , `trade_item` i \n" +
            "WHERE t.`id` = i.`trade_id` ${ew.sqlSegment}\n" +
            "GROUP BY i.`dish_id` ;")
    List<TradeItemEntity> dishSaleData(@Param("ew") Condition wrapper);

//    @Select("SELECT i.`dish_name` as name ,i.`quantity` as number ,i.`actual_amount` as actualAmount ,CASE t.`trade_type` WHEN 1 THEN '销货' WHEN 2 THEN '退货' END AS type , date_format(i.`server_create_time`,'%Y-%m-%d %H:%i:%s') as serverCreateTime ,c.`customer_name` ,u.`user_name` as tradeUser\n" +
//            "FROM trade t LEFT JOIN `trade_item` i on t.`id` = i.`trade_id` LEFT JOIN `trade_customer` c on t.`id` = c.`trade_id` LEFT JOIN `trade_user` u on i.`id` = u.`trade_item_id` \n" +
//            "${ew.sqlSegment} \n" +
//            "GROUP BY i.`id` \n" +
//            "ORDER BY `serverCreateTime` desc; ")
//    List<DishSaleReport> listDishSale(@Param("ew") Condition wrapper);

    @Select("SELECT t.id as tradeId,t.trade_no as tradeNo, i.id as tradeItemId, i.`dish_name` as name ,i.`quantity` as number ,i.`actual_amount` as actualAmount ,CASE t.`trade_type` WHEN 1 THEN '销货' WHEN 2 THEN '退货' END AS type , date_format(i.`server_create_time`,'%Y-%m-%d %H:%i:%s') as serverCreateTime \n" +
            "FROM trade t LEFT JOIN `trade_item` i on t.`id` = i.`trade_id` \n" +
            "${ew.sqlSegment} \n" +
            "ORDER BY `serverCreateTime` desc; ")
    List<DishSaleReport> listDishSale(@Param("ew") Condition wrapper);


}

