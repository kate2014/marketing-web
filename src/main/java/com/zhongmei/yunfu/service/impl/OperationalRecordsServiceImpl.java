package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.model.ActivityEffectModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.CollageMarketingEntity;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.domain.entity.PaymentEntity;
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
        eWrapper.eq("source",entity.getSource());
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
        eWrapper.eq("source",entity.getSource());
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
        eWrapper.eq("source",entity.getSource());

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
        eWrapper.eq("source",entity.getSource());
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
        eWrapper.eq("source",entity.getSource());
        return delete(eWrapper);
    }

    @Override
    public OperationalRecordsEntity queryByCustomer(OperationalRecordsEntity entity) throws Exception {

        EntityWrapper<OperationalRecordsEntity> eWrapper = new EntityWrapper<>(new OperationalRecordsEntity());

        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("activity_id",entity.getActivityId());
        if(entity.getCustomerId() != null){
            eWrapper.eq("customer_id",entity.getCustomerId());
        }
        if(entity.getType() != null){
            eWrapper.eq("type",entity.getType());
        }
        if(entity.getWxOpenId() != null){
            eWrapper.eq("wx_open_id",entity.getWxOpenId());
        }
        if(entity.getSource() != null){
            eWrapper.eq("source",entity.getSource());
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
        eWrapper.eq("source",entity.getSource());
        eWrapper.groupBy("type");
        eWrapper.orderBy("type",false);
        eWrapper.setSqlSelect("count(id) as id,type,sum(operational_count) as operationalCount");

        return selectList(eWrapper);
    }

    @Override
    public List<OperationalRecordsEntity> querySalesEffect(OperationalRecordsEntity entity) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        if(entity.getCustomerName() != null && !entity.getCustomerName().equals("")){
            eWrapper.like("b.customer_name", entity.getCustomerName());
        }
        if(entity.getCustomerPhone() != null && !entity.getCustomerPhone().equals("")){
            eWrapper.eq("b.customer_phone", entity.getCustomerPhone());
        }
        if(entity.getOperationalCount() != null && !entity.getOperationalCount().equals("")){
            eWrapper.ge("a.count", entity.getOperationalCount());
        }

        List<OperationalRecordsEntity> listData = baseMapper.querySalesEffect(eWrapper,entity.getBrandIdenty(),entity.getShopIdenty(),entity.getActivityId(),entity.getType());
        return listData;
    }

    @Override
    public List<OperationalRecordsEntity> queryJoinEffect(OperationalRecordsEntity entity) throws Exception {

        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        if(entity.getCustomerName() != null && !entity.getCustomerName().equals("")){
            eWrapper.like("b.customer_name", entity.getCustomerName());
        }
        if(entity.getCustomerPhone() != null && !entity.getCustomerPhone().equals("")){
            eWrapper.eq("b.customer_phone", entity.getCustomerPhone());
        }
        if(entity.getOperationalCount() != null && !entity.getOperationalCount().equals("")){
            eWrapper.ge("a.count", entity.getOperationalCount());
        }

        List<OperationalRecordsEntity> listData = baseMapper.queryJoinEffect(eWrapper,entity.getBrandIdenty(),entity.getShopIdenty(),entity.getActivityId(),entity.getType());
        return listData;

    }
}