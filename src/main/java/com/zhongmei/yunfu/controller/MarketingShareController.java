package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.controller.model.CouponModel;
import com.zhongmei.yunfu.controller.model.ShareMarketingModel;
import com.zhongmei.yunfu.domain.entity.CouponEntity;
import com.zhongmei.yunfu.domain.entity.MarketingShareEntity;
import com.zhongmei.yunfu.service.CouponService;
import com.zhongmei.yunfu.service.MarketingShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 分享营销 前端控制器
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-26
 */
@Controller
@RequestMapping("/marketingShare")
public class MarketingShareController extends BaseController{

    @Autowired
    MarketingShareService mMarketingShareService;
    @Autowired
    CouponService couponService;

    @RequestMapping("/gotoShareMarketing")
    public String gotoPlanActivity(Model model, ShareMarketingModel shareMarketingModel) {
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        List<MarketingShareEntity> listData = mMarketingShareService.findSharePlanByShopId(shopIdentity);

        MarketingShareEntity marketingShare = new MarketingShareEntity();
        //默认展示门店分享
        if (shareMarketingModel.getSelectShareType() == null) {
            shareMarketingModel.setSelectShareType(1);
            shareMarketingModel.setShareType(1);
        } else {
            shareMarketingModel.setShareType(shareMarketingModel.getSelectShareType());
        }
        for (MarketingShareEntity mMarketingShare : listData) {
            if (shareMarketingModel.getSelectShareType() == mMarketingShare.getShareType()) {
                marketingShare = mMarketingShare;
            }
            if (mMarketingShare.getShareType() == 1) {
                if (mMarketingShare.getShareState() == 1) {
                    shareMarketingModel.setShareShopState("投放中");
                    shareMarketingModel.setShareShopDate(mMarketingShare.getStartDate());
                } else {
                    shareMarketingModel.setShareShopState("停止投放");
                }

            } else if (mMarketingShare.getShareType() == 2) {
                if (mMarketingShare.getShareState() == 1) {
                    shareMarketingModel.setShareDishState("投放中");
                    shareMarketingModel.setShareDishDate(mMarketingShare.getStartDate());
                } else {
                    shareMarketingModel.setShareDishState("停止投放");
                }

            } else if (mMarketingShare.getShareType() == 3) {
                if (mMarketingShare.getShareState() == 1) {
                    shareMarketingModel.setShareActivityState("投放中");
                    shareMarketingModel.setShareActivityDate(mMarketingShare.getStartDate());
                } else {
                    shareMarketingModel.setShareActivityState("停止投放");
                }

            }
        }

        shareMarketingModel.setId(marketingShare.getId());
        shareMarketingModel.setCouponId(marketingShare.getCouponId());
        shareMarketingModel.setCouponName(marketingShare.getCouponName());
        shareMarketingModel.setShareState(marketingShare.getShareState());
        shareMarketingModel.setShareCount(marketingShare.getShareCount());
        if(marketingShare.getEndDate() != null){
            shareMarketingModel.setEndDate(DateFormatUtil.format(marketingShare.getEndDate(),DateFormatUtil.FORMAT_FULL_DATE));
        }

        shareMarketingModel.setProfile(marketingShare.getProfile());

        model.addAttribute("sharePlan", shareMarketingModel);

        return "shareplan";
    }

    /**
     * 添加/编辑分享营销
     *
     * @param model
     * @param shareMarketingModel
     * @return
     */
    @RequestMapping("/editShareMarketing")
    public String editShareMarketing(Model model, ShareMarketingModel shareMarketingModel)  {
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
        try{
            MarketingShareEntity mMarketingShare = new MarketingShareEntity();
            mMarketingShare.setId(shareMarketingModel.getId());
            mMarketingShare.setShareState(shareMarketingModel.getShareState());
            mMarketingShare.setShareType(shareMarketingModel.getShareType());
            mMarketingShare.setStartDate(new Date());
            if(shareMarketingModel.getEndDate() != null){
                mMarketingShare.setEndDate(DateFormatUtil.parseDate(shareMarketingModel.getEndDate(),DateFormatUtil.FORMAT_FULL_DATE));
            }

            mMarketingShare.setShareCount(shareMarketingModel.getShareCount());
            mMarketingShare.setProfile(shareMarketingModel.getProfile());

            mMarketingShare.setCouponId(shareMarketingModel.getCouponId());
            mMarketingShare.setCouponName(shareMarketingModel.getCouponName());

            mMarketingShare.setBrandIdenty(brandIdentity);
            mMarketingShare.setShopIdenty(shopIdentity);
            mMarketingShare.setServerCreateTime(new Date());
            mMarketingShare.setServerUpdateTime(new Date());
            mMarketingShare.setStatusFlag(1);


            Boolean isSuccess = null;
            if (shareMarketingModel.getId() == null) {
                mMarketingShare.setScanNumber(0);
                mMarketingShare.setShareNumber(0);
                isSuccess = mMarketingShareService.addShareMarketing(mMarketingShare);
            } else {
                isSuccess = mMarketingShareService.editShareMarketing(mMarketingShare);
            }

            int type = shareMarketingModel.getShareType();
            if (isSuccess) {
                return redirect("/marketingShare/gotoShareMarketing?successOrfail=success&selectShareType="+type);
            }else{
                return redirect("/marketingShare/gotoShareMarketing?successOrfail=fail&selectShareType="+type);
            }



        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

}

