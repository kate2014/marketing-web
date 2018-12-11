package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.CustomerStoredEntity;

/**
 * <p>
 * 会员储值、储值消费记录表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
public interface CustomerStoredService extends IService<CustomerStoredEntity> {

    void recharge(CustomerStoredEntity customerStored) throws Exception;

    void expense(CustomerStoredEntity customerStored) throws Exception;

    void refund(CustomerStoredEntity customerStored) throws Exception;
}
