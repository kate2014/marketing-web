package com.zhongmei.yunfu.controller;

import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.ui.Model;
import com.zhongmei.yunfu.controller.model.ExpandedMarketingModel;
import com.zhongmei.yunfu.domain.entity.MarketingExpandedEntity;
import com.zhongmei.yunfu.service.MarketingExpandedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 全员推广营销 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Controller
@RequestMapping("/marketingExpanded")
public class MarketingExpandedController extends BaseController {

    @Autowired
    MarketingExpandedService mMarketingExpandedService;

    @RequestMapping("/gotoMarketingExpanded")
    public String gotoPlanActivity(Model model, ExpandedMarketingModel expandedMarketingModel) {
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        expandedMarketingModel.setBrandIdenty(brandIdentity);
        expandedMarketingModel.setShopIdenty(shopIdentity);
        expandedMarketingModel.setStatusFlag(1);
        MarketingExpandedEntity mMarketingExpanded = mMarketingExpandedService.findMarketingExpanded(expandedMarketingModel);

        if (mMarketingExpanded != null) {
            expandedMarketingModel.setId(mMarketingExpanded.getId());
            expandedMarketingModel.setEndDate(DateFormatUtil.format(mMarketingExpanded.getEndDate(),DateFormatUtil.FORMAT_FULL_DATE));
            expandedMarketingModel.setProfile(mMarketingExpanded.getProfile());
            expandedMarketingModel.setFirstLevelDiscount(mMarketingExpanded.getFirstLevelDiscount());
            expandedMarketingModel.setSecondClassDicount(mMarketingExpanded.getSecondClassDicount());
            expandedMarketingModel.setExpandedState(mMarketingExpanded.getExpandedState());
            expandedMarketingModel.setDescription(mMarketingExpanded.getDescription());

        }

        model.addAttribute("expanded", expandedMarketingModel);
        return "expanded";
    }

    @RequestMapping("/editExpanded")
    public String editExpanded(Model model, ExpandedMarketingModel expandedMarketingModel) {

        Boolean isSuccess = true;
        try {
            MarketingExpandedEntity mMarketingExpanded = new MarketingExpandedEntity();
            mMarketingExpanded.setId(expandedMarketingModel.getId());
            mMarketingExpanded.setPlanName("全员推广");
            mMarketingExpanded.setExpandedState(expandedMarketingModel.getExpandedState());

            if (expandedMarketingModel.getExpandedState() == 1) {
                mMarketingExpanded.setStartDate(new Date());
            }

            mMarketingExpanded.setEndDate(DateFormatUtil.parseDate(expandedMarketingModel.getEndDate(),DateFormatUtil.FORMAT_FULL_DATE));
            mMarketingExpanded.setProfile(expandedMarketingModel.getProfile());
            mMarketingExpanded.setFirstLevelDiscount(expandedMarketingModel.getFirstLevelDiscount());
            mMarketingExpanded.setSecondClassDicount(expandedMarketingModel.getSecondClassDicount());
            mMarketingExpanded.setDescription(expandedMarketingModel.getDescription());

            if (expandedMarketingModel.getId() == null) {

                Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
                Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

                mMarketingExpanded.setBrandIdenty(brandIdentity);
                mMarketingExpanded.setShopIdenty(shopIdentity);
                mMarketingExpanded.setServerCreateTime(new Date());
                mMarketingExpanded.setServerUpdateTime(new Date());
                mMarketingExpanded.setStatusFlag(1);
                isSuccess = mMarketingExpandedService.addMarketingExpanded(mMarketingExpanded);

            } else {

                mMarketingExpanded.setServerUpdateTime(new Date());
                isSuccess = mMarketingExpandedService.editMarketingExpanded(mMarketingExpanded);
            }
            if (isSuccess) {
                return redirect("/marketingExpanded/gotoMarketingExpanded?successOrfail=success");
            } else {
                return redirect("/marketingExpanded/gotoMarketingExpanded?successOrfail=success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "success";
        }

    }

}

