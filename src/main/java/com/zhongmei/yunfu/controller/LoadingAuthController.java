package com.zhongmei.yunfu.controller;

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
            String url = "/internal/sales/saleReport?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 2){
            String url = "/internal/sales/salesReport?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 3){
            String url = "/internal/dish/dishReport?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 4){
            String url = "/internal/cardTimeReport/cardTime?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 5){
            String url = "/internal/saveReport/customerSave?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 6){
            String url = "/internal/report/customerShop?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);
        }else if(mLoadingModel.getRequestUrlType() == 7){
            String url = "/internal/report/userReport?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);
        }else if(mLoadingModel.getRequestUrlType() == 8){
            String url = "/internal/purchaseSale/report?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 9){
            String url = "/internal/purchaseSale/purchase?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 10){
            String url = "/internal/report/customerReport?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 11){
            String url = "/internal/marketingReport/marketing?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 12){
            String url = "/internal/activitySalesReport/activitySales?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 13){
            String url = "/internal/marketingReport/coupon?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 14){
            String url = "/internal/bookingReport/booking?brandIdenty="+mLoadingModel.getBrandIdenty()+"&shopIdenty="+mLoadingModel.getShopIdenty()+"&rquestSource="+mLoadingModel.getRquestSource();
            mLoadingModel.setRequestUrl(url);

        }


        //品牌报表
        else if(mLoadingModel.getRequestUrlType() == 1001){ //营业报表
            String url = "/internal/brand/report/salesReport?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 1002){//每日对账报表
            String url = "/internal/brand/report/reconciliation?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 1003){//门店业绩排行
            String url = "/internal/brand/report/shopOrder?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 1004){//品项销售报表
            String url = "/internal/brand/report/dishReport?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 1005){//储值报表
            String url = "/internal/brand/report/customerSave?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);

        }else if(mLoadingModel.getRequestUrlType() == 1006){//次卡报表
            String url = "/internal/brand/cardTimeReport/cardTime?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);
        }else if(mLoadingModel.getRequestUrlType() == 1007){//会员报表
            String url = "/internal/brand/report/customerReport?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);
        }else if(mLoadingModel.getRequestUrlType() == 1008){//特价活动报表
            String url = "/internal/brand/activitySalesReport/activitySales?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);
        }else if(mLoadingModel.getRequestUrlType() == 1010){//优惠券报表
            String url = "/internal/brand/couponReport/coupon?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);
        }else if(mLoadingModel.getRequestUrlType() == 1011){//预约报表
            String url = "/internal/brand/bookingReport/booking?brandIdenty="+mLoadingModel.getBrandIdenty();
            mLoadingModel.setRequestUrl(url);
        }

        model.addAttribute("mLoadingModel", mLoadingModel);
        return "loading";
    }





}
