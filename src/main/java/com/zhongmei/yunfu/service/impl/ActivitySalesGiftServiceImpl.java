package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.ActivitySalesEntity;
import com.zhongmei.yunfu.domain.entity.ActivitySalesGiftEntity;
import com.zhongmei.yunfu.domain.mapper.ActivitySalesGiftMapper;
import com.zhongmei.yunfu.domain.mapper.ActivitySalesMapper;
import com.zhongmei.yunfu.service.ActivitySalesGiftService;
import com.zhongmei.yunfu.service.ActivitySalesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 特价活动 服务实现类
 * </p>
 */
@Service
public class ActivitySalesGiftServiceImpl extends ServiceImpl<ActivitySalesGiftMapper, ActivitySalesGiftEntity> implements ActivitySalesGiftService {


    @Override
    public ActivitySalesGiftEntity addActivityGift(ActivitySalesGiftEntity mActivitySalesGiftEntity) throws Exception {
        insert(mActivitySalesGiftEntity);
        return mActivitySalesGiftEntity;
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        return deleteById(id);
    }

    @Override
    public List<ActivitySalesGiftEntity> queryListData(ActivitySalesGiftEntity mActivitySalesGiftEntity) throws Exception {
        EntityWrapper<ActivitySalesGiftEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy",mActivitySalesGiftEntity.getBrandIdenty());
        eWrapper.eq("shop_identy",mActivitySalesGiftEntity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        eWrapper.eq("activity_id",mActivitySalesGiftEntity.getActivityId());
        return selectList(eWrapper);
    }
}