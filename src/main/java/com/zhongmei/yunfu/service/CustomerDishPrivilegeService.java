package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.CustomerDishPrivilegeEntity;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;

import java.util.List;

public interface CustomerDishPrivilegeService extends IService<CustomerDishPrivilegeEntity> {

    /**
     * 获取会员等级对应的特价商品
     * @param mCustomerDishPrivilegeEntity
     * @return
     * @throws Exception
     */
    List<CustomerDishPrivilegeEntity> queryDishPrivilege(CustomerDishPrivilegeEntity mCustomerDishPrivilegeEntity)throws Exception;
}
