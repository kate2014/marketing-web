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
public class LoadingController {

    @RequestMapping("/loading")
    public String reportAddCustomer(Model model, LoadingModel mLoadingModel) {

        if(mLoadingModel.getRequestUrlType() == 1){
            String url = "/internal/sales/salesReport?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 2){
            String url = "/internal/dish/dishReport?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 3){
            String url = "/internal/report/customerReport?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 4){
            String url = "/internal/marketingReport/marketing?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 5){
            String url = "/internal/marketingReport/coupon?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 6){
            String url = "/internal/bookingReport/booking?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);

        }

        model.addAttribute("mLoadingModel", mLoadingModel);
        return "loading";
    }





}
