package com.zhongmei.yunfu.controller.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.api.model.ActivityPushReq;
import com.zhongmei.yunfu.controller.model.ActivitySearchModel;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.domain.entity.PushPlanActivityEntity;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.OperationalRecordsService;
import com.zhongmei.yunfu.service.PushPlanActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;


/**
 * 活动推送接口
 */
@RestController
@RequestMapping("/wxapp/pushPlanActivity")
public class PushActivityController {

    private static Logger log = LoggerFactory.getLogger(PushActivityController.class);

    @Autowired
    PushPlanActivityService pushPlanActivityService;

    @Autowired
    OperationalRecordsService mOperationalRecordsService;

    @Autowired
    CustomerService mCustomerService;

    /**
     * 获取门店活动数据
     *
     * @param model
     * @param mActivitySearchModel
     * @return
     */
    @GetMapping("/queryActivityData")
    public BaseDataModel queryActivityData(ModelMap model, ActivitySearchModel mActivitySearchModel) {

        Page<PushPlanActivityEntity> listPage = pushPlanActivityService.findListPage(mActivitySearchModel.getBrandIdenty(), mActivitySearchModel.getShopIdenty(), mActivitySearchModel.getPlanState(), null,null, mActivitySearchModel.getPageNo(), mActivitySearchModel.getPageSize());
        BaseDataModel mBaseDataModel = new BaseDataModel();
        if (listPage != null && listPage.getSize() != 0) {
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取门店活动信息成功");
            mBaseDataModel.setData(listPage);
        } else {
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("商户还未配置门店活动");
            mBaseDataModel.setData(listPage);
        }

        return mBaseDataModel;
    }

    /**
     * 根据Id获取活动详情
     *
     * @param model
     * @param mActivityPushReq
     * @return
     */
    @GetMapping("/findActivityDetail")
    public BaseDataModel findActivityData(ModelMap model, ActivityPushReq mActivityPushReq) {

        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            PushPlanActivityEntity mPushPlanActivity = pushPlanActivityService.findActivityById(mActivityPushReq.getId());

            if (mPushPlanActivity != null) {
                //添加浏览次数
                pushPlanActivityService.updateActivityNumber(mActivityPushReq.getId(), mPushPlanActivity.getScanNumber() + 1, null);


                //添加顾客查看记录,如该顾客对该条活跃已有同样的操作是，只需在原有操作次数的基础上+1
                if(mActivityPushReq.getWxOpenId() != null){
                    OperationalRecordsEntity orEntity = new OperationalRecordsEntity();
                    orEntity.setBrandIdenty(mActivityPushReq.getBrandIdenty());
                    orEntity.setShopIdenty(mActivityPushReq.getShopIdenty());
                    orEntity.setWxOpenId(mActivityPushReq.getWxOpenId());
                    orEntity.setCustomerId(mActivityPushReq.getCustomerId());
                    orEntity.setActivityId(mActivityPushReq.getId());
                    orEntity.setSource(3);
                    orEntity.setType(1);
                    OperationalRecordsEntity recordEntity = mOperationalRecordsService.queryByCustomer(orEntity);
                    //判断用户是否是第一次浏览
                    if(recordEntity == null){
                        //获取查看用户基本信息
                        CustomerEntity mCustomerEntity = new CustomerEntity();
                        mCustomerEntity.setBrandIdenty(mActivityPushReq.getBrandIdenty());
                        mCustomerEntity.setShopIdenty(mActivityPushReq.getShopIdenty());
                        mCustomerEntity.setId(mActivityPushReq.getCustomerId());
                        Map<String, String> tempMap =  mCustomerService.queryByWxCustomerId(mCustomerEntity);

                        orEntity = new OperationalRecordsEntity();
                        orEntity.setBrandIdenty(mActivityPushReq.getBrandIdenty());
                        orEntity.setShopIdenty(mActivityPushReq.getShopIdenty());
                        orEntity.setCustomerId(mActivityPushReq.getCustomerId());
                        orEntity.setCustomerPhone(tempMap.get("pPhone"));
                        orEntity.setCustomerName(tempMap.get("pName"));
                        orEntity.setWxOpenId(mActivityPushReq.getWxOpenId());
                        orEntity.setWxPhoto(tempMap.get("photo"));
                        orEntity.setWxName(tempMap.get("wName"));
                        orEntity.setActivityId(mActivityPushReq.getId());
                        orEntity.setOperationalCount(1);
                        orEntity.setType(1);
                        orEntity.setSource(3);
                        orEntity.setServerCreateTime(new Date());
                        orEntity.setServerUpdateTime(new Date());
                        mOperationalRecordsService.addOperational(orEntity);
                    }else{

                        OperationalRecordsEntity modifityEntity = new OperationalRecordsEntity();


                        //如果之前用户浏览信息中没有会员电话或名称，这可以进行后续补全
                        if(recordEntity.getCustomerPhone() == null || recordEntity.getCustomerPhone().equals("")){
                            //获取查看用户基本信息
                            CustomerEntity mCustomerEntity = new CustomerEntity();
                            mCustomerEntity.setBrandIdenty(mActivityPushReq.getBrandIdenty());
                            mCustomerEntity.setShopIdenty(mActivityPushReq.getShopIdenty());
                            mCustomerEntity.setId(mActivityPushReq.getCustomerId());
                            Map<String, String> tempMap =  mCustomerService.queryByWxCustomerId(mCustomerEntity);

                            modifityEntity.setCustomerPhone(tempMap.get("pPhone"));
                            modifityEntity.setCustomerName(tempMap.get("pName"));
                        }

                        modifityEntity.setId(recordEntity.getId());
                        modifityEntity.setOperationalCount(recordEntity.getOperationalCount()+1);
                        modifityEntity.setServerUpdateTime(new Date());
                        mOperationalRecordsService.modiftyById(modifityEntity);
                    }
                }
                
                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("获取活动详情成功");
                mBaseDataModel.setData(mPushPlanActivity);
            } else {
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("获取活动详情失败");
                mBaseDataModel.setData(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取活动详情失败");
            mBaseDataModel.setData(false);
        }


        return mBaseDataModel;
    }

    /**
     * 添加分析次数
     *
     * @param model
     * @param mActivitySearchModel
     * @return
     */
    @GetMapping("/addShareCount")
    public BaseDataModel shareCount(ModelMap model, ActivitySearchModel mActivitySearchModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            Boolean isSuccess = pushPlanActivityService.updateActivityNumber(mActivitySearchModel.getId(), null, 1);
            //用于调用发放优惠券
            mActivitySearchModel.getCustomerId();
            mActivitySearchModel.getWXOpenId();

            if (isSuccess) {
                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("分享成功");
                mBaseDataModel.setData(true);
            } else {
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("分享失败");
                mBaseDataModel.setData(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("分享失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

}
