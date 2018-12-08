package com.zhongmei.yunfu.controller;

import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.ui.Model;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.FlashSalesModel;
import com.zhongmei.yunfu.domain.entity.FlashSalesMarketingEntity;
import com.zhongmei.yunfu.service.FlashSalesMarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * <p>
 * 秒杀活动 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-09-10
 */
@Controller
@RequestMapping("/flashSalesMarketing")
public class FlashSalesMarketingController extends BaseController{

    @Autowired
    FlashSalesMarketingService mFlashSalesMarketingService;

    /**
     * 进入到秒杀活动界面
     *
     * @return
     */
    @RequestMapping("/queryListData")
    public String queryListData(Model model, FlashSalesModel mFlashSalesModel) {

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            mFlashSalesModel.setBrandIdentity(brandIdentity);
            mFlashSalesModel.setShopIdentity(shopIdentity);
            mFlashSalesModel.setStatusFlag(1);


            Page<FlashSalesMarketingEntity> listPage = mFlashSalesMarketingService.queryFlashSalesList(mFlashSalesModel);
            setWebPage(model, "/flashSalesMarketing/queryListData", listPage, mFlashSalesModel);
            model.addAttribute("mFlashSalesModel", mFlashSalesModel);
            model.addAttribute("list", listPage.getRecords());

            return "flash_sales_marketing_list";

        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * @param model
     * @param mFlashSalesModel
     * @return
     */
    @RequestMapping("/gotoModify")
    public String gotoModify(Model model, FlashSalesModel mFlashSalesModel) {

        try {
            if (mFlashSalesModel.getId() != null) {
                FlashSalesMarketingEntity mFlashSalesMarketing = mFlashSalesMarketingService.findFlashSalesById(mFlashSalesModel.getId());
                model.addAttribute("mFlashSalesModel", mFlashSalesMarketing);
            } else {
                model.addAttribute("mFlashSalesModel", mFlashSalesModel);
            }
            return "flash_sales_marketing_add";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * 编辑、添加秒杀活动
     *
     * @param model
     * @param mFlashSalesModel
     * @return
     */
    @RequestMapping("/modifyData")
    public String modifyData(Model model, FlashSalesModel mFlashSalesModel) {
        try {
            Boolean isSuccess = true;

            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
            Long creatorId = LoginManager.get().getUser().getCreatorId();
            String creatorName = LoginManager.get().getUser().getCreatorName();

            if (mFlashSalesModel.getId() == null) {
                FlashSalesMarketingEntity mFlashSalesMarketing = new FlashSalesMarketingEntity();
                mFlashSalesMarketing.setName(mFlashSalesModel.getName());
                mFlashSalesMarketing.setProfile(mFlashSalesModel.getProfile());
                mFlashSalesMarketing.setBeginTime(DateFormatUtil.parseDate(mFlashSalesModel.getBeginTime(), DateFormatUtil.FORMAT_FULL_DATE));
                mFlashSalesMarketing.setEndTime(DateFormatUtil.parseDate(mFlashSalesModel.getEndTime(), DateFormatUtil.FORMAT_FULL_DATE));
                mFlashSalesMarketing.setValidityPeriod(DateFormatUtil.parseDate(mFlashSalesModel.getValidityPeriod(), DateFormatUtil.FORMAT_FULL_DATE));
                mFlashSalesMarketing.setOriginalPrice(mFlashSalesModel.getOriginalPrice());
                mFlashSalesMarketing.setFlashPrice(mFlashSalesModel.getFlashPrice());
                mFlashSalesMarketing.setProductId(mFlashSalesModel.getProductId());
                mFlashSalesMarketing.setProductName(mFlashSalesModel.getProductName());
                mFlashSalesMarketing.setImgUrl(mFlashSalesModel.getImgUrl());
                mFlashSalesMarketing.setDescribe(mFlashSalesModel.getDescribe());
                mFlashSalesMarketing.setCustomerBuyCount(mFlashSalesModel.getCustomerBuyCount());
                mFlashSalesMarketing.setSalesCount(mFlashSalesModel.getSalesCount());
                mFlashSalesMarketing.setSoldCount(0);
                mFlashSalesMarketing.setEnabledFlag(mFlashSalesModel.getEnabledFlag());
                mFlashSalesMarketing.setBrandIdentity(brandIdentity);
                mFlashSalesMarketing.setShopIdentity(shopIdentity);
                mFlashSalesMarketing.setCreatorId(creatorId);
                mFlashSalesMarketing.setCreatorName(creatorName);
                mFlashSalesMarketing.setStatusFlag(1);

                mFlashSalesMarketing.setServerCreateTime(new Date());
                mFlashSalesMarketing.setServerUpdateTime(new Date());
                isSuccess = mFlashSalesMarketingService.createFlashSales(mFlashSalesMarketing);

            } else {
                FlashSalesMarketingEntity mFlashSalesMarketing = new FlashSalesMarketingEntity();
                mFlashSalesMarketing.setId(mFlashSalesModel.getId());
                mFlashSalesMarketing.setName(mFlashSalesModel.getName());
                mFlashSalesMarketing.setProfile(mFlashSalesModel.getProfile());
                mFlashSalesMarketing.setBeginTime(DateFormatUtil.parseDate(mFlashSalesModel.getBeginTime(), DateFormatUtil.FORMAT_FULL_DATE));
                mFlashSalesMarketing.setEndTime(DateFormatUtil.parseDate(mFlashSalesModel.getEndTime(), DateFormatUtil.FORMAT_FULL_DATE));
                mFlashSalesMarketing.setValidityPeriod(DateFormatUtil.parseDate(mFlashSalesModel.getValidityPeriod(), DateFormatUtil.FORMAT_FULL_DATE));
                mFlashSalesMarketing.setOriginalPrice(mFlashSalesModel.getOriginalPrice());
                mFlashSalesMarketing.setFlashPrice(mFlashSalesModel.getFlashPrice());
                mFlashSalesMarketing.setProductId(mFlashSalesModel.getProductId());
                mFlashSalesMarketing.setProductName(mFlashSalesModel.getProductName());
                mFlashSalesMarketing.setImgUrl(mFlashSalesModel.getImgUrl());
                mFlashSalesMarketing.setDescribe(mFlashSalesModel.getDescribe());
                mFlashSalesMarketing.setCustomerBuyCount(mFlashSalesModel.getCustomerBuyCount());
                mFlashSalesMarketing.setSalesCount(mFlashSalesModel.getSalesCount());
                mFlashSalesMarketing.setEnabledFlag(mFlashSalesModel.getEnabledFlag());
                mFlashSalesMarketing.setUpdatorId(creatorId);
                mFlashSalesMarketing.setUpdatorName(creatorName);
                mFlashSalesMarketing.setServerUpdateTime(new Date());
                isSuccess = mFlashSalesMarketingService.modifyFlashSales(mFlashSalesMarketing);
            }

            if (isSuccess) {
                return redirect("/flashSalesMarketing/queryListData");
            } else {
                return "fail";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * 更新秒杀活动状态
     *
     * @param model
     * @param mFlashSalesModel
     * @return
     */
    @RequestMapping("/updateState")
    public String updateState(Model model, FlashSalesModel mFlashSalesModel) {
        try {
            Boolean isSuccess = mFlashSalesMarketingService.updateState(mFlashSalesModel.getId(), mFlashSalesModel.getEnabledFlag());
            if (isSuccess) {
                return redirect("/flashSalesMarketing/queryListData");
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
     * @param mFlashSalesModel
     * @return
     */
    @RequestMapping("/deleteById")
    public String deleteById(Model model, FlashSalesModel mFlashSalesModel) {
        try {
            Boolean isSuccess = mFlashSalesMarketingService.deleteFlashSalesById(mFlashSalesModel.getId());
            if (isSuccess) {
                return redirect("/flashSalesMarketing/queryListData");
            } else {
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}

