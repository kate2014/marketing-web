package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.controller.model.DishSetmealModel;
import com.zhongmei.yunfu.domain.entity.DishSetmealEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 套餐 Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
public interface DishSetmealMapper extends BaseMapper<DishSetmealEntity> {

    @Select("SELECT ds.`name` as dishName ,ds.`dish_code` as dishCode,se.*  FROM `dish_shop` ds LEFT JOIN dish_setmeal se on ds.`id` = se.`child_dish_id` ${ew.sqlSegment}")
    List<DishSetmealModel> querySetmealList(@Param("ew") Condition wrapper);

}
