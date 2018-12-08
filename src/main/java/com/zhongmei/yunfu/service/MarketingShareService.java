package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.MarketingShareEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 分享营销 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface MarketingShareService extends IService<MarketingShareEntity> {

    /**
     * 获取门店下所有的分享营销
     *
     * @return
     */
    List<MarketingShareEntity> findSharePlanByShopId(Long shopIdentity);

    /**
     * 新建分享营销
     *
     * @param marketingShare
     * @return
     */
    public Boolean addShareMarketing(MarketingShareEntity marketingShare);

    /**
     * 添加/修改分享营销
     *
     * @return
     */
    Boolean editShareMarketing(MarketingShareEntity marketingShare);
}
