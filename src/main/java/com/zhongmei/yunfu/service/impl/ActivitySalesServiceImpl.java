package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.domain.entity.ActivitySalesEntity;
import com.zhongmei.yunfu.domain.mapper.ActivitySalesMapper;
import com.zhongmei.yunfu.service.ActivitySalesService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 特价活动 服务实现类
 * </p>
 */
@Service
public class ActivitySalesServiceImpl extends ServiceImpl<ActivitySalesMapper, ActivitySalesEntity> implements ActivitySalesService {


    @Override
    public ActivitySalesEntity modifityActivity(ActivitySalesEntity mActivitySalesEntity) throws Exception {
        insertOrUpdate(mActivitySalesEntity);
        return mActivitySalesEntity;
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        EntityWrapper<ActivitySalesEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("id",id);
        return delete(eWrapper);
    }

    @Override
    public List<ActivitySalesEntity> queryListData(ActivitySalesEntity mActivitySalesEntity) throws Exception {
        EntityWrapper<ActivitySalesEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy",mActivitySalesEntity.getBrandIdenty());
        eWrapper.eq("shop_identy",mActivitySalesEntity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        if(mActivitySalesEntity.getEnabledFlag() != null){
            eWrapper.eq("enabled_flag",mActivitySalesEntity.getEnabledFlag());
        }
        if(mActivitySalesEntity.getName() != null){
            eWrapper.like("name",mActivitySalesEntity.getName());
        }

        eWrapper.setSqlSelect("id,name,profile,image_url,sales_count,sale_amount,end_time,validity_period,enabled_flag");
        return selectList(eWrapper);
    }

    @Override
    public ActivitySalesEntity queryById(Long id) throws Exception {

        return selectById(id);
    }

    @Override
    public Integer queryJoinCountById(Long id) throws Exception {

        EntityWrapper<ActivitySalesEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("id",id);
        eWrapper.setSqlSelect("customer_buy_count");
        ActivitySalesEntity mmActivitySalesEntity = selectOne(eWrapper);
        return mmActivitySalesEntity.getCustomerBuyCount();
    }
}