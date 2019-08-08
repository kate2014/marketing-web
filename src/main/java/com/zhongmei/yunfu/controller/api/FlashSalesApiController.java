package com.zhongmei.yunfu.controller.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.api.model.FlashSalesReq;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.FlashSalesModel;
import com.zhongmei.yunfu.domain.entity.FlashSalesMarketingEntity;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.service.FlashSalesMarketingService;
import com.zhongmei.yunfu.service.OperationalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 秒杀活动接口
 */
@RestController
@RequestMapping("/wxapp/flashSales")
public class FlashSalesApiController {

    @Autowired
    FlashSalesMarketingService mFlashSalesMarketingService;
    @Autowired
    OperationalRecordsService mOperationalRecordsService;

    @GetMapping("/getListData")
    public BaseDataModel getFlashSalesList(ModelMap model, FlashSalesModel mFlashSalesModel) {

        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            mFlashSalesModel.setEnabledFlag(1);
            Page<FlashSalesMarketingEntity> listPage = mFlashSalesMarketingService.queryFlashSalesList(mFlashSalesModel);
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取秒杀数据成功");
            mBaseDataModel.setData(listPage);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取秒杀数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 获取秒杀活动详情
     *
     * @param model
     * @param mFlashSalesReq
     * @return
     */
    @GetMapping("/flashSalesDetail")
    public BaseDataModel flashSalesDetail(ModelMap model, FlashSalesReq mFlashSalesReq) {

        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            FlashSalesMarketingEntity mFlashSalesMarketing = mFlashSalesMarketingService.findFlashSalesById(mFlashSalesReq.getId());

            //添加顾客查看记录,如该顾客对该条活跃已有同样的操作是，只需在原有操作次数的基础上+1
            if(mFlashSalesReq.getWxOpenId() != null){
                OperationalRecordsEntity orEntity = new OperationalRecordsEntity();
                orEntity.setBrandIdenty(mFlashSalesReq.getBrandIdenty());
                orEntity.setShopIdenty(mFlashSalesReq.getShopIdenty());
                orEntity.setWxOpenId(mFlashSalesReq.getWxOpenId());
                orEntity.setActivityId(mFlashSalesReq.getId());
                orEntity.setType(1);
                OperationalRecordsEntity recordEntity = mOperationalRecordsService.queryByCustomer(orEntity);
                if(recordEntity == null){
                    orEntity = new OperationalRecordsEntity();
                    orEntity.setBrandIdenty(mFlashSalesReq.getBrandIdenty());
                    orEntity.setShopIdenty(mFlashSalesReq.getShopIdenty());
                    orEntity.setCustomerId(mFlashSalesReq.getCustomerId());
                    orEntity.setCustomerName(mFlashSalesReq.getCustomerName());
                    orEntity.setWxOpenId(mFlashSalesReq.getWxOpenId());
                    orEntity.setWxPhoto(mFlashSalesReq.getWxPhoto());
                    orEntity.setWxName(mFlashSalesReq.getWxName());
                    orEntity.setActivityId(mFlashSalesReq.getId());
                    orEntity.setOperationalCount(1);
                    orEntity.setType(1);
                    orEntity.setServerCreateTime(new Date());
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.addOperational(orEntity);
                }else{
                    orEntity.setBrandIdenty(mFlashSalesReq.getBrandIdenty());
                    orEntity.setShopIdenty(mFlashSalesReq.getShopIdenty());
                    orEntity.setWxOpenId(mFlashSalesReq.getWxOpenId());
                    orEntity.setWxPhoto(mFlashSalesReq.getWxPhoto());
                    orEntity.setActivityId(mFlashSalesReq.getId());
                    orEntity.setOperationalCount(recordEntity.getOperationalCount()+1);
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.modiftyOperational(orEntity);
                }
            }
            
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取秒杀数据成功");
            mBaseDataModel.setData(mFlashSalesMarketing);

        } catch (Exception e) {
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取秒杀数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }
}
