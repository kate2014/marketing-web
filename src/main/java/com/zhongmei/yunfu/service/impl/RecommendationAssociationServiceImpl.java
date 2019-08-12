package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.RecommendationAssociationEntity;
import com.zhongmei.yunfu.domain.mapper.RecommendationAssociationMapper;
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
    public RecommendationAssociationEntity queryByTradeId(Long brandIdenty, Long shopIdenty, Long tradeId) throws Exception {

        EntityWrapper<RecommendationAssociationEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy",brandIdenty);
        eWrapper.eq("shop_identy",shopIdenty);
        eWrapper.eq("status_flag",1);
        eWrapper.eq("trade_id",tradeId);

        return selectOne(eWrapper);
    }

    @Override
    public Page<RecommendationAssociationEntity> queryRAByCustomer(RecommendationAssociationEntity entity, Integer pageNo, Integer pageSize) throws Exception {
        RecommendationAssociationEntity raEntity = new RecommendationAssociationEntity();
        Page<RecommendationAssociationEntity> listPage = new Page<>(pageNo, pageSize);
        EntityWrapper<RecommendationAssociationEntity> eWrapper = new EntityWrapper<>(raEntity);
        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        eWrapper.eq("activity_id",entity.getActivityId());
        eWrapper.eq("transaction_status",2);
        eWrapper.groupBy("main_wx_open_id");
        eWrapper.orderBy("count(id)",false);
        eWrapper.setSqlSelect("count(id) as id,main_customer_id,main_wx_open_id,main_customer_name");
        Page<RecommendationAssociationEntity> listData = selectPage(listPage,eWrapper);
        return listData;
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

    @Override
    public Integer queryUserTradeCount(RecommendationAssociationEntity entity) throws Exception {
        EntityWrapper<RecommendationAssociationEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        eWrapper.eq("activity_id",entity.getActivityId());
        eWrapper.eq("main_wx_open_id",entity.getMainWxOpenId());
        List<RecommendationAssociationEntity> listData = selectList(eWrapper);
        return listData.size();
    }
}