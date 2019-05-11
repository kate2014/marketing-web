package com.zhongmei.yunfu.controller.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.*;
import com.zhongmei.yunfu.controller.weixinPay.RefundRequestModel;
import com.zhongmei.yunfu.controller.weixinPay.UnifiedorderEntity;
import com.zhongmei.yunfu.controller.weixinPay.WeiXinNotifyModel;
import com.zhongmei.yunfu.controller.weixinPay.WeiXinPayRsqEntity;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.thirdlib.wxapp.WxTemplateMessageHandler;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.ColloageFailMessage;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.OrderPayMessage;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.WxTempMsg;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.HttpMessageConverterUtils;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/wxapp/scenarioMarketing")
public class ScenarioMarketingApiController {

    private static Logger log = LoggerFactory.getLogger(ScenarioMarketingApiController.class);

    @Autowired
    CutDownMarketingService mCutDownMarketingService;
    @Autowired
    CutDownCustomerService mCutDownCustomerService;
    @Autowired
    CollageMarketingService mCollageMarketingService;
    @Autowired
    CollageCustomerService mCollageCustomerService;
    @Autowired
    FlashSalesMarketingService mFlashSalesMarketingService;
    @Autowired
    WxTradeCustomerService mWxTradeCustomerService;
    @Autowired
    TradeService mTradeService;
    @Autowired
    TradeItemService mTradeItemService;
    @Autowired
    PaymentService mPaymentService;
    @Autowired
    PaymentItemService mPaymentItemService;
    @Autowired
    TradeCustomerService mTradeCustomerService;
    @Autowired
    CommercialPaySettingService mCommercialPaySettingService;
    @Autowired
    RestTemplate restTemplate;

    /**
     * 获取门店场景化营销，用于首页展示
     * @param model
     * @param mScenariomarketingModel
     * @return
     */
    @GetMapping("/queryScenarioMarketing")
    public BaseDataModel queryScenarioMarketing(ModelMap model, ScenariomarketingModel mScenariomarketingModel){
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            ShopScenariomarketingModel mShopScenariomarketingModel = new ShopScenariomarketingModel();

            //获取最新一条拼团活动
            CollageMarketingEntity cm = new CollageMarketingEntity();
            cm.setBrandIdentity(mScenariomarketingModel.getBrandIdenty());
            cm.setShopIdentity(mScenariomarketingModel.getShopIdenty());
            CollageMarketingEntity collage = mCollageMarketingService.queryNewCollage(cm);
            if(collage != null){
                mShopScenariomarketingModel.setCollage(collage);
            }

            //获取最新一条砍价活动
            CutDownMarketingEntity mCutDownMarketing = new CutDownMarketingEntity();
            mCutDownMarketing.setBrandIdentity(mScenariomarketingModel.getBrandIdenty());
            mCutDownMarketing.setShopIdentity(mScenariomarketingModel.getShopIdenty());
            CutDownMarketingEntity cutDown = mCutDownMarketingService.queryNewCutDown(mCutDownMarketing);
            if(cutDown !=null){
                mShopScenariomarketingModel.setCutDown(cutDown);
            }

            //获取最新一条秒杀活动
            FlashSalesMarketingEntity mFlashSalesMarketing = new FlashSalesMarketingEntity();
            mFlashSalesMarketing.setBrandIdentity(mScenariomarketingModel.getBrandIdenty());
            mFlashSalesMarketing.setShopIdentity(mScenariomarketingModel.getShopIdenty());
            FlashSalesMarketingEntity flashSales = mFlashSalesMarketingService.queryNewFlashSales(mFlashSalesMarketing);
            if(flashSales != null){
                mShopScenariomarketingModel.setFlashSales(flashSales);
            }

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取门店场景营销活动数据成功");
            mBaseDataModel.setData(mShopScenariomarketingModel);
        }catch (Exception e){
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取门店场景营销活动数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 获取会员参与的砍价、拼团活动
     *
     * @param model
     * @param mScenariomarketingModel
     * @return
     */
    @GetMapping("/queryJoinMarketing")
    public BaseDataModel queryJoinMarketing(ModelMap model, ScenariomarketingModel mScenariomarketingModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            //获取参与的砍价活动
            List<CutDownCustomerModel> listCutDown = queryCutDownByCustomer(mScenariomarketingModel);
            //获取砍价活动使用情况
            for(CutDownCustomerModel item : listCutDown){

                WxTradeCustomerEntity mWxTradeCustomerEntity = mWxTradeCustomerService.queryByRelateId(item.getId());
                if(mWxTradeCustomerEntity != null){
                    item.setStatus(mWxTradeCustomerEntity.getStatus());
                }else {
                    item.setStatus(1);
                }

                checkCutDownVailb(item);

            }
            mScenariomarketingModel.setListCutDown(listCutDown);
            //获取参与的拼团活动
            List<CollageCustomerModel> listCollage = querCollageByCustomer(mScenariomarketingModel);
            //获取拼团活动使用情况
            for(CollageCustomerModel item:listCollage){

                if(item.getRelationId() != null){
                    CollageCustomerEntity mCollageCustomerEntity = mCollageCustomerService.queryCollage(item.getRelationId());
                    item.setJoinCount(mCollageCustomerEntity.getJoinCount());
                }

                WxTradeCustomerEntity mWxTradeCustomerEntity = mWxTradeCustomerService.queryByRelateId(item.getId());
                item.setStatus(mWxTradeCustomerEntity.getStatus());
            }
            mScenariomarketingModel.setListCollage(listCollage);

            //获取购买的秒杀活动
            List<FlashSalesCustomerModel> listFlashSalesModel = queryFlashSalesByCustomer(mScenariomarketingModel);
            mScenariomarketingModel.setListFlashSales(listFlashSalesModel);

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取参与活动数据成功");
            mBaseDataModel.setData(mScenariomarketingModel);
        } catch (Exception e) {
            e.printStackTrace();

            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取参与活动数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 验证活动是否到期是否有效
     * @param cutDown
     */
    public void checkCutDownVailb(CutDownCustomerModel cutDown)throws Exception{
        //活动已到期结束或者活动已停止,则将参与的活动修改为失败
        if(cutDown.getState()==1 && (cutDown.getEndTime().getTime() <= new Date().getTime() || cutDown.getEnabledFlag() == 2)){
            CutDownCustomerEntity mCutDownCustomer = new CutDownCustomerEntity();
            mCutDownCustomer.setId(cutDown.getId());
            mCutDownCustomer.setState(3);
            mCutDownCustomerService.updateData(mCutDownCustomer);
        }

    }

    /**
     * 获取会员参与的砍价活动
     * @param mScenariomarketingModel
     * @return
     * @throws Exception
     */
    public List<CutDownCustomerModel> queryCutDownByCustomer(ScenariomarketingModel mScenariomarketingModel) throws Exception {
        CutDownCustomerEntity mCutDownCustomer = new CutDownCustomerEntity();
        mCutDownCustomer.setBrandIdentity(mScenariomarketingModel.getBrandIdenty());
        mCutDownCustomer.setShopIdentity(mScenariomarketingModel.getShopIdenty());
        mCutDownCustomer.setCustomerId(mScenariomarketingModel.getCustomerId());
        List<CutDownCustomerModel> listData = mCutDownCustomerService.queryDataList(mCutDownCustomer);

        return listData;
    }

    /**
     * 获取会员参与的拼团
     * @param mScenariomarketingModel
     * @return
     */
    public List<CollageCustomerModel> querCollageByCustomer(ScenariomarketingModel mScenariomarketingModel)throws Exception{

        CollageCustomerEntity mCollageCustomer = new CollageCustomerEntity ();
        mCollageCustomer.setBrandIdentity(mScenariomarketingModel.getBrandIdenty());
        mCollageCustomer.setShopIdentity(mScenariomarketingModel.getShopIdenty());
        mCollageCustomer.setCustomerId(mScenariomarketingModel.getCustomerId());

        List<CollageCustomerModel> listData = mCollageCustomerService.queryCollageByCustomer(mCollageCustomer);

        checkCollageVailb(listData);


        return listData;
    }

    /**
     * 获取会员参与的秒杀活动
     * @return
     * @throws Exception
     */
    public List<FlashSalesCustomerModel> queryFlashSalesByCustomer(ScenariomarketingModel mScenariomarketingModel)throws Exception{
        List<FlashSalesCustomerModel> listData = mFlashSalesMarketingService.queryFlashSalesByCustomer(mScenariomarketingModel.getBrandIdenty(),mScenariomarketingModel.getShopIdenty(),mScenariomarketingModel.getCustomerId());
        return listData;
    }

    /**
     * 验证是否有活动结束到还未成团的订单，如有则将订单退回
     * @param listData
     */
    public Boolean checkCollageVailb(List<CollageCustomerModel> listData)throws Exception{

        Boolean isRufundTrade = false;//是否有执行退货

        for(CollageCustomerModel ccm : listData){

            Date endTime = ccm.getEndTime();
            //判断活动是否已结束
            if(ccm.getState() == 1 && (endTime.getTime() <= new Date().getTime() || ccm.getEnabledFlag() == 2)){
                //表示是开团记录
                if(ccm.getRelationId() == null){
                    isRufundTrade = queryCollageCustomer(ccm,ccm.getId());
                }else{
                    isRufundTrade = queryCollageCustomer(ccm,ccm.getRelationId());
                }

            }
        }

        return isRufundTrade;
     }

    /**
     * 修改该团所以参团为失败
     * @param relationId
     * @return
     */
    public Boolean queryCollageCustomer(CollageCustomerModel ccm,Long relationId) throws Exception{

        Boolean isRufundTrade = false;//是否有执行退货
        //将拼团信息标记为失败
        CollageCustomerEntity mCollageCustomerEntity = new CollageCustomerEntity();
        mCollageCustomerEntity.setState(3);
        Boolean isSuccess = mCollageCustomerService.updateCollageByRelationId(relationId,mCollageCustomerEntity);

        if(isSuccess){
            List<CollageCustomerEntity> listData = mCollageCustomerService.queryCollageByRelationId(relationId, 3);
            for(CollageCustomerEntity cce : listData){
                if(cce.isPaid() == 2){
                    returnTrade(ccm,cce);
                    isRufundTrade = true;
                }

            }
        }

        return isRufundTrade;

    }

    /**
     * 将订单退回
     * @param cce
     * @return
     */
    public void returnTrade(CollageCustomerModel ccm,CollageCustomerEntity cce) throws Exception{
        Long tradeId = cce.getTradeId();

        //生成退货单
        TradeEntity mTrade = mTradeService.queryTradeById(tradeId);
        if(mTrade.getTradeStatus() == 4 && mTrade.getTradePayStatus() == 3 && mTrade.getStatusFlag() == 1){
            TradeEntity relatedTrade = mTradeService.queryTradeByRelateId(tradeId);
            //判断是否已生成退货单
            if(relatedTrade != null && relatedTrade.getTradeStatus() == 5){
                return;
            }
            mTrade.setId(null);
            mTrade.setUuid(ToolsUtil.genOnlyIdentifier());
            mTrade.setTradeType(2);
            mTrade.setTradeStatus(5);
            mTrade.setTradePayStatus(4);
            mTrade.setServerCreateTime(new Date());
            mTrade.setClientCreateTime(new Date());
            mTrade.setServerUpdateTime(new Date());
            mTrade.setClientUpdateTime(new Date());
            mTrade.setBizDate(new Date());
            mTrade.setRelateTradeId(tradeId);
            mTrade = mTradeService.addTrade(mTrade);

            List<TradeItemEntity> listItem = mTradeItemService.queryTradeItemAllByTradeId(tradeId);
            for(TradeItemEntity item : listItem){
                item.setId(null);
                item.setUuid(ToolsUtil.genOnlyIdentifier());
                item.setTradeId(mTrade.getId());
                item.setTradeUuid(mTrade.getUuid());
                item.setServerCreateTime(new Date());
                item.setClientCreateTime(new Date());
                item.setServerUpdateTime(new Date());
                item.setClientUpdateTime(new Date());
                mTradeItemService.addTradeItem(item);
            }

            PaymentEntity mPaymentEntity = mPaymentService.queryPaymentByTradeId(tradeId);
            mPaymentEntity.setId(null);
            mPaymentEntity.setUuid(ToolsUtil.genOnlyIdentifier());
            mPaymentEntity.setPaymentType(2);
            mPaymentEntity.setRelateId(mTrade.getId());
            mPaymentEntity.setRelateUuid(mTrade.getUuid());
            mPaymentEntity.setServerCreateTime(new Date());
            mPaymentEntity.setClientCreateTime(new Date());
            mPaymentEntity.setServerUpdateTime(new Date());
            mPaymentEntity.setClientUpdateTime(new Date());
            mPaymentService.installPayment(mPaymentEntity);

            PaymentItemEntity mPaymentItemEntity = mPaymentItemService.queryPaymentItemByTradeId(tradeId);
            Long outRefundNo = mPaymentItemEntity.getId();
            mPaymentItemEntity.setId(null);
            mPaymentItemEntity.setUuid(ToolsUtil.genOnlyIdentifier());
            mPaymentItemEntity.setPaymentId(mPaymentEntity.getId());
            mPaymentItemEntity.setPaymentUuid(mPaymentEntity.getUuid());
            mPaymentItemEntity.setPayStatus(4);
            mPaymentItemEntity.setServerCreateTime(new Date());
            mPaymentItemEntity.setClientCreateTime(new Date());
            mPaymentItemEntity.setServerUpdateTime(new Date());
            mPaymentItemEntity.setClientUpdateTime(new Date());
            mPaymentItemService.installPaymentItem(mPaymentItemEntity);

            TradeCustomerEntity mTradeCustomerEntity = mTradeCustomerService.queryTradeCustomer(tradeId);
            mTradeCustomerEntity.setId(null);
            mTradeCustomerEntity.setUuid(ToolsUtil.genOnlyIdentifier());
            mTradeCustomerEntity.setTradeId(mTrade.getId());
            mTradeCustomerEntity.setTradeUuid(mTrade.getUuid());
            mTradeCustomerEntity.setServerCreateTime(new Date());
            mTradeCustomerEntity.setClientCreateTime(new Date());
            mTradeCustomerEntity.setServerUpdateTime(new Date());
            mTradeCustomerEntity.setClientUpdateTime(new Date());
            mTradeCustomerService.installTradeCustomer(mTradeCustomerEntity);

            returnPayment(outRefundNo,mPaymentItemEntity,mTrade.getId());
            sendServiceMessage(mTrade,ccm);
        }

    }

    /**
     * 发起微信退款
     * @param outRefundNo
     * @param returnPaymentItemEntity
     * @throws Exception
     */
    public String returnPayment(Long outRefundNo,PaymentItemEntity returnPaymentItemEntity,Long tradeId)throws Exception{

        String message = mPaymentItemService.retrunPayment(outRefundNo,returnPaymentItemEntity,tradeId);
        log.info("=========returnPayment:"+message);
        return message;
    }


    @RequestMapping(value = "/refundNotify",produces = "application/xml")
    public String refundNotify(HttpServletRequest request) {

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
            Map<String,Object> returnMap = new HashMap<>();
            returnMap = HttpMessageConverterUtils.xmlToObject(result,Map.class);

            log.info("=======returnMap="+returnMap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resXml;
    }

    public void sendServiceMessage(TradeEntity mTrade,CollageCustomerModel ccm){
        ColloageFailMessage wxTempMsg = new ColloageFailMessage();
        wxTempMsg.setCustomerId(ccm.getCustomerId());
        wxTempMsg.setBrandIdenty(ccm.getBrandIdentity());
        wxTempMsg.setShopIdenty(ccm.getShopIdentity());

        wxTempMsg.setTradeNo(mTrade.getTradeNo());
        wxTempMsg.setCollageName(ccm.getName());
        wxTempMsg.setTradeAmount(mTrade.getTradeAmount().toString());
        wxTempMsg.setJoinCount(ccm.getJoinCount().toString());
        wxTempMsg.setCollageStart("拼团失败");
        wxTempMsg.setFinishCount(ccm.getCollagePeopleCount().toString());
        wxTempMsg.setEndTime(DateFormatUtil.format(ccm.getEndTime(),DateFormatUtil.FORMAT_FULL_DATE));
        wxTempMsg.setFailMessage("活动结束前未达到成团人数");
        wxTempMsg.setTradeStart("拼团失败，退款中");
        //拼团失败小程序服务服务通知推送
        WxTemplateMessageHandler.sendWxTemplateMessage(wxTempMsg);
    }

}
