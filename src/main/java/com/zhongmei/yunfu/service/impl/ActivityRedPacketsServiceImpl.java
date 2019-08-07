package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.ActivityRedPacketsEntity;
import com.zhongmei.yunfu.domain.entity.ActivitySalesGiftEntity;
import com.zhongmei.yunfu.domain.mapper.ActivityRedPacketsMapper;
import com.zhongmei.yunfu.domain.mapper.ActivitySalesGiftMapper;
import com.zhongmei.yunfu.service.ActivityRedPacketsService;
import com.zhongmei.yunfu.service.ActivitySalesGiftService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 特价活动 红包规则
 * </p>
 */
@Service
public class ActivityRedPacketsServiceImpl extends ServiceImpl<ActivityRedPacketsMapper, ActivityRedPacketsEntity> implements ActivityRedPacketsService {


    @Override
    public Boolean addRedPacketsRule(ActivityRedPacketsEntity mActivityRedPacketsEntity) throws Exception {

        return insert(mActivityRedPacketsEntity);
    }

    @Override
    public Boolean addOrUpdateRule(ActivityRedPacketsEntity mActivityRedPacketsEntity) throws Exception {
        return insertOrUpdate(mActivityRedPacketsEntity);
    }

    @Override
    public ActivityRedPacketsEntity queryRule(ActivityRedPacketsEntity mActivityRedPacketsEntity) throws Exception {
        EntityWrapper<ActivityRedPacketsEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy",mActivityRedPacketsEntity.getBrandIdenty());
        eWrapper.eq("shop_identy",mActivityRedPacketsEntity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        eWrapper.eq("activity_id",mActivityRedPacketsEntity.getActivityId());
        ActivityRedPacketsEntity entity = selectOne(eWrapper);
        return entity;
    }
}