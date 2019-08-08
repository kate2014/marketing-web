package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.domain.entity.RecommendationAssociationEntity;
import com.zhongmei.yunfu.domain.entity.RedPacketsRecordEntity;
import com.zhongmei.yunfu.domain.mapper.OperationalRecordsMapper;
import com.zhongmei.yunfu.domain.mapper.RedPacketsRecordMapper;
import com.zhongmei.yunfu.service.OperationalRecordsService;
import com.zhongmei.yunfu.service.RedPacketsRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 特价活动 关联关联
 * </p>
 */
@Service
public class RedPacketsRecordServiceImpl extends ServiceImpl<RedPacketsRecordMapper, RedPacketsRecordEntity> implements RedPacketsRecordService {


    @Override
    public Boolean addRecord(RedPacketsRecordEntity entity) throws Exception {

        return insert(entity);
    }

    @Override
    public Page<RedPacketsRecordEntity> queryByCustomer(RedPacketsRecordEntity entity,int pageNo,int pageSize) throws Exception {

        RedPacketsRecordEntity mRedPacketsRecordEntity = new RedPacketsRecordEntity();
        Page<RedPacketsRecordEntity> listPage = new Page<>(pageNo, pageSize);
        EntityWrapper<RedPacketsRecordEntity> eWrapper = new EntityWrapper<>(mRedPacketsRecordEntity);

        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("status_flag",1);

        if(entity.getCustomerId() != null){
            eWrapper.eq("customer_id",entity.getCustomerId());
        }
        if(entity.getWxOpenId() != null){
            eWrapper.eq("wx_open_id",entity.getWxOpenId());
        }
        eWrapper.orderBy("server_update_time",false);
        Page<RedPacketsRecordEntity> listData = selectPage(listPage,eWrapper);
        return listData;
    }

    @Override
    public List<RedPacketsRecordEntity> queryRankingList(RedPacketsRecordEntity entity) throws Exception {

        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);

        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("activity_id",entity.getActivityId());
        eWrapper.eq("status_flag",1);

        List<RedPacketsRecordEntity> listData = baseMapper.queryRankingList(eWrapper);
        return listData;
    }
}