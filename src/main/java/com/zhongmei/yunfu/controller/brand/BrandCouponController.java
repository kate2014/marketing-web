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

import java.util.*;

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

    @RequestMapping({"/sendToShop"})
    @ResponseBody
    public String sendToShop(Model model, CouponModel couponModel){
        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();

            String[] shopList = couponModel.getSelectShopList().split(",");

            Long sourceId = couponModel.getSourceId();

            CouponEntity mainEntity = couponService.queryByid(sourceId);
            //查出该优惠券已下发过的门店优惠券信息
            List<CouponEntity> listData = couponService.queryDataBySourceId(brandIdentity,sourceId);
            Map<Long,CouponEntity> tempMap = new HashMap<>();

            for(CouponEntity entity : listData){
                tempMap.put(entity.getShopIdenty(),entity);
            }

            List<CouponEntity> listAdd = new ArrayList<>();

            List<Long> listDelete = new ArrayList<>();

            String updateData = "";

            for(String shopId : shopList){
                Long shopIdenty = Long.valueOf(shopId);
                CouponEntity mCouponEntity = tempMap.get(shopIdenty);
                if(mCouponEntity == null){
                    CouponEntity tempEntity = new CouponEntity().cloneCoupon(mainEntity,shopIdenty,sourceId);
                    listAdd.add(tempEntity);
                }else{
                    if(updateData.equals("")){
                        updateData = mCouponEntity.getId().toString();
                    }else{
                        updateData = updateData +","+ mCouponEntity.getId();
                    }

                    tempMap.remove(shopIdenty);
                }
            }
            for(CouponEntity value : tempMap.values()){
                listDelete.add(value.getId());
            }

            if(listAdd.size() != 0){
                couponService.batchAdd(listAdd);
            }
            if(listDelete.size() != 0){
                couponService.batchDelete(listDelete);
            }
            if(!updateData.equals("")){
                mainEntity.setSourceType(3);
                couponService.batchUpdate(mainEntity,updateData);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return "success";
    }

    @RequestMapping({"/refreshData"})
    @ResponseBody
    public String refreshData(Model model, CouponModel couponModel){

        try {
            Long sourceId = couponModel.getSourceId();
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            CouponEntity mainEntity = couponService.queryByid(sourceId);
            //查出该优惠券已下发过的门店优惠券信息
            List<CouponEntity> listData = couponService.queryDataBySourceId(brandIdentity,sourceId);

            for(CouponEntity entity : listData){
                int state = entity.getCouponState();
                if(state == 1 || state == 2 || state == 4) { //更新数据并将状态修改为数据刷新未接受
                    CouponEntity updateEntity = new CouponEntity().cloneCoupon(mainEntity,entity.getShopIdenty(),sourceId);
                    updateEntity.setId(entity.getId());
                    updateEntity.setCouponState(5);
                    couponService.updateCoupon(updateEntity);

                }else if(state == 3 || state == 5){
                    CouponEntity updateEntity = new CouponEntity().cloneCoupon(mainEntity,entity.getShopIdenty(),sourceId);
                    updateEntity.setId(entity.getId());
                    updateEntity.setCouponState(state);
                    couponService.updateCoupon(updateEntity);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return "success";
    }

    @RequestMapping("/disable")
    public String disable(Model model, Long id) {
        try {
            boolean result = couponService.modfityCouponState(id, 2);
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            couponService.batchUpdateState(brandIdentity,2,id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return redirect("/internal/brand/marketing/coupon/list");
    }

    @RequestMapping("/enable")
    public String enablePlan(Model model, Long id) {
        try {
            boolean result = couponService.modfityCouponState(id, 1);
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            couponService.batchUpdateState(brandIdentity,3,id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return redirect("/internal/brand/marketing/coupon/list");
    }


    @RequestMapping("/delete")
    public String deleteNewDishPushPlan(Model model, Long id) {
        try {
            if(id == null){
                return "fail";
            }
            boolean result = couponService.deleteCoupon(id);
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            couponService.batchDeleteBySouceId(brandIdentity,id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "success";
    }

}
