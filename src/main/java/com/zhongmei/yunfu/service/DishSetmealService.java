package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.CardTimeModel;
import com.zhongmei.yunfu.controller.model.DishSetmealModel;
import com.zhongmei.yunfu.domain.entity.DishSetmealEntity;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.DishSetmealGroupEntity;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;

import java.util.List;

/**
 * <p>
 * 套餐 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
public interface DishSetmealService extends IService<DishSetmealEntity> {

    /**
     * 插入子品项管理关系
     * @param listDishId
     * @param mDishShopEntity
     * @param mDishSetmealGroupEntity
     * @return
     */
    Boolean addSetmeal(List<Long> listDishId, DishShopEntity mDishShopEntity, DishSetmealGroupEntity mDishSetmealGroupEntity) throws Exception;

    /**
     * 根据dishId获取菜品列表
     * @param dishId
     * @return
     */
    List<DishSetmealEntity> querySetmeal(Long dishId) throws Exception;

    /**
     * 根据套餐壳id删除子品
     * @param dishId
     * @return
     * @throws Exception
     */
    Boolean delectSetmealByDishId(Long dishId) throws Exception;

    /**
     *
     * @param dishId
     * @return
     * @throws Exception
     */
    List<DishSetmealModel> querySetmealList(Long dishId) throws Exception;
}
