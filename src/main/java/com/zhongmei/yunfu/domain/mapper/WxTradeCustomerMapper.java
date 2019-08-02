package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.controller.model.CollageReportModel;
import com.zhongmei.yunfu.domain.entity.WxTradeCustomerEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 微信小程序购买使用记录 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
public interface WxTradeCustomerMapper extends BaseMapper<WxTradeCustomerEntity> {

    @Select("update `wx_trade_customer` set `enabled_flag`=1 where `trade_id` in (SELECT `trade_id` FROM `collage_customer` ${ew.sqlSegment})")
    Boolean modfiyEnabledFlagByMainCollage(@Param("ew") Condition wrapper);

    @Select("SELECT count(id) as countData,marketing_id as marketingId  FROM  `wx_trade_customer` " +
            "${ew.sqlSegment} and unix_timestamp(`validity_period`) >= unix_timestamp(NOW())" +
            "GROUP BY `marketing_id` ;")
    List<CollageReportModel> queryCollageData(@Param("ew") Condition wrapper);
}
