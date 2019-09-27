package com.zhongmei.yunfu.controller.brand;

import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import com.zhongmei.yunfu.service.AuthUserService;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
@RequestMapping("/internal/brand/marketing")
public class BrandMarketingController extends BaseController {

    @Autowired
    AuthUserService authUserService;

    @RequestMapping({"/main"})
    public String shopList(Model model, ShopSearchModel mShopSearchModel) {

        Long creatorId = mShopSearchModel.getCreatorId();
        String creatorName = mShopSearchModel.getCreatorName();
        Long shopIdenty = mShopSearchModel.getShopIdenty();

        Map<String, String> permissionData = authUserService.getAuthPermissionMap(creatorId,shopIdenty);

        //新品推广
        if(permissionData.get("WEB_PUSH_NEW_DISH") == null || permissionData.get("WEB_PUSH_NEW_DISH").equals("")){
            model.addAttribute("havaPushNewDish", 0);
        }else{
            model.addAttribute("havePushNewDish", 1);
        }
        //优惠券
        if(permissionData.get("WEB_COUPON_MARKETING") == null || permissionData.get("WEB_COUPON_MARKETING").equals("")){
            model.addAttribute("haveCouponMarketing", 0);
        }else{
            model.addAttribute("haveCouponMarketing", 1);
        }
        //活动推广
        if(permissionData.get("WEB_PUSH_ACTION") == null || permissionData.get("WEB_PUSH_ACTION").equals("")){
            model.addAttribute("havePushAction", 0);
        }else{
            model.addAttribute("havePushAction", 1);
        }
        //拼团活动
        if(permissionData.get("WEB_COLLAGE_MARKETING") == null || permissionData.get("WEB_COLLAGE_MARKETING").equals("")){
            model.addAttribute("haveCollage", 0);
        }else{
            model.addAttribute("haveCollage", 1);
        }
        //秒杀活动
        if(permissionData.get("WEB_FLASH_SALES_MARKETING") == null || permissionData.get("WEB_FLASH_SALES_MARKETING").equals("")){
            model.addAttribute("haveFlashSales", 0);
        }else{
            model.addAttribute("haveFlashSales", 1);
        }
        //砍价活动
        if(permissionData.get("WEB_CUT_DOWN_MARKETING") == null || permissionData.get("WEB_CUT_DOWN_MARKETING").equals("")){
            model.addAttribute("haveCutDown", 0);
        }else{
            model.addAttribute("haveCutDown", 1);
        }
        //分享营销
        if(permissionData.get("WEB_SHARE_MARKETING") == null || permissionData.get("WEB_SHARE_MARKETING").equals("")){
            model.addAttribute("haveShare", 0);
        }else{
            model.addAttribute("haveShare", 1);
        }
        //同行特惠
        if(permissionData.get("WEB_TOGETHER_MARKETING") == null || permissionData.get("WEB_TOGETHER_MARKETING").equals("")){
            model.addAttribute("haveTogether", 0);
        }else{
            model.addAttribute("haveTogether", 1);
        }
        //全员推广
        if(permissionData.get("WEB_EXPAND_MARKETING") == null || permissionData.get("WEB_EXPAND_MARKETING").equals("")){
            model.addAttribute("haveExpand", 0);
        }else{
            model.addAttribute("haveExpand", 1);
        }
        //优惠券投放
        if(permissionData.get("WEB_SETTING_SEND_COUPON") == null || permissionData.get("WEB_SETTING_SEND_COUPON").equals("")){
            model.addAttribute("haveSendCoupon", 0);
        }else{
            model.addAttribute("haveSendCoupon", 1);
        }
        //提成核算
        if(permissionData.get("WEB_EXPANDED_CUSTOMER") == null || permissionData.get("WEB_EXPANDED_CUSTOMER").equals("")){
            model.addAttribute("haveExpandedCustomer", 0);
        }else{
            model.addAttribute("haveExpandedCustomer", 1);
        }
        //特价活动
        if(permissionData.get("ACTIVITY_SALES") == null || permissionData.get("ACTIVITY_SALES").equals("")){
            model.addAttribute("shaveAactivitySales", 0);
        }else{
            model.addAttribute("shaveAactivitySales", 1);
        }

        model.addAttribute("mShopSearchModel", mShopSearchModel);
        return "brand_marketing_main";
    }
}
