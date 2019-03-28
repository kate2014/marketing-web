package com.zhongmei.yunfu.api.web;

import com.zhongmei.yunfu.controller.model.LoadingModel;
import com.zhongmei.yunfu.controller.model.PaymentItemModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.DishReport;
import com.zhongmei.yunfu.domain.entity.TradeEntity;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/internal/request")
public class LoadingAuthController {

    @RequestMapping("/loading")
    public String reportAddCustomer(Model model, LoadingModel mLoadingModel) {

        if(mLoadingModel.getRequestUrlType() == 1){
            String url = "/internal/sales/salesReport?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 2){
            String url = "/internal/dish/dishReport?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 3){
            String url = "/internal/report/customerReport?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 4){
            String url = "/internal/marketingReport/marketing?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 5){
            String url = "/internal/marketingReport/coupon?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 6){
            String url = "/internal/bookingReport/booking?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 7){
            String url = "/internal/cardTimeReport/cardTime?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 8){
            String url = "/internal/saveReport/customerSave?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty();
            mLoadingModel.setRequestUrl(url);

        }

        model.addAttribute("mLoadingModel", mLoadingModel);
        return "loading";
    }





}
