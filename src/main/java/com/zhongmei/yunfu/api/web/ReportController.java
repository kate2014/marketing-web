package com.zhongmei.yunfu.api.web;

import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import com.zhongmei.yunfu.controller.model.PaymentItemModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.DishReport;
import com.zhongmei.yunfu.domain.entity.TradeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/internal/report")
public class ReportController {

    @Autowired
    TradeService mTradeService;
    @Autowired
    TradeItemService mTradeItemService;
    @Autowired
    PaymentItemService mPaymentItemService;
    @Autowired
    AuthUserService authUserService;
    @Autowired
    CommercialService mCommercialService;

    @RequestMapping("/showEmptyReport")
    public String showEmptyReport(Model model, TradeModel mTradeModel) {

        return "empty_report";
    }

    @RequestMapping("/salesReport")
    public String reportSalse(Model model, TradeModel mTradeModel) {

        createData(model,mTradeModel);

        return "report";
    }

    @RequestMapping("/posReport")
    public String posReport(Model model, TradeModel mTradeModel){
        createData(model,mTradeModel);
        return "pos_report_main";
    }

    public void createData(Model model, TradeModel mTradeModel){
        model.addAttribute("brandIdenty", mTradeModel.getBrandIdenty());
        model.addAttribute("shopIdenty", mTradeModel.getShopIdenty());


        Long shopId = mTradeModel.getShopIdenty();
        Long creatorId = mTradeModel.getCreatorId();
        String creatorName = mTradeModel.getCreatorName();

        if(LoginManager.get().getUser() != null){
            if(creatorId != null && !creatorId.equals("")){
                LoginManager.get().getUser().setCreatorId(creatorId);
            }else{
                creatorId = LoginManager.get().getUser().getCreatorId();
            }

            if(creatorName != null && !creatorName.equals("")){
                LoginManager.get().getUser().setCreatorName(creatorName);
            }

            if(shopId != null && !shopId.equals("")){
                LoginManager.get().getUser().setShopIdenty(shopId);
            }else{
                shopId = LoginManager.get().getUser().getShopIdenty();
            }
        }

        Map<String, String> permissionData = authUserService.getAuthPermissionMap(creatorId,shopId);

        //销售报表
        if(permissionData.get("SALES_REP0RT") == null || permissionData.get("SALES_REP0RT").equals("")){
            model.addAttribute("haveSalesReport", 0);
        }else{
            model.addAttribute("haveSalesReport", 1);
        }

        //品项销售报表
        if(permissionData.get("DISH_REP0RT") == null || permissionData.get("DISH_REP0RT").equals("")){
            model.addAttribute("haveDishReport", 0);
        }else{
            model.addAttribute("haveDishReport", 1);
        }

        //会员报表
        if(permissionData.get("MEMBER_REP0RT") == null || permissionData.get("MEMBER_REP0RT").equals("")){
            model.addAttribute("haveMemberReport", 0);
        }else{
            model.addAttribute("haveMemberReport", 1);
        }

        //场景营销报表
        if(permissionData.get("MARKETING_REP0RT") == null || permissionData.get("MARKETING_REP0RT").equals("")){
            model.addAttribute("haveMarketingReport", 0);
        }else{
            model.addAttribute("haveMarketingReport", 1);
        }

        //优惠券报表
        if(permissionData.get("COUPON_REP0RT") == null || permissionData.get("COUPON_REP0RT").equals("")){
            model.addAttribute("haveCouponReport", 0);
        }else{
            model.addAttribute("haveCouponReport", 1);
        }

        //预约报表
        if(permissionData.get("BOOKING_REP0RT") == null || permissionData.get("BOOKING_REP0RT").equals("")){
            model.addAttribute("haveBookingReport", 0);
        }else{
            model.addAttribute("haveBookingReport", 1);
        }

        //次卡销售报表
        if(permissionData.get("CARD_TIME_REPORT") == null || permissionData.get("CARD_TIME_REPORT").equals("")){
            model.addAttribute("haveCardTimeReport", 0);
        }else{
            model.addAttribute("haveCardTimeReport", 1);
        }

        //会员储值报表
        if(permissionData.get("CUSTOMER_SAVE_REPORT") == null || permissionData.get("CUSTOMER_SAVE_REPORT").equals("")){
            model.addAttribute("haveCustomerSaveReport", 0);
        }else{
            model.addAttribute("haveCustomerSaveReport", 1);
        }

        //品项消耗详情报表
        if(permissionData.get("DISH_SALE_REPORT") == null || permissionData.get("DISH_SALE_REPORT").equals("")){
            model.addAttribute("haveDishSaleDetailReport", 0);
        }else{
            model.addAttribute("haveDishSaleDetailReport", 1);
        }

        //品项库存报表
        if(permissionData.get("DISH_KC_REPORT") == null || permissionData.get("DISH_KC_REPORT").equals("")){
            model.addAttribute("haveDishSaveReport", 0);
        }else{
            model.addAttribute("haveDishSaveReport", 1);
        }

        //品项入库报表
        if(permissionData.get("DISH_RK_ERPORT") == null || permissionData.get("DISH_RK_ERPORT").equals("")){
            model.addAttribute("haveDishPurchaseReport", 0);
        }else{
            model.addAttribute("haveDishPurchaseReport", 1);
        }

        //每日对账报表
        if(permissionData.get("ACCOUNTS_ERPORT") == null || permissionData.get("ACCOUNTS_ERPORT").equals("")){
            model.addAttribute("havaAccountReport", 0);
        }else{
            model.addAttribute("havaAccountReport", 1);
        }

        //员工业绩报表
        if(permissionData.get("STAFF_SALARY_REPORT") == null || permissionData.get("STAFF_SALARY_REPORT").equals("")){
            model.addAttribute("havaStaffSalaryReport", 0);
        }else{
            model.addAttribute("havaStaffSalaryReport", 1);
        }

        //会员到店
        if(permissionData.get("CUSTOMER_SHOP_REPORT") == null || permissionData.get("CUSTOMER_SHOP_REPORT").equals("")){
            model.addAttribute("havaCustomerShopReport", 0);
        }else{
            model.addAttribute("havaCustomerShopReport", 1);
        }
    }

    @RequestMapping("/main")
    public String mianPage(Model model, TradeModel mTradeModel) {
        try {

            Date stateDate = DateFormatUtil.getStartTime();
            Date endDate = new Date();
            mTradeModel.setStartDate(DateFormatUtil.format(stateDate, DateFormatUtil.FORMAT_FULL_DATE));
            mTradeModel.setEndDate(DateFormatUtil.format(endDate, DateFormatUtil.FORMAT_FULL_DATE));
            //获取当天所有订单
            List<TradeEntity> listSales = mTradeService.queryListTrade(mTradeModel);
            //保存产生销货订单时间点
            List<String> listTime = new LinkedList<>();
            //销货订单金额保存
            List<String> listAmount = new LinkedList<>();
            //销售总额
            BigDecimal totalAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            //销售单数
            Integer totalCount = 0;
            //销售总额
            BigDecimal salesAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            //储值单数
            Integer storeCount = 0;
            //储值总额
            BigDecimal storeAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            //次卡售卖单数
            Integer cardTimeCount = 0;
            //储值总额
            BigDecimal cardTimeAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            //退货单数
            Integer returnCount = 0;
            //退款总额
            BigDecimal returnAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            //未收银订单数
            Integer notSales = 0;
            //未收银订金额
            BigDecimal notSalesAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
            //销售总额
            BigDecimal salesTotal = BigDecimal.ZERO;
            //保存产生销货的订单金额
            for (TradeEntity td : listSales) {
                //展示销货单
                if (td.getTradeType() == 1 && td.getTradeStatus() == 4) {
                    listTime.add(DateFormatUtil.format(td.getServerUpdateTime(), DateFormatUtil.FORMAT_FULL_time));
                    listAmount.add(td.getTradeAmount().toString());
                }

                if ((td.getBusinessType() == 1 || td.getBusinessType() == 4)&& td.getTradeStatus() == 4) {//销货单 已支付完成
                    totalCount += 1;
                    salesAmount = salesAmount.add(td.getTradeAmount());

                } else if (td.getBusinessType() == 2 && td.getTradeStatus() == 4) {//余额储值 已支付完成
                    storeCount += 1;
                    storeAmount = storeAmount.add(td.getTradeAmount());
                } else if(td.getBusinessType() == 3 && td.getTradeStatus() == 4){//次卡储值 已支付完成
                    cardTimeCount += 1;
                    cardTimeAmount = cardTimeAmount.add(td.getTradeAmount());
                }else if (td.getTradeType() == 2 && td.getTradeStatus() == 5) {//退货数据信息
                    returnCount += 1;
                    returnAmount = returnAmount.add(td.getTradeAmount());
                } else if (td.getTradeStatus() == 3 && td.getTradePayStatus() == 1) {//已确认，未收银
                    notSales += 1;
                    notSalesAmount = storeAmount.add(td.getTradeAmount());
                }
            }
            salesTotal = salesAmount.add(storeAmount).add(cardTimeAmount).subtract(returnAmount);

            //获取支付方式报表
            queryPaymentReport(model,mTradeModel);

            model.addAttribute("mTradeModel", mTradeModel);
            model.addAttribute("stateDate", DateFormatUtil.format(stateDate, "YYYY-MM-dd HH:mm:ss"));
            model.addAttribute("endDate", DateFormatUtil.format(endDate, "YYYY-MM-dd HH:mm:ss"));


            //销售额
            model.addAttribute("salesTotal", salesTotal);
            model.addAttribute("totalCount", totalCount+storeCount+cardTimeCount+returnCount);
            //销货额
            model.addAttribute("salesAmount", salesAmount);
            model.addAttribute("salesCount", totalCount);
            //次卡、余额储值总额
            model.addAttribute("saveAmount", storeAmount.add(cardTimeAmount));
            model.addAttribute("saveCount", storeCount+cardTimeCount);
            //余额储值
            model.addAttribute("storeCount", storeCount);
            model.addAttribute("storeAmount", storeAmount);
            //次卡
            model.addAttribute("cardTimeCount", cardTimeCount);
            model.addAttribute("cardTimeAmount", cardTimeAmount);
            //退货
            model.addAttribute("returnCount", returnCount);
            model.addAttribute("returnAmount", returnAmount);
            //未支付
            model.addAttribute("notSales", notSales);
            model.addAttribute("notSalesAmount", notSalesAmount);
            //销货总额
            model.addAttribute("saldAmount", salesAmount.add(storeAmount).add(cardTimeAmount));

            model.addAttribute("listTime", listTime);
            model.addAttribute("listAmount", listAmount);


            //商品销售排行榜
            dishSalesReport(model, mTradeModel, stateDate, endDate);

            //获取门店信息
            CommercialEntity mCommercialEntity = mCommercialService.queryCommercialById(mTradeModel.getShopIdenty());
            model.addAttribute("mCommercialEntity", mCommercialEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

        return "main";
    }

    public Model dishSalesReport(Model model, TradeModel mTradeModel, Date start, Date end) throws Exception {

        List<DishReport> listData = mTradeItemService.selectDishSalesReport(mTradeModel.getBrandIdenty(), mTradeModel.getShopIdenty(), start, end);

        List<String> listDishName = new LinkedList<>();
        List<BigDecimal> listSalesAmount = new LinkedList<>();
        List<Long> listSalesCount = new LinkedList<>();
        Long maxCount = 0l;
        Long maxAmount = 0l;
        for (DishReport dp : listData) {
            listDishName.add(dp.getDishName());
            listSalesAmount.add(dp.getSalesAmount());
            listSalesCount.add(dp.getSalseCount());

            if (maxCount < dp.getSalseCount()) {
                maxCount = dp.getSalseCount();
            }
            if (maxAmount < dp.getSalesAmount().longValue()) {
                maxAmount = dp.getSalesAmount().longValue();
            }

        }

        maxCount = ToolsUtil.getMaxData(maxCount);
        maxAmount = ToolsUtil.getMaxData(maxAmount);

        model.addAttribute("maxCount", maxCount);
        model.addAttribute("intervalCount", maxCount / 10);
        model.addAttribute("maxAmount", maxAmount);
        model.addAttribute("intervalAmount", maxAmount / 10);


        model.addAttribute("listDishName", listDishName);
        model.addAttribute("listSalesAmount", listSalesAmount);
        model.addAttribute("listSalesCount", listSalesCount);

        model.addAttribute("listData", listData);

        return model;
    }

    /**
     * 获取支付相关报表
     * @param model
     * @param mTradeModel
     */
    public void queryPaymentReport(Model model, TradeModel mTradeModel) throws Exception{

        PaymentItemModel mPaymentItemModel = new PaymentItemModel();
        mPaymentItemModel.setBrandIdenty(mTradeModel.getBrandIdenty());
        mPaymentItemModel.setShopIdenty(mTradeModel.getShopIdenty());
        mPaymentItemModel.setStartDate(mTradeModel.getStartDate());
        mPaymentItemModel.setEndDate(mTradeModel.getEndDate());
        List<PaymentItemModel> listData = mPaymentItemService.queryPaymentItemReport(mPaymentItemModel);
        List<String> listPayName = new LinkedList<>();
        List<BigDecimal> listPayAmount = new LinkedList<>();
        List<Long> listPayCount = new LinkedList<>();

        //为默认展示出所有支付方式，对没有支付数据的类型添加默认数据
        Map<Integer,PaymentItemModel> payMap = new HashMap<>();
        for(int i=1;i<=5;i++){
            PaymentItemModel mm = new PaymentItemModel();
            mm.setPayModeId(i);
            mm.setCount(0l);
            mm.setTotalAmount(BigDecimal.ZERO);
            if(i==1){
                mm.setPayModeName("会员余额");
            }else if(i==2){
                mm.setPayModeName("现金");
            }else if(i==3){
                mm.setPayModeName("银联");
            }else if(i==4){
                mm.setPayModeName("微信支付");
            }else if(i==5){
                mm.setPayModeName("支付宝");
            }
            payMap.put(i,mm);
        }

        if(listData != null && listData.size()==0){
            for(int i=1;i<=5;i++){
                listData.add(payMap.get(i));
            }
        }else if(listData != null && listData.size()>0){
            for(PaymentItemModel ptm : listData){
                payMap.put(ptm.getPayModeId(),ptm);
            }
            listData = new ArrayList<>();
            for (Integer key : payMap.keySet()) {
                listData.add(payMap.get(key));
            }
        }else{
            listData = new ArrayList<>();
            for(int i=1;i<=5;i++){
                listData.add(payMap.get(i));
            }
        }
        Long maxCount = 0l;
        Long maxAmount = 0l;

        BigDecimal hyAmount = BigDecimal.ZERO;
        BigDecimal xjAmount = BigDecimal.ZERO;
        BigDecimal yhAmount = BigDecimal.ZERO;
        BigDecimal wxAmount = BigDecimal.ZERO;
        BigDecimal zfAmount = BigDecimal.ZERO;
        for(PaymentItemModel ptm : listData){
            listPayName.add(ptm.getPayModeName());
            listPayAmount.add(ptm.getTotalAmount());
            listPayCount.add(ptm.getCount());

            switch (ptm.getPayModeId()){
                case 1:
                    hyAmount = ptm.getTotalAmount();
                    break;
                case 2:
                    xjAmount = ptm.getTotalAmount();
                    break;
                case 3:
                    yhAmount = ptm.getTotalAmount();
                    break;
                case 4:
                    wxAmount = ptm.getTotalAmount();
                    break;
                case 5:
                    zfAmount = ptm.getTotalAmount();
                    break;
            }

            if (maxCount < ptm.getCount()) {
                maxCount = ptm.getCount();
            }
            if (maxAmount < ptm.getTotalAmount().longValue()) {
                maxAmount = ptm.getTotalAmount().longValue();
            }
        }
        maxCount = ToolsUtil.getMaxData(maxCount);
        maxAmount = ToolsUtil.getMaxData(maxAmount);

        model.addAttribute("payMaxCount", maxCount);
        model.addAttribute("payIntervalCount", maxCount / 10);
        model.addAttribute("payMaxAmount", maxAmount);
        model.addAttribute("payIntervalAmount", maxAmount / 10);


        model.addAttribute("hyAmount", hyAmount);
        model.addAttribute("xjAmount", xjAmount);
        model.addAttribute("yhAmount", yhAmount);
        model.addAttribute("wxAmount", wxAmount);
        model.addAttribute("zfAmount", zfAmount);

        model.addAttribute("listPayData", listData);
        model.addAttribute("listPayName", listPayName);
        model.addAttribute("listPayAmount", listPayAmount);
        model.addAttribute("listPayCount", listPayCount);
    }
}
