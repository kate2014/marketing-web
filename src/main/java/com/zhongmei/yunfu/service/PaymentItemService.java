package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.controller.model.PaymentItemModel;
import com.zhongmei.yunfu.domain.entity.PaymentEntity;
import com.zhongmei.yunfu.domain.entity.PaymentItemEntity;

import java.util.List;


public interface PaymentItemService extends IService<PaymentItemEntity> {

    /**
     * 插入支付信息
     * @param mPaymentItemEntity
     * @return
     */
    Boolean installPaymentItem(PaymentItemEntity mPaymentItemEntity) throws Exception;

    /**
     * 修改支付信息
     * @param mPaymentItemEntity
     * @return
     */
    Boolean updatePaymentItem(PaymentItemEntity mPaymentItemEntity) throws Exception;

    /**
     * 查询订单的支付信息
     * @param paymentId
     * @return
     */
    List<PaymentItemEntity> queryPaymentItem(Long paymentId) throws Exception;

    /**
     * 获取支付报表
     * @param mPaymentItemModel
     * @return
     */
    List<PaymentItemModel> queryPaymentItemReport(PaymentItemModel mPaymentItemModel) throws Exception;

    /**
     * 根据订单查询支付信息
     * @param tradeId
     * @return
     * @throws Exception
     */
    PaymentItemEntity queryPaymentItemByTradeId(Long tradeId) throws Exception;

    /**
     * 根据订单查询支付信息
     * @param tradeId
     * @return
     * @throws Exception
     */
    List<PaymentItemEntity> queryPaymentItemsByTradeId(Long tradeId) throws Exception;
    /**
     * 根据支付信息id查询支付信息
     * @param id
     * @return
     * @throws Exception
     */
    PaymentItemEntity queryPaymentItemById(Long id) throws Exception;

    /**
     * 发起退款
     * @param returnPaymentItemEntity
     * @param outRefundNo
     * @return
     * @throws Exception
     */
    String retrunPayment(Long outRefundNo,PaymentItemEntity returnPaymentItemEntity,Long tradeId)throws Exception;

    /**
     * 刷新退款状态
     * @param returnPaymentItemEntity
     * @return
     * @throws Exception
     */
    PaymentItemEntity refundquery(PaymentItemEntity returnPaymentItemEntity,Long tradeId)throws Exception;
}
