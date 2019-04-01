package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.model.PurchSaleModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.domain.mapper.PurchaseSaleMapper;
import com.zhongmei.yunfu.service.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseSaleServiceImpl extends ServiceImpl<PurchaseSaleMapper, PurchaseAndSaleEntity> implements PurchasSaleService {


    @Override
    public List<PurchaseAndSaleEntity> queryPurchase(PurchSaleModel mPurchSaleModel) throws Exception {

        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("shop_identy", mPurchSaleModel.getShopIdenty());
        eWrapper.eq("brand_identy", mPurchSaleModel.getBrandIdenty());
        if(mPurchSaleModel.getType() != null){
            eWrapper.eq("type",mPurchSaleModel.getType());
        }
        if(mPurchSaleModel.getSourceId() != null){
            eWrapper.eq("source_id",mPurchSaleModel.getSourceId());
        }
        eWrapper.between("server_create_time", mPurchSaleModel.getStartDate(), mPurchSaleModel.getEndDate());
        List<PurchaseAndSaleEntity> listData = baseMapper.listPurchaseAndSale(eWrapper);
        return listData;
    }

    @Override
    public List<PurchaseAndSaleEntity> queryPurchaseDetail(PurchSaleModel mPurchSaleModel) throws Exception {

        EntityWrapper<PurchaseAndSaleEntity> eWrapper = new EntityWrapper<>(new PurchaseAndSaleEntity());
        eWrapper.eq("shop_identy", mPurchSaleModel.getShopIdenty());
        eWrapper.eq("brand_identy", mPurchSaleModel.getBrandIdenty());
        eWrapper.eq("dish_shop_id", mPurchSaleModel.getDishShopId());
        eWrapper.between("server_create_time", mPurchSaleModel.getStartDate(), mPurchSaleModel.getEndDate());
        eWrapper.orderBy("server_create_time",false);

        List<PurchaseAndSaleEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public List<PurchaseSaleDetailReport> listPurchaseAndSaleDetail(PurchSaleModel mPurchSaleModel) throws Exception {

        Condition eWrapper = ConditionFilter.create();

        eWrapper.eq("shop_identy", mPurchSaleModel.getShopIdenty());
        eWrapper.eq("brand_identy", mPurchSaleModel.getBrandIdenty());
        eWrapper.eq("dish_shop_id", mPurchSaleModel.getDishShopId());
        eWrapper.between("server_create_time", mPurchSaleModel.getStartDate(), mPurchSaleModel.getEndDate());

        EntityWrapper<PurchaseAndSaleEntity> eWrapper1 = new EntityWrapper<>(new PurchaseAndSaleEntity());
        eWrapper1.eq("shop_identy", mPurchSaleModel.getShopIdenty());
        eWrapper1.eq("brand_identy", mPurchSaleModel.getBrandIdenty());
        eWrapper1.eq("dish_shop_id", mPurchSaleModel.getDishShopId());
        eWrapper1.between("server_create_time", mPurchSaleModel.getStartDate(), mPurchSaleModel.getEndDate());

        List<PurchaseSaleDetailReport> listData = baseMapper.listPurchaseAndSaleDetail(eWrapper);
        return listData;
    }

    @Override
    public List<PurchaseSaleReport> purchaseList(PurchSaleModel mPurchSaleModel) throws Exception {

        Condition eWrapper = ConditionFilter.create();

        eWrapper.eq("p.shop_identy", mPurchSaleModel.getShopIdenty());
        eWrapper.eq("p.brand_identy", mPurchSaleModel.getBrandIdenty());
        eWrapper.like("p.source_name", mPurchSaleModel.getSourceName());
        eWrapper.eq("p.type", 1);//1：进货 2：销货
        eWrapper.like("d.name", mPurchSaleModel.getDishName());

        eWrapper.between("p.server_create_time", mPurchSaleModel.getStartDate(), mPurchSaleModel.getEndDate());
        List<PurchaseSaleReport> listData = baseMapper.purchaseList(eWrapper);
        return listData;
    }
}
