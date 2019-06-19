package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.PurchaseAndSaleEntity;

import java.util.List;

/**
 * <p>
 * 菜品属性表 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
public interface PurchaseAndSaleService extends IService<PurchaseAndSaleEntity> {

    /**
     * 添加进货、销货数据
     * @param mPurchaseAndSaleEntity
     * @return
     * @throws Exception
     */
    boolean addPurchaseAndSale(PurchaseAndSaleEntity mPurchaseAndSaleEntity)throws Exception;

}
