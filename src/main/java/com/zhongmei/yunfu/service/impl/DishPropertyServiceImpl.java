package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.domain.entity.DishPropertyEntity;
import com.zhongmei.yunfu.domain.mapper.DishPropertyMapper;
import com.zhongmei.yunfu.service.DishPropertyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜品属性表 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
@Service
public class DishPropertyServiceImpl extends ServiceImpl<DishPropertyMapper, DishPropertyEntity> implements DishPropertyService {

    @Override
    public boolean addDishProperty(DishPropertyEntity mDishPropertyEntity) throws Exception {

        return insert(mDishPropertyEntity);
    }

    @Override
    public boolean batchAddDishProperty(List<DishPropertyEntity> listData) throws Exception {
        return insertBatch(listData);
    }

    @Override
    public boolean deletePropertyByDishId(Long brandIdenty,Long shopIdenty,Long dishId) throws Exception {
        EntityWrapper<DishPropertyEntity> eWrapper = new EntityWrapper<>(new DishPropertyEntity());
        eWrapper.eq("brand_identy",brandIdenty);
        eWrapper.eq("shop_identy",shopIdenty);
        eWrapper.eq("dish_shop_id",dishId);

        return delete(eWrapper);
    }
}
