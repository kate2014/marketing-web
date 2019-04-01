package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.controller.model.PurchSaleModel;
import com.zhongmei.yunfu.controller.model.ReportSalesExportModel;
import com.zhongmei.yunfu.controller.model.TradeDataModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface PurchasSaleService extends IService<PurchaseAndSaleEntity> {

    /**
     * 获取库存和进货数据
     * @param mPurchSaleModel
     * @return
     * @throws Exception
     */
    List<PurchaseAndSaleEntity> queryPurchase(PurchSaleModel mPurchSaleModel) throws Exception;

    /**
     * 获取品项进货详情
     * @param mPurchSaleModel
     * @return
     * @throws Exception
     */
    List<PurchaseAndSaleEntity> queryPurchaseDetail(PurchSaleModel mPurchSaleModel) throws Exception;

    /**
     * 获取商品进销存详情
     * @param mPurchSaleModel
     * @return
     * @throws Exception
     */
    List<PurchaseSaleDetailReport> listPurchaseAndSaleDetail(PurchSaleModel mPurchSaleModel) throws Exception;
}
