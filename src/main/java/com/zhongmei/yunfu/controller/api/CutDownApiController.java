package com.zhongmei.yunfu.controller.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.api.model.CutDownReq;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.ToolsUtil;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.CutDownHistoryModel;
import com.zhongmei.yunfu.controller.model.CutDownModel;
import com.zhongmei.yunfu.domain.entity.CutDownCustomerEntity;
import com.zhongmei.yunfu.domain.entity.CutDownHistoryEntity;
import com.zhongmei.yunfu.domain.entity.CutDownMarketingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 砍价活动相关api
 */
@RestController
@RequestMapping("/wxapp/cutDown")
public class CutDownApiController {

    @Autowired
    CutDownMarketingService mCutDownMarketingService;
    @Autowired
    CutDownCustomerService mCutDownCustomerService;
    @Autowired
    CutDownHistoryService mCutDownHistoryService;
    @Autowired
    CustomerCouponService mCustomerCouponService;
    @Autowired
    OperationalRecordsService mOperationalRecordsService;

    @GetMapping("/getListData")
    public BaseDataModel getCutDownList(ModelMap model, CutDownModel mCutDownModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {

            mCutDownModel.setEnabledFlag(1);
            Page<CutDownMarketingEntity> listData = mCutDownMarketingService.queryCutDownList(mCutDownModel);
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取砍价数据成功");
            mBaseDataModel.setData(listData);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取砍价数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    @GetMapping("/cutDownDetail")
    public BaseDataModel queryCutDownDetail(ModelMap model, CutDownReq CutDownReq) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            CutDownMarketingEntity mCutDownMarketing = mCutDownMarketingService.findCutDownById(CutDownReq.getId());

            //添加顾客查看记录,如该顾客对该条活跃已有同样的操作是，只需在原有操作次数的基础上+1
            if(CutDownReq.getWxOpenId() != null){
                OperationalRecordsEntity orEntity = new OperationalRecordsEntity();
                orEntity.setBrandIdenty(CutDownReq.getBrandIdenty());
                orEntity.setShopIdenty(CutDownReq.getShopIdenty());
                orEntity.setWxOpenId(CutDownReq.getWxOpenId());
                orEntity.setActivityId(CutDownReq.getId());
                orEntity.setType(1);
                OperationalRecordsEntity recordEntity = mOperationalRecordsService.queryByCustomer(orEntity);
                if(recordEntity == null){
                    orEntity = new OperationalRecordsEntity();
                    orEntity.setBrandIdenty(CutDownReq.getBrandIdenty());
                    orEntity.setShopIdenty(CutDownReq.getShopIdenty());
                    orEntity.setCustomerId(CutDownReq.getCustomerId());
                    orEntity.setCustomerPhone(CutDownReq.getCustomerPhone());
                    orEntity.setCustomerName(CutDownReq.getCustomerName());
                    orEntity.setWxOpenId(CutDownReq.getWxOpenId());
                    orEntity.setWxPhoto(CutDownReq.getWxPhoto());
                    orEntity.setWxName(CutDownReq.getWxName());
                    orEntity.setActivityId(CutDownReq.getId());
                    orEntity.setOperationalCount(1);
                    orEntity.setType(1);
                    orEntity.setServerCreateTime(new Date());
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.addOperational(orEntity);
                }else{
                    orEntity.setBrandIdenty(CutDownReq.getBrandIdenty());
                    orEntity.setShopIdenty(CutDownReq.getShopIdenty());
                    orEntity.setWxOpenId(CutDownReq.getWxOpenId());
                    orEntity.setWxPhoto(CutDownReq.getWxPhoto());
                    orEntity.setActivityId(CutDownReq.getId());
                    orEntity.setOperationalCount(recordEntity.getOperationalCount()+1);
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.modiftyOperational(orEntity);
                }
            }
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取砍价详情成功");
            mBaseDataModel.setData(mCutDownMarketing);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取砍价详情失败");
            mBaseDataModel.setData(false);
        }
        return mBaseDataModel;
    }

    /**
     * 发起砍价活动
     *
     * @param model
     * @param mCutDownCustomer
     * @return
     */
    @GetMapping("/createCutDown")
    public BaseDataModel cutDownAction(ModelMap model, CutDownCustomerEntity mCutDownCustomer) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            CutDownMarketingEntity mCutDownMarketingEntity = mCutDownMarketingService.findCutDownDatailById(mCutDownCustomer.getCutDownId());
            if(mCutDownMarketingEntity.getSalesCount() <= mCutDownMarketingEntity.getSoldCount()){
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("该砍价活动已经售罄");
                mBaseDataModel.setData(mCutDownCustomer);
                return mBaseDataModel;
            }
            if(mCutDownMarketingEntity.getBeginTime().getTime() > new Date().getTime()){
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("活动还未开始");
                mBaseDataModel.setData(mCutDownCustomer);
                return mBaseDataModel;
            }
            if(mCutDownMarketingEntity.getEndTime().getTime() < new Date().getTime()){
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("活动已结束");
                mBaseDataModel.setData(mCutDownCustomer);
                return mBaseDataModel;
            }

            mCutDownCustomer.setState(1);
            mCutDownCustomer.setJoinCount(0);
            if(mCutDownCustomer.getWxName() != null){
                String name = ToolsUtil.filterEmoji(mCutDownCustomer.getWxName(),"?");
                mCutDownCustomer.setWxName(name);
            }

            Boolean isSuccess = mCutDownCustomerService.installData(mCutDownCustomer);
            if (isSuccess) {
                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("发起砍价操作成功");
                mBaseDataModel.setData(mCutDownCustomer);
            } else {
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("发起砍价操作失败");
                mBaseDataModel.setData(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("发起砍价操作失败");
            mBaseDataModel.setData(false);
        }


        return mBaseDataModel;
    }


    /**
     * 参与砍价
     *
     * @param model
     * @param mCutDownHistory
     * @return
     */
    @GetMapping("/joinCutDown")
    public BaseDataModel joinCutDown(ModelMap model, CutDownHistoryEntity mCutDownHistory) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {

            mCutDownHistory.setServerCreateTime(new Date());

            //获取发起的砍价信息
            CutDownCustomerEntity mCutDownCustomer = mCutDownCustomerService.queryDataById(mCutDownHistory.getRelationId());
            if (mCutDownCustomer.getState() == 2) {
                mBaseDataModel.setState("1002");
                mBaseDataModel.setMsg("活动已砍到底价");
                mBaseDataModel.setData(true);
                return mBaseDataModel;
            }


            int count = mCutDownHistoryService.queryJoinCount(mCutDownHistory);

            if(count > 0){
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("每人仅限于参与一次砍价活动");
                mBaseDataModel.setData(true);
                return mBaseDataModel;
            }


            BigDecimal currentPrice = mCutDownCustomer.getCurrentPrice();

            //获取砍价区间
            CutDownMarketingEntity mCutDownMarketing = mCutDownMarketingService.findCutPice(mCutDownHistory.getCutDownId());
            BigDecimal randomPriceMax = mCutDownMarketing.getRandomPriceMax();
            BigDecimal randomPriceMini = mCutDownMarketing.getRandomPriceMini();
            BigDecimal endPrice = mCutDownMarketing.getEndPrice();


            double cutValue;
            //剩余多少金额可进行砍价
            BigDecimal subtract = currentPrice.subtract(endPrice);
            //如果剩余可看价格小于最大随机砍价区间，这个直接砍到底价
            if (randomPriceMax.compareTo(subtract) >= 0) {
                cutValue = subtract.doubleValue();

            } else {
                cutValue = ToolsUtil.getRandomData(randomPriceMini.longValue(), randomPriceMax.longValue());

            }

            //随机进行砍价
            mCutDownHistory.setCutPrice(BigDecimal.valueOf(cutValue));

            if(mCutDownHistory.getWxName() != null){
                String name = ToolsUtil.filterEmoji(mCutDownHistory.getWxName(),"?");
                mCutDownHistory.setWxName(name);
            }

            Boolean isSuccess = mCutDownHistoryService.installData(mCutDownHistory);
            //更改发起的砍价信息
            //如果 当前价格 - 砍价 > 最终底价
            if (currentPrice.subtract(BigDecimal.valueOf(cutValue)).compareTo(endPrice) > 0) {
                mCutDownCustomer.setCurrentPrice(currentPrice.subtract(BigDecimal.valueOf(cutValue)));

            } else {
                //表示最后一次砍价到底价
                mCutDownCustomer.setState(2);
                mCutDownCustomer.setCurrentPrice(endPrice);
            }
            //参与人数加 1
            mCutDownCustomer.setJoinCount(mCutDownCustomer.getJoinCount() + 1);
            mCutDownCustomerService.updateData(mCutDownCustomer);


            if (isSuccess) {
                //对参与砍价好友发放优惠券
                //添加成功后判断是否需要推送新人优惠券
                if(mCutDownHistory.getCustomerId() != null){
                    mCustomerCouponService.putOnCoupon(mCutDownHistory.getBrandIdentity(), mCutDownHistory.getShopIdentity(),mCutDownHistory.getCustomerId(),mCutDownHistory.getOpenId(), 3,2);
                }

                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("参与砍价成功");
                mBaseDataModel.setData(mCutDownHistory);
            } else {
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("参与砍价失败");
                mBaseDataModel.setData(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("参与砍价失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 获取活动参与砍价信息
     *
     * @param model
     * @param mCutDownHistory
     * @return
     */
    @GetMapping("/queryCutDownHistory")
    public BaseDataModel queryCutDownHistory(ModelMap model, CutDownHistoryEntity mCutDownHistory) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {

            CutDownHistoryModel mCutDownHistoryModel = new CutDownHistoryModel();

            //获取活动当前砍价情况
            CutDownCustomerEntity mCutDownCustomer = mCutDownCustomerService.queryDataById(mCutDownHistory.getRelationId());
            mCutDownHistoryModel.setmCutDownCustomer(mCutDownCustomer);

            CutDownMarketingEntity mCutDownMarketing = mCutDownMarketingService.findCutDownDatailById(mCutDownCustomer.getCutDownId());
            mCutDownHistoryModel.setmCutDownMarketing(mCutDownMarketing);


            List<CutDownHistoryEntity> listData = mCutDownHistoryService.queryDataList(mCutDownHistory);
            mCutDownHistoryModel.setListData(listData);

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取数据成功");
            mBaseDataModel.setData(mCutDownHistoryModel);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }
}
