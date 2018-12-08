package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.TradePrivilegeEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 优惠信息 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
public interface TradePrivilegeService extends IService<TradePrivilegeEntity> {

    /**
     * 根据订单信息查询订单相关优惠
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param tradeId
     * @return
     */
    List<TradePrivilegeEntity> queryPrivilegeByTradeId(Long brandIdenty, Long shopIdenty, Long tradeId);

    /**
     * 修改订单中优惠的核销状态
     *
     * @param tradePrivilegeId
     * @return
     */
    Boolean modifyUseStatus(Long tradePrivilegeId, Integer useStatus);
}
