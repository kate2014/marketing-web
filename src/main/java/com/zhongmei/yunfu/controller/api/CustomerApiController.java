package com.zhongmei.yunfu.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhongmei.yunfu.api.ApiRespStatusException;
import com.zhongmei.yunfu.api.internal.SalesActionApiController;
import com.zhongmei.yunfu.controller.api.model.CustomerResp;
import com.zhongmei.yunfu.controller.api.model.WxAccessToken;
import com.zhongmei.yunfu.controller.api.model.WxPhoneReq;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import com.zhongmei.yunfu.controller.model.*;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.WxUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员相关接口
 */
@RestController
@RequestMapping("/wxapp/customer")
public class CustomerApiController {

    private static Logger log = LoggerFactory.getLogger(CustomerApiController.class);

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
    @Autowired
    CustomerMarketingExpandedService mCustomerMarketingExpandedService;
    @Autowired
    MarketingExpandedService mMarketingExpandedService;

    private String defaultHeadIcon = "http://media.zhongmeiyunfu.com/systemheader_icon.png";
    /**
     * 添加微信小程序会员
     *
     * @param model
     * @param mCustomerModel
     */
    @GetMapping("/addCustomer")
    public BaseDataModel addCustomer(Model model, CustomerModel mCustomerModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        CustomerResp mCustomerResp = new CustomerResp();
        try {
            Integer actionCode = selectOpenIdFromWx(mBaseDataModel,mCustomerModel);
            if(actionCode != 1000){
                return mBaseDataModel;
            }

            //先验证该会是否已在该品牌下存在
            CustomerEntity mCustomer = mCustomerService.queryCustomerByThirdId(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), mCustomerModel.getWxOpenId());


            //为null表示该会员未添加
            if (mCustomer == null) {
                mCustomer = executeAdd(mBaseDataModel,mCustomerModel);

                //建立会员推荐关联
                addCustomerExpanded(mCustomerModel);

                //添加成功后判断是否需要推送新人优惠券
                if(mCustomer != null && mCustomer.getId() != null){
                    mCustomerCouponService.putOnCoupon(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(),mCustomer.getId(),mCustomer.getThirdId(), 2,1);
                }
            } else {
                boolean isMidfityData = false;
                //判断用户是否更换了头像
                if(mCustomerModel.getWxPhoto() != null && !mCustomerModel.getWxPhoto().equals("")){
                    if(mCustomer.getPhoto() == null || !mCustomer.getPhoto().equals(mCustomerModel.getWxPhoto())){
                        isMidfityData = true;
                        mCustomer.setPhoto(mCustomerModel.getWxPhoto());
                    }
                }
                //判断用户是否更换了微信名称
                if(mCustomerModel.getName() != null && !mCustomerModel.getName().equals("")){
                    if(mCustomer.getName() == null || !mCustomerModel.getName().equals(mCustomer.getName())){
                        isMidfityData = true;
                        mCustomer.setName(mCustomerModel.getName());
                    }
                }
                //如果会员名称或头像有修改，则更新会员信息
                if(isMidfityData){
                    mCustomer.setServerUpdateTime(new Date());
                    mCustomerService.midfityCustomer(mCustomer);
                }

                if(mCustomer.getRelateId() != null && mCustomer.getRelateId() != 0){
                    CustomerEntity mobileCustomer = mCustomerService.queryCustomerById(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), mCustomer.getRelateId());
                    mCustomer.setMobile(mobileCustomer.getMobile());
                }

                if(mCustomer.getPhoto()==null || mCustomer.getPhoto().equals("")){
                    mCustomer.setPhoto(defaultHeadIcon);
                }
                mCustomerResp.setmCustomer(mCustomer);
                mCustomerResp.setSessionKey(mCustomerModel.getSessionKey());
                mCustomerResp.setAccesstoken(mCustomerModel.getAccesstoken());
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

    @GetMapping("/loginCustomer")
    public BaseDataModel loginCustomer(Model model, CustomerModel mCustomerModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        CustomerResp mCustomerResp = new CustomerResp();
        try {
            Integer actionCode = selectOpenIdFromWx(mBaseDataModel,mCustomerModel);
            if(actionCode != 1000){
                return mBaseDataModel;
            }

            //先验证该会是否已在该品牌下存在。我们的会员是同品牌下共享会员
            CustomerEntity mCustomer = mCustomerService.queryCustomerByThirdId(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), mCustomerModel.getWxOpenId());


            //为null表示该会员未添加
            if (mCustomer == null) {
                mCustomer = executeAdd(mBaseDataModel,mCustomerModel);

                //建立会员推荐关联
                addCustomerExpanded(mCustomerModel);

                //添加成功后判断是否需要推送新人优惠券
                if(mCustomer != null && mCustomer.getId() != null){
                    mCustomerCouponService.putOnCoupon(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(),mCustomer.getId(),mCustomer.getThirdId(), 2,1);
                }
            } else {
                boolean isMidfityData = false;
                //判断用户是否更换了头像
                if(mCustomerModel.getWxPhoto() != null && !mCustomerModel.getWxPhoto().equals("")){
                    if(mCustomer.getPhoto() == null || !mCustomer.getPhoto().equals(mCustomerModel.getWxPhoto())){
                        isMidfityData = true;
                        mCustomer.setPhoto(mCustomerModel.getWxPhoto());
                    }
                }
                //判断用户是否更换了微信名称
                if(mCustomerModel.getName() != null && !mCustomerModel.getName().equals("")){
                    if(mCustomer.getName() == null || !mCustomerModel.getName().equals(mCustomer.getName())){
                        isMidfityData = true;
                        mCustomer.setName(mCustomerModel.getName());
                    }
                }
                //如果会员名称或头像有修改，则更新会员信息
                if(isMidfityData){
                    mCustomer.setServerUpdateTime(new Date());
                    mCustomerService.midfityCustomer(mCustomer);
                }

                if(mCustomer.getRelateId() != null && mCustomer.getRelateId() != 0){
                    CustomerEntity mobileCustomer = mCustomerService.queryCustomerById(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), mCustomer.getRelateId());
                    mCustomer.setMobile(mobileCustomer.getMobile());
                }

                if(mCustomer.getPhoto()==null || mCustomer.getPhoto().equals("")){
                    mCustomer.setPhoto(defaultHeadIcon);
                }

                mCustomerResp.setmCustomer(mCustomer);
                mCustomerResp.setSessionKey(mCustomerModel.getSessionKey());
                mCustomerResp.setAccesstoken(mCustomerModel.getAccesstoken());

                mBaseDataModel.setState("1002");
                mBaseDataModel.setMsg("该会员已添加过");
                mBaseDataModel.setData(mCustomerResp);
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
//        mCustomer.setEmail(mCustomerModel.getEmail());
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
        mCustomer.setPhoto(mCustomerModel.getWxPhoto());


        Boolean isSuccess = mCustomerService.addCustomer(mCustomer);

        if(mCustomer.getPhoto()==null || mCustomer.getPhoto().equals("")){
            mCustomer.setPhoto(defaultHeadIcon);
        }

        if (isSuccess) {
            CustomerResp mCustomerResp = new CustomerResp();

            mCustomerResp.setmCustomer(mCustomer);
            mCustomerResp.setSessionKey(mCustomerModel.getSessionKey());
            mCustomerResp.setAccesstoken(mCustomerModel.getAccesstoken());

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("添加会员成功");
            mBaseDataModel.setData(mCustomerResp);
        } else {
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("添加会员失败");
            mBaseDataModel.setData(false);
        }
        return mCustomer;
    }

    public void addCustomerExpanded(CustomerModel mCustomerModel) {
        try {

            if(mCustomerModel.getExpandedId() != null && !mCustomerModel.getExpandedId().equals("")){
                //判断门店是否开启了全员推广
                MarketingExpandedEntity mMarketingExpanded = new MarketingExpandedEntity();
                mMarketingExpanded.setBrandIdenty(mCustomerModel.getBrandIdenty());
                mMarketingExpanded.setShopIdenty(mCustomerModel.getShopIdenty());
                MarketingExpandedEntity mMarketingExpandedEntity = mMarketingExpandedService.queryMarketingExpanded(mMarketingExpanded);
                if(mMarketingExpandedEntity != null){
                    CustomerMarketingExpandedEntity mCustomerMarketingExpanded = new CustomerMarketingExpandedEntity();
                    mCustomerMarketingExpanded.setBrandIdenty(mCustomerModel.getBrandIdenty());
                    mCustomerMarketingExpanded.setShopIdenty(mCustomerModel.getShopIdenty());
                    mCustomerMarketingExpanded.setCustomerId(mCustomerModel.getExpandedId());
                    mCustomerMarketingExpanded.setExpandedCustomerName(mCustomerModel.getName());
                    mCustomerMarketingExpanded.setExpandedCustomerOpenid(mCustomerModel.getWxOpenId());
                    mCustomerMarketingExpanded.setConsumptionPrice(BigDecimal.ZERO);
                    mCustomerMarketingExpanded.setExpandedCustomerPic(mCustomerModel.getWxPhoto());

                    Date m = new Date();

                    String batchCode = Long.toString(m.getTime()) + mCustomerMarketingExpanded.getCustomerId();
                    mCustomerMarketingExpanded.setExpandedCode(batchCode);
                    mCustomerMarketingExpanded.setState(3);

                    Boolean isSuccess = mCustomerMarketingExpandedService.addCustomerExpanded(mCustomerMarketingExpanded);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

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
                //mLoginCustomerModel.setStoredBalance(mobileCustomer.getStoredBalance());
                mLoginCustomerModel.setConsumptionLastTime(mobileCustomer.getConsumptionLastTime());

                CustomerExtraEntity customerExtra = mCustomerService.getCustomerExtra(mobileCustomer.getId());
                if (customerExtra != null) {
                    mLoginCustomerModel.setStoredBalance(customerExtra.getStoredBalance());
                }


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
                    mBaseDataModel.setMsg("该手机号和（"+relateCustomer.getCreatorName()+"）已绑定，如需要绑定该手机号，请先解绑");
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
//        String passWorld = ToolsUtil.encodeValue(mCustomerModel.getPassword(),mCustomerModel.getMobile());
//        if(passWorld.equals(mCustomer.getPassword())){
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

//        }else{
//            mBaseDataModel.setState("1001");
//            mBaseDataModel.setMsg("密码验证失败");
//            mBaseDataModel.setData(false);
//        }
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

        //初始密码为电话号码后6位
        String inputPassword = mCustomerModel.getMobile().substring(5);
        String passWorld = ToolsUtil.encodeValue(inputPassword,mCustomerModel.getMobile());
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

            CustomerEntity mCustomer = new CustomerEntity();
            mCustomer.setBrandIdenty(mCustomerModel.getBrandIdenty());
            mCustomer.setShopIdenty(mCustomerModel.getShopIdenty());
            mCustomer.setId(mCustomerModel.getId());
            mCustomer.setThirdId(mCustomerModel.getWxOpenId());

            Boolean isSuccess = mCustomerService.relieveBind(mCustomer);
            if(isSuccess){
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

            log.info("====jscode2session===appid="+settingData.getAppid()+"&secret="+settingData.getAppsecret()+"&js_code="+mCustomerModel.getWxOpenId());

            String url = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code&appid="+settingData.getAppid()+"&secret="+settingData.getAppsecret()+"&js_code="+mCustomerModel.getWxOpenId();
            String message = restTemplate.getForObject(url, String.class);
            WxLoginModel mWxLoginModel = JSON.parseObject(message,WxLoginModel.class);

            WxAccessToken mWxAccessToken = getWxAccessToken(settingData.getAppid(),settingData.getAppsecret());
            mCustomerModel.setAccesstoken(mWxAccessToken.access_token);

            if(mWxLoginModel.getOpenid() != null){
                mCustomerModel.setWxOpenId(mWxLoginModel.getOpenid());
                mCustomerModel.setSessionKey(mWxLoginModel.getSession_key());
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

    //获取微信token
    private WxAccessToken getWxAccessToken(String appID, String appSecret) throws ApiRespStatusException {
        String accessTokenUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appID, appSecret);
        WxAccessToken wxAccessToken = restTemplate.getForObject(accessTokenUrl, WxAccessToken.class);
        return wxAccessToken;
    }

    /**
     * 解密获取用户微信绑定的手机号码
     * @param mWxPhoneReq
     */
    @RequestMapping("/getUserPhone")
    public Object authPhone(@RequestBody WxPhoneReq mWxPhoneReq) throws ApiRespStatusException {
        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {

        String result = WxUtils.wxDecrypt(mWxPhoneReq.getEncryptedData(), mWxPhoneReq.getSeesionKey(), mWxPhoneReq.getIv());
        JSONObject json = JSONObject.parseObject(result);
        log.info("====result==="+result);

            if (json.containsKey("phoneNumber")) {
                String phone = json.getString("phoneNumber");
//                String appid = json.getJSONObject("watermark").getString("appid");
                if (StringUtils.isNoneBlank(phone)) {

                    mBaseDataModel.setState("1000");
                    mBaseDataModel.setMsg("获取成功");
                    mBaseDataModel.setData(phone);
                    return mBaseDataModel;


                } else {
                    mBaseDataModel.setState("1001");
                    mBaseDataModel.setMsg("失败！用户未绑定手机号");
                    mBaseDataModel.setData(false);
                    return mBaseDataModel;
                }
            } else {
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("获取失败:"+json.toString());
                mBaseDataModel.setData(false);
                return mBaseDataModel;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1002");
            mBaseDataModel.setMsg("获取失败");
            mBaseDataModel.setData(false);
            return mBaseDataModel;
        }
    }



}

