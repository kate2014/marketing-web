package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.model.CardTimeModel;
import com.zhongmei.yunfu.controller.model.DishSetmealModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
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
    public Boolean addSetmeal(List<Long> listDishId, DishShopEntity mDishShopEntity, DishSetmealGroupEntity mDishSetmealGroupEntity) throws Exception{

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

            mDishSetmealEntity.setLeastCellNum(BigDecimal.ONE);
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

    @Override
    public List<DishSetmealEntity> querySetmeal(Long dishId) throws Exception {

        Condition eWrapper = ConditionFilter.create();

        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        eWrapper.eq("brand_identy",brandIdentity);
        eWrapper.eq("shop_identy",shopIdentity);
        eWrapper.eq("dish_id",dishId);

        List<DishSetmealEntity> listData = selectList(eWrapper);

        return listData;
    }

    @Override
    public Boolean delectSetmealByDishId(Long dishId) throws Exception {
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        if(dishId == null || shopIdentity == null){
            return false;
        }
        EntityWrapper<DishSetmealEntity> eWrapper = new EntityWrapper<>(new DishSetmealEntity());

        eWrapper.eq("brand_identy",brandIdentity);
        eWrapper.eq("shop_identy",shopIdentity);
        eWrapper.eq("dish_id",dishId);

        Boolean isSuccess = delete(eWrapper);

        return isSuccess;
    }

    @Override
    public List<DishSetmealModel> querySetmealList(Long dishId) throws Exception {

        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("se.dish_id",dishId);
        eWrapper.eq("se.status_flag",1);
        List<DishSetmealModel> listDishShop = baseMapper.querySetmealList(eWrapper);

        return listDishShop;
    }

    @Override
    public boolean batchAddOrUpdateSetmeal(List<DishSetmealEntity> listSetmeal) throws Exception {

        return insertOrUpdateBatch(listSetmeal);
    }

    @Override
    public boolean batchDelete(List<Long> ids) throws Exception {
        return deleteBatchIds(ids);
    }

    @Override
    public boolean deleteSetmealByTypeId(Long brandIdentity,Long shopIdentity, Long typeId) throws Exception {
        EntityWrapper<DishSetmealEntity> eWrapper = new EntityWrapper<>(new DishSetmealEntity());
        eWrapper.eq("brand_identy",brandIdentity);
        eWrapper.eq("shop_identy",shopIdentity);
        eWrapper.eq("combo_dish_type_id",typeId);
        return delete(eWrapper);
    }
}
