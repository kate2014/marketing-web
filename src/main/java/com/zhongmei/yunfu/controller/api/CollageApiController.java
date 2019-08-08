package com.zhongmei.yunfu.controller.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.api.model.CollageReq;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.CollageJoinMessgeModel;
import com.zhongmei.yunfu.controller.model.CollageMarketingModel;
import com.zhongmei.yunfu.domain.entity.CollageCustomerEntity;
import com.zhongmei.yunfu.domain.entity.CollageMarketingEntity;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.service.CollageCustomerService;
import com.zhongmei.yunfu.service.CollageMarketingService;
import com.zhongmei.yunfu.service.OperationalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 拼团活动接口
 */
@RestController
@RequestMapping("/wxapp/collageMarketing")
public class CollageApiController {

    @Autowired
    CollageMarketingService mCollageMarketingService;
    @Autowired
    CollageCustomerService mCollageCustomerService;
    @Autowired
    OperationalRecordsService mOperationalRecordsService;

    @GetMapping("/getListData")
    public BaseDataModel queryListData(ModelMap model, CollageMarketingModel mCollageMarketingModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            mCollageMarketingModel.setEnabledFlag(1);
            Page<CollageMarketingEntity> listData = mCollageMarketingService.queryCollageList(mCollageMarketingModel);
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取拼团数据成功");
            mBaseDataModel.setData(listData);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取拼团数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    @GetMapping("/collageDetail")
    public BaseDataModel collageDetail(ModelMap model, CollageReq mCollageReq) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            CollageMarketingEntity mCollageMarketing = mCollageMarketingService.findCollageById(mCollageReq.getId());

            //添加顾客查看记录,如该顾客对该条活跃已有同样的操作是，只需在原有操作次数的基础上+1
            if(mCollageReq.getWxOpenId() != null){
                OperationalRecordsEntity orEntity = new OperationalRecordsEntity();
                orEntity.setBrandIdenty(mCollageReq.getBrandIdenty());
                orEntity.setShopIdenty(mCollageReq.getShopIdenty());
                orEntity.setWxOpenId(mCollageReq.getWxOpenId());
                orEntity.setActivityId(mCollageReq.getId());
                orEntity.setType(1);
                OperationalRecordsEntity recordEntity = mOperationalRecordsService.queryByCustomer(orEntity);
                if(recordEntity == null){
                    orEntity = new OperationalRecordsEntity();
                    orEntity.setBrandIdenty(mCollageReq.getBrandIdenty());
                    orEntity.setShopIdenty(mCollageReq.getShopIdenty());
                    orEntity.setCustomerId(mCollageReq.getCustomerId());
                    orEntity.setCustomerName(mCollageReq.getCustomerName());
                    orEntity.setWxOpenId(mCollageReq.getWxOpenId());
                    orEntity.setWxPhoto(mCollageReq.getWxPhoto());
                    orEntity.setWxName(mCollageReq.getWxName());
                    orEntity.setActivityId(mCollageReq.getId());
                    orEntity.setOperationalCount(1);
                    orEntity.setType(1);
                    orEntity.setServerCreateTime(new Date());
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.addOperational(orEntity);
                }else{
                    orEntity.setBrandIdenty(mCollageReq.getBrandIdenty());
                    orEntity.setShopIdenty(mCollageReq.getShopIdenty());
                    orEntity.setWxOpenId(mCollageReq.getWxOpenId());
                    orEntity.setWxPhoto(mCollageReq.getWxPhoto());
                    orEntity.setActivityId(mCollageReq.getId());
                    orEntity.setOperationalCount(recordEntity.getOperationalCount()+1);
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.modiftyOperational(orEntity);
                }
            }
            
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取拼团数据成功");
            mBaseDataModel.setData(mCollageMarketing);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取拼团数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 根据拼团活动id获取拼团信息
     * @param model
     * @param customerCollageId
     * @return
     */
    @GetMapping("/joinCollageDetail")
    public BaseDataModel queryCustomerCollageById(ModelMap model,Long customerCollageId){
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            CollageCustomerEntity mCollageCustomerEntity = mCollageCustomerService.queryCollage(customerCollageId);
            //主团
            if(mCollageCustomerEntity.getRelationId() == null){
                mCollageCustomerEntity.setRelationId(mCollageCustomerEntity.getId());
            }else{//拼团
                mCollageCustomerEntity = mCollageCustomerService.queryCollage(mCollageCustomerEntity.getRelationId());
            }
            CollageMarketingEntity mCollageMarketing = mCollageMarketingService.findCollageById(mCollageCustomerEntity.getCollageId());

            CollageJoinMessgeModel mCollageJoinMessgeModel = new CollageJoinMessgeModel();
            mCollageJoinMessgeModel.setmCollageCustomerEntity(mCollageCustomerEntity);
            mCollageJoinMessgeModel.setmCollageMarketing(mCollageMarketing);

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取拼团数据成功");
            mBaseDataModel.setData(mCollageJoinMessgeModel);
        }catch (Exception e){
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取拼团数据失败");
            mBaseDataModel.setData(false);
        }


        return mBaseDataModel;
    }


    /**
     * 根据订单id查询活动参与情况
     * @param model
     * @param tradeId
     * @return
     */
    @GetMapping("/joinDetailByTradeId")
    public BaseDataModel queryCustomerCollageByTradeId(ModelMap model,Long tradeId){
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            CollageCustomerEntity mCollageCustomerEntity = mCollageCustomerService.queryCollageByTradeId(tradeId);
            CollageMarketingEntity mCollageMarketing = mCollageMarketingService.findCollageById(mCollageCustomerEntity.getCollageId());

            CollageJoinMessgeModel mCollageJoinMessgeModel = new CollageJoinMessgeModel();
            mCollageJoinMessgeModel.setmCollageCustomerEntity(mCollageCustomerEntity);
            mCollageJoinMessgeModel.setmCollageMarketing(mCollageMarketing);

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取拼团数据成功");
            mBaseDataModel.setData(mCollageJoinMessgeModel);
        }catch (Exception e){
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取拼团数据失败");
            mBaseDataModel.setData(false);
        }


        return mBaseDataModel;
    }
}

