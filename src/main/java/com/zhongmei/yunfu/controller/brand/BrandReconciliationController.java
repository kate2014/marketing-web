package com.zhongmei.yunfu.controller.brand;

import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.PurchSaleModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.ShopSalesReport;
import com.zhongmei.yunfu.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 对账报表
 */
@Controller
@RequestMapping("/internal/brand/report")
public class BrandReconciliationController extends BaseController {

    @Autowired
    TradeService mTradeService;

    @RequestMapping("/reconciliation")
    public String reportReconciliation(Model model, PurchSaleModel mPurchSaleModel) {


        mPurchSaleModel.setSearchDate(1);
        mPurchSaleModel.setSearchType(0);



        model.addAttribute("mPurchSaleModel", mPurchSaleModel);
        return "brand_report_reconciliation";
    }

}
