package com.zhongmei.yunfu.controller.api;

import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.PushMessageCustomerEntity;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.PushMessageCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 会员信息推送
 */
@RestController
@RequestMapping("/wxapp/pushMessage")
public class PushMessageApiController {

    @Autowired
    PushMessageCustomerService mPushMessageCustomerService;
    @Autowired
    CustomerService mCustomerService;

    @GetMapping("/queryPushMessage")
    public BaseDataModel queryPushMessage(Model model, PushMessageCustomerEntity mpm) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            String customerIds = mpm.getCustomerId().toString();

            CustomerEntity mCustomerEntity = mCustomerService.queryCustomerById(mpm.getBrandIdenty(), mpm.getShopIdenty(), mpm.getCustomerId());

            if(mCustomerEntity.getRelateId() != null && mCustomerEntity.getRelateId() != 0){
                customerIds = customerIds +","+mCustomerEntity.getRelateId();
            }

            List<PushMessageCustomerEntity> listData = mPushMessageCustomerService.queryPushByCustomer(mpm.getBrandIdenty(), mpm.getShopIdenty(), customerIds);

            for (PushMessageCustomerEntity pmc : listData) {
                mPushMessageCustomerService.modifyPushState(pmc.getId(), 2);
            }
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取数据成功");
            mBaseDataModel.setData(listData);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取数据失败");
            mBaseDataModel.setData(false);
        }


        return mBaseDataModel;
    }


}
