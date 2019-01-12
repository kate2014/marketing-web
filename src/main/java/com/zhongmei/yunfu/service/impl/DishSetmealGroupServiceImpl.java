package com.zhongmei.yunfu.service.impl;

import com.zhongmei.yunfu.controller.model.CardTimeModel;
import com.zhongmei.yunfu.domain.entity.DishSetmealGroupEntity;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.zhongmei.yunfu.domain.mapper.DishSetmealGroupMapper;
import com.zhongmei.yunfu.service.DishSetmealGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 套餐内菜品分组 : dish_type表只能存在两个级别的分类 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
@Service
public class DishSetmealGroupServiceImpl extends ServiceImpl<DishSetmealGroupMapper, DishSetmealGroupEntity> implements DishSetmealGroupService {

    @Override
    public DishSetmealGroupEntity addSetmealGroup(CardTimeModel mCardTimeModel, DishShopEntity mDishShopEntity) throws Exception {

        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorName = LoginManager.get().getUser().getCreatorName();

        DishSetmealGroupEntity mDishSetmealGroupEntity = new DishSetmealGroupEntity();
        mDishSetmealGroupEntity.setSetmealDishId(mDishShopEntity.getId());
        mDishSetmealGroupEntity.setName("次卡服务品项选择");
        mDishSetmealGroupEntity.setOrderMin(BigDecimal.ONE);
        mDishSetmealGroupEntity.setOrderMax(new BigDecimal(100));
        mDishSetmealGroupEntity.setSort(1);
        mDishSetmealGroupEntity.setStatusFlag(1);
        mDishSetmealGroupEntity.setShopIdenty(shopIdentity);
        mDishSetmealGroupEntity.setBrandIdenty(brandIdentity);
        mDishSetmealGroupEntity.setCreatorId(creatorId);
        mDishSetmealGroupEntity.setCreatorName(creatorName);
        insert(mDishSetmealGroupEntity);

        return mDishSetmealGroupEntity;
    }
}
