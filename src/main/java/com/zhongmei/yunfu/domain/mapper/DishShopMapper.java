package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhongmei.yunfu.domain.entity.SalesReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 门店菜品 Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
public interface DishShopMapper extends BaseMapper<DishShopEntity> {


    @Select("SELECT d.`id`, d.`name`, d.`market_price`  FROM `dish_shop` d LEFT JOIN `dish_setmeal` s on d.`id` = s.`child_dish_id` ${ew.sqlSegment}")
    List<DishShopEntity> queryDishList(@Param("ew") Condition wrapper);

}
