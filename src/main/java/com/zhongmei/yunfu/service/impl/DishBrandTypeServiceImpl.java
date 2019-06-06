package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.domain.entity.DishBrandTypeEntity;
import com.zhongmei.yunfu.domain.mapper.DishBrandTypeMapper;
import com.zhongmei.yunfu.service.DishBrandTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜品类型 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
@Service
public class DishBrandTypeServiceImpl extends ServiceImpl<DishBrandTypeMapper, DishBrandTypeEntity> implements DishBrandTypeService {

    @Override
    public List<DishBrandTypeEntity> queryDishType(DishBrandTypeEntity mDishBrandTypeEntity)throws Exception {

        EntityWrapper<DishBrandTypeEntity> eWrapper = new EntityWrapper<>(new DishBrandTypeEntity());
        eWrapper.eq("brand_identy",mDishBrandTypeEntity.getBrandIdenty());
        eWrapper.eq("shop_identy",mDishBrandTypeEntity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        eWrapper.isNotNull("parent_id");

        List<DishBrandTypeEntity> listData = selectList(eWrapper);
        return listData;
    }
}
