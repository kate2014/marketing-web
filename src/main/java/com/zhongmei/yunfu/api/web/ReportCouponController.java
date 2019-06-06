package com.zhongmei.yunfu.api.web;

import com.zhongmei.yunfu.domain.entity.TradePrivilegeReport;
import com.zhongmei.yunfu.service.TradeService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.controller.model.ReportMarketingModel;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.bean.CustomerCouponReport;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.CustomerCouponService;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Controller
@RequestMapping("/internal/marketingReport")
public class ReportCouponController {

    @Autowired
    CustomerCouponService mCustomerCouponService;
    @Autowired
    BrandService mBrandService;
    @Autowired
    CommercialService mCommercialService;
    @Autowired
    TradeService mTradeService;
    /**
     * 优惠券数据报表
     * @param model
     * @param mReportMarketingModel
     * @return
     */
    @RequestMapping("/coupon")
    public String marketingCouponReport(Model model, ReportMarketingModel mReportMarketingModel) {

        try {
            //获取门店品牌和门店编号
//            queryShopMessage(model, mReportMarketingModel);

            Date start = null;
            Date end = null;
            //设置默认查询时间
            if (mReportMarketingModel.getStartDate() == null) {
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, -15);
                start = c.getTime();
                String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
                start = DateFormatUtil.parseDate(temp, DateFormatUtil.FORMAT_FULL_DATE);

                mReportMarketingModel.setStartDate(temp);
            } else {
                start = DateFormatUtil.parseDate(mReportMarketingModel.getStartDate(), DateFormatUtil.FORMAT_FULL_DATE);
            }
            if (mReportMarketingModel.getEndDate() == null) {
                end = new Date();
                mReportMarketingModel.setEndDate(DateFormatUtil.format(end, DateFormatUtil.FORMAT_FULL_DATE));
            } else {
                end = DateFormatUtil.parseDate(mReportMarketingModel.getEndDate(), DateFormatUtil.FORMAT_FULL_DATE);
            }

            List<CustomerCouponReport> listSource = mCustomerCouponService.selectCouponSource();
            //查询所有未使用的优惠券
            List<CustomerCouponReport> listNotUsed = mCustomerCouponService.selectCouponReport(mReportMarketingModel.getBrandIdenty(), mReportMarketingModel.getShopIdenty(), start, end, 1);
            //查询已使用的优惠券
            List<CustomerCouponReport> listUsed = mCustomerCouponService.selectCouponReport(mReportMarketingModel.getBrandIdenty(), mReportMarketingModel.getShopIdenty(), start, end, 2);


            List<String> listSourceName = new LinkedList<>();
            LinkedList<Long> usedCount = new LinkedList<>();
            LinkedList<Long> notUsedCount = new LinkedList<>();

            Long used = 0l;
            Long notUsed = 0l;
            for (CustomerCouponReport sc : listSource) {
                boolean isHad = false;
                for (CustomerCouponReport cc : listNotUsed) {
                    if (sc.getSourceId() == cc.getSourceId()) {
                        isHad = true;
                        notUsedCount.add(cc.getCouponCount());
                        notUsed += cc.getCouponCount();
                        break;
                    }
                }
                if (!isHad) {
                    notUsedCount.add(0l);
                }

                isHad = false;
                for (CustomerCouponReport cc : listUsed) {
                    if (sc.getSourceId() == cc.getSourceId()) {
                        isHad = true;
                        usedCount.add(cc.getCouponCount());
                        used += cc.getCouponCount();
                        break;
                    }
                }
                if (!isHad) {
                    usedCount.add(0l);
                }
                listSourceName.add(sc.getSourceName());
            }

            //计算使用比例
            BigDecimal bDused = new BigDecimal(used);
            BigDecimal bDnotUsed = new BigDecimal(notUsed);
            BigDecimal useProportion = BigDecimal.ZERO;
            if (bDnotUsed.compareTo(BigDecimal.ZERO) != 0) {
                useProportion = bDused.divide(bDnotUsed, 2, RoundingMode.HALF_UP);
                useProportion = useProportion.multiply(new BigDecimal(100));
            }

            model.addAttribute("total", used + notUsed);
            model.addAttribute("used", used);
            model.addAttribute("notUsed", notUsed);
            model.addAttribute("useProportion", useProportion + "%");

            model.addAttribute("listSourceName", listSourceName);
            model.addAttribute("usedCount", usedCount);
            model.addAttribute("notUsedCount", notUsedCount);

            model.addAttribute("mReportMarketingModel", mReportMarketingModel);

            queryTradePrivilegeReport(model, mReportMarketingModel);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return "report_coupon";
    }

    /**
     * 查询优惠券拉动消费额
     */
    public void queryTradePrivilegeReport(Model model, ReportMarketingModel mReportMarketingModel){
        try{
            //4为优惠券
            mReportMarketingModel.setPrivilegeType(4);
            List<TradePrivilegeReport> listData = mTradeService.queryTradePrivilege(mReportMarketingModel);
            List<String> privilegeNameList = new LinkedList<>();
            List<BigDecimal> countList = new LinkedList<>();
            List<BigDecimal> amountList = new LinkedList<>();

            BigDecimal totalCount = BigDecimal.ZERO;
            BigDecimal totalAmount = BigDecimal.ZERO;

            Long maxCount = 0l;
            Long maxAmount = 0l;

            for(TradePrivilegeReport tp : listData){
                privilegeNameList.add(tp.getPrivilegeName());
                countList.add(tp.getTradeCount());
                amountList.add(tp.getTradeAmount());

                totalCount = totalCount.add(tp.getTradeCount());
                totalAmount = totalAmount.add(tp.getTradeAmount());

                if (maxCount < tp.getTradeCount().longValue()) {
                    maxCount = tp.getTradeCount().longValue();
                }
                if (maxAmount < tp.getTradeAmount().longValue()) {
                    maxAmount = tp.getTradeAmount().longValue();
                }
            }


            maxCount = ToolsUtil.getMaxData(maxCount);
            maxAmount = ToolsUtil.getMaxData(maxAmount);

            model.addAttribute("maxCount", maxCount);
            model.addAttribute("intervalCount", maxCount / 10);
            model.addAttribute("maxAmount", maxAmount);
            model.addAttribute("intervalAmount", maxAmount / 10);

            model.addAttribute("privilegeNameList", privilegeNameList);
            model.addAttribute("countList", countList);
            model.addAttribute("amountList", amountList);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("totalAmount", totalAmount);

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public Model queryShopMessage(Model model, ReportMarketingModel mReportMarketingModel) throws Exception {

        BrandEntity brand = mBrandService.queryBrandByAppId(mReportMarketingModel.getBrandIdenty());

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(brand.getId());

        model.addAttribute("brand", brand);
        model.addAttribute("listCommercial", listCommercial);
        return model;
    }


}
