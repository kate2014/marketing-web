package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.controller.model.CouponModel;
import com.zhongmei.yunfu.controller.model.TogetherMarketingModel;
import com.zhongmei.yunfu.domain.entity.CouponEntity;
import com.zhongmei.yunfu.domain.entity.MarketingTogetherEntity;
import com.zhongmei.yunfu.service.CouponService;
import com.zhongmei.yunfu.service.MarketingTogetherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 同行特惠 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Controller
@RequestMapping("/marketingTogether")
public class MarketingTogetherController extends BaseController {

    @Autowired
    MarketingTogetherService marketingTogetherService;
    @Autowired
    CouponService couponService;

    @RequestMapping("/gotoTogetherMarketing")
    public String gotoPlanActivity(Model model, TogetherMarketingModel togetherMarketingModel) {

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            MarketingTogetherEntity mMarketingTogether = marketingTogetherService.findMarketingTogether(shopIdentity, 1);

            if (mMarketingTogether != null) {
                togetherMarketingModel.setId(mMarketingTogether.getId());
                togetherMarketingModel.setBrandIdenty(mMarketingTogether.getBrandIdenty());
                togetherMarketingModel.setShopIdenty(mMarketingTogether.getShopIdenty());
                togetherMarketingModel.setPlanName(mMarketingTogether.getPlanName());
                togetherMarketingModel.setEndDate(DateFormatUtil.format(mMarketingTogether.getEndDate(),DateFormatUtil.FORMAT_FULL_DATE));
                togetherMarketingModel.setProfile(mMarketingTogether.getProfile());
                togetherMarketingModel.setCouponId(mMarketingTogether.getCouponId());
                togetherMarketingModel.setCouponName(mMarketingTogether.getCouponName());
                togetherMarketingModel.setDescription(mMarketingTogether.getDescription());
                togetherMarketingModel.setState(mMarketingTogether.getState());
            }

            model.addAttribute("together", togetherMarketingModel);


            Page<CouponEntity> listPage = couponService.findListPage(null, null, null, null, null, togetherMarketingModel.getCurPage(), togetherMarketingModel.getDataCount());
            List<CouponModel> zkCouponArray = new ArrayList<>();
            List<CouponModel> lpCouponArray = new ArrayList<>();
            List<CouponModel> djCouponArray = new ArrayList<>();

            if (listPage != null && listPage.getRecords() != null) {
                for (CouponEntity coupon : listPage.getRecords()) {
                    CouponModel couponModel = new CouponModel(coupon);
                    if (couponModel.getCouponType() == 1) {
                        zkCouponArray.add(couponModel);
                    } else if (couponModel.getCouponType() == 2) {
                        djCouponArray.add(couponModel);
                    } else if (couponModel.getCouponType() == 3) {
                        lpCouponArray.add(couponModel);
                    }

                }
            }

            model.addAttribute("zkCouponArray", zkCouponArray);
            model.addAttribute("lpCouponArray", lpCouponArray);
            model.addAttribute("djCouponArray", djCouponArray);


        } catch (Exception e) {
            e.printStackTrace();

        }

        return "togethermarketing";
    }

    @RequestMapping("/editMarketingTogether")
    public String editMarketingTogether(Model model, TogetherMarketingModel togetherMarketingModel) {

        Boolean isSuccess = true;
        try {
            MarketingTogetherEntity mMarketingTogether = new MarketingTogetherEntity();
            mMarketingTogether.setId(togetherMarketingModel.getId());
            mMarketingTogether.setPlanName("同行特惠");
            if(togetherMarketingModel.getEndDate() != null){
                mMarketingTogether.setEndDate(DateFormatUtil.parseDate(togetherMarketingModel.getEndDate(),DateFormatUtil.FORMAT_FULL_DATE));
            }

            mMarketingTogether.setProfile(togetherMarketingModel.getProfile());
            mMarketingTogether.setCouponId(togetherMarketingModel.getCouponId());
            mMarketingTogether.setCouponName(togetherMarketingModel.getCouponName());
            mMarketingTogether.setDescription(togetherMarketingModel.getDescription());
            if(togetherMarketingModel.getState() == 1){
                mMarketingTogether.setStartDate(new Date());
            }
            mMarketingTogether.setState(togetherMarketingModel.getState());

            if (togetherMarketingModel.getId() == null) {
                Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
                Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

                mMarketingTogether.setScanNumber(0);
                mMarketingTogether.setShareNumber(0);
                mMarketingTogether.setBrandIdenty(brandIdentity);
                mMarketingTogether.setShopIdenty(shopIdentity);
                mMarketingTogether.setServerCreateTime(new Date());
                mMarketingTogether.setServerUpdateTime(new Date());
                mMarketingTogether.setStatusFlag(1);

                isSuccess = marketingTogetherService.addShareMarketing(mMarketingTogether);
            } else {
                mMarketingTogether.setServerUpdateTime(new Date());
                isSuccess = marketingTogetherService.editShareMarketing(mMarketingTogether);
            }
            if (isSuccess) {
                return redirect("/marketingTogether/gotoTogetherMarketing?successOrfail=success");
            } else {
                return redirect("/marketingTogether/gotoTogetherMarketing?successOrfail=fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }

    }
}

