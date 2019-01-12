package com.zhongmei.yunfu.service.impl;

import com.zhongmei.yunfu.controller.model.CardTimeModel;
import com.zhongmei.yunfu.domain.entity.DishSetmealEntity;
import com.zhongmei.yunfu.domain.entity.DishSetmealGroupEntity;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.zhongmei.yunfu.domain.mapper.DishSetmealMapper;
import com.zhongmei.yunfu.service.DishSetmealService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 套餐 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
@Service
public class DishSetmealServiceImpl extends ServiceImpl<DishSetmealMapper, DishSetmealEntity> implements DishSetmealService {

    @Override
    public Boolean addSetmeal(List<Long> listDishId, DishShopEntity mDishShopEntity, DishSetmealGroupEntity mDishSetmealGroupEntity) {

        List<DishSetmealEntity> listData = new ArrayList<>();
        for(Long dishId : listDishId){
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
            Long creatorId = LoginManager.get().getUser().getCreatorId();
            String creatorName = LoginManager.get().getUser().getCreatorName();

            DishSetmealEntity mDishSetmealEntity = new DishSetmealEntity();
            mDishSetmealEntity.setChildDishId(dishId);
            mDishSetmealEntity.setDishId(mDishShopEntity.getId());
            mDishSetmealEntity.setComboDishTypeId(mDishSetmealGroupEntity.getId());
            mDishSetmealEntity.setChildDishType(0);

            mDishSetmealEntity.setPrice(BigDecimal.ZERO);
            mDishSetmealEntity.setStatusFlag(1);
            mDishSetmealEntity.setBrandIdenty(brandIdentity);
            mDishSetmealEntity.setShopIdenty(shopIdentity);
            mDishSetmealEntity.setCreatorId(creatorId);
            mDishSetmealEntity.setCreatorName(creatorName);
            listData.add(mDishSetmealEntity);
        }

        Boolean isSuccess = insertBatch(listData);

        return isSuccess;
    }
}
