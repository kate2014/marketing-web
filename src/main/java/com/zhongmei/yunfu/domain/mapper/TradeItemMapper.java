package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.domain.entity.DishReport;
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
}

