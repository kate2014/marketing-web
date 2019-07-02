package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.model.BookingSearchModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.BookingEntity;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.zhongmei.yunfu.domain.entity.DishTimeChargingRuleEntity;
import com.zhongmei.yunfu.domain.entity.bean.BookingReport;
import com.zhongmei.yunfu.domain.mapper.BookingMapper;
import com.zhongmei.yunfu.domain.mapper.DishTimeChargingRuleMapper;
import com.zhongmei.yunfu.service.BookingService;
import com.zhongmei.yunfu.service.DishTimeChargingRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 预订表 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-14
 */
@Service
public class DishTimeChargingRuleServiceImpl extends ServiceImpl<DishTimeChargingRuleMapper, DishTimeChargingRuleEntity> implements DishTimeChargingRuleService {


    @Override
    public boolean addOrUpdateRule(DishTimeChargingRuleEntity mDishTimeChargingRuleEntity) throws Exception {

        return insertOrUpdate(mDishTimeChargingRuleEntity);
    }

    @Override
    public DishTimeChargingRuleEntity queryByDishId(Long brandIdenty, Long shopIdenty, Long dishId) throws Exception {

        EntityWrapper<DishTimeChargingRuleEntity> eWrapper = new EntityWrapper<>(new DishTimeChargingRuleEntity());

        eWrapper.eq("brand_identy",brandIdenty);
        eWrapper.eq("shop_identy",shopIdenty);
        eWrapper.eq("dish_id",dishId);
        eWrapper.eq("status_flag",1);

        DishTimeChargingRuleEntity mDishTimeChargingRuleEntity = selectOne(eWrapper);

        return mDishTimeChargingRuleEntity;
    }

    @Override
    public boolean deleteByDishId(Long brandIdenty, Long shopIdenty, Long dishId) throws Exception {
        EntityWrapper<DishTimeChargingRuleEntity> eWrapper = new EntityWrapper<>(new DishTimeChargingRuleEntity());

        eWrapper.eq("brand_identy",brandIdenty);
        eWrapper.eq("shop_identy",shopIdenty);
        eWrapper.eq("dish_id",dishId);

        return delete(eWrapper);
    }
}
