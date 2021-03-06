package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.ActivityRedPacketsEntity;
import com.zhongmei.yunfu.domain.entity.RecommendationAssociationEntity;

import java.util.List;

/**
 * <p>
 * 特价活动 关联关系
 * </p>
 */
public interface RecommendationAssociationService extends IService<RecommendationAssociationEntity> {

    /**
     * 添加关联关系
     * @param entity
     * @return
     * @throws Exception
     */
    Boolean addAssociation(RecommendationAssociationEntity entity)throws  Exception;

    /**
     * 查询推荐关系
     * @param entity
     * @return
     * @throws Exception
     */
    List<RecommendationAssociationEntity> queryListAssociation(RecommendationAssociationEntity entity)throws Exception;

    /**
     * 获取顾客对应该活动的推荐者
     * @param entity
     * @return
     * @throws Exception
     */
    RecommendationAssociationEntity queryMainCustomer(RecommendationAssociationEntity entity)throws Exception;

    /**
     * 根据订单tradeId获取推荐关系
     * @param brandIdenty
     * @param shopIdenty
     * @param tradeId
     * @return
     * @throws Exception
     */
    RecommendationAssociationEntity queryByTradeId(Long brandIdenty,Long shopIdenty,Long tradeId)throws Exception;

    /**
     * 查询会员推荐成单信息
     * @param entity
     * @return
     * @throws Exception
     */
    Page<RecommendationAssociationEntity> queryRAByCustomer(RecommendationAssociationEntity entity,Integer pageNo,Integer pageSize)throws Exception;

    /**
     * 更新推荐状态
     * @param entity
     * @return
     * @throws Exception
     */
    Boolean modfityAssciation(RecommendationAssociationEntity entity)throws Exception;

    /**
     * 查询用户直接推荐成单数
     * @param entity
     * @return
     * @throws Exception
     */
    Integer queryUserTradeCount(RecommendationAssociationEntity entity)throws Exception;

    /**
     * 根据活动id删除对应的推荐信息
     * @param activityId
     * @return
     * @throws Exception
     */
    Boolean deleteByActivity(Long activityId)throws Exception;

}
