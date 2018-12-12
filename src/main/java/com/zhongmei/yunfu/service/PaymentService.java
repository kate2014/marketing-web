package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.PaymentEntity;

import java.util.List;


public interface PaymentService extends IService<PaymentEntity> {

    /**
     * 插入支付信息
     * @param mPaymentEntity
     * @return
     */
    Boolean installPayment(PaymentEntity mPaymentEntity) throws Exception;

    /**
     * 修改支付信息
     * @param mPaymentEntity
     * @return
     */
    Boolean updatePayment(PaymentEntity mPaymentEntity) throws Exception;

    /**
     * 查询订单的支付信息
     * @param tradeId
     * @return
     */
    List<PaymentEntity> queryPayment(Long tradeId) throws Exception;

    /**
     *
     * @return
     * @throws Exception
     */
    PaymentEntity queryPaymentByItemId(Long paymentItemId)throws Exception;

    /**
     * 根据订单好查询支付记录
     * @param tradeId
     * @return
     * @throws Exception
     */
    PaymentEntity queryPaymentByTradeId(Long tradeId) throws Exception;

    /**
     * 根据trade_id删除支付payment
     * @param tradeId
     * @return
     * @throws Exception
     */
    Boolean deleteByTradeId(Long tradeId) throws Exception;
}
