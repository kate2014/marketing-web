package com.zhongmei.yunfu.controller.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.api.model.FlashSalesReq;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.FlashSalesModel;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.FlashSalesMarketingEntity;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.FlashSalesMarketingService;
import com.zhongmei.yunfu.service.OperationalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

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
    @Autowired
    CustomerService mCustomerService;

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
     * @param req
     * @return
     */
    @GetMapping("/flashSalesDetail")
    public BaseDataModel flashSalesDetail(ModelMap model, FlashSalesReq req) {

        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            FlashSalesMarketingEntity mFlashSalesMarketing = mFlashSalesMarketingService.findFlashSalesById(req.getId());

            //添加顾客查看记录,如该顾客对该条活跃已有同样的操作是，只需在原有操作次数的基础上+1
            if(req.getWxOpenId() != null){
                OperationalRecordsEntity orEntity = new OperationalRecordsEntity();
                orEntity.setBrandIdenty(req.getBrandIdenty());
                orEntity.setShopIdenty(req.getShopIdenty());
                orEntity.setWxOpenId(req.getWxOpenId());
                orEntity.setCustomerId(req.getCustomerId());
                orEntity.setActivityId(req.getId());
                orEntity.setSource(5);
                orEntity.setType(1);
                OperationalRecordsEntity recordEntity = mOperationalRecordsService.queryByCustomer(orEntity);
                //判断用户是否是第一次浏览
                if(recordEntity == null){
                    //获取查看用户基本信息
                    CustomerEntity mCustomerEntity = new CustomerEntity();
                    mCustomerEntity.setBrandIdenty(req.getBrandIdenty());
                    mCustomerEntity.setShopIdenty(req.getShopIdenty());
                    mCustomerEntity.setId(req.getCustomerId());
                    Map<String, String> tempMap =  mCustomerService.queryByWxCustomerId(mCustomerEntity);

                    orEntity = new OperationalRecordsEntity();
                    orEntity.setBrandIdenty(req.getBrandIdenty());
                    orEntity.setShopIdenty(req.getShopIdenty());
                    orEntity.setCustomerId(req.getCustomerId());
                    orEntity.setCustomerPhone(tempMap.get("pPhone"));
                    orEntity.setCustomerName(tempMap.get("pName"));
                    orEntity.setWxOpenId(req.getWxOpenId());
                    orEntity.setWxPhoto(tempMap.get("photo"));
                    orEntity.setWxName(tempMap.get("wName"));
                    orEntity.setActivityId(req.getId());
                    orEntity.setOperationalCount(1);
                    orEntity.setType(1);
                    orEntity.setSource(5);
                    orEntity.setServerCreateTime(new Date());
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.addOperational(orEntity);
                }else{

                    OperationalRecordsEntity modifityEntity = new OperationalRecordsEntity();

                    //如果之前用户浏览信息中没有会员电话或名称，这可以进行后续补全
                    if(recordEntity.getCustomerPhone() == null || recordEntity.getCustomerPhone().equals("")){
                        //获取查看用户基本信息
                        CustomerEntity mCustomerEntity = new CustomerEntity();
                        mCustomerEntity.setBrandIdenty(req.getBrandIdenty());
                        mCustomerEntity.setShopIdenty(req.getShopIdenty());
                        mCustomerEntity.setId(req.getCustomerId());
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
