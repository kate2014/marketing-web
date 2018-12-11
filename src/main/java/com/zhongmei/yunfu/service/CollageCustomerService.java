package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.CollageCustomerModel;
import com.zhongmei.yunfu.domain.entity.CollageCustomerEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 会员发起拼团记录 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-14
 */
public interface CollageCustomerService extends IService<CollageCustomerEntity> {

    /**
     * 发起拼团活动
     *
     * @param mCollageCustomer
     * @return
     */
    CollageCustomerEntity installCollageCustomer(CollageCustomerEntity mCollageCustomer) throws Exception;

    /**
     * 参与拼团
     * @param mCollageCustomer
     * @return
     * @throws Exception
     */
    CollageCustomerEntity joinCollage(CollageCustomerEntity mCollageCustomer) throws Exception;

    /**
     * 查询拼团活动
     * @return
     * @throws Exception
     */
    CollageCustomerEntity queryCollage(Long collageId) throws Exception;

    /**
     * 根据订单Id查询拼团活动
     * @param tradeId
     * @return
     * @throws Exception
     */
    CollageCustomerEntity queryCollageByTradeId(Long tradeId) throws Exception;

    /**
     * 更改参与的拼团活动信息
     *
     * @param mCollageCustomer
     * @return
     */
    Boolean updateCollageCustomer(CollageCustomerEntity mCollageCustomer) throws Exception;

    /**
     * 获取会员参与的拼团活动
     * @param mCollageCustomer
     * @return
     * @throws Exception
     */
    List<CollageCustomerModel> queryCollageByCustomer(CollageCustomerEntity mCollageCustomer)throws Exception;

    /**
     * 获取会员参与的该活动信息
     * @param mCollageCustomer
     * @return
     * @throws Exception
     */
    CollageCustomerEntity queryCollageByCustomerId(CollageCustomerEntity mCollageCustomer)throws Exception;

    /**
     * 查询参与该团的所以信息
     * @param relationId
     * @return
     * @throws Exception
     */
    List<CollageCustomerEntity> queryCollageByRelationId(Long relationId,Integer state)throws Exception;

    /**
     * 修改参团信息
     * @param relationId
     * @return
     * @throws Exception
     */
    Boolean updateCollageByRelationId(Long relationId,CollageCustomerEntity mCollageCustomerEntity)throws Exception;
}
