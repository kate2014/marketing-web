package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.MarketingTogetherEntity;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 同行特惠 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface MarketingTogetherService extends IService<MarketingTogetherEntity> {

    /**
     * 获取门店同行特惠活动信息
     *
     * @param shopIdentity
     * @return
     */
    MarketingTogetherEntity findMarketingTogether(Long shopIdentity, Integer statusFlag, Integer state);

    /**
     * 根据方案id获取方案信息
     *
     * @param id
     * @return
     */
    MarketingTogetherEntity findMarketingTogetherById(Long id);

    /**
     * 新建分享营销
     *
     * @param mMarketingTogether
     * @return
     */
    public Boolean addShareMarketing(MarketingTogetherEntity mMarketingTogether);

    /**
     * 添加/修改分享营销
     *
     * @return
     */
    Boolean editShareMarketing(MarketingTogetherEntity mMarketingTogether);

    /**
     * 更改分享数量，浏览数量
     *
     * @param id
     * @param scanNumber
     * @param shareNumber
     * @return
     */
    public Boolean updateNumber(Long id, Integer scanNumber, Integer shareNumber);
}
