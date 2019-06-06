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

    List<DishBrandTypeEntity> queryDishType(DishBrandTypeEntity mDishBrandTypeEntity) throws Exception;
}
