package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.ExpandedMarketingModel;
import com.zhongmei.yunfu.domain.entity.MarketingExpandedEntity;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 全员推广营销 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-26
 */
public interface MarketingExpandedService extends IService<MarketingExpandedEntity> {

    /**
     * 获取门店全员推广活动信息
     *
     * @param expandedMarketingModel
     * @return
     */
    MarketingExpandedEntity findMarketingExpanded(ExpandedMarketingModel expandedMarketingModel);

    /**
     * 根据方案id获取方案信息
     *
     * @param mMarketingExpanded
     * @return
     */
    MarketingExpandedEntity queryMarketingExpanded(MarketingExpandedEntity mMarketingExpanded);

    /**
     * 新建全员推广
     *
     * @param mMarketingExpanded
     * @return
     */
    public Boolean addMarketingExpanded(MarketingExpandedEntity mMarketingExpanded);

    /**
     * 添加/修改全员推广
     *
     * @return
     */
    Boolean editMarketingExpanded(MarketingExpandedEntity mMarketingExpanded);

}
