package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.controller.model.FeedbackModel;
import com.zhongmei.yunfu.domain.entity.FeedbackEntity;

import java.util.List;

/**
 * 用户反馈
 */
public interface FeedbackService extends IService<FeedbackEntity> {

    /**
     * 添加反馈
     * @param mFeedbackEntity
     * @return
     * @throws Exception
     */
    boolean addFeedback(FeedbackEntity mFeedbackEntity)throws Exception;

    /**
     * 分页查询顾客反馈，不包含门店回复
     * @param mFeedbackModel
     * @return
     * @throws Exception
     */
    Page<FeedbackEntity> queryFeedbackPage(FeedbackModel mFeedbackModel)throws Exception;

    /**
     * 查询反馈列表
     * @param mFeedbackEntity
     * @return
     * @throws Exception
     */
    List<FeedbackEntity> queryFeedbackList(FeedbackEntity mFeedbackEntity)throws Exception;

    /**
     * 根据tradeId查询评论
     * @param brandIdenty
     * @param shopIdenty
     * @param tradeId
     * @return
     * @throws Exception
     */
    List<FeedbackEntity> queryFeedbackByTradeId(Long brandIdenty,Long shopIdenty,Long tradeId)throws Exception;

    /**
     * 更新评价
     * @param mFeedbackEntity
     * @return
     * @throws Exception
     */
    boolean midfityFeedback(FeedbackEntity mFeedbackEntity)throws Exception;
}
