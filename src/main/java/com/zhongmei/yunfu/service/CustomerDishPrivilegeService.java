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

    /**
     * 批量插入或修改
     * @param listData
     * @return
     * @throws Exception
     */
    boolean batchInstallOrUpdate(List<CustomerDishPrivilegeEntity> listData)throws Exception;

    /**
     * 批量删除
     * @param brandIdenty
     * @param shopIdenty
     * @param ids
     * @return
     * @throws Exception
     */
    boolean batchDeleteById(Long brandIdenty,Long shopIdenty,String ids)throws Exception;

    /**
     * 删除门店下所有该类数据
     * @param brandIdenty
     * @param shopIdenty
     * @return
     * @throws Exception
     */
    boolean deleteAllForShop(Long brandIdenty,Long shopIdenty)throws Exception;
    /**
     * 批量添加
     * @param listData
     * @return
     * @throws Exception
     */
    boolean batchInstall(List<CustomerDishPrivilegeEntity> listData)throws Exception;
}
