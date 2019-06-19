package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.SupplierEntity;

import java.util.List;

/**
 * <p>
 * 菜品属性表 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
public interface SupplierService extends IService<SupplierEntity> {

    /**
     * 查询门店下所以的供货厂商
     * @param mSupplierEntity
     * @return
     * @throws Exception
     */
    List<SupplierEntity> querySupplier(SupplierEntity mSupplierEntity)throws Exception;

}
