package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.CustomerEntityCardEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pigeon88
 * @since 2019-05-20
 */
public interface CustomerEntityCardService extends IService<CustomerEntityCardEntity> {

    List<CustomerEntityCardEntity> getByCustomerId(Long customerId);
}
