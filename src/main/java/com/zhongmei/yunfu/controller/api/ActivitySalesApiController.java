package com.zhongmei.yunfu.controller.api;


import com.zhongmei.yunfu.controller.api.model.ActivitySalesReq;
import com.zhongmei.yunfu.controller.api.model.ActivitySalesResp;
import com.zhongmei.yunfu.controller.model.ActivitySalesModel;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
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
@RequestMapping("/wxapp/activitySales")
public class ActivitySalesApiController {

    @Autowired
    ActivitySalesService mActivitySalesService;

    @Autowired
    ActivitySalesGiftService mActivitySalesGiftService;

    @Autowired
    OperationalRecordsService mOperationalRecordsService;

    @Autowired
    RecommendationAssociationService mRecommendationAssociationService;

    @Autowired
    RedPacketsRecordServiceImpl mRedPacketsRecordServiceImpl;

    @Autowired
    WxTradeCustomerService mWxTradeCustomerService;

    @GetMapping("/list")
    public BaseDataModel queryList(ModelMap model, ActivitySalesReq mActivitySalesReq) {
        BaseDataModel responseMode = new BaseDataModel();
        try {
            ActivitySalesEntity mActivitySalesEntity = new ActivitySalesEntity();
            mActivitySalesEntity.setBrandIdenty(mActivitySalesReq.getBrandIdenty());
            mActivitySalesEntity.setShopIdenty(mActivitySalesReq.getShopIdenty());
            mActivitySalesEntity.setEnabledFlag(1);
            List<ActivitySalesEntity> listDate = mActivitySalesService.queryListData(mActivitySalesEntity);

            responseMode.setMsg("数据获取成功");
            responseMode.setData(listDate);
            responseMode.setState("1000");
        } catch (Exception ex) {
            ex.printStackTrace();
            responseMode.setData(false);
            responseMode.setMsg("数据获取失败：" + ex.getMessage());
            responseMode.setState("1001");
        }
        return responseMode;
    }

    @GetMapping("/queryDetail")
    public BaseDataModel queryByid(ModelMap model, ActivitySalesReq mActivitySalesReq) {
        BaseDataModel responseMode = new BaseDataModel();

        try {

            //查询活动基本信息
            ActivitySalesResp mActivitySalesResp = new ActivitySalesResp();
            ActivitySalesEntity mActivitySalesEntity = mActivitySalesService.queryById(mActivitySalesReq.getActivityId());
            mActivitySalesResp.setmActivitySalesEntity(mActivitySalesEntity);

            //获取活动查看数、购买数
            OperationalRecordsEntity mOperationalRecordsEntity = new OperationalRecordsEntity();
            mOperationalRecordsEntity.setBrandIdenty(mActivitySalesReq.getBrandIdenty());
            mOperationalRecordsEntity.setShopIdenty(mActivitySalesReq.getShopIdenty());
            mOperationalRecordsEntity.setActivityId(mActivitySalesReq.getActivityId());
            List<OperationalRecordsEntity> listOperationalCount = mOperationalRecordsService.queryDataByActivityId(mOperationalRecordsEntity);
            //售卖数量统计
            TradeModel mTradeModel = new TradeModel();
            mTradeModel.setBrandIdenty(mActivitySalesReq.getBrandIdenty());
            mTradeModel.setShopIdenty(mActivitySalesReq.getShopIdenty());
            mTradeModel.setMarketingId(mActivitySalesReq.getActivityId());
            mTradeModel.setType(4);
            Integer salesCount = mWxTradeCustomerService.querySalesCount(mTradeModel);

            OperationalRecordsEntity salesOperationalRecords = new OperationalRecordsEntity();
            salesOperationalRecords.setActivityId(mActivitySalesReq.getActivityId());
            salesOperationalRecords.setType(3);
            salesOperationalRecords.setOperationalCount(salesCount);

            listOperationalCount.add(salesOperationalRecords);

            mActivitySalesResp.setListOperationalCount(listOperationalCount);


            //查询活动推荐成单赠礼信息
            ActivitySalesGiftEntity mActivitySalesGiftEntity = new ActivitySalesGiftEntity();
            mActivitySalesGiftEntity.setBrandIdenty(mActivitySalesReq.getBrandIdenty());
            mActivitySalesGiftEntity.setShopIdenty(mActivitySalesReq.getShopIdenty());
            mActivitySalesGiftEntity.setActivityId(mActivitySalesReq.getActivityId());

            List<ActivitySalesGiftEntity> listGift = mActivitySalesGiftService.queryListData(mActivitySalesGiftEntity);
            mActivitySalesResp.setListGift(listGift);

            //查询浏览最新用户数据
            OperationalRecordsEntity entity = new OperationalRecordsEntity();
            entity.setBrandIdenty(mActivitySalesReq.getBrandIdenty());
            entity.setShopIdenty(mActivitySalesReq.getShopIdenty());
            entity.setActivityId(mActivitySalesReq.getActivityId());
            entity.setType(1);
            List<OperationalRecordsEntity> listOperational = mOperationalRecordsService.queryCustomer(entity);
            mActivitySalesResp.setListOperational(listOperational);

            //查询用户推荐成单情况
            RecommendationAssociationEntity raEntity = new RecommendationAssociationEntity();
            raEntity.setBrandIdenty(mActivitySalesReq.getBrandIdenty());
            raEntity.setShopIdenty(mActivitySalesReq.getShopIdenty());
            raEntity.setActivityId(mActivitySalesReq.getActivityId());
            raEntity.setMainWxOpenId(mActivitySalesReq.getWxOpenId());
            raEntity.setTransactionStatus(2);
            List<RecommendationAssociationEntity> listRA = mRecommendationAssociationService.queryListAssociation(raEntity);
            mActivitySalesResp.setTradeCount(listRA.size());

            //查询活动红包获取情况
            RedPacketsRecordEntity mRedPacketsRecordEntity = new RedPacketsRecordEntity();
            mRedPacketsRecordEntity.setBrandIdenty(mActivitySalesReq.getBrandIdenty());
            mRedPacketsRecordEntity.setShopIdenty(mActivitySalesReq.getShopIdenty());
            mRedPacketsRecordEntity.setActivityId(mActivitySalesReq.getActivityId());
            List<RedPacketsRecordEntity> listRedPacketsRecord = mRedPacketsRecordServiceImpl.queryRankingList(mRedPacketsRecordEntity);
            mActivitySalesResp.setListRedPacketsRecord(listRedPacketsRecord);

            //获取已成单用户数据信息
            WxTradeCustomerEntity mWxTradeCustomerEntity = new WxTradeCustomerEntity();
            mWxTradeCustomerEntity.setBrandIdenty(mActivitySalesReq.getBrandIdenty());
            mWxTradeCustomerEntity.setShopIdenty(mActivitySalesReq.getShopIdenty());
            mWxTradeCustomerEntity.setMarketingId(mActivitySalesReq.getActivityId());
            List<WxTradeCustomerEntity> listTrade = mWxTradeCustomerService.queryListByActivity(mWxTradeCustomerEntity);
            mActivitySalesResp.setListTrade(listTrade);



            //添加顾客查看记录,如该顾客对该条活跃已有同样的操作是，只需在原有操作次数的基础上+1
            if(mActivitySalesReq.getWxOpenId() != null){
                OperationalRecordsEntity orEntity = new OperationalRecordsEntity();
                orEntity.setBrandIdenty(mActivitySalesReq.getBrandIdenty());
                orEntity.setShopIdenty(mActivitySalesReq.getShopIdenty());
                orEntity.setWxOpenId(mActivitySalesReq.getWxOpenId());
                orEntity.setActivityId(mActivitySalesReq.getActivityId());
                orEntity.setType(1);
                OperationalRecordsEntity recordEntity = mOperationalRecordsService.queryByCustomer(orEntity);
                if(recordEntity == null){
                    orEntity = new OperationalRecordsEntity();
                    orEntity.setBrandIdenty(mActivitySalesReq.getBrandIdenty());
                    orEntity.setShopIdenty(mActivitySalesReq.getShopIdenty());
                    orEntity.setCustomerId(mActivitySalesReq.getCustomerId());
                    orEntity.setCustomerPhone(mActivitySalesReq.getCustomerPhone());
                    orEntity.setCustomerName(mActivitySalesReq.getCustomerName());
                    orEntity.setWxOpenId(mActivitySalesReq.getWxOpenId());
                    orEntity.setWxPhoto(mActivitySalesReq.getWxPhoto());
                    orEntity.setWxName(mActivitySalesReq.getWxName());
                    orEntity.setActivityId(mActivitySalesReq.getActivityId());
                    orEntity.setOperationalCount(1);
                    orEntity.setType(1);
                    orEntity.setServerCreateTime(new Date());
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.addOperational(orEntity);
                }else{
                    orEntity.setBrandIdenty(mActivitySalesReq.getBrandIdenty());
                    orEntity.setShopIdenty(mActivitySalesReq.getShopIdenty());
                    orEntity.setWxOpenId(mActivitySalesReq.getWxOpenId());
                    orEntity.setWxPhoto(mActivitySalesReq.getWxPhoto());
                    orEntity.setActivityId(mActivitySalesReq.getActivityId());
                    orEntity.setOperationalCount(recordEntity.getOperationalCount()+1);
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.modiftyOperational(orEntity);
                }
            }
            responseMode.setMsg("数据获取成功");
            responseMode.setData(mActivitySalesResp);
            responseMode.setState("1000");

        }catch (Exception ex){
            ex.printStackTrace();
            responseMode.setData(false);
            responseMode.setMsg("数据获取失败：" + ex.getMessage());
            responseMode.setState("1001");
        }
        return responseMode;
    }
}
