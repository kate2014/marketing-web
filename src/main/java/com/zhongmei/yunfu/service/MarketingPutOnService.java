package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.MarketingPutOnEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 优惠券投放 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface MarketingPutOnService extends IService<MarketingPutOnEntity> {

    /**
     * 获取门店自动推送优惠券设置
     *
     * @param brandIdentity
     * @param shopIdentity
     * @return
     */
    List<MarketingPutOnEntity> queryMarketingPutOn(Long brandIdentity, Long shopIdentity);

    /**
     * 根据投放类型获取投放信息
     * @param brandIdentity
     * @param shopIdentity
     * @param palnId
     * @return
     */
    MarketingPutOnEntity queryMarketingPutOnByType(Long brandIdentity, Long shopIdentity,Integer palnId);
    /**
     * 添加自动推送
     *
     * @param mMarketingPutOn
     * @return
     */
    Boolean addMarketingPutOn(MarketingPutOnEntity mMarketingPutOn);

    /**
     * 修改优惠券推送
     *
     * @param mMarketingPutOn
     * @return
     */
    Boolean updateMarketingPutOn(MarketingPutOnEntity mMarketingPutOn);
}
