package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.model.ActivityEffectModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.CollageMarketingEntity;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.domain.mapper.OperationalRecordsMapper;
import com.zhongmei.yunfu.service.OperationalRecordsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 特价活动 关联关联
 * </p>
 */
@Service
public class OperationalRecordsServiceImpl extends ServiceImpl<OperationalRecordsMapper, OperationalRecordsEntity> implements OperationalRecordsService {


    @Override
    public Boolean addOperational(OperationalRecordsEntity entity) throws Exception {

        return insert(entity);
    }

    @Override
    public Boolean modiftyOperational(OperationalRecordsEntity entity) throws Exception {

        EntityWrapper<OperationalRecordsEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        eWrapper.eq("activity_id",entity.getActivityId());
        eWrapper.eq("type",entity.getType());
        if(entity.getCustomerId() != null){
            eWrapper.eq("customer_id",entity.getCustomerId());
        }
        if(entity.getWxOpenId() != null){
            eWrapper.eq("wx_open_id",entity.getWxOpenId());
        }


        return update(entity,eWrapper);
    }

    @Override
    public Page<OperationalRecordsEntity> queryByActivityId(OperationalRecordsEntity entity,int pageNo, int pageSize) throws Exception {

        OperationalRecordsEntity mOperationalRecordsEntity = new OperationalRecordsEntity();
        Page<OperationalRecordsEntity> listPage = new Page<>(pageNo, pageSize);
        EntityWrapper<OperationalRecordsEntity> eWrapper = new EntityWrapper<>(mOperationalRecordsEntity);
        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        eWrapper.eq("activity_id",entity.getActivityId());
        if(entity.getCustomerName() != null){
            eWrapper.like("customer_name",entity.getCustomerName());
        }
        if(entity.getCustomerPhone() != null){
            eWrapper.like("customer_phone",entity.getCustomerPhone());
        }
        if(entity.getOperationalCount() != null){
            eWrapper.ge("operational_count",entity.getOperationalCount());
        }
        eWrapper.eq("type",entity.getType());
        eWrapper.orderBy("operational_count",false);
        Page<OperationalRecordsEntity> listData = selectPage(listPage,eWrapper);
        return listData;
    }

    @Override
    public List<OperationalRecordsEntity> queryDataByActivityId(OperationalRecordsEntity entity) throws Exception {
        EntityWrapper<OperationalRecordsEntity> eWrapper = new EntityWrapper<>(new OperationalRecordsEntity());
        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        eWrapper.eq("activity_id",entity.getActivityId());

        eWrapper.setSqlSelect("sum(operational_count) as operational_count,type,activity_id");
        eWrapper.groupBy("type");

        return selectList(eWrapper);
    }

    @Override
    public List<OperationalRecordsEntity> queryCustomer(OperationalRecordsEntity entity) throws Exception {
        EntityWrapper<OperationalRecordsEntity> eWrapper = new EntityWrapper<>(entity);
        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        eWrapper.eq("activity_id",entity.getActivityId());
        eWrapper.eq("type",entity.getType());
        eWrapper.orderBy("server_update_time",false);
        eWrapper.last("limit 6");

        eWrapper.setSqlSelect("wx_photo,wx_name");
        return selectList(eWrapper);
    }

    @Override
    public Boolean deleteByAction(OperationalRecordsEntity entity) throws Exception {

        EntityWrapper<OperationalRecordsEntity> eWrapper = new EntityWrapper<>(entity);
        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("activity_id",entity.getActivityId());
        return delete(eWrapper);
    }

    @Override
    public OperationalRecordsEntity queryByCustomer(OperationalRecordsEntity entity) throws Exception {

        EntityWrapper<OperationalRecordsEntity> eWrapper = new EntityWrapper<>(new OperationalRecordsEntity());

        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("activity_id",entity.getActivityId());
        if(entity.getType() != null){
            eWrapper.eq("type",entity.getType());
        }
        if(entity.getWxOpenId() != null){
            eWrapper.eq("wx_open_id",entity.getWxOpenId());
        }
//        eWrapper.setSqlSelect("id,brand_identy,shop_identy,activity_id,customer_id,operational_count,");
        OperationalRecordsEntity mOperationalRecordsEntity = selectOne(eWrapper);
        return mOperationalRecordsEntity;
    }

    @Override
    public List<OperationalRecordsEntity> queryEffectCount(OperationalRecordsEntity entity) throws Exception {

        EntityWrapper<OperationalRecordsEntity> eWrapper = new EntityWrapper<>(new OperationalRecordsEntity());

        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("activity_id",entity.getActivityId());
        eWrapper.groupBy("type");
        eWrapper.orderBy("type",false);
        eWrapper.setSqlSelect("count(id) as id,type,sum(operational_count) as operationalCount");

        return selectList(eWrapper);
    }

    @Override
    public Boolean deleteByActivity(Long activityId) throws Exception {
        EntityWrapper<OperationalRecordsEntity> eWrapper = new EntityWrapper<>(new OperationalRecordsEntity());

        eWrapper.eq("activity_id",activityId);

        return delete(eWrapper);
    }
}