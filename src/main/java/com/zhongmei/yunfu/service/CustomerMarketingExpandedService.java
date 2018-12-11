package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.CustomerMarketingExpandedEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 会员关联推广回馈 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
public interface CustomerMarketingExpandedService extends IService<CustomerMarketingExpandedEntity> {

    /**
     * 添加会员发起的推广
     *
     * @param mCustomerMarketingExpanded
     * @return
     * @throws Exception
     */
    Boolean addCustomerExpanded(CustomerMarketingExpandedEntity mCustomerMarketingExpanded) throws Exception;

    /**
     * 处理推广操作
     *
     * @param mCustomerMarketingExpanded
     * @return
     * @throws Exception
     */
    Boolean modifyExpanded(CustomerMarketingExpandedEntity mCustomerMarketingExpanded) throws Exception;

    /**
     * 查询该用户是否已被推广过
     *
     * @param mCustomerMarketingExpanded
     * @return
     * @throws Exception
     */
    CustomerMarketingExpandedEntity checkHadExpandedCustomer(CustomerMarketingExpandedEntity mCustomerMarketingExpanded) throws Exception;

    /**
     * 获取我的推广
     *
     * @param mCustomerMarketingExpanded
     * @return
     * @throws Exception
     */
    List<CustomerMarketingExpandedEntity> queryExpanded(CustomerMarketingExpandedEntity mCustomerMarketingExpanded) throws Exception;

}
