package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.DishPropertyEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 菜品属性表 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
public interface DishPropertyService extends IService<DishPropertyEntity> {

    /**
     * 添加品项加项
     * @param mDishPropertyEntity
     * @return
     * @throws Exception
     */
    boolean addDishProperty(DishPropertyEntity mDishPropertyEntity)throws Exception;

    /**
     * 批量添加加项
     * @param listData
     * @return
     * @throws Exception
     */
    boolean batchAddDishProperty(List<DishPropertyEntity> listData)throws Exception;

    /**
     * 删除菜品下所有的加项
     * @param dishId
     * @return
     * @throws Exception
     */
    boolean deletePropertyByDishId(Long brandIdenty,Long shopIdenty,Long dishId)throws Exception;

    /**
     * 获取商品的所有加项
     * @param dishId
     * @return
     * @throws Exception
     */
    List<DishPropertyEntity> queryPropertyByDishId(Long brandIdenty,Long shopIdenty,Long dishId)throws Exception;

}
