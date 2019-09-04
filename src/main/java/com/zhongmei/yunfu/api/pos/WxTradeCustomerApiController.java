package com.zhongmei.yunfu.api.pos;

import com.zhongmei.yunfu.controller.model.WxTradeContentModel;
import com.zhongmei.yunfu.controller.model.WxTradeCustomerModel;
import com.zhongmei.yunfu.controller.model.WxTradeCustomerRespModel;
import com.zhongmei.yunfu.controller.model.WxTradeRepModel;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.WxTradeCustomerEntity;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.WxTradeCustomerService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pos/wxTradeData")
public class WxTradeCustomerApiController {

    @Autowired
    WxTradeCustomerService mWxTradeCustomerService;
    @Autowired
    CustomerService mCustomerService;

    @RequestMapping("/queryByCustomer")
    public WxTradeCustomerModel queryWxTradeByCustomer(@RequestBody WxTradeRepModel mWxTradeRepModel){
        WxTradeCustomerModel mWxTradeCustomerModel = new WxTradeCustomerModel();
        try{
            WxTradeContentModel content = mWxTradeRepModel.getContent();

            WxTradeCustomerEntity mWxTradeCustomer = new WxTradeCustomerEntity();
            mWxTradeCustomer.setBrandIdenty(content.getBrandIdenty());
            mWxTradeCustomer.setShopIdenty(content.getShopIdenty());

            //应pos端传过来的值是mobile对应的会员id，需查询对绑定在下面的微信会员id，再根据该id或获取到购买的wx服务
            CustomerEntity mCustomerEntity = mCustomerService.queryCustomerByRelateId(content.getBrandIdenty(),content.getShopIdenty(),content.getCustomerId());
            if(mCustomerEntity != null){
                mWxTradeCustomer.setCustomerId(mCustomerEntity.getId());

                List<WxTradeCustomerEntity> listData = mWxTradeCustomerService.queryListDataByCustomerId(mWxTradeCustomer);

                mWxTradeCustomerModel.setContent(listData);
                mWxTradeCustomerModel.setStateCode("1000");
                mWxTradeCustomerModel.setStatus("1000");
                mWxTradeCustomerModel.setMessage("获取数据成功");
                mWxTradeCustomerModel.setMessageId("");
                mWxTradeCustomerModel.setErrors("");
            }else{
                mWxTradeCustomerModel.setContent(null);
                mWxTradeCustomerModel.setStateCode("1001");
                mWxTradeCustomerModel.setStatus("1001");
                mWxTradeCustomerModel.setMessage("该会员未使用过小程序");
                mWxTradeCustomerModel.setMessageId("");
                mWxTradeCustomerModel.setErrors("");
            }

        }catch (Exception e){
            e.printStackTrace();
            mWxTradeCustomerModel.setContent(new ArrayList<WxTradeCustomerEntity>());
            mWxTradeCustomerModel.setStateCode("1002");
            mWxTradeCustomerModel.setStatus("1002");
            mWxTradeCustomerModel.setMessage("");
            mWxTradeCustomerModel.setMessageId("");
            mWxTradeCustomerModel.setErrors("获取会员购买的小程序服务失败");
        }


        return mWxTradeCustomerModel;
    }


    @RequestMapping("/queryByCode")
    public WxTradeCustomerRespModel queryWxTradeByCode(@RequestBody WxTradeRepModel mWxTradeRepModel){
        WxTradeCustomerRespModel mWxTradeCustomerModel = new WxTradeCustomerRespModel();
        try{
            WxTradeContentModel content = mWxTradeRepModel.getContent();

            WxTradeCustomerEntity mWxTradeCustomer = new WxTradeCustomerEntity();
            mWxTradeCustomer.setBrandIdenty(content.getBrandIdenty());
            mWxTradeCustomer.setShopIdenty(content.getShopIdenty());
            mWxTradeCustomer.setCode(content.getCode());

            WxTradeCustomerEntity mWxTradeCustomerEntity = mWxTradeCustomerService.queryWxTradeByCode(mWxTradeCustomer);
            if(mWxTradeCustomerEntity != null){
                if(mWxTradeCustomerEntity.getStatus() == 1){
                    mWxTradeCustomerModel.setContent(mWxTradeCustomerEntity);
                    mWxTradeCustomerModel.setStateCode("1000");
                    mWxTradeCustomerModel.setStatus("1000");
                    mWxTradeCustomerModel.setMessage("获取数据成功");
                    mWxTradeCustomerModel.setMessageId("");
                    mWxTradeCustomerModel.setErrors("");
                }else{
                    mWxTradeCustomerModel.setContent(null);
                    mWxTradeCustomerModel.setStateCode("1001");
                    mWxTradeCustomerModel.setStatus("1001");
                    mWxTradeCustomerModel.setMessage("您购买的活动已于"+ DateFormatUtil.format(mWxTradeCustomerEntity.getServerUpdateTime(),DateFormatUtil.FORMAT_FULL_DATE)+"进行了使用");
                    mWxTradeCustomerModel.setMessageId("");
                    mWxTradeCustomerModel.setErrors("");
                }
            }else{
                mWxTradeCustomerModel.setContent(null);
                mWxTradeCustomerModel.setStateCode("1001");
                mWxTradeCustomerModel.setStatus("1001");
                mWxTradeCustomerModel.setMessage("验证码错误，请确认活动是否购买成功！");
                mWxTradeCustomerModel.setMessageId("");
                mWxTradeCustomerModel.setErrors("");
            }



        }catch (Exception e){
            e.printStackTrace();
            mWxTradeCustomerModel.setContent(null);
            mWxTradeCustomerModel.setStateCode("1002");
            mWxTradeCustomerModel.setStatus("1002");
            mWxTradeCustomerModel.setMessage("");
            mWxTradeCustomerModel.setMessageId("");
            mWxTradeCustomerModel.setErrors("获取会员购买的小程序服务失败");
        }


        return mWxTradeCustomerModel;
    }


}
