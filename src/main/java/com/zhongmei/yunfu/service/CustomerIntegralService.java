package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.api.pos.vo.CustomerIntegralTradeReq;
import com.zhongmei.yunfu.domain.entity.CustomerIntegralEntity;

/**
 * <p>
 * 会员积分表 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-02
 */
public interface CustomerIntegralService extends IService<CustomerIntegralEntity> {

    /**
     * 验证积是否满足抵扣
     *
     * @param customerId
     * @param integral
     * @return
     */
    void checkIntegral(Long customerId, Long integral) throws Exception;

    /**
     * 积分收入
     *
     * @param req
     * @throws Exception
     */
    void income(CustomerIntegralTradeReq req) throws Exception;

    /**
     * 积分退回（反核销）
     *
     * @param req
     * @throws Exception
     */
    void refundIncome(CustomerIntegralTradeReq req) throws Exception;

    /**
     * 积分支出（核销）
     *
     * @param req
     * @throws Exception
     */
    void expend(CustomerIntegralTradeReq req) throws Exception;

    /**
     * 消费积分退回
     *
     * @param req
     * @throws Exception
     */
    void refundExpend(CustomerIntegralTradeReq req) throws Exception;
}
