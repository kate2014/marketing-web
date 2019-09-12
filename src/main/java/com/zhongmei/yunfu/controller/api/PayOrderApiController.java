package com.zhongmei.yunfu.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhongmei.yunfu.controller.weixinPay.*;
import com.zhongmei.yunfu.thirdlib.wxapp.WxTemplateMessageHandler;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.ActivityBuyMessage;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.OrderPayMessage;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.WxTempMsg;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.HttpMessageConverterUtils;
import com.zhongmei.yunfu.util.ToolsUtil;
import com.zhongmei.yunfu.controller.model.*;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.KeyStore;
import java.util.*;

@RestController
@RequestMapping("/wxapp/pay")
public class PayOrderApiController {

    private static Logger log = LoggerFactory.getLogger(PayOrderApiController.class);

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
    CutDownCustomerService mCutDownCustomerService;
    @Autowired
    FlashSalesMarketingService mFlashSalesMarketingService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedPacketsRecordService mRedPacketsRecordService;
    @Autowired
    RecommendationAssociationService mRecommendationAssociationService;
    @Autowired
    CommercialService mCommercialService;

    @GetMapping("/payOrder")
    public BaseDataModel payOrder(Model model, TradeModel mTradeModel){
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            //验证是否能参加
            WxTradeCustomerEntity mWxTradeCustomerEntity = mWxTradeCustomerService.queryByTradeId(mTradeModel.getTradeId());

            if(mWxTradeCustomerEntity != null){
                if(mWxTradeCustomerEntity.getType() == 1){
                    CollageCustomerEntity mCollageCustomerEntity = mCollageCustomerService.queryCollageByTradeId(mTradeModel.getTradeId());
                    mTradeModel.setRelationId(mCollageCustomerEntity.getRelationId());
                }
                mTradeModel.setType(mWxTradeCustomerEntity.getType());
                mTradeModel.setMarketingId(mWxTradeCustomerEntity.getMarketingId());

                String checkMessage = mWxTradeCustomerService.checkMarketingVaild(mTradeModel);

                if(checkMessage.equals("")){
                    //获取用户支付相关信息
                    CommercialPaySettingEntity mCommercialPaySettingEntity = new CommercialPaySettingEntity();
                    mCommercialPaySettingEntity.setShopIdenty(mTradeModel.getShopIdenty());
                    mCommercialPaySettingEntity.setBrandIdenty(mTradeModel.getBrandIdenty());
                    mCommercialPaySettingEntity.setType(1);
                    mCommercialPaySettingEntity.setStatusFlag(1);
                    CommercialPaySettingEntity paySetting =  mCommercialPaySettingService.queryData(mCommercialPaySettingEntity);
                    if(paySetting == null){
                        mBaseDataModel.setState("1001");
                        mBaseDataModel.setMsg("商户未开通支付");
                        mBaseDataModel.setData(false);
                        return mBaseDataModel;
                    }else{
                        TradeEntity trade =  mTradeService.queryTradeById(mTradeModel.getTradeId());

                        List<PaymentEntity> listPayment = mPaymentService.queryPayment(trade.getId());
                        //判断支付单是否已生成
                        if(listPayment != null && listPayment.size() > 0){
                            Long paymentId = listPayment.get(0).getId();
                            List<PaymentItemEntity> listPaymentItem = mPaymentItemService.queryPaymentItem(paymentId);
                            PaymentItemEntity paymentItem = listPaymentItem.get(0);
                            WxPayMsgModel mWxPayMsgModel = getPayparams(paymentItem,paymentItem.getUsefulAmount(),paySetting,mTradeModel.getWxOpenId());

                            CreatePayModel mCreatePayModel = new CreatePayModel();
                            mCreatePayModel.setmWxPayMsgModel(mWxPayMsgModel);
                            mCreatePayModel.setmPaymentEntity(listPayment.get(0));
                            mCreatePayModel.setmPaymentItemEntity(paymentItem);

                            mBaseDataModel.setState("1000");
                            mBaseDataModel.setMsg("发起支付成功，已生成支付订单");
                            mBaseDataModel.setData(mCreatePayModel);

                        }else{
                            PaymentEntity mPaymentEntity = installPayment(trade);
                            PaymentItemEntity mPaymentItemEntity = installPaymentItem(mPaymentEntity);

//                            WxPayMsgModel mWxPayMsgModel = getYiPayparams(mPaymentItemEntity,mTradeModel.getWxOpenId());

                            WxPayMsgModel mWxPayMsgModel = getPayparams(mPaymentItemEntity,mPaymentItemEntity.getUsefulAmount(),paySetting,mTradeModel.getWxOpenId());

                            CreatePayModel mCreatePayModel = new CreatePayModel();
                            mCreatePayModel.setmWxPayMsgModel(mWxPayMsgModel);
                            mCreatePayModel.setmPaymentEntity(mPaymentEntity);
                            mCreatePayModel.setmPaymentItemEntity(mPaymentItemEntity);

                            mBaseDataModel.setState("1000");
                            mBaseDataModel.setMsg("发起支付成功，已生成支付订单");
                            mBaseDataModel.setData(mCreatePayModel);
                        }

                        return mBaseDataModel;

                    }
                }else{
                    mBaseDataModel.setState("1001");
                    mBaseDataModel.setMsg(checkMessage);
                    mBaseDataModel.setData(false);
                    return mBaseDataModel;
                }
            }

        }catch (Exception e){
            e.printStackTrace();

        }
        mBaseDataModel.setState("1001");
        mBaseDataModel.setMsg("发起支付失败");
        mBaseDataModel.setData(false);
        return mBaseDataModel;
    }

    /**
     * 查询本地数据库获取订单支付结果
     * @param model
     * @param mTradeModel
     * @return
     */
    @GetMapping("/queryPayState")
    public BaseDataModel queryPayState(Model model, TradeModel mTradeModel){
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            TradeEntity mTradeEntity = mTradeService.queryTradeById(mTradeModel.getTradeId());
            //判断是否支付完成
            if(mTradeEntity.getTradePayStatus() == 3){
                WxTradeCustomerEntity mWxTradeCustomerEntity = mWxTradeCustomerService.queryByTradeId(mTradeModel.getTradeId());
                //判断是否是拼团
                if(mWxTradeCustomerEntity.getType() == 1){
                    CollageCustomerEntity mCollageCustomerEntity = mCollageCustomerService.queryCollageByTradeId(mTradeModel.getTradeId());
                    if(mCollageCustomerEntity.getRelationId() == null){
                        mCollageCustomerEntity.setRelationId(mCollageCustomerEntity.getId());
                    }
                    mBaseDataModel.setState("1000");
                    mBaseDataModel.setMsg("购买小程序服务成功");
                    mBaseDataModel.setData(mCollageCustomerEntity);
                    return mBaseDataModel;
                }else{
                    mBaseDataModel.setState("1000");
                    mBaseDataModel.setMsg("购买小程序服务成功");
                    mBaseDataModel.setData(true);
                    return mBaseDataModel;
                }
            }else{
                //如果为支付完成，这调用微信支付接口验证支付结果
                WxTradeCustomerEntity mWxTradeCustomerEntity = orderquery(model,mTradeModel);
                if(mWxTradeCustomerEntity != null){
                    if(mWxTradeCustomerEntity.getType() == 1){
                        CollageCustomerEntity mCollageCustomerEntity = mCollageCustomerService.queryCollageByTradeId(mTradeModel.getTradeId());
                        if(mCollageCustomerEntity.getRelationId() == null){
                            mCollageCustomerEntity.setRelationId(mCollageCustomerEntity.getId());
                        }
                        mBaseDataModel.setState("1000");
                        mBaseDataModel.setMsg("购买小程序服务成功");
                        mBaseDataModel.setData(mCollageCustomerEntity);
                        return mBaseDataModel;
                    }else{
                        mBaseDataModel.setState("1000");
                        mBaseDataModel.setMsg("购买小程序服务成功");
                        mBaseDataModel.setData(true);
                        return mBaseDataModel;
                    }
                }else{
                    mBaseDataModel.setState("1001");
                    mBaseDataModel.setMsg("订单支付结果未成功，请前往个人订单中心查看");
                    mBaseDataModel.setData(false);
                    return mBaseDataModel;
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取订单支付结果失败");
            mBaseDataModel.setData(false);
            return mBaseDataModel;
        }

    }


    /**
     * 修改活动参与信息,主要修改活动修改情况和会员购买信息
     * @param tradeId
     * @return
     */
    private WxTradeCustomerEntity updateMarketing(Long tradeId){

        Boolean isSuccess = true;

        WxTradeCustomerEntity mWxTradeCustomerEntity = new WxTradeCustomerEntity();
        try{
            mWxTradeCustomerEntity = mWxTradeCustomerService.queryByTradeId(tradeId);

            int type = mWxTradeCustomerEntity.getType();
            //拼团
            if(type == 1){
                //更新顾客活动购买状态
                updateWxTrade(tradeId,2);
                //修改参团信息
                isSuccess = updateCollageCustomer(tradeId);

            }
            //砍价
            if(type == 2){
                //更新顾客活动购买状态
                updateWxTrade(tradeId,1);
                isSuccess = updateCutDownMarketing(mWxTradeCustomerEntity);
            }
            //秒杀
            if(type == 3){
                //更新顾客活动购买状态
                updateWxTrade(tradeId,1);
                isSuccess = updateFlashSalesMarketing(mWxTradeCustomerEntity);
            }
            //特价活动
            if(type == 4){
                //更改推荐成单状态
                RecommendationAssociationEntity mRAEntity = modfityRAState(mWxTradeCustomerEntity);
                //更新顾客活动购买状态
                updateWxTrade(tradeId,1);
                //触发发送推荐成单红包,发放推荐达成单数赠送礼品
                mRedPacketsRecordService.sendRedPackets(mRAEntity,tradeId);
            }

        }catch (Exception e){
            e.printStackTrace();

        }

        return mWxTradeCustomerEntity;
    }

    /**
     * 更新推荐成单状态
     * @param mWxTradeCustomerEntity
     * @return
     * @throws Exception
     */
    public RecommendationAssociationEntity modfityRAState(WxTradeCustomerEntity mWxTradeCustomerEntity) throws Exception{
        RecommendationAssociationEntity mRAEntity = mRecommendationAssociationService.queryByTradeId(mWxTradeCustomerEntity.getBrandIdenty(),mWxTradeCustomerEntity.getShopIdenty(),mWxTradeCustomerEntity.getTradeId());
        //如果不为空，这表示该订单是推荐成单
        if(mRAEntity != null){
            RecommendationAssociationEntity entity = new RecommendationAssociationEntity();
            entity.setBrandIdenty(mRAEntity.getBrandIdenty());
            entity.setShopIdenty(mRAEntity.getShopIdenty());
            entity.setTradeId(mWxTradeCustomerEntity.getTradeId());
            entity.setActivityId(mWxTradeCustomerEntity.getMarketingId());
            entity.setTransactionStatus(2);
            entity.setServerUpdateTime(new Date());
            mRecommendationAssociationService.modfityAssciation(entity);
        }
        return mRAEntity;
    }

    /**
     * 修改砍价购买信息
     * @param mWxTradeCustomerEntity
     * @return
     */
    public Boolean updateCutDownMarketing(WxTradeCustomerEntity mWxTradeCustomerEntity){
        try{

            CutDownMarketingEntity mCutDownMarketing = new CutDownMarketingEntity();
            mCutDownMarketing.setId(mWxTradeCustomerEntity.getMarketingId());
            mCutDownMarketingService.modifyCutDownCount(mCutDownMarketing);

            CutDownCustomerEntity mCutDownCustomerEntity = mCutDownCustomerService.queryDataById(mWxTradeCustomerEntity.getRelateId());
            CutDownCustomerEntity temp = new CutDownCustomerEntity();
            temp.setId(mCutDownCustomerEntity.getId());
            temp.setState(4);
            temp.setServerUpdateTime(new Date());
            mCutDownCustomerService.updateData(temp);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改秒杀活动购买信息
     * @param mWxTradeCustomerEntity
     * @return
     */
    public Boolean updateFlashSalesMarketing(WxTradeCustomerEntity mWxTradeCustomerEntity){
        try {
            FlashSalesMarketingEntity mFlashSalesMarketing = new FlashSalesMarketingEntity();
            mFlashSalesMarketing.setId(mWxTradeCustomerEntity.getMarketingId());
            mFlashSalesMarketingService.modifyFlashSalesCount(mFlashSalesMarketing);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }

    }

    /**
     * 根据参团CollageCustomer数据信息
     * @param tradeId
     * @return
     */
    public Boolean updateCollageCustomer(Long tradeId){
        Boolean isSuccess = true;
        try{

            CollageCustomerEntity mCollageCustomerEntity = mCollageCustomerService.queryCollageByTradeId(tradeId);

            //修改该次拼团参团信息
            CollageCustomerEntity mCollageCustomer = new CollageCustomerEntity();
            mCollageCustomer.setId(mCollageCustomerEntity.getId());
            mCollageCustomer.setEnabledFlag(1);
            mCollageCustomer.setPaid(2);

            //修改该团参与数信息
            CollageMarketingEntity mCollageMarketingEntity = mCollageMarketingService.queryCollageById(mCollageCustomerEntity.getCollageId());
            CollageMarketingEntity mCollageMarketing = new CollageMarketingEntity();
            mCollageMarketing.setId(mCollageMarketingEntity.getId());
            mCollageMarketing.setJoinCount(mCollageMarketingEntity.getJoinCount() + 1);

            //表示参团
            if(mCollageCustomerEntity.getRelationId() != null && mCollageCustomerEntity.getRelationId() != 0){

                //查询开团信息
                CollageCustomerEntity mainCollageCustomer = mCollageCustomerService.queryCollage(mCollageCustomerEntity.getRelationId());
                mainCollageCustomer.setJoinCount(mainCollageCustomer.getJoinCount() + 1);

                //验证是否已成团
                if(mCollageMarketingEntity.getCollagePeopleCount() <= mainCollageCustomer.getJoinCount()){
                    //更新拼团活动成团数量

                    mCollageMarketing.setFinishGroupCount(mCollageMarketingEntity.getFinishGroupCount() + 1);

                    mainCollageCustomer.setState(2);

                    //参与该团活动的所以购买信息修改为购买完成并生效
                    updateCollageWxTrade(mCollageCustomerEntity.getRelationId());
                }
                //修改开团住团信息
                mCollageCustomerService.updateCollageCustomer(mainCollageCustomer);

            }else{//表示开团
                mCollageMarketing.setOpenGroupCount(mCollageMarketingEntity.getOpenGroupCount() + 1);

                mCollageCustomer.setJoinCount(1);
            }

            //修改拼团活动参与人数和成团数
            mCollageMarketingService.modifyCollage(mCollageMarketing);

            mCollageCustomerService.updateCollageCustomer(mCollageCustomer);

        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 成团时 根据开团Id修改该团所以参团状态为已购买已生效
     * @param customerCollageId
     * @return
     */
    public Boolean updateCollageWxTrade(Long customerCollageId)throws Exception{
        //将该团购买信息标记为有效
        Boolean isSuccess = mWxTradeCustomerService.modfiyEnabledFlagByMainCollage(customerCollageId);
        //将参团信息信息为成功
        CollageCustomerEntity mCollageCustomerEntity = new CollageCustomerEntity();
        mCollageCustomerEntity.setState(2);
        mCollageCustomerEntity.setPaid(2);
        mCollageCustomerEntity.setEnabledFlag(1);
        isSuccess =  mCollageCustomerService.updateCollageByRelationId(customerCollageId,mCollageCustomerEntity);
        return isSuccess;
    }

    public Boolean updateWxTrade(Long tradeId,Integer enabledFlag){
        Boolean isSuccess = true;
        try{
            WxTradeCustomerEntity mWxTradeCustomer = new WxTradeCustomerEntity();
            mWxTradeCustomer.setTradeId(tradeId);
            mWxTradeCustomer.setEnabledFlag(enabledFlag);
            isSuccess = mWxTradeCustomerService.modfiyEnabledFlag(mWxTradeCustomer);
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
        }
        return isSuccess;

    }

    /**
     * 插入支付payment信息
     * @param mTrade
     * @return
     */
    private PaymentEntity installPayment(TradeEntity mTrade)throws Exception{

        PaymentEntity mPaymentEntity = new PaymentEntity();
        mPaymentEntity.setBizDate(new Date());
        mPaymentEntity.setPaymentTime(new Date());
        mPaymentEntity.setPaymentType(1);//付款类型：1:trade_sell:交易支付，2:trade_refund:交易退款， 3:会员储值(pad支付/微信上支付,没有mac地址)， 4:会员储值退款,5次卡购买,6次卡退卡，7:调账
        mPaymentEntity.setRelateId(mTrade.getId());
        mPaymentEntity.setRelateUuid(mTrade.getUuid());
        mPaymentEntity.setReceivableAmount(mTrade.getTradeAmount());//可收金额，抹零前顾客应该支付的金额
        mPaymentEntity.setExemptAmount(BigDecimal.ZERO);//抹零金额
        mPaymentEntity.setActualAmount(mTrade.getTradeAmount());//顾客实付金额
        mPaymentEntity.setBrandIdenty(mTrade.getBrandIdenty());
        mPaymentEntity.setShopIdenty(mTrade.getShopIdenty());
        mPaymentEntity.setDeviceIdenty(mTrade.getDeviceIdenty());
        mPaymentEntity.setUuid(ToolsUtil.genOnlyIdentifier());
        mPaymentEntity.setStatusFlag(1);
        mPaymentEntity.setClientCreateTime(new Date());
        mPaymentEntity.setClientUpdateTime(new Date());
        mPaymentEntity.setServerCreateTime(new Date());
        mPaymentEntity.setServerUpdateTime(new Date());
        mPaymentEntity.setPaid(2);//是否已付款 1 已付款  2 未付款
        mPaymentEntity.setMemo(null);
        mPaymentEntity.setRecycleStatus(1);
        mPaymentEntity.setShopActualAmount(mTrade.getTradeAmount());
        Boolean isSuccess = mPaymentService.installPayment(mPaymentEntity);
        return mPaymentEntity;
    }

    private PaymentItemEntity installPaymentItem(PaymentEntity mPaymentEntity) throws Exception{
        PaymentItemEntity mPaymentItemEntity = new PaymentItemEntity();
        mPaymentItemEntity.setPaymentId(mPaymentEntity.getId());
        mPaymentItemEntity.setPaymentUuid(mPaymentEntity.getUuid());
        mPaymentItemEntity.setPayModeId(4l);//支付方式ID(cashTypeId)：1:会员余额, 2:现金,3:银行卡,4:微信支付,5:支付宝
        mPaymentItemEntity.setPayModeName("微信支付");
        mPaymentItemEntity.setFaceAmount(mPaymentEntity.getActualAmount());
        mPaymentItemEntity.setChangeAmount(BigDecimal.ZERO);//应小程序微信支付，所以没有找零
        mPaymentItemEntity.setUsefulAmount(mPaymentEntity.getActualAmount());
        mPaymentItemEntity.setRelateId(null);
        mPaymentItemEntity.setPayStatus(1);//支付状态：  1:UNPAID:未支付  2:PAYING:支付中，微信下单选择了在线支付但实际上未完成支付的  (删了)  3:PAID:已支付  4:REFUNDING:退款中  5:REFUNDED:已退款  6:REFUND_FAILED:退款失败  7:PREPAID:预支付(现在都没用)  8:WAITING_REFUND:等待退款 9:PAY_FAIL支付失败 10:REPEAT_PAID重复支付 11:异常支付
        mPaymentItemEntity.setBrandIdenty(mPaymentEntity.getBrandIdenty());
        mPaymentItemEntity.setShopIdenty(mPaymentEntity.getShopIdenty());
        mPaymentItemEntity.setDeviceIdenty(mPaymentEntity.getDeviceIdenty());
        mPaymentItemEntity.setUuid(ToolsUtil.genOnlyIdentifier());
        mPaymentItemEntity.setStatusFlag(1);
        mPaymentItemEntity.setClientCreateTime(new Date());
        mPaymentItemEntity.setClientUpdateTime(new Date());
        mPaymentItemEntity.setServerCreateTime(new Date());
        mPaymentItemEntity.setServerUpdateTime(new Date());
        mPaymentItemEntity.setRefund(2);//是否支持退款  1：是；2：否'
        mPaymentItemEntity.setPayMemo(null);
        mPaymentItemEntity.setRefundWay(1);//退款方式，1：无需退款 2：自动退款 3：手动退款
        mPaymentItemEntity.setPaySource(2);//支付来源， 1：pos, 2:美业小程序
        mPaymentItemService.installPaymentItem(mPaymentItemEntity);
        return mPaymentItemEntity;
    }

    /**
     * 获取小程序支付信息
     * @return
     */
    public WxPayMsgModel getPayparams(PaymentItemEntity mPaymentItemEntity,BigDecimal totalFee,CommercialPaySettingEntity mCommercialPaySettingEntity,String openId) throws Exception{

        UnifiedorderEntity mUnifiedorderEntity = createUnifiedorderEntity(mPaymentItemEntity,totalFee,mCommercialPaySettingEntity,openId);
        String params = HttpMessageConverterUtils.objectToXml(mUnifiedorderEntity);

        HttpEntity<String> formEntity = new HttpEntity<String>(params);
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String xmlResult = restTemplate.exchange(url, HttpMethod.POST, formEntity, String.class).getBody().toString();
        WeiXinPayRsqEntity mWeiXinPayRsqEntity = (WeiXinPayRsqEntity) HttpMessageConverterUtils.xmlToObject(xmlResult,WeiXinPayRsqEntity.class);

        WxPayMsgModel mWxPayMsgModel = new WxPayMsgModel();
        mWxPayMsgModel.setAppid(mCommercialPaySettingEntity.getAppid());
        mWxPayMsgModel.setNonce_str(mWeiXinPayRsqEntity.getNonce_str());
        mWxPayMsgModel.setPrepay_id(mWeiXinPayRsqEntity.getPrepay_id());
        mWxPayMsgModel.setSign_type("MD5");
        mWxPayMsgModel.setTime_stamp(mUnifiedorderEntity.getTime_start());

        String packageValue = "prepay_id="+mWeiXinPayRsqEntity.getPrepay_id();

        String paySign = "appId="+mCommercialPaySettingEntity.getAppid()+"&nonceStr="+mWeiXinPayRsqEntity.getNonce_str()+"&package="+packageValue+"&signType=MD5&timeStamp="+mUnifiedorderEntity.getTime_start();

        paySign = paySign+"&key="+mCommercialPaySettingEntity.getApiSecret();

        String sign = ToolsUtil.getMd5(paySign).toUpperCase();

        mWxPayMsgModel.setSign(sign);

        return mWxPayMsgModel;
    }

    /**
     * 构建调用微信统一下单接口参数
     * @param mPaymentItemEntity
     * @param totalFee
     * @param mCommercialPaySettingEntity
     * @param openId
     * @return
     * @throws Exception
     */
    public UnifiedorderEntity createUnifiedorderEntity(PaymentItemEntity mPaymentItemEntity,BigDecimal totalFee, CommercialPaySettingEntity mCommercialPaySettingEntity,String openId) throws Exception{

        UnifiedorderEntity mUnifiedorderEntity = new UnifiedorderEntity();

        mUnifiedorderEntity.setAppid(mCommercialPaySettingEntity.getAppid());
        mUnifiedorderEntity.setMch_id(mCommercialPaySettingEntity.getWxShopId());
        mUnifiedorderEntity.setNonce_str(mPaymentItemEntity.getUuid());

        mUnifiedorderEntity.setBody("众美云服-服务购买");
        mUnifiedorderEntity.setOut_trade_no(mPaymentItemEntity.getId().toString());
        mUnifiedorderEntity.setFee_type("CNY");
        mUnifiedorderEntity.setTotal_fee(totalFee.multiply(new BigDecimal(100)).intValue());

        mUnifiedorderEntity.setSpbill_create_ip("47.105.100.99");

        String timeStart = DateFormatUtil.format(new Date(),DateFormatUtil.FORMAT_DATE_STRING);
        mUnifiedorderEntity.setTime_start(timeStart);

        Date date = new Date();
        Calendar dar= Calendar.getInstance();
        dar.setTime(date);
        dar.add(java.util.Calendar.HOUR_OF_DAY, 2);
        String timeExpire = DateFormatUtil.format(dar.getTime(),DateFormatUtil.FORMAT_DATE_STRING);
        mUnifiedorderEntity.setTime_expire(timeExpire);

        mUnifiedorderEntity.setNotify_url("https://mk.zhongmeiyunfu.com/marketing/wxapp/pay/payNotify");
        mUnifiedorderEntity.setTrade_type("JSAPI");

        mUnifiedorderEntity.setOpenid(openId);


        mUnifiedorderEntity.setSign_type("MD5");

        String stringA = "appid="+mUnifiedorderEntity.getAppid()+"&body="+mUnifiedorderEntity.getBody()+"&fee_type="+mUnifiedorderEntity.getFee_type()+"&mch_id="+mUnifiedorderEntity.getMch_id()+
                "&nonce_str="+mUnifiedorderEntity.getNonce_str()+"&notify_url="+mUnifiedorderEntity.getNotify_url()+"&openid="+mUnifiedorderEntity.getOpenid()+
                "&out_trade_no="+mUnifiedorderEntity.getOut_trade_no()+"&sign_type="+mUnifiedorderEntity.getSign_type()+"&spbill_create_ip="+
                mUnifiedorderEntity.getSpbill_create_ip()+"&time_expire="+mUnifiedorderEntity.getTime_expire()+"&time_start="+mUnifiedorderEntity.getTime_start()+
                "&total_fee="+mUnifiedorderEntity.getTotal_fee()+"&trade_type="+mUnifiedorderEntity.getTrade_type();

        String stringSignTemp=stringA+"&key="+mCommercialPaySettingEntity.getApiSecret();;
        String sign = ToolsUtil.getMd5(stringSignTemp).toUpperCase();

        mUnifiedorderEntity.setSign(sign);

        return mUnifiedorderEntity;

    }

    @RequestMapping(value = "/payNotify",produces = "application/xml")
    public String payNotify(HttpServletRequest request) {

        String resXml = "";

        ServletInputStream inStream;
        try {
            inStream = request.getInputStream();

            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }

            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息

            WeiXinNotifyModel mWeiXinNotifyModel = HttpMessageConverterUtils.xmlToObject(result,WeiXinNotifyModel.class);

            if (mWeiXinNotifyModel.getResult_code().equalsIgnoreCase("SUCCESS")) {
                if (verifyWeixinNotify(mWeiXinNotifyModel)) {
                    //订单paymentItem id
                    updateTradeState(Long.valueOf(mWeiXinNotifyModel.getOut_trade_no()));

                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                }else{
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                }

            }else {

                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resXml;
    }


    /**
     * 签名验证
     * @param mWeiXinNotifyModel
     * @return
     */
    public boolean verifyWeixinNotify(WeiXinNotifyModel mWeiXinNotifyModel)throws Exception {

        String paymentItemId = mWeiXinNotifyModel.getOut_trade_no();
        String paymentUUId = mWeiXinNotifyModel.getNonce_str();

        PaymentItemEntity mPaymentItemEntity = mPaymentItemService.queryPaymentItemById(Long.valueOf(paymentItemId));
        int totalFee = mWeiXinNotifyModel.getTotal_fee();
        BigDecimal fee = new BigDecimal(totalFee);

        fee = fee.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

        //判断支付金额是否一致
        if(mPaymentItemEntity.getUsefulAmount().compareTo(fee) == 0){
            if(paymentUUId.equals(mWeiXinNotifyModel.getNonce_str())){
                return true;
            }else {
                return false;
            }
        }else{
            return false;
        }

    }


    /**
     * 修改订单相关的支付状态
     * @param paymentItemId
     * @throws Exception
     */
    public WxTradeCustomerEntity updateTradeState(Long paymentItemId)throws Exception{

        //修改paymentItem支付状态
        PaymentItemEntity mPaymentItemEntity = new PaymentItemEntity();
        mPaymentItemEntity.setServerUpdateTime(new Date());
        mPaymentItemEntity.setId(paymentItemId);
        mPaymentItemEntity.setPayStatus(3);
        mPaymentItemService.updatePaymentItem(mPaymentItemEntity);

        //修改payment支付状态
        PaymentEntity mPaymentEntity = mPaymentService.queryPaymentByItemId(paymentItemId);
        PaymentEntity tempPayment = new PaymentEntity();
        tempPayment.setServerUpdateTime(new Date());
        tempPayment.setId(mPaymentEntity.getId());
        tempPayment.setPaid(1);
        mPaymentService.updatePayment(tempPayment);

        //修改trade支付状态
        TradeEntity mTradeEntity = new TradeEntity();
        mTradeEntity.setServerUpdateTime(new Date());
        mTradeEntity.setId(mPaymentEntity.getRelateId());
        mTradeEntity.setTradePayStatus(3);
        mTradeEntity.setTradeStatus(4);
        mTradeService.updateTrade(mTradeEntity);

        WxTradeCustomerEntity mWxTradeCustomerEntity = updateMarketing(mPaymentEntity.getRelateId());

        sendActivityByWxMessage(mTradeEntity.getId(),mWxTradeCustomerEntity.getCustomerId());

        return mWxTradeCustomerEntity;
    }

    public void sendActivityByWxMessage(Long tradeId,Long customerId) throws Exception{
        //购买成功小程序服务服务通知推送
        WxTemplateMessageHandler mWxTemplateMessageHandler = WxTemplateMessageHandler.create(WxTempMsg.activity_buy);
        TradeEntity mTradeEntity = mTradeService.queryBaseTradeById(tradeId);
        List<TradeItemEntity> listItem = mTradeItemService.querTradeItemByTradeId(tradeId);
        String dishList = "";
        for(TradeItemEntity item : listItem){
            if(dishList.equals("")){
                dishList = item.getDishName()+" X "+ToolsUtil.subZeroAndDot(item.getQuantity().toString());
            }else{
                dishList = dishList +";"+ item.getDishName()+" X "+ToolsUtil.subZeroAndDot(item.getQuantity().toString());
            }
        }

        CommercialEntity mCommercialEntity = mCommercialService.queryCommercialById(mTradeEntity.getShopIdenty());

        WxTradeCustomerEntity mWxTradeCustomerEntity = mWxTradeCustomerService.queryByTradeId(tradeId);
        ActivityBuyMessage wxTempMsg = new ActivityBuyMessage();
        wxTempMsg.setTradeNo(mTradeEntity.getTradeNo());
        wxTempMsg.setCode(mWxTradeCustomerEntity.getCode());
        wxTempMsg.setTradePayAmount(mTradeEntity.getTradeAmount());
        wxTempMsg.setValidityPeriod(DateFormatUtil.format(mWxTradeCustomerEntity.getValidityPeriod(),DateFormatUtil.FORMAT_FULL_DATE));
        wxTempMsg.setBuyDate(DateFormatUtil.format(mTradeEntity.getServerUpdateTime(),DateFormatUtil.FORMAT_FULL_DATE));
        wxTempMsg.setDishName(dishList);
        wxTempMsg.setShopName(mCommercialEntity.getCommercialName());
        wxTempMsg.setRemarks("请在到期时间前到店使用，如有疑问烦请联系门店");
        wxTempMsg.setBrandIdenty(mTradeEntity.getBrandIdenty());
        wxTempMsg.setShopIdenty(mTradeEntity.getShopIdenty());
        wxTempMsg.setCustomerId(customerId);

        mWxTemplateMessageHandler.send(wxTempMsg);
    }


    public WxTradeCustomerEntity orderquery(Model model, TradeModel mTradeModel){

        try{
            //获取用户支付相关信息
            CommercialPaySettingEntity mCommercialPaySettingEntity = new CommercialPaySettingEntity();
            mCommercialPaySettingEntity.setShopIdenty(mTradeModel.getShopIdenty());
            mCommercialPaySettingEntity.setBrandIdenty(mTradeModel.getBrandIdenty());
            mCommercialPaySettingEntity.setType(1);
            mCommercialPaySettingEntity.setStatusFlag(1);
            CommercialPaySettingEntity paySetting =  mCommercialPaySettingService.queryData(mCommercialPaySettingEntity);

            QueryTradeModel mQueryTradeModel = new QueryTradeModel();
            mQueryTradeModel.setAppid(paySetting.getAppid());
            mQueryTradeModel.setMch_id(paySetting.getWxShopId());

            PaymentItemEntity mPaymentItemEntity = mPaymentItemService.queryPaymentItemByTradeId(mTradeModel.getTradeId());
            mQueryTradeModel.setOut_trade_no(mPaymentItemEntity.getId().toString());
            mQueryTradeModel.setNonce_str(mPaymentItemEntity.getUuid());

            String singMessage = "appid="+mQueryTradeModel.getAppid()+"&mch_id="+paySetting.getWxShopId()+"&nonce_str="+mPaymentItemEntity.getUuid()+"&out_trade_no="+mQueryTradeModel.getOut_trade_no();
            String stringSignTemp=singMessage+"&key="+paySetting.getApiSecret();
            String sign = ToolsUtil.getMd5(stringSignTemp).toUpperCase();

            mQueryTradeModel.setSign(sign);

            String params = HttpMessageConverterUtils.objectToXml(mQueryTradeModel);
            HttpEntity<String> formEntity = new HttpEntity<String>(params);
            String url = "https://api.mch.weixin.qq.com/pay/orderquery";
            String xmlResult = restTemplate.exchange(url, HttpMethod.POST, formEntity, String.class).getBody().toString();

            QueryTradePayStateModel mQueryTradePayStateModel = (QueryTradePayStateModel) HttpMessageConverterUtils.xmlToObject(xmlResult, QueryTradePayStateModel.class);

            if(mQueryTradePayStateModel.getResult_code().equalsIgnoreCase("SUCCESS")){
                if(mQueryTradePayStateModel.getTrade_state().equalsIgnoreCase("SUCCESS")){
                    String paymentItemId = mQueryTradePayStateModel.getOut_trade_no();
                    WxTradeCustomerEntity mWxTradeCustomerEntity = updateTradeState(Long.valueOf(paymentItemId));
                    return mWxTradeCustomerEntity;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取小程序翼支付信息
     * @return
     */
    public static WxPayMsgModel getYiPayparams(PaymentItemEntity mPaymentItemEntity, String openId) throws Exception{

        //获取用户支付相关信息

        YiPayRepuestModel mYiPayRepuestModel = new YiPayRepuestModel();

        mYiPayRepuestModel.setAppid("hf1129016484PQGA");

        mYiPayRepuestModel.setTotal_amount(1);
        mYiPayRepuestModel.setOut_trade_no(mPaymentItemEntity.getUuid());
        mYiPayRepuestModel.setReturn_url("https://mk.zhongmeiyunfu.com/marketing/wxapp/pay/payNotify");
        mYiPayRepuestModel.setSub_appid("wx22d9607fa73e9364");
        mYiPayRepuestModel.setSub_openid(openId);
        mYiPayRepuestModel.setSpbill_create_ip("47.105.100.99");
        String nonceStr = ToolsUtil.getCard(32);
        mYiPayRepuestModel.setNonce_str(nonceStr);
        mYiPayRepuestModel.setVersion("V1.0");

        String stringA =
                "appid=hf1129016484PQGA"+
                        "&nonce_str="+nonceStr+
                        "&out_trade_no="+mPaymentItemEntity.getUuid()+
                        "&return_url=https://mk.zhongmeiyunfu.com/marketing/wxapp/pay/payNotify"+
                        "&spbill_create_ip=47.105.100.99"+
                        "&sub_appid="+"wx22d9607fa73e9364"+
                        "&sub_openid="+openId+
                        "&total_amount="+1+
                        "&version=V1.0";

        log.info("=======stringA:"+stringA);

        String stringSignTemp=stringA+"&appsecret="+"lUxhOjcBWqaa7oxiFUMATAtiYfEL42WA";
        String sign = ToolsUtil.getMd5(stringSignTemp).toUpperCase();


        mYiPayRepuestModel.setSign(sign);


        String url = "https://pay.sc.189.cn/haipay/applet-pay";

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(type);
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(paramsMap,  headers);


        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));


        String params = JSONObject.toJSON(mYiPayRepuestModel).toString();

        log.info("==========params:"+params);
        HttpEntity<String> formEntity = new HttpEntity<String>(params,headers);

//        String reponse = restTemplate.exchange(url, HttpMethod.POST, formEntity, String.class).getBody().toString();
        JSONObject json = restTemplate.postForEntity(url, params, JSONObject.class).getBody();

//        JSONObject json = restTemplate.postForObject(url, paramsMap, JSONObject.class);

        log.info("=========reponse json:"+json);

        return null;
    }


    public final static void main(String[] args) throws Exception {

        PaymentItemEntity mPaymentItemEntity = new PaymentItemEntity();
        mPaymentItemEntity.setUsefulAmount(new BigDecimal(0.01));
        mPaymentItemEntity.setUuid(ToolsUtil.genOnlyIdentifier());

        String openId = "oTf-e4g_nqsOuXwBBZnt1eenqUWE";
        WxPayMsgModel mWxPayMsgModel =  getYiPayparams(mPaymentItemEntity,openId);
    }


}

