package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.api.model.ActivityGiftEffectResp;
import com.zhongmei.yunfu.controller.model.ActivityEffectModel;
import com.zhongmei.yunfu.controller.model.CollageCustomerModel;
import com.zhongmei.yunfu.controller.model.CustomerGiftModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
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
    public boolean deleteGiftById(Long id) throws Exception {
        return deleteById(id);
    }

    @Override
    public Boolean deleteByActivityId(Long activityId) throws Exception {
        EntityWrapper<ActivitySalesGiftEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("activity_id",activityId);

        return delete(eWrapper);
    }

    @Override
    public List<ActivitySalesGiftEntity> queryListData(ActivitySalesGiftEntity mActivitySalesGiftEntity) throws Exception {
        EntityWrapper<ActivitySalesGiftEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy",mActivitySalesGiftEntity.getBrandIdenty());
        eWrapper.eq("shop_identy",mActivitySalesGiftEntity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        eWrapper.eq("activity_id",mActivitySalesGiftEntity.getActivityId());
        eWrapper.setSqlSelect("id,image_url,order_count,gift_name,gift_price");
        return selectList(eWrapper);
    }

    @Override
    public List<CustomerGiftModel> queryActivityGift(ActivityEffectModel mActivityEffectModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.eq("c.brand_identy",mActivityEffectModel.getBrandIdenty());
        eWrapper.eq("c.shop_identy",mActivityEffectModel.getShopIdenty());
        eWrapper.eq("c.activity_id",mActivityEffectModel.getActivityId());
        eWrapper.eq("c.status_flag",1);
        if(mActivityEffectModel.getCustomerName() != null){
            eWrapper.like("o.customer_name",mActivityEffectModel.getCustomerName());
        }
        if(mActivityEffectModel.getCustomerPhone() != null){
            eWrapper.eq("o.customer_phone",mActivityEffectModel.getCustomerPhone());
        }
        List<CustomerGiftModel> listData = baseMapper.queryActivityGift(eWrapper);

        return listData;
    }

    @Override
    public List<ActivityGiftEffectResp> queryCustomerGift(ActivityEffectModel mActivityEffectModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("r.brand_identy",mActivityEffectModel.getBrandIdenty());
        eWrapper.eq("r.shop_identy",mActivityEffectModel.getShopIdenty());
        eWrapper.eq("r.main_customer_id",mActivityEffectModel.getCustomerId());
        eWrapper.eq("r.transaction_status",2);
        eWrapper.eq("r.status_flag",1);

        List<ActivityGiftEffectResp> listData = baseMapper.queryCustomerGift(eWrapper);

        return listData;
    }


}