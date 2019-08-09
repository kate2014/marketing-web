package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CommissionSearchModel;
import com.zhongmei.yunfu.domain.entity.ExpandedCommissionEntity;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 推广提成 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-16
 */
public interface ExpandedCommissionService extends IService<ExpandedCommissionEntity> {

    /**
     * 会员提成累加
     *
     * @param mExpandedCommission
     * @return
     * @throws Exception
     */
    Boolean addExpandedCommission(ExpandedCommissionEntity mExpandedCommission) throws Exception;

    /**
     * 扣减提成，当提成订单退货是需要退回该比提成
     * @param mExpandedCommission
     * @return
     * @throws Exception
     */
    Boolean subtractCommission(ExpandedCommissionEntity mExpandedCommission) throws Exception;
    /**
     * 兑换提成
     *
     * @param mExpandedCommission
     * @return
     * @throws Exception
     */
    Boolean exchangeExpandedCommission(ExpandedCommissionEntity mExpandedCommission) throws Exception;

    /**
     * 获取用户的推广提成信息
     *
     * @param mExpandedCommission
     * @return
     * @throws Exception
     */
    ExpandedCommissionEntity queryNewCommission(ExpandedCommissionEntity mExpandedCommission) throws Exception;

    /**
     * 根据订单查询提成信息
     * @param mExpandedCommission
     * @return
     * @throws Exception
     */
    ExpandedCommissionEntity queryCommissionByTradeId(ExpandedCommissionEntity mExpandedCommission) throws Exception;

    /**
     * 获取门店推广消费提成信息
     * @param mCommissionSearchModel
     * @return
     * @throws Exception
     */
    Page<CommissionSearchModel> queryListCommission(CommissionSearchModel mCommissionSearchModel) throws Exception;

    /**
     * 添加会员返点获取额度
     * @param mExpandedCommission
     * @param amount
     * @return
     * @throws Exception
     */
    Boolean addRedPacketsCommission(ExpandedCommissionEntity mExpandedCommission, BigDecimal amount) throws Exception;
}
