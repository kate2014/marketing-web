package com.zhongmei.yunfu.web;

import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.service.AuthUserService;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController extends BaseController {

    @Autowired
    AuthUserService authUserService;

    @RequestMapping({"/", "/home"})
    public String home(Model model,Long creatorId,String creatorName,Long shopIdenty,Long brandIdenty) {

        AuthUserEntity user = LoginManager.get().getUser();
        creatorId = user.getId();
        creatorName = user.getName();
        shopIdenty = user.getShopIdenty();
        brandIdenty = user.getBrandIdenty();


        model.addAttribute("creatorId", creatorId);
        model.addAttribute("creatorName", creatorName);
        model.addAttribute("shopIdenty", shopIdenty);
        model.addAttribute("brandIdenty", brandIdenty);


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

        //会员积分
        if(permissionData.get("WEB_CUSTOMER_SCOKT") == null || permissionData.get("WEB_CUSTOMER_SCOKT").equals("")){
            model.addAttribute("shaveCustomerSockt", 0);
        }else{
            model.addAttribute("shaveCustomerSockt", 1);
        }
        //会员储值权益
        if(permissionData.get("WEB_CUSTOMER_SAVE_MARKETING") == null || permissionData.get("WEB_CUSTOMER_SAVE_MARKETING").equals("")){
            model.addAttribute("shaveSaveMarketing", 0);
        }else{
            model.addAttribute("shaveSaveMarketing", 1);
        }
        //储值赠送
        if(permissionData.get("WEB_CUSTOMER_SAVE_RULE") == null || permissionData.get("WEB_CUSTOMER_SAVE_RULE").equals("")){
            model.addAttribute("shaveSaveRule", 0);
        }else{
            model.addAttribute("shaveSaveRule", 1);
        }
        //会员权益
        if(permissionData.get("WEB_CUSTOMER_PRIVILEGE") == null || permissionData.get("WEB_CUSTOMER_PRIVILEGE").equals("")){
            model.addAttribute("shaveCustomerPrivilege", 0);
        }else{
            model.addAttribute("shaveCustomerPrivilege", 1);
        }

        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login/test")
    public String login(Model model, Long shopId, String account, String password) {
        if (shopId != null && !StringUtils.isEmpty(account) && !StringUtils.isEmpty(password)) {
            boolean login = LoginManager.get().login(authUserService, account, password, shopId);
            if (login) {
                AuthUserEntity user = LoginManager.get().getUser();
                return redirect(String.format("/?creatorId=%d&creatorName=%sshopIdenty=%d&brandIdenty=%d", user.getId(), user.getName(),user.getShopIdenty(),user.getBrandIdenty()));
            }
        }

        model.addAttribute("account", account);
        model.addAttribute("password", password);
        return "login_test";
    }

    @RequestMapping("/**/token/{token}")
    public String token(HttpServletRequest request, @PathVariable String token) {
        if (LoginManager.get().login(authUserService, token)) {
            String href = request.getRequestURI().replaceFirst(request.getContextPath(), "").replace("/token/" + token, "");
            if (request.getQueryString() != null) {
                href = href + "?" + request.getQueryString();
            }
            return redirect(href);
        }

        throw new IllegalStateException("Token未认证");
    }
}
