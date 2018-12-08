package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.controller.model.CutDownModel;
import com.zhongmei.yunfu.domain.entity.CutDownMarketingEntity;
import com.zhongmei.yunfu.service.CutDownMarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * <p>
 * 砍价活动 前端控制器
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-10
 */
@Controller
@RequestMapping("/cutDownMarketing")
public class CutDownMarketingController extends BaseController{

    @Autowired
    CutDownMarketingService mCutDownMarketingService;

    /**
     * 进入砍价活动界面
     *
     * @param model
     * @param mCutDownModel
     * @return
     */
    @RequestMapping("/queryListData")
    public String gotoPlanActivity(Model model, CutDownModel mCutDownModel) {

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            mCutDownModel.setBrandIdentity(brandIdentity);
            mCutDownModel.setShopIdentity(shopIdentity);
            mCutDownModel.setStatusFlag(1);

            Page<CutDownMarketingEntity> listPage = mCutDownMarketingService.queryCutDownList(mCutDownModel);
            setWebPage(model, "/cutDownMarketing/queryListData", listPage, mCutDownModel);
            model.addAttribute("mCutDownModel", mCutDownModel);
            model.addAttribute("list", listPage.getRecords());

            return "cut_down_marketing_list";
        } catch (Exception e) {
            e.printStackTrace();

            return "fail";
        }

    }

    /**
     * 进入编辑/添加界面
     *
     * @param model
     * @param mCutDownModel
     * @return
     */
    @RequestMapping("/gotoModify")
    public String gotoModify(Model model, CutDownModel mCutDownModel) {

        try {
            if (mCutDownModel.getId() != null) {
                CutDownMarketingEntity mCutDownMarketing = mCutDownMarketingService.findCutDownById(mCutDownModel.getId());
                model.addAttribute("mCutDownModel", mCutDownMarketing);
            } else {
                model.addAttribute("mCutDownModel", mCutDownModel);
            }
            return "cut_down_marketing_add";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * 编辑、添加砍价活动
     *
     * @param model
     * @param mCutDownModel
     * @return
     */
    @RequestMapping("/modifyData")
    public String modifyData(Model model, CutDownModel mCutDownModel) {
        try {
            Boolean isSuccess = true;

            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
            Long creatorId = LoginManager.get().getUser().getCreatorId();
            String creatorName = LoginManager.get().getUser().getCreatorName();

            if (mCutDownModel.getId() == null) {
                CutDownMarketingEntity mCutDownMarketing = new CutDownMarketingEntity();
                mCutDownMarketing.setName(mCutDownModel.getName());
                mCutDownMarketing.setProfile(mCutDownModel.getProfile());
                mCutDownMarketing.setBeginTime(DateFormatUtil.parseDate(mCutDownModel.getBeginTime(), DateFormatUtil.FORMAT_FULL_DATE));
                mCutDownMarketing.setEndTime(DateFormatUtil.parseDate(mCutDownModel.getEndTime(), DateFormatUtil.FORMAT_FULL_DATE));
                mCutDownMarketing.setProfile(mCutDownModel.getProfile());
                mCutDownMarketing.setStartPrice(mCutDownModel.getStartPrice());
                mCutDownMarketing.setEndPrice(mCutDownModel.getEndPrice());
                mCutDownMarketing.setRandomPriceMini(mCutDownModel.getRandomPriceMini());
                mCutDownMarketing.setRandomPriceMax(mCutDownModel.getRandomPriceMax());
                mCutDownMarketing.setProductId(mCutDownModel.getProductId());
                mCutDownMarketing.setProductName(mCutDownModel.getProductName());
                mCutDownMarketing.setImgUrl(mCutDownModel.getImgUrl());
                mCutDownMarketing.setDescribe(mCutDownModel.getDescribe());
                mCutDownMarketing.setSalesCount(mCutDownModel.getSalesCount());
                mCutDownMarketing.getSalesCount();
                mCutDownMarketing.setSoldCount(0);
                mCutDownMarketing.setCustomerCutCount(mCutDownModel.getCustomerCutCount());
                mCutDownMarketing.setValidityPeriod(DateFormatUtil.parseDate(mCutDownModel.getValidityPeriod(), DateFormatUtil.FORMAT_FULL_DATE));
                mCutDownMarketing.setStatusFlag(1);
                mCutDownMarketing.setEnabledFlag(mCutDownModel.getEnabledFlag());
                mCutDownMarketing.setBrandIdentity(brandIdentity);
                mCutDownMarketing.setShopIdentity(shopIdentity);
                mCutDownMarketing.setCreatorId(creatorId);
                mCutDownMarketing.setCreatorName(creatorName);

                mCutDownMarketing.setServerCreateTime(new Date());
                mCutDownMarketing.setServerUpdateTime(new Date());

                isSuccess = mCutDownMarketingService.createCutDown(mCutDownMarketing);

            } else {
                CutDownMarketingEntity mCutDownMarketing = new CutDownMarketingEntity();
                mCutDownMarketing.setId(mCutDownModel.getId());
                mCutDownMarketing.setName(mCutDownModel.getName());
                mCutDownMarketing.setProfile(mCutDownModel.getProfile());
                mCutDownMarketing.setBeginTime(DateFormatUtil.parseDate(mCutDownModel.getBeginTime(), DateFormatUtil.FORMAT_FULL_DATE));
                mCutDownMarketing.setEndTime(DateFormatUtil.parseDate(mCutDownModel.getEndTime(), DateFormatUtil.FORMAT_FULL_DATE));
                mCutDownMarketing.setProfile(mCutDownModel.getProfile());
                mCutDownMarketing.setStartPrice(mCutDownModel.getStartPrice());
                mCutDownMarketing.setEndPrice(mCutDownModel.getEndPrice());
                mCutDownMarketing.setRandomPriceMini(mCutDownModel.getRandomPriceMini());
                mCutDownMarketing.setRandomPriceMax(mCutDownModel.getRandomPriceMax());
                mCutDownMarketing.setProductId(mCutDownModel.getProductId());
                mCutDownMarketing.setProductName(mCutDownModel.getProductName());
                mCutDownMarketing.setImgUrl(mCutDownModel.getImgUrl());
                mCutDownMarketing.setDescribe(mCutDownModel.getDescribe());
                mCutDownMarketing.setSalesCount(mCutDownModel.getSalesCount());
                mCutDownMarketing.getSalesCount();
                mCutDownMarketing.setCustomerCutCount(mCutDownModel.getCustomerCutCount());
                mCutDownMarketing.setValidityPeriod(DateFormatUtil.parseDate(mCutDownModel.getValidityPeriod(), DateFormatUtil.FORMAT_FULL_DATE));
                mCutDownMarketing.setEnabledFlag(mCutDownModel.getEnabledFlag());
                mCutDownMarketing.setUpdatorId(creatorId);
                mCutDownMarketing.setUpdatorName(creatorName);
                mCutDownMarketing.setServerUpdateTime(new Date());

                isSuccess = mCutDownMarketingService.modifyCutDown(mCutDownMarketing);
            }

            if (isSuccess) {
                return redirect("/cutDownMarketing/queryListData");
            } else {
                return "fail";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * 更新砍价活动状态
     *
     * @param model
     * @param mCutDownModel
     * @return
     */
    @RequestMapping("/updateState")
    public String updateState(Model model, CutDownModel mCutDownModel) {
        try {
            Boolean isSuccess = mCutDownMarketingService.updateState(mCutDownModel.getId(), mCutDownModel.getEnabledFlag());
            if (isSuccess) {
                return redirect("/cutDownMarketing/queryListData");
            } else {
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * 删除秒杀活动
     *
     * @param model
     * @param mCutDownModel
     * @return
     */
    @RequestMapping("/deleteById")
    public String deleteById(Model model, CutDownModel mCutDownModel) {
        try {
            Boolean isSuccess = mCutDownMarketingService.deleteCutDownById(mCutDownModel.getId());
            if (isSuccess) {
                return redirect("/cutDownMarketing/queryListData");
            } else {
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

}

