package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.CustomerCardTimeExpendEntity;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.enums.RecordType;

import java.util.List;

/**
 * <p>
 * 会员次卡消费表 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-02
 */
public interface CustomerCardTimeExpendService extends IService<CustomerCardTimeExpendEntity> {

    /**
     * 根据订单获取交易记录
     *
     * @param tradeId
     * @return
     */
    List<CustomerCardTimeExpendEntity> findByTradeId(Long tradeId, RecordType recordType);
}
