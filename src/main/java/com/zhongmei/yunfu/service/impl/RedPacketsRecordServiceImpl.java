package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.domain.mapper.OperationalRecordsMapper;
import com.zhongmei.yunfu.domain.mapper.RedPacketsRecordMapper;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 特价活动 关联关联
 * </p>
 */
@Service
public class RedPacketsRecordServiceImpl extends ServiceImpl<RedPacketsRecordMapper, RedPacketsRecordEntity> implements RedPacketsRecordService {

    @Autowired
    RecommendationAssociationService mRAService;
    @Autowired
    ActivityRedPacketsService mARPService;
    @Autowired
    ExpandedCommissionService mECService;
    @Autowired
    ExpandedCommissionService mExpandedCommissionService;

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

    /**
     * 发放红包
     * @return
     */
    @Override
    public Boolean sendRedPackets(Long brandIdenty,Long shopIdenty,Long tradeId) throws Exception{

        //获取订单推荐关联关系
        RecommendationAssociationEntity firstRA = mRAService.queryByTradeId(brandIdenty,shopIdenty,tradeId);
        //如不为空这表示，该订单是好友推荐下单
        if(firstRA != null){
            //获取红包发放规则
            ActivityRedPacketsEntity arpEntity = new ActivityRedPacketsEntity();
            arpEntity.setBrandIdenty(brandIdenty);
            arpEntity.setShopIdenty(shopIdenty);
            arpEntity.setActivityId(firstRA.getActivityId());
            arpEntity = mARPService.queryRule(arpEntity);

            //算出一级推荐成单红包
            double firstAmount = ToolsUtil.getRandomData(arpEntity.getFirstMinAmount().doubleValue(),arpEntity.getFirstMaxAmount().doubleValue());
            //构建一级推荐红包发放记录
            RedPacketsRecordEntity firstRPEntity = buildRedPacketsRecord(firstRA,firstAmount,1);

            //插入红包发放记录
            insert(firstRPEntity);

            //添加提成信息
            ExpandedCommissionEntity firstEC= buildCommission(brandIdenty,shopIdenty,tradeId,firstRA.getMainCustomerId());
            mExpandedCommissionService.addRedPacketsCommission(firstEC, new BigDecimal(firstAmount));

            //获取间接推荐用户信息
            RecommendationAssociationEntity raEntity = new RecommendationAssociationEntity();
            raEntity.setShopIdenty(shopIdenty);
            raEntity.setBrandIdenty(brandIdenty);
            raEntity.setActivityId(firstRA.getActivityId());
            raEntity.setAcceptWxOpenId(firstRA.getMainWxOpenId());

            RecommendationAssociationEntity secondRA = mRAService.queryMainCustomer(raEntity);

            //获取间接推荐者，如null则表示没有间接推荐者
            if(secondRA != null){
                //算出二级推广成单红包金额
                double secondAmount = ToolsUtil.getRandomData(arpEntity.getFirstMinAmount().doubleValue(),arpEntity.getFirstMaxAmount().doubleValue());

                RedPacketsRecordEntity secondRPEntity = buildRedPacketsRecord(secondRA,secondAmount,2);

                //插入红包发放记录
                insert(secondRPEntity);

                //添加提成信息
                ExpandedCommissionEntity secondEC= buildCommission(brandIdenty,shopIdenty,tradeId,secondRA.getMainCustomerId());
                mExpandedCommissionService.addRedPacketsCommission(secondEC, new BigDecimal(firstAmount));
            }

            //修改操作为购买成功
            RecommendationAssociationEntity entity = new RecommendationAssociationEntity();
            entity.setBrandIdenty(brandIdenty);
            entity.setShopIdenty(shopIdenty);
            entity.setActivityId(firstRA.getActivityId());
            entity.setTradeId(firstRA.getTradeId());
            entity.setTransactionStatus(2);
            mRAService.modfityAssciation(entity);
        }

        return true;
    }

    /**
     * 构建红包发放记录对象
     * @return
     */
    public RedPacketsRecordEntity buildRedPacketsRecord(RecommendationAssociationEntity raEntity,double amount,int sourcId){
        RedPacketsRecordEntity entity = new RedPacketsRecordEntity();
        entity.setWxOpenId(raEntity.getMainWxOpenId());
        entity.setCustomerName(raEntity.getMainCustomerName());
        entity.setWxPhoto(raEntity.getMainWxPhoto());
        entity.setAmount(new BigDecimal(amount));
        entity.setActivityId(raEntity.getActivityId());
        entity.setTradeId(raEntity.getTradeId());
        entity.setSource(sourcId);
        entity.setStatusFlag(1);
        entity.setBrandIdenty(raEntity.getBrandIdenty());
        entity.setShopIdenty(raEntity.getShopIdenty());
        entity.setServerCreateTime(new Date());
        entity.setServerUpdateTime(new Date());

        return entity;
    }

    public ExpandedCommissionEntity buildCommission(Long brandIdenty,Long shopIdenty,Long tradeId,Long customerId){
        ExpandedCommissionEntity mExpandedCommission = new ExpandedCommissionEntity();

        mExpandedCommission.setBrandIdenty(brandIdenty);
        mExpandedCommission.setShopIdenty(shopIdenty);
        mExpandedCommission.setCustomerId(customerId);
        mExpandedCommission.setChangeSalesAmount(BigDecimal.ZERO);//单次消费金额
        mExpandedCommission.setCommissionRatio(BigDecimal.ZERO);//当前提成比例
        mExpandedCommission.setTradeId(tradeId);
        mExpandedCommission.setStatusFlag(1);

        return mExpandedCommission;
    }
}