package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zhongmei.yunfu.controller.model.ReportSalesExportModel;
import com.zhongmei.yunfu.domain.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

public interface PurchaseSaleMapper extends BaseMapper<PurchaseAndSaleEntity> {

    @Select("SELECT d.id, `name` ,residue_total ,`dish_qty`,`market_price` , p. `source_name` ,p.`number` ,p.`purchase_price` ,p.`total_purchase_price` ,p.`type` \n" +
            "FROM `dish_shop` d LEFT JOIN \n" +
            "(SELECT SUM(`number`)  as number, source_name, `dish_shop_id` , SUM(`total_purchase_price`) as total_purchase_price ,purchase_price,`type` \n" +
            " FROM `purchase_and_sale` \n" +
            " WHERE `type` = 1 ${ew.sqlSegment} GROUP BY `dish_shop_id` ) as p\n" +
            "ON  d.`id` = p.`dish_shop_id` \n" +
            "WHERE d.`type` in (0,1) ${ew.sqlSegment} ORDER BY `dish_qty` desc")
    List<PurchaseSaleReport> queryPurchaseAndSale(@Param("ew") Condition wrapper);

    @Select("SELECT SUM(`number`) as number,`dish_shop_id`,source_id,source_name, purchase_price,type, SUM(`total_purchase_price`) as total_purchase_price " +
            "FROM `purchase_and_sale` " +
            "${ew.sqlSegment} GROUP BY `dish_shop_id`")
    List<PurchaseAndSaleEntity> listPurchaseAndSale(@Param("ew") Condition wrapper);

    @Select("SELECT i.`server_create_time` ,i.`quantity`  as qty, i.dish_name as dish_name, i.`actual_amount` as total_price, '' as source_name,'' as type,i.`price` as price,t.`trade_type` as trade_type\n" +
            "FROM trade t , `trade_item` i \n" +
            "WHERE t.`id` = i.`trade_id` and t.`business_type` = 1 and t.`trade_status` = 4 and t.`trade_type` in (1,2) and ,t.`status_flag` = 1 ${ew.sqlSegment}\n" +
            "union all\n" +
            "SELECT p.`server_create_time` ,p.`number` as qty, d.`name` as dish_name,p.`total_purchase_price` as total_price,p.`source_name` as source_name, p.`type` as type, p.`purchase_price` as price, '' as trade_type\n" +
            "from `purchase_and_sale` p ,`dish_shop` d \n" +
            "WHERE p.`dish_shop_id` = d.`id` ${ew.sqlSegment}\n" +
            "ORDER BY `server_create_time` desc;")
    List<PurchaseSaleDetailReport> listPurchaseAndSaleDetail(@Param("ew") Condition wrapper);

    @Select("SELECT date_format(p.`server_create_time`,  '%Y-%m-%d %H:%i:%s')as serverCreateTime ,d.name as name,p.`purchase_price` ,p.`number`,p.`total_purchase_price` ,p.`source_name`, CASE p.`type` WHEN 1 THEN '入库' WHEN 2 THEN '耗损' END AS typeName, p.type \n" +
            "FROM `purchase_and_sale` p , `dish_shop` d \n" +
            "where p.`dish_shop_id` = d.`id` ${ew.sqlSegment} \n" +
            "ORDER BY `serverCreateTime` desc;")
    List<PurchaseSaleReport> purchaseList(@Param("ew") Condition wrapper);
}
