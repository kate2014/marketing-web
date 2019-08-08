package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.ActivityRedPacketsEntity;
import com.zhongmei.yunfu.domain.entity.ActivitySalesEntity;
import com.zhongmei.yunfu.domain.entity.RecommendationAssociationEntity;
import com.zhongmei.yunfu.domain.mapper.ActivitySalesMapper;
import com.zhongmei.yunfu.domain.mapper.RecommendationAssociationMapper;
import com.zhongmei.yunfu.service.ActivitySalesService;
import com.zhongmei.yunfu.service.RecommendationAssociationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 特价活动 关联关联
 * </p>
 */
@Service
public class RecommendationAssociationServiceImpl extends ServiceImpl<RecommendationAssociationMapper, RecommendationAssociationEntity> implements RecommendationAssociationService {


    @Override
    public Boolean addAssociation(RecommendationAssociationEntity entity) throws Exception {

        return insert(entity);
    }

    @Override
    public List<RecommendationAssociationEntity> queryListAssociation(RecommendationAssociationEntity entity) throws Exception {
        EntityWrapper<RecommendationAssociationEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        eWrapper.eq("activity_id",entity.getActivityId());

        if(entity.getMainCustomerId() != null){
            eWrapper.eq("main_customer_id",entity.getMainCustomerId());
        }
        if(entity.getMainWxOpenId() != null){
            eWrapper.eq("main_wx_open_id",entity.getMainWxOpenId());
        }
        if(entity.getAcceptCustomerId() != null){
            eWrapper.eq("accept_customer_id",entity.getAcceptCustomerId());
        }
        if(entity.getAcceptWxOpenId() != null){
            eWrapper.eq("accept_wx_open_id",entity.getAcceptWxOpenId());
        }
        if(entity.getTradeId() != null){
            eWrapper.eq("trade_id",entity.getTradeId());
        }
        if(entity.getTransactionStatus() != null){
            eWrapper.eq("transaction_status",entity.getTransactionStatus());
        }

        List<RecommendationAssociationEntity>  listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public RecommendationAssociationEntity queryMainCustomer(RecommendationAssociationEntity entity) throws Exception {
        EntityWrapper<RecommendationAssociationEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        eWrapper.eq("activity_id",entity.getActivityId());
        if(entity.getAcceptCustomerId() != null){
            eWrapper.eq("accept_customer_id",entity.getActivityId());
        }
        if(entity.getAcceptWxOpenId() != null){
            eWrapper.eq("accept_wx_open_id",entity.getActivityId());
        }
        if(entity.getTradeId() != null){
            eWrapper.eq("trade_id",entity.getActivityId());
        }
        if(entity.getTransactionStatus() != null){
            eWrapper.eq("transaction_status",entity.getActivityId());
        }

        RecommendationAssociationEntity mEntity = selectOne(eWrapper);

        return mEntity;
    }

    @Override
    public Boolean modfityAssciation(RecommendationAssociationEntity entity) throws Exception {
        EntityWrapper<RecommendationAssociationEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        eWrapper.eq("activity_id",entity.getActivityId());
        eWrapper.eq("trade_id",entity.getActivityId());

        return update(entity,eWrapper);
    }
}