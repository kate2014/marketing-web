package com.zhongmei.yunfu.controller.api;

import com.alibaba.fastjson.JSON;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import com.zhongmei.yunfu.controller.model.*;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 会员相关接口
 */
@RestController
@RequestMapping("/wxapp/customer")
public class CustomerApiController {

    @Autowired
    CustomerService mCustomerService;
    @Autowired
    CommercialPaySettingService mCommercialPaySettingService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CustomerCouponService mCustomerCouponService;
    @Autowired
    CustomerCardTimeService mCustomerCardTimeService;
    @Autowired
    BookingService mBookingService;
    /**
     * 添加微信小程序会员
     *
     * @param model
     * @param mCustomerModel
     */
    @GetMapping("/addCustomer")
    public BaseDataModel addCustomer(Model model, CustomerModel mCustomerModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            Integer actionCode = selectOpenIdFromWx(mBaseDataModel,mCustomerModel);
            if(actionCode != 1000){
                return mBaseDataModel;
            }

            //先验证该会是否已在该品牌下存在。我们的会员是同品牌下共享会员
            CustomerEntity mCustomer = mCustomerService.queryCustomerByThirdId(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), mCustomerModel.getWxOpenId());


            //为null表示该会员为添加
            if (mCustomer == null) {
                CustomerEntity addCustomer = executeAdd(mBaseDataModel,mCustomerModel);
                //添加成功后判断是否需要推送新人优惠券
                if(addCustomer != null && addCustomer.getId() != null){
                    mCustomerCouponService.putOnCoupon(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(),addCustomer.getId(),addCustomer.getThirdId(), 2,1);
                }
            } else {
                if(mCustomer.getRelateId() != null && mCustomer.getRelateId() != 0){
                    CustomerEntity mobileCustomer = mCustomerService.queryCustomerById(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), mCustomer.getRelateId());
                    mCustomer.setMobile(mobileCustomer.getMobile());
                }

                mBaseDataModel.setState("1002");
                mBaseDataModel.setMsg("该会员已添加过");
                mBaseDataModel.setData(mCustomer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("添加会员失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 添加会员
     */
    public CustomerEntity executeAdd(BaseDataModel mBaseDataModel, CustomerModel mCustomerModel) throws Exception{
        CustomerEntity mCustomer = new CustomerEntity();
        String name = ToolsUtil.filterEmoji(mCustomerModel.getName(),"?");
        mCustomer.setName(name);
        mCustomer.setGender(mCustomerModel.getGender());
        mCustomer.setMobile(mCustomerModel.getMobile());
        if(mCustomerModel.getBirthday() != null){
            mCustomer.setBirthday(DateFormatUtil.parseDate(mCustomerModel.getBirthday(), DateFormatUtil.FORMAT_DATE));
        }
        mCustomer.setTelephone(mCustomerModel.getTelephone());
        mCustomer.setEmail(mCustomerModel.getEmail());
        mCustomer.setHobby(mCustomerModel.getHobby());
        mCustomer.setAddress(mCustomerModel.getAddress());
        mCustomer.setMobile(mCustomerModel.getMobile());
        mCustomer.setThirdId(mCustomerModel.getWxOpenId());
        mCustomer.setExpandedId(mCustomerModel.getExpandedId());
        mCustomer.setShopIdenty(mCustomerModel.getShopIdenty());
        mCustomer.setBrandIdenty(mCustomerModel.getBrandIdenty());
        mCustomer.setGroupLevel("普通会员");//银卡会员
        mCustomer.setGroupLevelId(0l);
        mCustomer.setSourceId(2);


        Boolean isSuccess = mCustomerService.addCustomer(mCustomer);
        if (isSuccess) {
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("添加会员成功");
            mBaseDataModel.setData(mCustomer);
        } else {
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("添加会员失败");
            mBaseDataModel.setData(false);
        }
        return mCustomer;
    }

    @GetMapping("/queryCustomer")
    public BaseDataModel queryCustomerByopenId(Model model, CustomerModel mCustomerModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            LoginCustomerModel mLoginCustomerModel = new LoginCustomerModel();
            String listCustomer = "";
            //根据小程序openId获取微信会员信息
            CustomerEntity wxCustomer =  mCustomerService.queryCustomerByThirdId(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(),mCustomerModel.getWxOpenId());

            mLoginCustomerModel.setBrandIdenty(wxCustomer.getBrandIdenty());
            mLoginCustomerModel.setShopIdenty(wxCustomer.getShopIdenty());
            mLoginCustomerModel.setThirdId(wxCustomer.getThirdId());
            mLoginCustomerModel.setRelateId(wxCustomer.getRelateId());


            listCustomer = wxCustomer.getId().toString();

            //获取手机号码绑定的会员信息
            if(wxCustomer.getId() != null && wxCustomer.getId() != 0){
                CustomerEntity mobileCustomer =  mCustomerService.queryCustomerById(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(),wxCustomer.getRelateId());
                listCustomer += "," + mobileCustomer.getId();

                mLoginCustomerModel.setGender(mobileCustomer.getGender());
                mLoginCustomerModel.setGroupLevel(mobileCustomer.getGroupLevel());
                mLoginCustomerModel.setGroupLevelId(mobileCustomer.getGroupLevelId());
                mLoginCustomerModel.setMobile(mobileCustomer.getMobile());
                mLoginCustomerModel.setName(mobileCustomer.getName());
                mLoginCustomerModel.setStoredBalance(mobileCustomer.getStoredBalance());
                mLoginCustomerModel.setConsumptionLastTime(mobileCustomer.getConsumptionLastTime());


                int cardTimeCount = mCustomerCardTimeService.selectCount(mobileCustomer.getId(),mCustomerModel.getShopIdenty());
                mLoginCustomerModel.setCardTimeCount(cardTimeCount);

            }

            //获取优惠券数量
            List<CustomerCouponModel> listCoupon = mCustomerCouponService.queryCouponByCustomerGroup(mCustomerModel.getBrandIdenty(),mCustomerModel.getShopIdenty(),listCustomer,1);
            mLoginCustomerModel.setCouponCount(listCoupon.size());

            //获取最新一条预约
            BookingSearchModel mBookingSearchModel = new BookingSearchModel();
            mBookingSearchModel.setBrandIdenty(mCustomerModel.getBrandIdenty());
            mBookingSearchModel.setShopIdenty(mCustomerModel.getShopIdenty());
            mBookingSearchModel.setCustomerList(listCustomer);
            BookingEntity mBookingEntity = mBookingService.queryNewBookingByCustomer(mBookingSearchModel);
            mLoginCustomerModel.setmBookingEntity(mBookingEntity);

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取会员成功");
            mBaseDataModel.setData(mLoginCustomerModel);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取会员失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 绑定会员手机号码
     *
     * @param model
     * @param mCustomerModel
     * @return
     */
    @GetMapping("/bindCustomer")
    public BaseDataModel bindCustomer(Model model, CustomerModel mCustomerModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            //验证该手机号码是否已注册为会员
            CustomerEntity mCustomer = mCustomerService.queryCustomerByMobile(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), mCustomerModel.getMobile());

            if(mCustomer != null){
                //判断该会员是否被绑定
                CustomerEntity relateCustomer =  mCustomerService.queryCustomerByRelateId(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(),mCustomer.getId());
                if(relateCustomer == null){//如果未被绑定则直接进行绑定
                    executeBind(mCustomer,mCustomerModel,mBaseDataModel);
                    return mBaseDataModel;
                }else if(relateCustomer != null && relateCustomer.getThirdId().equals(mCustomerModel.getWxOpenId())){
                    mBaseDataModel.setState("1001");
                    mBaseDataModel.setMsg("绑定关系已存在，不需要重复绑定");
                    mBaseDataModel.setData(relateCustomer);
                    return mBaseDataModel;

                }else{//如已被绑定，这提示该账号已被绑定，先进行解绑
                    mBaseDataModel.setState("1001");
                    mBaseDataModel.setMsg("该手机号和（"+relateCustomer.getThirdId()+"）已绑定，如需要绑定该手机号，请先解绑");
                    mBaseDataModel.setData(false);
                    return mBaseDataModel;
                }
            }else{

                CustomerEntity mobileCustomerEntity = installMobileCustomer(mCustomerModel);
                if(mobileCustomerEntity.getId() !=null ){
                    CustomerEntity wxCustomer = new CustomerEntity();
                    wxCustomer.setBrandIdenty(mobileCustomerEntity.getBrandIdenty());
                    wxCustomer.setShopIdenty(mobileCustomerEntity.getShopIdenty());
                    wxCustomer.setRelateId(mobileCustomerEntity.getId());
                    wxCustomer.setThirdId(mCustomerModel.getWxOpenId());
                    Boolean isSuccess = mCustomerService.bindCustomerByMobile(wxCustomer);
                    if(isSuccess){
                        //绑定成功推送优惠券
                        mCustomerCouponService.putOnCoupon(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(),mCustomerModel.getId(),mCustomerModel.getWxOpenId(), 4,3);
                        mBaseDataModel.setState("1000");
                        mBaseDataModel.setMsg("绑定会员成功");
                        wxCustomer.setMobile(mCustomerModel.getMobile());
                        mBaseDataModel.setData(wxCustomer);
                        return mBaseDataModel;
                    }else{
                        mBaseDataModel.setState("1001");
                        mBaseDataModel.setMsg("绑定会员失败");
                        mBaseDataModel.setData(false);
                        return mBaseDataModel;
                    }
                }else{
                    mBaseDataModel.setState("1001");
                    mBaseDataModel.setMsg("绑定会员失败");
                    mBaseDataModel.setData(false);
                    return mBaseDataModel;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("绑定会员失败");
            mBaseDataModel.setData(false);
            return mBaseDataModel;
        }

    }

    public void executeBind(CustomerEntity mCustomer,CustomerModel mCustomerModel,BaseDataModel mBaseDataModel)throws Exception{
        //进行绑定前的密码验证
        String passWorld = ToolsUtil.encodeValue(mCustomerModel.getPassword(),mCustomerModel.getMobile());
        if(passWorld.equals(mCustomer.getPassword())){
            CustomerEntity wxCustomer = new CustomerEntity();
            wxCustomer.setBrandIdenty(mCustomer.getBrandIdenty());
            wxCustomer.setShopIdenty(mCustomer.getShopIdenty());
            wxCustomer.setRelateId(mCustomer.getId());
            wxCustomer.setThirdId(mCustomerModel.getWxOpenId());
            wxCustomer.setId(mCustomerModel.getId());
            Boolean isSuccess = mCustomerService.bindCustomerByMobile(wxCustomer);
            if (isSuccess) {

                //绑定成功，推送优惠券
                mCustomerCouponService.putOnCoupon(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(),mCustomerModel.getId(),mCustomerModel.getWxOpenId(), 4,3);

                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("绑定会员成功");
                mBaseDataModel.setData(mCustomer);
            } else {
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("绑定会员失败");
                mBaseDataModel.setData(false);
            }

        }else{
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("密码验证失败");
            mBaseDataModel.setData(false);
        }
    }

    public CustomerEntity installMobileCustomer(CustomerModel mCustomerModel) throws Exception{
        CustomerEntity mWxCustomer = mCustomerService.queryCustomerByThirdId(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), mCustomerModel.getWxOpenId());
        //先创建一条带手机号码的会员信息
        CustomerEntity mobileCustomer = new CustomerEntity();
        mobileCustomer.setName(mWxCustomer.getName());
        mobileCustomer.setGender(mWxCustomer.getGender());
        mobileCustomer.setMobile(mCustomerModel.getMobile());
        mobileCustomer.setExpandedId(mCustomerModel.getExpandedId());
        mobileCustomer.setShopIdenty(mCustomerModel.getShopIdenty());
        mobileCustomer.setBrandIdenty(mCustomerModel.getBrandIdenty());
        String passWorld = ToolsUtil.encodeValue(mCustomerModel.getPassword(),mCustomerModel.getMobile());
        mobileCustomer.setPassword(passWorld);
        mobileCustomer.setGroupLevel("普通会员");//银卡会员
        mobileCustomer.setGroupLevelId(0l);
        mobileCustomer.setSourceId(2);

        Boolean isSuccess = mCustomerService.addCustomer(mobileCustomer);
        return mobileCustomer;
    }


    /**
     * 解除绑定
     * @param model
     * @param mCustomerModel
     * @return
     */
    @GetMapping("/unbind")
    public BaseDataModel unbind(Model model, CustomerModel mCustomerModel){
        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {

            CustomerEntity mCustomer = mCustomerService.queryCustomerByMobile(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), mCustomerModel.getMobile());
            String inputPassWorld = ToolsUtil.encodeValue(mCustomerModel.getPassword(),mCustomerModel.getMobile());
            //验证输入密码是是否正确
            if(inputPassWorld.equals(mCustomer.getPassword())){
                CustomerEntity unBindCustomer = new CustomerEntity();
                unBindCustomer.setBrandIdenty(mCustomer.getBrandIdenty());
                unBindCustomer.setShopIdenty(mCustomer.getShopIdenty());
                unBindCustomer.setThirdId(mCustomerModel.getWxOpenId());
                Boolean isRelieveSuccess = mCustomerService.relieveBind(unBindCustomer);
                if(isRelieveSuccess){
                    mBaseDataModel.setState("1000");
                    mBaseDataModel.setMsg("解绑成功");
                    mBaseDataModel.setData(mCustomer);
                    return mBaseDataModel;
                }else{
                    mBaseDataModel.setState("1001");
                    mBaseDataModel.setMsg("解绑手机号码失败");
                    mBaseDataModel.setData(false);
                    return mBaseDataModel;
                }
            }else{
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("密码验证失败");
                mBaseDataModel.setData(false);
                return mBaseDataModel;
            }
        }catch (Exception e){
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("解绑手机号码失败");
            mBaseDataModel.setData(false);
            return mBaseDataModel;
        }


    }


    public Integer selectOpenIdFromWx(BaseDataModel mBaseDataModel,CustomerModel mCustomerModel) throws Exception{
        //获取商户的appid和secret
        CommercialPaySettingEntity mCommercialPaySetting = new CommercialPaySettingEntity();
        mCommercialPaySetting.setBrandIdenty(mCustomerModel.getBrandIdenty());
        mCommercialPaySetting.setShopIdenty(mCustomerModel.getShopIdenty());
        mCommercialPaySetting.setStatusFlag(1);
        mCommercialPaySetting.setType(1);
        CommercialPaySettingEntity settingData = mCommercialPaySettingService.queryData(mCommercialPaySetting);
        //一个门店只有唯一一个appid和secret
        if(settingData != null && settingData.getAppid() != null){

            String url = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code&appid="+settingData.getAppid()+"&secret="+settingData.getAppsecret()+"&js_code="+mCustomerModel.getWxOpenId();
            String message = restTemplate.getForObject(url, String.class);
            WxLoginModel mWxLoginModel = JSON.parseObject(message,WxLoginModel.class);
            if(mWxLoginModel.getOpenid() != null){
                mCustomerModel.setWxOpenId(mWxLoginModel.getOpenid());
                return 1000;
            }else{
                mBaseDataModel.setState("1003");
                mBaseDataModel.setMsg(mWxLoginModel.getErrmsg());
                mBaseDataModel.setData(false);
                return 1001;
            }

        }else{
            //为设置appid和secret
            mBaseDataModel.setState("10000");
            mBaseDataModel.setMsg("商户未配置门店appid");
            mBaseDataModel.setData(false);
            return 1001;
        }

    }

}

