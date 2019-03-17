package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CouponModel;
import com.zhongmei.yunfu.controller.model.CouponSearchModel;
import com.zhongmei.yunfu.domain.entity.CouponEntity;
import com.zhongmei.yunfu.domain.enums.StatusFlag;
import com.zhongmei.yunfu.service.CouponService;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 优惠券 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {

    @Autowired
    CouponService couponService;

    @RequestMapping({"", "/list"})
    public String list(Model model, CouponSearchModel searchModel) {
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        Page<CouponEntity> listPage = couponService.findListPage(brandIdentity, shopIdentity, searchModel.getCouponType(), searchModel.getCouponState(), searchModel.getKeyWord(), searchModel.getPageNo(), searchModel.getPageSize());
        List<CouponModel> listCouponModel = new ArrayList<>();
        if (listPage != null && listPage.getRecords() != null) {
            for (CouponEntity coupon : listPage.getRecords()) {
                CouponModel couponModel = new CouponModel(coupon);
                listCouponModel.add(couponModel);
            }
        }
        setWebPage(model, "/coupon/list", listPage, searchModel);
        model.addAttribute("searchModel", searchModel);
        model.addAttribute("list", listCouponModel);
        return "couponlist";
    }

    @RequestMapping("/couponList")
    public String queryCouponlist(Model model, CouponSearchModel searchModel) {

        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        Page<CouponEntity> listPage = couponService.queryList(brandIdentity, shopIdentity, searchModel.getCouponType(),searchModel.getPageNo(), 6);

        setWebPage(model, "/coupon/couponList", listPage, searchModel);
        model.addAttribute("searchModel", searchModel);
        model.addAttribute("list", listPage.getRecords());
        return "select_coupon_list";
    }

    @RequestMapping("/showSelectCoupon")
    public String showCouponlist(Model model, CouponSearchModel searchModel) {
        return "select_coupon";
    }

    @RequestMapping("/addOrEdit")
    public String addOrEditCoupon(Model model, CouponModel couponModel) {
        boolean result = false;

        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorName = LoginManager.get().getUser().getCreatorName();
        //数据验证

        CouponEntity coupon = couponModel.obtainCoupon();

        coupon.setStatusFlag(StatusFlag.VALiD.value());
        coupon.setUpdatorId(creatorId);
        coupon.setUpdatorName(creatorName);
        coupon.setServerUpdateTime(new Date());

        if (coupon.getId() != null) {
            //编辑
            result = couponService.updateCoupon(coupon);
        } else {
            //新增
            coupon.setUseNumber(0);
            coupon.setCreatorId(creatorId);
            coupon.setCreatorName(creatorName);
            coupon.setServerCreateTime(new Date());
            coupon.setBrandIdenty(brandIdentity);
            coupon.setShopIdenty(shopIdentity);
            result = couponService.addCoupon(coupon);
        }

        return redirect("/coupon");
    }


    @RequestMapping("/addCouponPage")
    public String redirectAddOrEditPage(Model model) {
        model.addAttribute("couponModel", new CouponModel());
        return "couponadd";
    }

    @RequestMapping("/disable")
    public String disable(Model model, Long id) {
        boolean result = couponService.enableCoupon(id, 2);
        return redirect("/coupon");
    }

    @RequestMapping("/enable")
    public String enablePlan(Model model, Long id) {
        boolean result = couponService.enableCoupon(id, 1);
        return redirect("/coupon");
    }


    @RequestMapping("/delete")
    public String deleteNewDishPushPlan(Model model, Long id) {
        if(id == null){
            return "fail";
        }
        boolean result = couponService.deleteCoupon(id);
        return "success";
    }


    @RequestMapping("/editPage")
    public String editPage(Model model, Long id) {
        CouponEntity coupon = couponService.queryByid(id);
        CouponModel couponModel = new CouponModel(coupon);
        model.addAttribute("couponModel", couponModel);
        return "couponadd";
    }
}

