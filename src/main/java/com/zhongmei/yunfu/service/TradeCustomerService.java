package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.TradeCustomerEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 交易的顾客信息 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
public interface TradeCustomerService extends IService<TradeCustomerEntity> {

    /**
     * 添加订单关联会员
     *
     * @param mTradeCustomer
     * @return
     */
    Boolean installTradeCustomer(TradeCustomerEntity mTradeCustomer) throws Exception;

    /**
     * 根据订单号查询订单会员信息
     *
     * @param tradeId
     * @return
     */
    TradeCustomerEntity queryTradeCustomer(Long tradeId) throws Exception;

    /**
     * 获取会员相关的订单
     *
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    Page<TradeCustomerEntity> queryTradeByCustomer(TradeModel mTradeModel) throws Exception;

    /**
     * 根据订单id删除数据
     * @param tradeId
     * @return
     * @throws Exception
     */
    Boolean deleteByTradeId(Long tradeId) throws Exception;

}
