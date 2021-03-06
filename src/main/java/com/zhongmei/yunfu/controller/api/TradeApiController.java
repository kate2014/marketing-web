package com.zhongmei.yunfu.controller.api;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ServerAddress;
import com.zhongmei.yunfu.util.ToolsUtil;
import com.zhongmei.yunfu.controller.model.*;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import org.jooq.util.derby.sys.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 交易记录主单（相当于ORDERS）。
 * 主单及其所有子单中的数量、金额在退货时都取反 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
@RestController
@RequestMapping("/wxapp/trade")
public class TradeApiController {

    @Autowired
    TradeService mTradeService;
    @Autowired
    TradeItemService mTradeItemService;
    @Autowired
    TradeCustomerService mTradeCustomerService;
    @Autowired
    WxTradeCustomerService mWxTradeCustomerService;
    @Autowired
    PaymentService mPaymentService;
    @Autowired
    PaymentItemService mPaymentItemService;
    @Autowired
    CommercialPaySettingService mCommercialPaySettingService;
    @Autowired
    CollageMarketingService mCollageMarketingService;
    @Autowired
    CollageCustomerService mCollageCustomerService;
    @Autowired
    CutDownMarketingService mCutDownMarketingService;
    @Autowired
    FlashSalesMarketingService mFlashSalesMarketingService;
    @Autowired
    CustomerService mCustomerService;
    @Autowired
    TradePrivilegeService mTradePrivilegeService;
    @Autowired
    FeedbackService mFeedbackService;
    @Autowired
    StarRatingService mStarRatingService;
    @Autowired
    ActivitySalesService mActivitySalesService;
    @Autowired
    RecommendationAssociationService mRAService;
    @Autowired
    OperationalRecordsService mOperationalRecordsService;
    /**
     * 创建订单
     *
     * @param model
     * @param mTradeModel
     * @return
     */
    @GetMapping("/addTrade")
    public BaseDataModel addTrade(Model model, TradeModel mTradeModel) {

        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            Boolean isScuccess = null;

            //判断用户是能参与活动
            String alertMessage = mWxTradeCustomerService.checkMarketingVaild(mTradeModel);
            if(!alertMessage.equals("")){
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg(alertMessage);
                mBaseDataModel.setData(false);
                return mBaseDataModel;
            }

            //获取商户的appid和secret
            CommercialPaySettingEntity mCommercialPaySetting = new CommercialPaySettingEntity();
            mCommercialPaySetting.setBrandIdenty(mTradeModel.getBrandIdenty());
            mCommercialPaySetting.setShopIdenty(mTradeModel.getShopIdenty());
            mCommercialPaySetting.setStatusFlag(1);
            mCommercialPaySetting.setType(1);
            CommercialPaySettingEntity settingData = mCommercialPaySettingService.queryData(mCommercialPaySetting);

            String subAppid = null;
            if(settingData != null && settingData.getAppid() != null){
                subAppid = settingData.getAppid();
            }else{
                //为设置appid和secret
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("商户未配置门店appid,不能进行购买操作");
                mBaseDataModel.setData(false);
                return mBaseDataModel;
            }

            TradeEntity trade = installTrade(mTradeModel);
            //添加小程序购买记录
            String messgae = createCustomrMarketing(mTradeModel,trade);

            if(!messgae.equals("")){
                mTradeService.deleteById(trade.getId());
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("创建订单失败");
                mBaseDataModel.setData(false);
            }else{
                isScuccess = installTradeItem(mTradeModel, trade);
                isScuccess = installTradeCustomer(mTradeModel, trade);

                if (isScuccess) {

                    mBaseDataModel.setState("1000");
                    mBaseDataModel.setMsg("创建订单成功");
                    mBaseDataModel.setData(trade);
                } else {
                    mBaseDataModel.setState("1001");
                    mBaseDataModel.setMsg("创建订单失败");
                    mBaseDataModel.setData(false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("创建订单失败");
            mBaseDataModel.setData(false);
        }


        return mBaseDataModel;
    }

    /**
     * 创建顾客参与活动信息
     * @param mTradeModel
     * @param trade
     * @return
     */
    public String createCustomrMarketing(TradeModel mTradeModel,TradeEntity trade) throws Exception{
        String message = "";
        //拼团
        if(mTradeModel.getType() == 1){
            //判断是开团还是参团
            if(mTradeModel.getRelationId() == null || mTradeModel.getRelationId() == 0){
                message = createCollageCustomer(mTradeModel, trade);//添加开团信息，并添加wxtradeCustomer表数据
            }else{
                message = joinCollage(mTradeModel, trade);//添加参团信息，并添加wxtradeCustomer表数据
            }

        }else if(mTradeModel.getType() == 2){
            addWxTradeCustomer(trade,mTradeModel);
        }else if(mTradeModel.getType() == 3){
            addWxTradeCustomer(trade,mTradeModel);
        }else if(mTradeModel.getType() == 4){

            String returnMessage = addWxTradeCustomer(trade,mTradeModel);

            if(returnMessage.equals("")){ //订单创建成功后，构建推荐人关联，用于支付完成后发放红包和验证发放礼品

                buildRecommendAssociation(mTradeModel,trade);
            }
        }


        return message;
    }

    /**
     * 构建推荐成单关联关系
     * @param mTradeModel
     * @param trade
     */
    public void buildRecommendAssociation(TradeModel mTradeModel,TradeEntity trade){
        try {


            if((mTradeModel.getRecommendCustomerId() != null && !mTradeModel.getRecommendCustomerId().equals(""))){
                //如果相等，则表示点击是的自己分享的入口，对该操作不做成单记录
                if(mTradeModel.getRecommendCustomerId().longValue() == mTradeModel.getCustomerId().longValue() || mTradeModel.getRecommendOpenId().equals(mTradeModel.getWxOpenId())){
                    return;
                }

                //获取推荐人详细信息
                OperationalRecordsEntity opEntity = new OperationalRecordsEntity();
                opEntity.setBrandIdenty(mTradeModel.getBrandIdenty());
                opEntity.setShopIdenty(mTradeModel.getShopIdenty());
                opEntity.setActivityId(mTradeModel.getMarketingId());
                opEntity.setCustomerId(mTradeModel.getRecommendCustomerId());
//                opEntity.setWxOpenId(mTradeModel.getRecommendOpenId());
                opEntity.setType(1);
                OperationalRecordsEntity mOREntity = mOperationalRecordsService.queryByCustomer(opEntity);

                RecommendationAssociationEntity entity = new RecommendationAssociationEntity();

                entity.setShopIdenty(mTradeModel.getShopIdenty());
                entity.setBrandIdenty(mTradeModel.getBrandIdenty());
                entity.setMainCustomerId(mTradeModel.getRecommendCustomerId());
                entity.setMainCustomerName(mOREntity.getCustomerName());
                entity.setMainWxOpenId(mTradeModel.getRecommendOpenId());
                entity.setMainWxName(mOREntity.getWxName());
                entity.setMainWxPhoto(mOREntity.getWxPhoto());
                entity.setMainCustomerPhone(mOREntity.getCustomerPhone());
                entity.setAcceptWxOpenId(mTradeModel.getWxOpenId());
                entity.setTradeId(trade.getId());
                entity.setActivityId(mTradeModel.getMarketingId());
                entity.setTransactionStatus(1);
                entity.setStatusFlag(1);

                mRAService.addAssociation(entity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 添加会员在小程序上购买的服务
     *
     * @param mTradeModel
     * @return
     */
    private String addWxTradeCustomer(TradeEntity trade, TradeModel mTradeModel) {
        String message = "";

        try{
            Date validityPeriod = null;
            //拼团
            if(mTradeModel.getType() == 1){
                //获取团购活动信息
                CollageMarketingEntity mCollageMarketingEntity = mCollageMarketingService.queryCollageById(mTradeModel.getMarketingId());
                validityPeriod = mCollageMarketingEntity.getValidityPeriod();
                mTradeModel.setMarketingName(mCollageMarketingEntity.getName());
            }
            //砍价
            if(mTradeModel.getType() == 2){
                CutDownMarketingEntity mCutDownMarketingEntity = mCutDownMarketingService.findCutDownDatailById(mTradeModel.getMarketingId());
                validityPeriod = mCutDownMarketingEntity.getValidityPeriod();
                mTradeModel.setMarketingName(mCutDownMarketingEntity.getName());
            }
            //秒杀
            if(mTradeModel.getType() == 3){
                FlashSalesMarketingEntity mFlashSalesMarketingEntity = mFlashSalesMarketingService.queryFlashSalesById(mTradeModel.getMarketingId());
                validityPeriod = mFlashSalesMarketingEntity.getValidityPeriod();
                mTradeModel.setMarketingName(mFlashSalesMarketingEntity.getName());
            }
            //特价活动
            if(mTradeModel.getType() ==4){
                ActivitySalesEntity mActivitySalesEntity = mActivitySalesService.queryById(mTradeModel.getMarketingId());
                validityPeriod = mActivitySalesEntity.getValidityPeriod();
                mTradeModel.setMarketingName(mActivitySalesEntity.getName());
            }


            WxTradeCustomerEntity mWxTradeCustomer = new WxTradeCustomerEntity();
            mWxTradeCustomer.setMarketingId(mTradeModel.getMarketingId());
            mWxTradeCustomer.setMarketingName(mTradeModel.getMarketingName());
            mWxTradeCustomer.setRelateId(mTradeModel.getRelationId());
            mWxTradeCustomer.setCustomerId(mTradeModel.getCustomerId());
            mWxTradeCustomer.setWxPhoto(mTradeModel.getWxPhoto());
            mWxTradeCustomer.setWxOpenId(mTradeModel.getWxOpenId());
            mWxTradeCustomer.setCustomerName(mTradeModel.getCustomerName());
            mWxTradeCustomer.setStatus(1);
            mWxTradeCustomer.setEnabledFlag(2);//启用停用标识 : 1:启用;2:停用
            mWxTradeCustomer.setType(mTradeModel.getType());
            mWxTradeCustomer.setTradeId(trade.getId());

            mWxTradeCustomer.setCode(buildCode(mTradeModel));//随机生成6位数
            if (mTradeModel.getDishId() != null) {
                mWxTradeCustomer.setDishId(Long.valueOf(mTradeModel.getDishId()));
            }
            mWxTradeCustomer.setDishName(mTradeModel.getDishName());
            mWxTradeCustomer.setValidityPeriod(validityPeriod);
            mWxTradeCustomer.setShopIdenty(mTradeModel.getShopIdenty());
            mWxTradeCustomer.setBrandIdenty(mTradeModel.getBrandIdenty());

            Boolean isSuccess = mWxTradeCustomerService.addWxTradeCustomer(mWxTradeCustomer);
            if(!isSuccess){
                message = "添加小程序购买服务失败";
            }
        }catch (Exception e){
            e.printStackTrace();
            message = "添加小程序购买服务失败";
        }

        return message;
    }

    //获取唯一核销码，通过数据库查询判断是否有使用过
    public String buildCode (TradeModel mTradeModel) throws Exception{

        String code = ToolsUtil.getCard(6);

        int count = mWxTradeCustomerService.queryCountByCode(mTradeModel.getBrandIdenty(),mTradeModel.getShopIdenty(),code);
        if(count > 0){
            buildCode (mTradeModel);
        }
        return code;
    }

    /**
     * 发起开团
     * @param mTradeModel
     * @param trade
     * @return
     */
    public String createCollageCustomer(TradeModel mTradeModel,TradeEntity trade) {
        String message = "";
        try {
            CollageCustomerEntity mCollageCustomer = new CollageCustomerEntity();
            mCollageCustomer.setCollageId(mTradeModel.getMarketingId());
            mCollageCustomer.setRelationId(mTradeModel.getRelationId());
            mCollageCustomer.setTradeId(trade.getId());
            mCollageCustomer.setType(1);
            mCollageCustomer.setCustomerId(mTradeModel.getCustomerId());
            mCollageCustomer.setState(1);
            mCollageCustomer.setPaid(1);
            mCollageCustomer.setJoinCount(1);
            mCollageCustomer.setEnabledFlag(2);
            mCollageCustomer.setBrandIdentity(mTradeModel.getBrandIdenty());
            mCollageCustomer.setShopIdentity(mTradeModel.getShopIdenty());

            mCollageCustomer = mCollageCustomerService.installCollageCustomer(mCollageCustomer);

            //添加小程序购买记录
            mTradeModel.setRelationId(mCollageCustomer.getId());
            addWxTradeCustomer(trade,mTradeModel);

        } catch (Exception e) {
            e.printStackTrace();
            message = "开团失败";
        }
        return message;
    }

    /**
     * 参与拼团
     * @param mTradeModel
     * @param trade
     *
     * relationId 参与拼团活动id
     * @return
     */
    public String joinCollage(TradeModel mTradeModel,TradeEntity trade){

        String message = "";
        try{
            CollageCustomerEntity mCollageCustomer = new CollageCustomerEntity();
            mCollageCustomer.setCollageId(mTradeModel.getMarketingId());
            mCollageCustomer.setRelationId(mTradeModel.getRelationId());
            mCollageCustomer.setTradeId(trade.getId());
            mCollageCustomer.setType(2);
            mCollageCustomer.setCustomerId(mTradeModel.getCustomerId());
            mCollageCustomer.setState(1);
            mCollageCustomer.setJoinCount(1);
            mCollageCustomer.setEnabledFlag(2);
            mCollageCustomer.setBrandIdentity(mTradeModel.getBrandIdenty());
            mCollageCustomer.setShopIdentity(mTradeModel.getShopIdenty());


            mCollageCustomer = mCollageCustomerService.installCollageCustomer(mCollageCustomer);

            //添加小程序购买记录
            mTradeModel.setRelationId(mCollageCustomer.getId());
            addWxTradeCustomer(trade,mTradeModel);

        }catch (Exception e){
            e.printStackTrace();
            message = "参与拼团失败";

        }
        return message;

    }

    /**
     * 插入trade表 该服务只支持小程程序下单，而小程程序的拼团、秒杀和砍价只支持单商品，单服务的购买,所以几个价格参数信息一致
     *
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    private TradeEntity installTrade(TradeModel mTradeModel) throws Exception {
        TradeEntity mTrade = new TradeEntity();
        mTrade.setUuid(ToolsUtil.genOnlyIdentifier()); //uuid
        mTrade.setBizDate(new Date());//营业日期
        mTrade.setDomainType(1);
        mTrade.setBusinessType(4);
        mTrade.setTradeType(1);
        mTrade.setSerialNumber(ToolsUtil.getCard(6));//流水号
        mTrade.setTradeTime(new Date());
        mTrade.setTradeStatus(3);
        mTrade.setTradePayStatus(1);
        mTrade.setDeliveryType(1);
        mTrade.setSource(2);
        mTrade.setTradeNo(ToolsUtil.getBillNumber(mTradeModel.getCustomerId()));//订单号,生成规则:固定编码(3)+年月日时分秒(yyMMddHHmmSS)+erp后台设置硬件编号(6)
        mTrade.setDishKindCount(1);
        mTrade.setSaleAmount(mTradeModel.getPrice());//销售金额
        mTrade.setPrivilegeAmount(BigDecimal.ZERO);
        mTrade.setTradeAmount(mTradeModel.getPrice());//应收金额
        mTrade.setTradeAmountBefore(mTradeModel.getPrice());//优惠前金额
        mTrade.setTradeMemo(null);
        mTrade.setDeviceIdenty(mTradeModel.getDeviceIdenty());
        mTrade.setTradePeopleCount(1);
        mTrade.setStatusFlag(1);
        mTrade.setBrandIdenty(mTradeModel.getBrandIdenty());
        mTrade.setShopIdenty(mTradeModel.getShopIdenty());

        mTradeService.addTrade(mTrade);

        return mTrade;
    }

    /**
     * 插入tradeItem表
     *
     * @param mTrade
     * @return
     */
    private Boolean installTradeItem(TradeModel mTradeModel, TradeEntity mTrade) throws Exception {
        TradeItemEntity mTradeItem = new TradeItemEntity();
        mTradeItem.setUuid(ToolsUtil.genOnlyIdentifier());
        mTradeItem.setTradeId(mTrade.getId());
        mTradeItem.setTradeUuid(mTrade.getUuid());
        mTradeItem.setDishId(mTradeModel.getDishId());
        mTradeItem.setDishName(mTradeModel.getDishName());
        mTradeItem.setType(1);
        mTradeItem.setSort(1);
        mTradeItem.setPrice(mTradeModel.getPrice());
        mTradeItem.setQuantity(BigDecimal.ONE);
        mTradeItem.setAmount(mTradeModel.getPrice());
        mTradeItem.setPropertyAmount(BigDecimal.ZERO);
        mTradeItem.setActualAmount(mTradeModel.getPrice());
        mTradeItem.setStatusFlag(1);
        mTradeItem.setBrandIdenty(mTradeModel.getBrandIdenty());
        mTradeItem.setShopIdenty(mTradeModel.getShopIdenty());
        mTradeItem.setDeviceIdenty(mTradeModel.getDeviceIdenty());

        Boolean isScuccess = mTradeItemService.addTradeItem(mTradeItem);

        return isScuccess;
    }

    /**
     * 添加订单关联会员信息
     *
     * @param mTradeModel
     * @param mTrade
     * @return
     * @throws Exception
     */
    private Boolean installTradeCustomer(TradeModel mTradeModel, TradeEntity mTrade) throws Exception {

        CustomerEntity mCustomerEntity = mCustomerService.queryCustomerByMobile(mTradeModel.getBrandIdenty(),mTradeModel.getShopIdenty(),mTradeModel.getCustomerPhone());

        TradeCustomerEntity mTradeCustomer = new TradeCustomerEntity();
        mTradeCustomer.setUuid(ToolsUtil.genOnlyIdentifier());
        mTradeCustomer.setTradeId(mTrade.getId());
        mTradeCustomer.setTradeUuid(mTrade.getUuid());
        mTradeCustomer.setCustomerType(3);//来客类型: 1:预约人, 2:支付人, 3:MEMBER:登录的会员
        mTradeCustomer.setCustomerId(mCustomerEntity.getId());
        mTradeCustomer.setCustomerName(mCustomerEntity.getName());
        mTradeCustomer.setCustomerPhone(mCustomerEntity.getMobile());
        mTradeCustomer.setCustomerSex(mCustomerEntity.getGender());
        mTradeCustomer.setStatusFlag(1);
        mTradeCustomer.setBrandIdenty(mTradeModel.getBrandIdenty());
        mTradeCustomer.setShopIdenty(mTradeModel.getShopIdenty());
        mTradeCustomer.setDeviceIdenty(mTradeModel.getDeviceIdenty());

        Boolean isSuccess = mTradeCustomerService.installTradeCustomer(mTradeCustomer);

        return isSuccess;
    }


    /**
     * 修改订单
     *
     * @param model
     * @param mTradeModel
     * @return
     */
    @GetMapping("/updateTrade")
    private BaseDataModel updateTrade(Model model, TradeModel mTradeModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();

        return mBaseDataModel;
    }

    /**
     * 获取会员相关的订单
     * /trade/queryTradeByCustomer?brandIdenty=59659&shopIdenty=810180656&customerId=31&startDate=2018-09-17 00:00:00.000&endDate=2018-09-19 00:00:00.000
     *
     * @param model
     * @param mTradeModel
     * @return
     */
    @GetMapping("/queryTradeByCustomer")
    private BaseDataModel queryTradeByCustomer(Model model, TradeModel mTradeModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            CustomerEntity mCustomerEntity = mCustomerService.queryCustomerById(mTradeModel.getBrandIdenty(),mTradeModel.getShopIdenty(),mTradeModel.getCustomerId());
            //根据带有电话号码的会员进行订单提取
            mTradeModel.setCustomerId(mCustomerEntity.getRelateId());
            Page<TradeCustomerEntity> listTradeCustomer = mTradeCustomerService.queryTradeByCustomer(mTradeModel);


            StringBuffer tradeId = new StringBuffer();


            for (TradeCustomerEntity tc : listTradeCustomer.getRecords()) {
                if (tradeId.length() != 0) {
                    tradeId.append(",");
                }
                tradeId.append(tc.getTradeId());
            }
            if(tradeId.length() != 0) {
                List<TradeEntity> listTrade = mTradeService.queryTradeByCustomer(mTradeModel.getBrandIdenty(), mTradeModel.getShopIdenty(), tradeId.toString(),mTradeModel.getTradeStatus());

                //判断是否有退款中的订单，如果有这发起刷新退货单退货信息
                for(TradeEntity trade:listTrade){
                    if(trade.getTradePayStatus() == 4){
                        PaymentItemEntity mPaymentItemEntity = mPaymentItemService.queryPaymentItemByTradeId(trade.getId());
                        mPaymentItemService.refundquery(mPaymentItemEntity,trade.getId());
                    }
                }

                TradePageDataModel mTradePageDataModel = new TradePageDataModel();
                if(listTrade.size() < mTradeModel.getPageSize()){
                    mTradePageDataModel.setEndPage(true);
                }else{
                    mTradePageDataModel.setEndPage(false);
                }
                mTradePageDataModel.setListTradeData(listTrade);
                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("获取会员订单数据成功");
                mBaseDataModel.setData(mTradePageDataModel);

            }else{
                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("该会员无订单信息");
                mBaseDataModel.setData(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取会员订单数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 查询订单相关的详细信息
     * @param model
     * @param mTradeModel
     * @return
     */
    @GetMapping("/queryTradeDetail")
    private BaseDataModel queryTradeDetail(Model model, TradeModel mTradeModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();

        try{

            TradeDataModel mTradeDataModel = mTradeService.queryTradeDetail(mTradeModel);

            List<FeedbackEntity> listFeedback = mFeedbackService.queryFeedbackByTradeId(mTradeModel.getBrandIdenty(),mTradeModel.getShopIdenty(),mTradeModel.getTradeId());
            StarRatingEntity mStarRatingEntity = new StarRatingEntity();
            mStarRatingEntity.setBrandIdenty(mTradeModel.getBrandIdenty());
            mStarRatingEntity.setShopIdenty(mTradeModel.getShopIdenty());
            mStarRatingEntity.setTradeId(mTradeModel.getTradeId());
            List<StarRatingEntity> listStar = mStarRatingService.queryStarList(mStarRatingEntity);

            mTradeDataModel.setListStarRating(listStar);

            List<FeedbackModel> listFeedbackModel = new ArrayList<>();
            for(FeedbackEntity entity : listFeedback){
                FeedbackModel mFeedbackModel = new FeedbackModel();
                mFeedbackModel.setType(entity.getStart());
                mFeedbackModel.setRelateId(entity.getRelateId());
                mFeedbackModel.setContent(entity.getContent());
                mFeedbackModel.setReplayContent(entity.getReplayContent());
                mFeedbackModel.setStart(entity.getStart());
                mFeedbackModel.setServerCreateTime(DateFormatUtil.format(entity.getServerCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                mFeedbackModel.setServerUpdateTime(DateFormatUtil.format(entity.getServerUpdateTime(),"yyyy-MM-dd HH:mm:ss"));
                listFeedbackModel.add(mFeedbackModel);
            }
            mTradeDataModel.setListFeedback(listFeedbackModel);

            if(mTradeDataModel.getTrade().getSource() == 2){
                WxTradeCustomerEntity mWxTradeCustomerEntity = mWxTradeCustomerService.queryByTradeId(mTradeDataModel.getTrade().getId());
                mTradeDataModel.setWxTradeCustomerEntity(mWxTradeCustomerEntity);
            }

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("该会订单信息成功");
            mBaseDataModel.setData(mTradeDataModel);

        }catch (Exception e){
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("该会订单信息失败");
            mBaseDataModel.setData(false);
        }


        return mBaseDataModel;
    }

}

