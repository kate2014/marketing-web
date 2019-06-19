package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.PurchaseAndSaleEntity;
import com.zhongmei.yunfu.domain.entity.SupplierEntity;
import com.zhongmei.yunfu.domain.mapper.PurchaseAndSaleMapper;
import com.zhongmei.yunfu.domain.mapper.SupplierMapper;
import com.zhongmei.yunfu.service.PurchaseAndSaleService;
import com.zhongmei.yunfu.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseAndSaleServiceImpl extends ServiceImpl<PurchaseAndSaleMapper, PurchaseAndSaleEntity> implements PurchaseAndSaleService {


    @Override
    public boolean addPurchaseAndSale(PurchaseAndSaleEntity mPurchaseAndSaleEntity) throws Exception {

        return insert(mPurchaseAndSaleEntity);
    }
}
