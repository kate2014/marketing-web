package com.zhongmei.yunfu.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.ActivitySearchModel;
import com.zhongmei.yunfu.controller.model.FlashSalesModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.FlashSalesMarketingEntity;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.domain.entity.WxTradeCustomerEntity;
import com.zhongmei.yunfu.service.FlashSalesMarketingService;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.service.OperationalRecordsService;
import com.zhongmei.yunfu.service.WxTradeCustomerService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 秒杀活动 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-09-10
 */
@Controller
@RequestMapping("/flashSalesEffect")
public class FlashSalesEffectController extends BaseController{

    @Autowired
    OperationalRecordsService mOperationalRecordsService;
    @Autowired
    WxTradeCustomerService mWxTradeCustomerService;

    @RequestMapping("/effect")
    public String effect(Model model, ActivitySearchModel searchModel){

        try {
            //获取活动效果数据
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            OperationalRecordsEntity entity = new OperationalRecordsEntity();

            entity.setBrandIdenty(brandIdentity);
            entity.setShopIdenty(shopIdentity);
            entity.setActivityId(searchModel.getActivityId());
            entity.setSource(5);

            List<OperationalRecordsEntity> listCount = mOperationalRecordsService.queryEffectCount(entity);

            OperationalRecordsEntity seeCount = new OperationalRecordsEntity();
            seeCount.setId(0l);
            seeCount.setOperationalCount(0);

            OperationalRecordsEntity shareCount = new OperationalRecordsEntity();
            shareCount.setId(0l);
            shareCount.setOperationalCount(0);

            OperationalRecordsEntity salesCount = new OperationalRecordsEntity();
            salesCount.setId(0l);
            salesCount.setOperationalCount(0);

            for(OperationalRecordsEntity effect : listCount){

                if(effect.getType() == 1){
                    seeCount = effect;
                }else if(effect.getType() == 2){
                    shareCount = effect;
                }

            }

            TradeModel mTradeModel = new TradeModel();
            mTradeModel.setBrandIdenty(brandIdentity);
            mTradeModel.setShopIdenty(shopIdentity);
            mTradeModel.setMarketingId(searchModel.getActivityId());
            mTradeModel.setType(3);
            //获取秒杀成功信息
            List<WxTradeCustomerEntity> listSalseData = mWxTradeCustomerService.querySalseDetail(mTradeModel);

            if(listSalseData != null && listSalseData.size() > 0){
                Integer salseCount = mWxTradeCustomerService.querySalseCount(mTradeModel);
                salesCount.setId(Long.valueOf(listSalseData.size()));
                salesCount.setOperationalCount(salseCount);
            }


            model.addAttribute("searchModel", searchModel);

            model.addAttribute("seeCount",seeCount);
            model.addAttribute("shareCount",shareCount);
            model.addAttribute("salesCount",salesCount);

            List<Long> persionCount = new LinkedList<>();
            List<Integer> optionCount = new LinkedList<>();

            persionCount.add(seeCount.getId());
            optionCount.add(seeCount.getOperationalCount());

            persionCount.add(shareCount.getId());
            optionCount.add(shareCount.getOperationalCount());

            persionCount.add(salesCount.getId());
            optionCount.add(salesCount.getOperationalCount());

            model.addAttribute("persionCount",persionCount);
            model.addAttribute("optionCount",optionCount);

            model.addAttribute("searchModel",searchModel);

            return "flash_sales_effect";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }


    @RequestMapping("/browseDetail")
    public String effectDetail(Model model,ActivitySearchModel searchModel){

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            OperationalRecordsEntity entity = new OperationalRecordsEntity();
            entity.setBrandIdenty(brandIdentity);
            entity.setShopIdenty(shopIdentity);
            entity.setActivityId(searchModel.getActivityId());
            entity.setType(searchModel.getType());
            entity.setSource(5);
            entity.setCustomerName(searchModel.getCustomerName());
            entity.setCustomerPhone(searchModel.getCustomerPhone());
            entity.setOperationalCount(searchModel.getOperationalCount());

            Page<OperationalRecordsEntity> listPage = mOperationalRecordsService.queryByActivityId(entity,searchModel.getPageNo(),searchModel.getPageSize());
            setWebPage(model, "/flashSalesEffect/browseDetail", listPage, searchModel);

            model.addAttribute("listData", listPage.getRecords());
            model.addAttribute("searchModel", searchModel);
            return "flash_sales_effect_detail";

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }


    /**
     * 参团情况详情
     * @param model
     * @param searchModel
     * @return
     */
    @RequestMapping("/finishFlashSalesEffect")
    public String finishCollageEffect(Model model,ActivitySearchModel searchModel){
        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
            OperationalRecordsEntity entity = new OperationalRecordsEntity();
            entity.setBrandIdenty(brandIdentity);
            entity.setShopIdenty(shopIdentity);
            entity.setActivityId(searchModel.getActivityId());
            entity.setCustomerName(searchModel.getCustomerName());
            entity.setCustomerPhone(searchModel.getCustomerPhone());
            entity.setOperationalCount(searchModel.getOperationalCount());
            entity.setType(3);//wxtradeCustomer中type为3表示秒杀

            List<OperationalRecordsEntity> listData = mOperationalRecordsService.querySalesEffect(entity);

            model.addAttribute("searchModel", searchModel);
            model.addAttribute("listData", listData);

            return "flash_sales_finish_effect";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

}
