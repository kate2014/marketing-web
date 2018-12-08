package com.zhongmei.yunfu.controller.api;

import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.domain.entity.CustomerCardTimeEntity;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.service.CustomerCardTimeService;
import com.zhongmei.yunfu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wxapp/cardTime")
public class CardTimeApiController{

    @Autowired
    CustomerCardTimeService mCustomerCardTimeService;
    @Autowired
    CustomerService mCustomerService;

    @GetMapping("/customerCardTime")
    public BaseDataModel queryCustomerCoupon(ModelMap model, CustomerCardTimeEntity mCustomerCardTimeEntity) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        mBaseDataModel.setState("1000");
        try{
            CustomerEntity mCustomerEntity = mCustomerService.queryCustomerById(mCustomerCardTimeEntity.getBrandIdenty(),mCustomerCardTimeEntity.getShopIdenty(),mCustomerCardTimeEntity.getCustomerId());
            List<CustomerCardTimeEntity> listData = new ArrayList<>();
            if(mCustomerEntity != null && mCustomerEntity.getRelateId() != null && mCustomerEntity.getRelateId() != 0){
                listData.addAll(mCustomerCardTimeService.getListPageByCustomerId(mCustomerEntity.getRelateId()));

            }
            mBaseDataModel.setData(listData);
            mBaseDataModel.setMsg("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setData(false);
            mBaseDataModel.setMsg("操作失败");
        }

        return mBaseDataModel;
    }
}
