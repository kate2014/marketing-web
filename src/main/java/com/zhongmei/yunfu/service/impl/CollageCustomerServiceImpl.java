package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.model.CollageCustomerModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.CollageCustomerEntity;
import com.zhongmei.yunfu.domain.mapper.CollageCustomerMapper;
import com.zhongmei.yunfu.service.CollageCustomerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员发起拼团记录 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-14
 */
@Service
public class CollageCustomerServiceImpl extends ServiceImpl<CollageCustomerMapper, CollageCustomerEntity> implements CollageCustomerService {

    @Override
    public CollageCustomerEntity installCollageCustomer(CollageCustomerEntity mCollageCustomer) throws Exception {

        mCollageCustomer.setState(1);//拼团状态 1：拼团中  2：拼团完成  3：拼团失败
        mCollageCustomer.setType(1);//参团类型：1：发起拼团  2：参与拼团
        mCollageCustomer.setJoinCount(1);
        mCollageCustomer.setStatusFlag(1);
        Boolean isSuccess = insert(mCollageCustomer);

        return mCollageCustomer;
    }

    @Override
    public CollageCustomerEntity joinCollage(CollageCustomerEntity mCollageCustomer) throws Exception {
        mCollageCustomer.setRelationId(mCollageCustomer.getId());
        mCollageCustomer.setState(1);//拼团状态 1：拼团中  2：拼团完成  3：拼团失败
        mCollageCustomer.setType(2);//参团类型：1：发起拼团  2：参与拼团
        mCollageCustomer.setJoinCount(1);
        mCollageCustomer.setStatusFlag(1);
        Boolean isSuccess = insert(mCollageCustomer);

        return mCollageCustomer;
    }

    @Override
    public CollageCustomerEntity queryCollage(Long collageId) throws Exception {
        EntityWrapper<CollageCustomerEntity> eWrapper = new EntityWrapper<>(new CollageCustomerEntity());

        eWrapper.eq("id",collageId);
        eWrapper.setSqlSelect("id,collage_id,relation_id,trade_id,type,customer_id,state,join_count,enabled_flag");
        CollageCustomerEntity mCollageCustomerEntity = selectOne(eWrapper);
        return mCollageCustomerEntity;
    }

    @Override
    public CollageCustomerEntity queryCollageByTradeId(Long tradeId) throws Exception {
        EntityWrapper<CollageCustomerEntity> eWrapper = new EntityWrapper<>(new CollageCustomerEntity());

        eWrapper.eq("trade_id",tradeId);
        eWrapper.setSqlSelect("id,collage_id,relation_id,trade_id,type,customer_id,state,join_count,enabled_flag");
        CollageCustomerEntity mCollageCustomerEntity = selectOne(eWrapper);
        return mCollageCustomerEntity;
    }

    @Override
    public Boolean updateCollageCustomer(CollageCustomerEntity mCollageCustomer) throws Exception {

        Boolean isSuccess = updateById(mCollageCustomer);
        return isSuccess;
    }

    @Override
    public List<CollageCustomerModel> queryCollageByCustomer(CollageCustomerEntity mCollageCustomer) throws Exception {

        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("c.brand_identity",mCollageCustomer.getBrandIdentity());
        eWrapper.eq("c.shop_identity",mCollageCustomer.getShopIdentity());
        eWrapper.eq("c.customer_id",mCollageCustomer.getCustomerId());
        eWrapper.eq("c.is_paid",2);
        eWrapper.eq("c.enabled_flag",1);
        eWrapper.eq("c.state",1);
        eWrapper.eq("c.status_flag",1);
        List<CollageCustomerModel> listData = baseMapper.querCollageByCustomer(eWrapper);

        return listData;
    }

    @Override
    public CollageCustomerEntity queryCollageByCustomerId(CollageCustomerEntity mCollageCustomer) throws Exception {

        EntityWrapper<CollageCustomerEntity> eWrapper = new EntityWrapper<>(new CollageCustomerEntity());

        eWrapper.eq("brand_identity",mCollageCustomer.getBrandIdentity());
        eWrapper.eq("shop_identity",mCollageCustomer.getShopIdentity());
        eWrapper.eq("collage_id",mCollageCustomer.getCollageId());
        eWrapper.eq("customer_id",mCollageCustomer.getCustomerId());
        eWrapper.eq("status_flag",mCollageCustomer.getStatusFlag());
        eWrapper.eq("enabled_Flag",mCollageCustomer.getEnabledFlag());

        eWrapper.setSqlSelect("id,collage_id,relation_id,trade_id,type,customer_id,state,join_count,enabled_flag");
        CollageCustomerEntity mCollageCustomerEntity = selectOne(eWrapper);
        return mCollageCustomerEntity;
    }

    @Override
    public List<CollageCustomerEntity> queryCollageByRelationId(Long relationId,Integer state) throws Exception {

        EntityWrapper<CollageCustomerEntity> eWrapper = new EntityWrapper<>(new CollageCustomerEntity());

        eWrapper.orNew().eq("id",relationId).or().eq("relation_id",relationId);
        eWrapper.andNew().eq("state",state);
        eWrapper.eq("status_flag",1);
        eWrapper.setSqlSelect("id,collage_id,relation_id,trade_id,type,customer_id,state,join_count,enabled_flag,is_paid");
        List<CollageCustomerEntity> listData = selectList(eWrapper);

        return listData;
    }

    @Override
    public Boolean updateCollageByRelationId(Long relationId,CollageCustomerEntity mCollageCustomerEntity) throws Exception {

        EntityWrapper<CollageCustomerEntity> eWrapper = new EntityWrapper<>(new CollageCustomerEntity());


        eWrapper.orNew().eq("id",relationId).or().eq("relation_id",relationId);
        eWrapper.andNew().eq("state",1);
        eWrapper.eq("status_flag",1);
        Boolean isSuccess = update(mCollageCustomerEntity,eWrapper);

        return isSuccess;
    }
}
