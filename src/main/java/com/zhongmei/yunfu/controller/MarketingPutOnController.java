package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.controller.model.CouponModel;
import com.zhongmei.yunfu.controller.model.MarketingPutOnModel;
import com.zhongmei.yunfu.domain.entity.CouponEntity;
import com.zhongmei.yunfu.domain.entity.MarketingPutOnEntity;
import com.zhongmei.yunfu.service.CouponService;
import com.zhongmei.yunfu.service.MarketingPutOnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * <p>
 * 优惠券投放 前端控制器
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-26
 */
@Controller
@RequestMapping("/marketingPutOn")
public class MarketingPutOnController extends BaseController {

    @Autowired
    MarketingPutOnService mMarketingPutOnService;
    @Autowired
    CouponService couponService;

    @RequestMapping
    public String gotoPutOn(Model model, MarketingPutOnModel mMarketingPutOnModel) {

        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        List<MarketingPutOnEntity> listMarketingPutOn = mMarketingPutOnService.queryMarketingPutOn(brandIdentity, shopIdentity);


        for (MarketingPutOnEntity mMarketingPutOn : listMarketingPutOn) {
            if (mMarketingPutOn.getPalnId() == 1) {
                mMarketingPutOnModel.setIdA(mMarketingPutOn.getId());
                mMarketingPutOnModel.setCouponIdA(mMarketingPutOn.getCouponId());
                mMarketingPutOnModel.setCouponNameA(mMarketingPutOn.getCouponName());
                mMarketingPutOnModel.setStatusA(mMarketingPutOn.getStatus());
                continue;
            }
            if (mMarketingPutOn.getPalnId() == 2) {
                mMarketingPutOnModel.setIdB(mMarketingPutOn.getId());
                mMarketingPutOnModel.setCouponIdB(mMarketingPutOn.getCouponId());
                mMarketingPutOnModel.setCouponNameB(mMarketingPutOn.getCouponName());
                mMarketingPutOnModel.setStatusB(mMarketingPutOn.getStatus());
                continue;
            }
            if (mMarketingPutOn.getPalnId() == 3) {
                mMarketingPutOnModel.setIdC(mMarketingPutOn.getId());
                mMarketingPutOnModel.setCouponIdC(mMarketingPutOn.getCouponId());
                mMarketingPutOnModel.setCouponNameC(mMarketingPutOn.getCouponName());
                mMarketingPutOnModel.setStatusC(mMarketingPutOn.getStatus());
                continue;
            }
            if (mMarketingPutOn.getPalnId() == 4) {
                mMarketingPutOnModel.setIdD(mMarketingPutOn.getId());
                mMarketingPutOnModel.setCouponIdD(mMarketingPutOn.getCouponId());
                mMarketingPutOnModel.setCouponNameD(mMarketingPutOn.getCouponName());
                mMarketingPutOnModel.setStatusD(mMarketingPutOn.getStatus());
                continue;
            }
            if (mMarketingPutOn.getPalnId() == 5) {
                mMarketingPutOnModel.setIdE(mMarketingPutOn.getId());
                mMarketingPutOnModel.setCouponIdE(mMarketingPutOn.getCouponId());
                mMarketingPutOnModel.setCouponNameE(mMarketingPutOn.getCouponName());
                mMarketingPutOnModel.setStatusE(mMarketingPutOn.getStatus());
                continue;
            }
        }


        model.addAttribute("marketingPutOn", mMarketingPutOnModel);

        //获取优惠券信息
        Page<CouponEntity> listPage = couponService.findListPage(null, null, null, null, null, 1, 1000);
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


        return "settingcoupon";

    }


    @RequestMapping("/modify")
    public String modfiyMarketing(Model model, MarketingPutOnModel mMarketingPutOnModel) {

        MarketingPutOnEntity mMarketingPutOn = new MarketingPutOnEntity();

        mMarketingPutOn.setId(mMarketingPutOnModel.getId());

        mMarketingPutOn.setPalnId(mMarketingPutOnModel.getPalnId());
        mMarketingPutOn.setCouponId(mMarketingPutOnModel.getCouponId());
        mMarketingPutOn.setCouponName(mMarketingPutOnModel.getCouponName());
        Boolean isSuccess = null;
        if (mMarketingPutOnModel.getId() == null) {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            mMarketingPutOn.setStatusFlag(1);
            mMarketingPutOn.setStatus(1);
            mMarketingPutOn.setBrandIdenty(brandIdentity);
            mMarketingPutOn.setShopIdenty(shopIdentity);
            mMarketingPutOn.setServerCreateTime(new Date());
            mMarketingPutOn.setServerUpdateTime(new Date());
            isSuccess = mMarketingPutOnService.insert(mMarketingPutOn);
        } else {
            mMarketingPutOn.setStatus(mMarketingPutOnModel.getStatus());
            mMarketingPutOn.setServerUpdateTime(new Date());
            isSuccess = mMarketingPutOnService.updateMarketingPutOn(mMarketingPutOn);
        }

        if (isSuccess) {

        } else {

        }
        gotoPutOn(model, mMarketingPutOnModel);

        return "settingcoupon";
    }
}

