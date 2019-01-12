package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.CardTimeModel;
import com.zhongmei.yunfu.domain.entity.DishSetmealGroupEntity;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;

/**
 * <p>
 * 套餐内菜品分组 : dish_type表只能存在两个级别的分类 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
public interface DishSetmealGroupService extends IService<DishSetmealGroupEntity> {

    /**
     * 插入数据
     * @param mCardTimeModel
     * @return
     * @throws Exception
     */
    DishSetmealGroupEntity addSetmealGroup(CardTimeModel mCardTimeModel, DishShopEntity mDishShopEntity)throws Exception;

}
