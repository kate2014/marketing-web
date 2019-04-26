package com.zhongmei.yunfu.controller.brand;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.AuthUserModel;
import com.zhongmei.yunfu.controller.model.PaymentItemModel;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
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
@RequestMapping("/internal/brand")
public class BrandMainController extends BaseController {

    @Autowired
    TradeService mTradeService;
    @Autowired
    TradeItemService mTradeItemService;
    @Autowired
    PaymentItemService mPaymentItemService;
    @Autowired
    CommercialService mCommercialService;

    @RequestMapping({"/main"})
    public String mianPage(Model model, AuthUserModel mAuthUserModel) {

        model.addAttribute("mAuthUserModel", mAuthUserModel);
        return "brand_main";
    }

    @RequestMapping({"/index"})
    public String indexPage(Model model, AuthUserModel mAuthUserModel) {
        try {
            Date stateDate = DateFormatUtil.getStartTime();
            Date endDate = new Date();

            TradeModel mTradeModel = new TradeModel();
            mTradeModel.setBrandIdenty(mAuthUserModel.getBrandIdenty());

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


            model.addAttribute("mAuthUserModel", mAuthUserModel);
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

            return "brand_index";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

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
