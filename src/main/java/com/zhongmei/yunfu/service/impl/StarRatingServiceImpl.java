package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.FeedbackEntity;
import com.zhongmei.yunfu.domain.entity.StarRatingEntity;
import com.zhongmei.yunfu.domain.mapper.StarRatingMapper;
import com.zhongmei.yunfu.service.StarRatingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarRatingServiceImpl extends ServiceImpl<StarRatingMapper, StarRatingEntity> implements StarRatingService {


    @Override
    public boolean batchAddStar(List<StarRatingEntity> listData) throws Exception {

        return insertBatch(listData);
    }

    @Override
    public List<StarRatingEntity> queryStarList(StarRatingEntity mStarRatingEntity) throws Exception {

        EntityWrapper<StarRatingEntity> eWrapper = new EntityWrapper<>(new StarRatingEntity());

        eWrapper.eq("brand_identy",mStarRatingEntity.getBrandIdenty());
        eWrapper.eq("shop_identy",mStarRatingEntity.getShopIdenty());
        if(mStarRatingEntity.getUserId() != null && !mStarRatingEntity.getUserId().equals("")){
            eWrapper.eq("user_id",mStarRatingEntity.getUserId());
        }
        if(mStarRatingEntity.getTradeId() != null && !mStarRatingEntity.getTradeId().equals("")){
            eWrapper.eq("trade_id",mStarRatingEntity.getTradeId());
        }

        eWrapper.eq("status_flag",1);
        eWrapper.orderBy("server_create_time",false);

        List<StarRatingEntity> listData = selectList(eWrapper);

        return listData;
    }

    @Override
    public List<StarRatingEntity> queryStarRatingList(String tradeIds, Long brandIdenty, Long shopIdenty) throws Exception {
        EntityWrapper<StarRatingEntity> eWrapper = new EntityWrapper<>(new StarRatingEntity());

        eWrapper.eq("brand_identy",brandIdenty);
        eWrapper.eq("shop_identy",shopIdenty);
        eWrapper.in("trade_id",tradeIds);
        eWrapper.eq("status_flag",1);
        eWrapper.orderBy("server_create_time",false);
        List<StarRatingEntity> listData = selectList(eWrapper);
        return listData;
    }

}
