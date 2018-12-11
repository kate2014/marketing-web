package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.TradeUserEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 订单用户关联表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
public interface TradeUserService extends IService<TradeUserEntity> {

    /**
     * 根据订单id查询TradeUserEntity
     * @param tradeId
     * @return
     * @throws Exception
     */
    public List<TradeUserEntity> queryDataByTradeId(Long tradeId) throws Exception;
}
