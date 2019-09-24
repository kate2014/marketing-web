package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.controller.model.ExpandedMarketingModel;
import com.zhongmei.yunfu.domain.entity.ExchangeCodeEntity;
import com.zhongmei.yunfu.domain.entity.MarketingExpandedEntity;

/**
 * <p>
 * 提成兑换验证码
 * </p>
 *
 */
public interface ExchangeCodeService extends IService<ExchangeCodeEntity> {

    /**
     * 插入验证编码
     * @param entity
     * @return
     * @throws Exception
     */
    ExchangeCodeEntity queryCode(ExchangeCodeEntity entity)throws Exception;
    /**
     * 插入兑换编码
     * @param entity
     * @return
     * @throws Exception
     */
    ExchangeCodeEntity installCode(ExchangeCodeEntity entity)throws Exception;

    /**
     * 更新验证编码
     * @param entity
     * @return
     * @throws Exception
     */
    ExchangeCodeEntity updateCode(ExchangeCodeEntity entity)throws Exception;

    /**
     * 删除兑换编码
     * @param entity
     * @return
     * @throws Exception
     */
    Boolean deleteCode(ExchangeCodeEntity entity)throws Exception;
}
