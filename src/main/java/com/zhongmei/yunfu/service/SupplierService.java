package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
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

    /**
     * 分页查询供货商列表
     * @param mSupplierEntity
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<SupplierEntity> querySupplierPage(SupplierEntity mSupplierEntity,int pageNo,int pageSize)throws Exception;

    /**
     * 根据Id删除供货来源
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSupplierById(Long id)throws Exception;

    /**
     * 添加供货来源
     * @param mSupplierEntity
     * @return
     * @throws Exception
     */
    boolean addOrUpdateSupplier(SupplierEntity mSupplierEntity)throws Exception;
}
