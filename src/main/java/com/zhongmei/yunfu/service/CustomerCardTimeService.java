package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.api.internal.vo.CustomerCardTimeBuyReq;
import com.zhongmei.yunfu.api.internal.vo.CustomerCardTimeExpenseReq;
import com.zhongmei.yunfu.api.internal.vo.CustomerCardTimeRefundReq;
import com.zhongmei.yunfu.domain.entity.CustomerCardTimeEntity;

import java.util.List;

/**
 * <p>
 * 会员次卡表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
public interface CustomerCardTimeService extends IService<CustomerCardTimeEntity> {

    /**
     * 获取次卡信息
     *
     * @param id
     * @param isCheckState
     * @return
     * @throws Exception
     */
    CustomerCardTimeEntity getCustomerCardTimeEntity(Long id, boolean isCheckState) throws Exception;

    /**
     * 根据会员ID获取次卡服务
     *
     * @param customerId
     * @return
     */
    List<CustomerCardTimeEntity> getListPageByCustomerId(Long customerId);

    /**
     * 根据会员ID获取有效的次卡
     *
     * @param customerId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<CustomerCardTimeEntity> getCardValidByCustomerId(Long customerId, Integer pageNo, Integer pageSize);

    /**
     * 购买次卡
     *
     * @param req
     */
    void buy(CustomerCardTimeBuyReq req) throws Exception;

    /**
     * 消费次卡，核销次卡
     */
    void expense(CustomerCardTimeExpenseReq req) throws Exception;

    /**
     * 退回次卡，反核销
     *
     * @param req
     */
    void refund(CustomerCardTimeRefundReq req) throws Exception;

    /**
     * 根据会员ID获取优惠券数
     *
     * @param customerId
     * @return
     */
    int selectCount(Long customerId, Long shopId);
}
