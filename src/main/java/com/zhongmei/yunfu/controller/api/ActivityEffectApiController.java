package com.zhongmei.yunfu.controller.api;


import com.zhongmei.yunfu.controller.api.model.ActivityGiftEffectResp;
import com.zhongmei.yunfu.controller.api.model.ActivitySalesReq;
import com.zhongmei.yunfu.controller.api.model.ActivitySalesResp;
import com.zhongmei.yunfu.controller.model.ActivityEffectModel;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.service.impl.RedPacketsRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/wxapp/activityEffect")
public class ActivityEffectApiController {

    @Autowired
    ActivitySalesGiftService mActivitySalesGiftService;

    @Autowired
    CustomerCouponService mCustomerCouponService;

    @Autowired
    RedPacketsRecordService mRedPacketsRecordService;
    /**
     * 获取会员活动礼品获取情况
     * @param model
     * @param mActivitySalesReq
     * @return
     */
    @GetMapping("/queryGift")
    public BaseDataModel queryGift(ModelMap model, ActivitySalesReq mActivitySalesReq) {
        BaseDataModel responseMode = new BaseDataModel();
        try {
            //1、获取会员已推荐成单的活动
            ActivityEffectModel mActivityEffectModel = new ActivityEffectModel();
            mActivityEffectModel.setBrandIdenty(mActivitySalesReq.getBrandIdenty());
            mActivityEffectModel.setShopIdenty(mActivitySalesReq.getShopIdenty());
            mActivityEffectModel.setCustomerId(mActivitySalesReq.getCustomerId());

            List<ActivityGiftEffectResp> listGift = mActivitySalesGiftService.queryCustomerGift(mActivityEffectModel);

            //获取活动礼品获取情况
            List<CustomerCouponEntity> listCC = mCustomerCouponService.queryActivityGfitByCustomer(mActivitySalesReq.getBrandIdenty(),mActivitySalesReq.getShopIdenty(),mActivitySalesReq.getCustomerId(),null);

            for(ActivityGiftEffectResp ageEntity : listGift){
                if(ageEntity.getFinishCount() >= ageEntity.getOrderCount()){ //表示已经大的推荐成单获取礼品条件
                    ageEntity.setFinishStatus("已获取一件礼品");

                    for(CustomerCouponEntity cc : listCC){
                        if(ageEntity.getActivityId().longValue() == cc.getActivityId().longValue() && ageEntity.getGiftName().equals(cc.getCouponName())){
                            if(cc.getStatus() == 1){
                                ageEntity.setStatus("未兑换");
                            }else if(cc.getStatus() == 2){
                                ageEntity.setStatus("已兑换");
                            }
                        }
                    }
                }else{
                    ageEntity.setFinishStatus("您未达礼品兑换条件，快去邀请好友吧");
                }
            }

            responseMode.setMsg("数据获取成功");
            responseMode.setData(listGift);
            responseMode.setState("1000");
        } catch (Exception ex) {
            ex.printStackTrace();
            responseMode.setData(false);
            responseMode.setMsg("数据获取失败：" + ex.getMessage());
            responseMode.setState("1001");
        }
        return responseMode;
    }

    @GetMapping("/queryRedPackets")
    public BaseDataModel queryRedPackets(ModelMap model, ActivitySalesReq mActivitySalesReq) {
        BaseDataModel responseMode = new BaseDataModel();
        try {
            RedPacketsRecordEntity mRPRentity = new RedPacketsRecordEntity();
            mRPRentity.setBrandIdenty(mActivitySalesReq.getBrandIdenty());
            mRPRentity.setShopIdenty(mActivitySalesReq.getShopIdenty());
            mRPRentity.setCustomerId(mActivitySalesReq.getCustomerId());
            List<RedPacketsRecordEntity> listRedPackets = mRedPacketsRecordService.queryRedPacketsByCustomer(mRPRentity);

            responseMode.setMsg("数据获取成功");
            responseMode.setData(listRedPackets);
            responseMode.setState("1000");
        }catch (Exception ex) {
            ex.printStackTrace();
            responseMode.setData(false);
            responseMode.setMsg("数据获取失败：" + ex.getMessage());
            responseMode.setState("1001");
        }
        return responseMode;


    }


}
