package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.CustomerSaveReport;
import com.zhongmei.yunfu.domain.entity.CustomerStoredEntity;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员储值、储值消费记录表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
public interface CustomerStoredService extends IService<CustomerStoredEntity> {

    CustomerStoredEntity getByPaymentItemId(Long shopId, Long tradeId, Long paymentItemId);

    void recharge(CustomerStoredEntity customerStored) throws Exception;

    void expense(CustomerStoredEntity customerStored) throws Exception;

    void refund(CustomerStoredEntity customerStored) throws Exception;

    CustomerStoredEntity queryByTradeId(Long tradeId) throws Exception;

    /**
     * 查询一个时间段内门店的储值消费情况
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    List<CustomerSaveReport> querySaveData(TradeModel mTradeModel)throws Exception;


}
