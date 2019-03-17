package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.controller.model.CollageMarketingModel;
import com.zhongmei.yunfu.domain.entity.CollageMarketingEntity;
import com.zhongmei.yunfu.service.CollageMarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 拼团活动 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-09-10
 */
@Controller
@RequestMapping("/collageMarketing")
public class CollageMarketingController extends BaseController{

    @Autowired
    CollageMarketingService mCollageMarketingService;

    @RequestMapping("/queryListData")
    public String queryListData(Model model, CollageMarketingModel mCollageMarketingModel) {
        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            mCollageMarketingModel.setBrandIdentity(brandIdentity);
            mCollageMarketingModel.setShopIdentity(shopIdentity);
            mCollageMarketingModel.setStatusFlag(1);

            Page<CollageMarketingEntity> listPage = mCollageMarketingService.queryCollageList(mCollageMarketingModel);
            setWebPage(model, "/collageMarketing/queryListData", listPage, mCollageMarketingModel);
            model.addAttribute("collageMarketingModel", mCollageMarketingModel);
            model.addAttribute("list", listPage.getRecords());
            return "collage_marketing_list";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * @param model
     * @param mCollageMarketingModel
     * @return
     */
    @RequestMapping("/gotoModifyCollage")
    public String gotoModifyCollage(Model model, CollageMarketingModel mCollageMarketingModel) {
        try {
            if (mCollageMarketingModel.getId() != null) {
                CollageMarketingEntity mCollageMarketing = mCollageMarketingService.findCollageById(mCollageMarketingModel.getId());
                model.addAttribute("collageMarketing", mCollageMarketing);
            } else {
                model.addAttribute("collageMarketing", mCollageMarketingModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

        return "collage_marketing_add";
    }

    /**
     * @param model
     * @param mCollageMarketingModel
     * @return
     */
    @RequestMapping("/modifyCollage")
    public String createCollage(Model model, CollageMarketingModel mCollageMarketingModel) {

        Boolean isSuccess = true;
        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
            Long creatorId = LoginManager.get().getUser().getCreatorId();
            String creatorName = LoginManager.get().getUser().getCreatorName();

            //如果id为null这表示新增
            if (mCollageMarketingModel.getId() == null) {
                CollageMarketingEntity mCM = new CollageMarketingEntity();
                mCM.setName(mCollageMarketingModel.getName());
                mCM.setProfile(mCollageMarketingModel.getProfile());
                if (mCollageMarketingModel.getEnabledFlag() == 1) {
                    mCM.setBeginTime(new Date());
                }
                mCM.setBeginTime(DateFormatUtil.parseDate(mCollageMarketingModel.getBeginTime(), DateFormatUtil.FORMAT_FULL_DATE));
                mCM.setEndTime(DateFormatUtil.parseDate(mCollageMarketingModel.getEndTime(), DateFormatUtil.FORMAT_FULL_DATE));
                mCM.setProductId(mCollageMarketingModel.getProductId());
                mCM.setProductName(mCollageMarketingModel.getProductName());
                mCM.setCollagePeopleCount(mCollageMarketingModel.getCollagePeopleCount());
                mCM.setJoinCount(0);
                mCM.setCollagePrice(new BigDecimal(mCollageMarketingModel.getCollagePrice()));
                mCM.setOriginalPrice(new BigDecimal(mCollageMarketingModel.getOriginalPrice()));
                mCM.setImgUrl(mCollageMarketingModel.getImgUrl());
                mCM.setMaxOpenGroup(mCollageMarketingModel.getMaxOpenGroup());
                mCM.setEnabledFlag(mCollageMarketingModel.getEnabledFlag());
                mCM.setOpenGroupCount(0);
                mCM.setFinishGroupCount(0);
                mCM.setDescribe(mCollageMarketingModel.getDescribe());
                mCM.setValidityPeriod(DateFormatUtil.parseDate(mCollageMarketingModel.getValidityPeriod(), DateFormatUtil.FORMAT_FULL_DATE));
                mCM.setBrandIdentity(brandIdentity);
                mCM.setShopIdentity(shopIdentity);
                mCM.setStatusFlag(1);
                mCM.setCreatorId(creatorId);
                mCM.setCreatorName(creatorName);
                mCM.setUpdatorId(creatorId);
                mCM.setUpdatorName(creatorName);
                mCM.setServerCreateTime(new Date());
                mCM.setServerUpdateTime(new Date());

                isSuccess = mCollageMarketingService.createCollage(mCM);
            } else {

                CollageMarketingEntity mCM = new CollageMarketingEntity();
                mCM.setId(mCollageMarketingModel.getId());
                mCM.setName(mCollageMarketingModel.getName());
                mCM.setProfile(mCollageMarketingModel.getProfile());
                if (mCollageMarketingModel.getEnabledFlag() == 1) {
                    mCM.setBeginTime(new Date());
                }
                mCM.setBeginTime(DateFormatUtil.parseDate(mCollageMarketingModel.getBeginTime(), DateFormatUtil.FORMAT_FULL_DATE));
                mCM.setEndTime(DateFormatUtil.parseDate(mCollageMarketingModel.getEndTime(), DateFormatUtil.FORMAT_FULL_DATE));
                mCM.setProductId(mCollageMarketingModel.getProductId());
                mCM.setProductName(mCollageMarketingModel.getProductName());
                mCM.setCollagePeopleCount(mCollageMarketingModel.getCollagePeopleCount());
                mCM.setCollagePrice(new BigDecimal(mCollageMarketingModel.getCollagePrice()));
                mCM.setOriginalPrice(new BigDecimal(mCollageMarketingModel.getOriginalPrice()));
                mCM.setImgUrl(mCollageMarketingModel.getImgUrl());
                mCM.setMaxOpenGroup(mCollageMarketingModel.getMaxOpenGroup());
                mCM.setEnabledFlag(mCollageMarketingModel.getEnabledFlag());
                mCM.setDescribe(mCollageMarketingModel.getDescribe());
                mCM.setValidityPeriod(DateFormatUtil.parseDate(mCollageMarketingModel.getValidityPeriod(), DateFormatUtil.FORMAT_FULL_DATE));
                mCM.setServerUpdateTime(new Date());
                mCM.setUpdatorId(creatorId);
                mCM.setUpdatorName(creatorName);

                isSuccess = mCollageMarketingService.modifyCollage(mCM);
            }
            if (isSuccess) {
                return redirect("/collageMarketing/queryListData");
            } else {
                return "fail";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }


    }

    /**
     * 修改活动状态
     *
     * @param model
     * @param mCollageMarketingModel
     * @return
     */
    @RequestMapping("/updateState")
    public String updateState(Model model, CollageMarketingModel mCollageMarketingModel) {

        try {
            Boolean isSuccess = mCollageMarketingService.updateState(mCollageMarketingModel.getId(), mCollageMarketingModel.getEnabledFlag());
            if (isSuccess) {
                return redirect("/collageMarketing/queryListData");
            } else {
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * 根据Id删除拼团活动
     *
     * @param model
     * @param mCollageMarketingModel
     * @return
     */
    @RequestMapping("/deleteById")
    public String deleteById(Model model, CollageMarketingModel mCollageMarketingModel) {
        try {
            if(mCollageMarketingModel.getId() == null){
                return "fail";
            }
            Boolean isSuccess = mCollageMarketingService.deleteCollageById(mCollageMarketingModel.getId());
            if (isSuccess) {
                return "success";
            } else {
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

    }
}

