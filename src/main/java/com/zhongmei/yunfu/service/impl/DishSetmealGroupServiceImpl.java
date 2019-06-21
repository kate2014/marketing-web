package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.model.CardTimeModel;
import com.zhongmei.yunfu.domain.entity.DishSetmealEntity;
import com.zhongmei.yunfu.domain.entity.DishSetmealGroupEntity;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.zhongmei.yunfu.domain.mapper.DishSetmealGroupMapper;
import com.zhongmei.yunfu.service.DishSetmealGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    @Override
    public Boolean delectSetmealGroup(Long dishId) throws Exception {
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        if(dishId == null || shopIdentity == null){
            return false;
        }

        EntityWrapper<DishSetmealGroupEntity> eWrapper = new EntityWrapper<>(new DishSetmealGroupEntity());

        eWrapper.eq("brand_identy",brandIdentity);
        eWrapper.eq("shop_identy",shopIdentity);
        eWrapper.eq("setmeal_dish_id",dishId);

        Boolean isSuccess = delete(eWrapper);
        return isSuccess;
    }

    @Override
    public DishSetmealGroupEntity queryDishSetmealGroupByDishId(Long dishId) throws Exception {
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        if(dishId == null || shopIdentity == null){
            return null;
        }

        EntityWrapper<DishSetmealGroupEntity> eWrapper = new EntityWrapper<>(new DishSetmealGroupEntity());
        eWrapper.eq("brand_identy",brandIdentity);
        eWrapper.eq("shop_identy",shopIdentity);
        eWrapper.eq("setmeal_dish_id",dishId);
        eWrapper.eq("status_flag",1);

        DishSetmealGroupEntity mDishSetmealGroupEntity = selectOne(eWrapper);

        return mDishSetmealGroupEntity;
    }

    @Override
    public List<DishSetmealGroupEntity> querySetmealTypeByDishId(Long brandIdentity,Long shopIdentity,Long dishId) throws Exception {

        if(dishId == null || shopIdentity == null){
            return null;
        }

        EntityWrapper<DishSetmealGroupEntity> eWrapper = new EntityWrapper<>(new DishSetmealGroupEntity());
        eWrapper.eq("brand_identy",brandIdentity);
        eWrapper.eq("shop_identy",shopIdentity);
        eWrapper.eq("setmeal_dish_id",dishId);
        eWrapper.eq("status_flag",1);
        eWrapper.orderBy("server_create_time",true);
        List<DishSetmealGroupEntity> listData = selectList(eWrapper);

        return listData;
    }

}
