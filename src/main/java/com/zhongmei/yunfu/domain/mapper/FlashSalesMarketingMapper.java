package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.controller.model.FlashSalesCustomerModel;
import com.zhongmei.yunfu.controller.model.FlashSalesModel;
import com.zhongmei.yunfu.controller.model.FlashSalesReportModel;
import com.zhongmei.yunfu.domain.entity.FlashSalesMarketingEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 秒杀活动 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-09-10
 */
public interface FlashSalesMarketingMapper extends BaseMapper<FlashSalesMarketingEntity> {

    @Select("SELECT id,name,begin_time,end_time,original_price,flash_price,img_url,profile FROM `flash_sales_marketing` ${ew.sqlSegment} ORDER BY  `server_create_time`  DESC LIMIT 1;")
    FlashSalesMarketingEntity queryNewFlashSales(@Param("ew") Condition wrapper);

    @Select("SELECT name,`sold_count` as soldCount FROM flash_sales_marketing ${ew.sqlSegment} ORDER BY `server_create_time` DESC ;")
    List<FlashSalesReportModel> queryFlashSalesReport(@Param("ew") Condition wrapper);

    @Select("UPDATE `flash_sales_marketing` set `sold_count` = `sold_count` + 1 ${ew.sqlSegment}")
    void modifyFlashSalesCount(@Param("ew") Condition wrapper);

    @Select("SELECT w.`status` ,f.`id` ,f.`name` ,f.`img_url` ,f.`profile` , f.`begin_time` ,f.`end_time` ,f.`validity_period` ,f.`shop_identity` ,f.`brand_identity`,f.`original_price` ,f.`flash_price` \n" +
            "FROM `wx_trade_customer` w LEFT JOIN `flash_sales_marketing` f on w.`marketing_id` = f.`id` \n" +
            "${ew.sqlSegment} and unix_timestamp(f.`validity_period`)  >= unix_timestamp(NOW())\n" +
            "ORDER BY w.`server_create_time` desc;")
    List<FlashSalesCustomerModel> queryFlashSalesByCustomer(@Param("ew") Condition wrapper);
}
