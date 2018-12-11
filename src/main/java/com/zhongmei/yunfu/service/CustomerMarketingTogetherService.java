package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.CustomerMTModel;
import com.zhongmei.yunfu.domain.entity.CustomerMarketingTogetherEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 会员关联同行特惠 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
public interface CustomerMarketingTogetherService extends IService<CustomerMarketingTogetherEntity> {

    /**
     * 插入会员发起的同行特惠
     *
     * @param mCustomerMTModel
     * @return
     */
    CustomerMTModel installCMT(CustomerMTModel mCustomerMTModel) throws Exception;

    /**
     * 修改会员发起的同行特惠
     *
     * @param mCustomerMTModel
     * @return
     */
    Boolean modfiyCMT(CustomerMTModel mCustomerMTModel) throws Exception;

    /**
     * 查询会员发起的同行特惠
     *
     * @param mCustomerMTModel
     * @return
     */
    List<CustomerMarketingTogetherEntity> findCMTList(CustomerMTModel mCustomerMTModel) throws Exception;

    /**
     * 根据同行批次号获取同行信息
     *
     * @return
     * @throws Exception
     */
    CustomerMarketingTogetherEntity queryCMTByBatchCode(String batchCode) throws Exception;

    /**
     * 查询受邀同行特惠数据
     *
     * @param mCustomerMTModel
     * @return
     */
    List<CustomerMarketingTogetherEntity> findCMTInvited(CustomerMTModel mCustomerMTModel) throws Exception;

    /**
     * 查询会员发起的某次同行
     *
     * @param mCustomerMTModel
     * @return
     */
    CustomerMarketingTogetherEntity findCMTById(CustomerMTModel mCustomerMTModel);

    /**
     * 核销同行特惠验证
     *
     * @param mCustomerMTModel
     * @return
     */
    Boolean writeOffCheck(CustomerMTModel mCustomerMTModel) throws Exception;
}
