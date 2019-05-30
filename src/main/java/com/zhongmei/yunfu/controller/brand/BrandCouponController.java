package com.zhongmei.yunfu.controller.brand;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.CouponModel;
import com.zhongmei.yunfu.controller.model.CouponSearchModel;
import com.zhongmei.yunfu.controller.model.NewDishPushModel;
import com.zhongmei.yunfu.domain.entity.CouponEntity;
import com.zhongmei.yunfu.domain.enums.StatusFlag;
import com.zhongmei.yunfu.service.CouponService;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/internal/brand/marketing/coupon")
public class BrandCouponController extends BaseController {

    @Autowired
    CouponService couponService;

    @RequestMapping({"/list"})
    public String pushDishList(Model model, CouponSearchModel searchModel) {

        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        searchModel.setBrandIdenty(brandIdentity);

        Page<CouponEntity> listPage = couponService.findBrandCouponList(searchModel);
        List<CouponModel> listCouponModel = new ArrayList<>();
        if (listPage != null && listPage.getRecords() != null) {
            for (CouponEntity coupon : listPage.getRecords()) {
                CouponModel couponModel = new CouponModel(coupon);
                listCouponModel.add(couponModel);
            }
        }

        setWebPage(model, "/internal/brand/marketing/coupon/list", listPage, searchModel);
        model.addAttribute("searchModel", searchModel);
        model.addAttribute("list", listCouponModel);
        return "brand_coupon_list";
    }

    @RequestMapping({"/goToAddOrEdit"})
    public String goToAddOrEdit(Model model, CouponModel couponModel) {

        if(couponModel.getId() != null){
            CouponEntity mCouponEntity =couponService.queryByid(couponModel.getId());
            couponModel = new CouponModel(mCouponEntity);
        }else{
            couponModel = new CouponModel();
        }

        model.addAttribute("couponModel", couponModel);
        return "brand_coupon_add";
    }

    @RequestMapping({"/addOrEdit"})
    public String addOrEdit(Model model, CouponModel couponModel) {
        boolean result = false;

        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
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
            coupon.setSourceType(1);
            coupon.setUseNumber(0);
            coupon.setCreatorId(creatorId);
            coupon.setCreatorName(creatorName);
            coupon.setServerCreateTime(new Date());
            coupon.setBrandIdenty(brandIdentity);

            result = couponService.addCoupon(coupon);
        }

        return redirect("/internal/brand/marketing/coupon/list");
    }

    @RequestMapping({"/updateStatus"})
    public String updateStatus(Model model, CouponModel couponModel) {
        couponService.enableCoupon(couponModel.getId(),couponModel.getCouponState());
        return redirect("/internal/brand/marketing/coupon/list");
    }

    @RequestMapping({"/deleteData"})
    public String deleteData(Model model, CouponModel couponModel) {
        couponService.deleteCoupon(couponModel.getId());
        return redirect("/internal/brand/marketing/coupon/list");
    }

    @RequestMapping({"/sendToShop"})
    @ResponseBody
    public String sendToShop(Model model, CouponModel couponModel){
        System.out.println("=====couponModel====="+couponModel.getSelectShopList()+"===="+couponModel.getSourceId());
        //查出该优惠券已下发过的门店优惠券信息

        return "success";
    }

    @RequestMapping({"/refreshData"})
    @ResponseBody
    public String refreshData(Model model, CouponModel couponModel){
        System.out.println("=====refreshData=====");

        return "success";
    }

}
