package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.DishBrandTypeEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 菜品类型 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
public interface DishBrandTypeService extends IService<DishBrandTypeEntity> {

    /**
     * 查询门店下所有品项类别
     * @param mDishBrandTypeEntity
     * @return
     * @throws Exception
     */
    List<DishBrandTypeEntity> queryAllDishType(DishBrandTypeEntity mDishBrandTypeEntity) throws Exception;

    /**
     * 查询门店下所以二级分类
     * @param mDishBrandTypeEntity
     * @return
     * @throws Exception
     */
    List<DishBrandTypeEntity> queryDishType(DishBrandTypeEntity mDishBrandTypeEntity) throws Exception;

    /**
     * 添加类别
     * @param mDishBrandTypeEntity
     * @return
     * @throws Exception
     */
    boolean addDishType(DishBrandTypeEntity mDishBrandTypeEntity) throws Exception;

    /**
     * 编辑类别
     * @param mDishBrandTypeEntity
     * @return
     * @throws Exception
     */
    boolean modfityDishType(DishBrandTypeEntity mDishBrandTypeEntity) throws Exception;

    /**
     * 删除类别
     * @param typeId
     * @return
     * @throws Exception
     */
    boolean deleteDishType(Long typeId) throws Exception;
}
