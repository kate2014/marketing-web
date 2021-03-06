package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.model.FeedbackModel;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.zhongmei.yunfu.domain.entity.FeedbackEntity;
import com.zhongmei.yunfu.domain.mapper.FeedbackMapper;
import com.zhongmei.yunfu.service.FeedbackService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, FeedbackEntity> implements FeedbackService {


    @Override
    public boolean addFeedback(FeedbackEntity mFeedbackEntity) throws Exception {

        return insert(mFeedbackEntity);
    }

    @Override
    public Page<FeedbackEntity> queryFeedbackPage(FeedbackModel mFeedbackModel) throws Exception {

        Page<FeedbackEntity> listPage = new Page<>(mFeedbackModel.getPageNo(), mFeedbackModel.getPageSize());

        EntityWrapper<FeedbackEntity> eWrapper = new EntityWrapper<>(new FeedbackEntity());

        eWrapper.eq("brand_identy",mFeedbackModel.getBrandIdenty());
        eWrapper.eq("shop_identy",mFeedbackModel.getShopIdenty());

        if(mFeedbackModel.getType() != null){
            eWrapper.eq("type",mFeedbackModel.getType());
        }
        if(mFeedbackModel.getCustomerId() != null && !mFeedbackModel.getCustomerId().equals("")){
            eWrapper.eq("customer_id",mFeedbackModel.getCustomerId());
        }
        if(mFeedbackModel.getUserId() != null && !mFeedbackModel.getUserId().equals("")){
            eWrapper.eq("user_id",mFeedbackModel.getUserId());
        }
        if(mFeedbackModel.getRelateId() != null){
            eWrapper.eq("relate_id",mFeedbackModel.getRelateId());
        }
        if(mFeedbackModel.getStart() != null){
            eWrapper.eq("start",mFeedbackModel.getStart());
        }
        eWrapper.between("server_create_time",mFeedbackModel.getStartDate(),mFeedbackModel.getEndDate());

        if(mFeedbackModel.getStarNum() != null){
            eWrapper.and("min_score <= {0}",mFeedbackModel.getStarNum());
        }

        eWrapper.eq("status_flag",1);
        eWrapper.orderBy("server_create_time",false);


        Page<FeedbackEntity> listData = selectPage(listPage, eWrapper);

        return listData;
    }

    @Override
    public List<FeedbackEntity> queryFeedbackList(FeedbackEntity mFeedbackEntity) throws Exception {
        EntityWrapper<FeedbackEntity> eWrapper = new EntityWrapper<>(new FeedbackEntity());

        eWrapper.eq("brand_identy",mFeedbackEntity.getBrandIdenty());
        eWrapper.eq("shop_identy",mFeedbackEntity.getShopIdenty());

        if(mFeedbackEntity.getType() != null){
            eWrapper.eq("type",mFeedbackEntity.getType());
        }

        if(mFeedbackEntity.getCustomerId() != null && !mFeedbackEntity.getCustomerId().equals("")){
            eWrapper.eq("customer_id",mFeedbackEntity.getCustomerId());
        }
        if(mFeedbackEntity.getUserId() != null && !mFeedbackEntity.getUserId().equals("")){
            eWrapper.eq("user_id",mFeedbackEntity.getUserId());
        }
        if(mFeedbackEntity.getRelateId() != null){
            eWrapper.eq("relate_id",mFeedbackEntity.getRelateId());
        }
        if(mFeedbackEntity.getStart() != null){
            eWrapper.eq("start",mFeedbackEntity.getStart());
        }

        eWrapper.eq("status_flag",1);
        eWrapper.orderBy("server_create_time",false);

        List<FeedbackEntity> listData = selectList(eWrapper);

        return listData;
    }

    @Override
    public List<FeedbackEntity> queryFeedbackByTradeId(Long brandIdenty, Long shopIdenty, Long tradeId) throws Exception {
        EntityWrapper<FeedbackEntity> eWrapper = new EntityWrapper<>(new FeedbackEntity());

        eWrapper.eq("brand_identy",brandIdenty);
        eWrapper.eq("shop_identy",shopIdenty);
        eWrapper.eq("status_flag",1);

        eWrapper.eq("trade_id",tradeId);
        eWrapper.orderBy("server_create_time",true);
        List<FeedbackEntity> listData = selectList(eWrapper);

        return listData;
    }

    @Override
    public boolean midfityFeedback(FeedbackEntity mFeedbackEntity) throws Exception {

        return updateById(mFeedbackEntity);
    }
}
