package com.zhongmei.yunfu.api.internal;

import com.zhongmei.yunfu.api.pos.vo.CustomerIntegralTradeReq;
import com.zhongmei.yunfu.controller.model.*;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.thirdlib.wxapp.WxTemplateMessageHandler;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.OrderPayMessage;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.WxTempMsg;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单支付完成、退货完成时操作
 */
@RestController
@RequestMapping("/internal/salesAction")
public class SalesActionApiController {

    private static Logger log = LoggerFactory.getLogger(SalesActionApiController.class);

    @Autowired
    MarketingExpandedService mMarketingExpandedService;

    @Autowired
    CustomerMarketingExpandedService mCustomerMarketingExpandedService;

    @Autowired
    ExpandedCommissionService mExpandedCommissionService;

    @Autowired
    CustomerService mCustomerService;

    @Autowired
    TradeService mTradeService;

    @Autowired
    TradeItemService mTradeItemService;

    @Autowired
    TradeCustomerService mTradeCustomerService;

    @Autowired
    CustomerCouponService mCustomerCouponService;

    @Autowired
    CustomerIntegralService mCustomerIntegralService;

    @Autowired
    CustomerScoreRuleService mCustomerScoreRuleService;

    @RequestMapping("/payOverAction")
    public BaseDataModel payOverAction(@RequestBody SalesActionModel mSalesActionModel){

        BaseDataModel mBaseDataModel = new BaseDataModel();
        try{

            //获取消费会信息
            TradeCustomerEntity mTradeCustomer = mTradeCustomerService.queryTradeCustomer(mSalesActionModel.getTradeId());

            log.info("========payOverAction:"+mTradeCustomer.getCustomerId());

            sendWxMessage(mSalesActionModel.getTradeId(),mTradeCustomer.getCustomerId());

            if(mTradeCustomer != null){
                //添加消费提成
                addExpandedCommission(mSalesActionModel,mTradeCustomer);
                //消费添加会员积分
                addCustomerScore(mSalesActionModel,mTradeCustomer);
            }
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("操作成功");
            mBaseDataModel.setData(true);
            return mBaseDataModel;

        }catch (Exception e){
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("操作失败");
            mBaseDataModel.setData(false);
            return mBaseDataModel;
        }

    }

    @RequestMapping("/returnOrverAction")
    public BaseDataModel returnOrverAction(@RequestBody SalesActionModel mSalesActionModel){
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            //获取消费会信息
            TradeCustomerEntity mTradeCustomer = mTradeCustomerService.queryTradeCustomer(mSalesActionModel.getTradeId());
            //退回消费提成
            mBaseDataModel = subtractCommission(mSalesActionModel);
        }catch (Exception e){
            e.printStackTrace();
        }


        return mBaseDataModel;
    }


    /**
     * 添加消费提成
     * @param mSalesActionModel
     */
    public BaseDataModel addExpandedCommission(SalesActionModel mSalesActionModel,TradeCustomerEntity mTradeCustomer){
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            Long shopIdenty = mSalesActionModel.getShopIdenty();
            Long brandIdenty = mSalesActionModel.getBrandIdenty();
            Long tradeId = mSalesActionModel.getTradeId();

            //获取当前提测比例
            MarketingExpandedEntity mMarketingExpanded = new MarketingExpandedEntity();
            mMarketingExpanded.setShopIdenty(shopIdenty);
            mMarketingExpanded.setBrandIdenty(brandIdenty);
            mMarketingExpanded = mMarketingExpandedService.queryMarketingExpanded(mMarketingExpanded);



            //判断商家是否有配置推广活动，推广活动是否启动，推广活动是否在活动期间，如都满足则进行提成计算
            if(mMarketingExpanded != null && mMarketingExpanded.getStartDate().getTime() < new Date().getTime() && new Date().getTime() < mMarketingExpanded.getEndDate().getTime()){

                //获取提成订单
                TradeEntity mTradeEntity =  mTradeService.queryTradeById(tradeId);


                //查询交易用于的推广会员Id
                CustomerEntity mCustomerEntity = mCustomerService.queryCustomerByRelateId(mTradeEntity.getBrandIdenty(),mTradeEntity.getShopIdenty(),mTradeCustomer.getCustomerId());

                //判断该会员是否有注册微信账号，如果没有微信账号这该会员是pos端创建会员，不会进行推广提成计算
                if(mCustomerEntity != null){
                    CustomerMarketingExpandedEntity mCustomerMarketingExpanded = new CustomerMarketingExpandedEntity();
                    mCustomerMarketingExpanded.setExpandedCustomerOpenid(mCustomerEntity.getThirdId());
                    mCustomerMarketingExpanded.setBrandIdenty(brandIdenty);
                    mCustomerMarketingExpanded.setShopIdenty(shopIdenty);
                    CustomerMarketingExpandedEntity isHad = mCustomerMarketingExpandedService.checkHadExpandedCustomer(mCustomerMarketingExpanded);
                    //判断该会员是被推广会员
                    if(isHad != null){
                        ExpandedCommissionEntity mExpandedCommission = new ExpandedCommissionEntity();
                        mExpandedCommission.setBrandIdenty(brandIdenty);
                        mExpandedCommission.setShopIdenty(shopIdenty);
                        mExpandedCommission.setCustomerId(isHad.getCustomerId());
                        mExpandedCommission.setChangeSalesAmount(mTradeEntity.getTradeAmount());//单次消费金额
                        mExpandedCommission.setCommissionRatio(mMarketingExpanded.getFirstLevelDiscount());//当前提成比例
                        mExpandedCommission.setTradeId(tradeId);
                        mExpandedCommission.setStatusFlag(1);
                        Boolean isSuccess = mExpandedCommissionService.addExpandedCommission(mExpandedCommission);
                        if (isSuccess) {
                            mBaseDataModel.setState("1000");
                            mBaseDataModel.setMsg("操作成功");
                            mBaseDataModel.setData(true);
                        } else {
                            mBaseDataModel.setState("1001");
                            mBaseDataModel.setMsg("操作失败");
                            mBaseDataModel.setData(false);
                        }
                    }else{
                        mBaseDataModel.setState("1001");
                        mBaseDataModel.setMsg("该会员不属于被推广加人会员，不需进行消费提成");
                        mBaseDataModel.setData(false);
                    }


                }else{
                    mBaseDataModel.setState("1001");
                    mBaseDataModel.setMsg("该会员不属于被推广加人会员，不需进行消费提成");
                    mBaseDataModel.setData(false);
                }

            }else{
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("商户未配置推广活动或活动已失效");
                mBaseDataModel.setData(false);
            }

            //支付成功推送优惠券
            mCustomerCouponService.putOnCoupon(mTradeCustomer.getBrandIdenty(), mTradeCustomer.getShopIdenty(),mTradeCustomer.getCustomerId(),"", 5,4);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("操作失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 撤销消费提成
     * @param mSalesActionModel
     */
    public BaseDataModel subtractCommission(SalesActionModel mSalesActionModel){
        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            Long shopIdenty = mSalesActionModel.getShopIdenty();
            Long brandIdenty = mSalesActionModel.getBrandIdenty();
            Long tradeId = mSalesActionModel.getTradeId();
            ExpandedCommissionEntity mExpandedCommission = new ExpandedCommissionEntity();
            mExpandedCommission.setBrandIdenty(brandIdenty);
            mExpandedCommission.setShopIdenty(shopIdenty);
            mExpandedCommission.setTradeId(tradeId);
            mExpandedCommission = mExpandedCommissionService.queryCommissionByTradeId(mExpandedCommission);
            if(mExpandedCommission != null && mExpandedCommission.getId() != null){
                mExpandedCommission.setType(3);
                mExpandedCommission.setStatusFlag(1);
                Boolean isSuccess = mExpandedCommissionService.subtractCommission(mExpandedCommission);
                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("提成撤回操作成功");
                mBaseDataModel.setData(isSuccess);
            }else{
                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("无提成需要撤回");
                mBaseDataModel.setData(true);
            }

        }catch (Exception e){
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("提成撤回操作失败");
            mBaseDataModel.setData(false);
        }
        return mBaseDataModel;
    }

    /**
     * 消费添加积分
     * @param mSalesActionModel
     * @return
     */
    public BaseDataModel addCustomerScore(SalesActionModel mSalesActionModel,TradeCustomerEntity mTradeCustomer){
        BaseDataModel mBaseDataModel = new BaseDataModel();

        try{
            //获取提成订单
            TradeEntity mTradeEntity =  mTradeService.queryTradeById(mSalesActionModel.getTradeId());

            CustomerIntegralTradeReq req = new CustomerIntegralTradeReq();

            req.setShopId(mSalesActionModel.getShopIdenty());
            req.setBrandId(mSalesActionModel.getBrandIdenty());
            req.setCreatorId(mTradeCustomer.getCreatorId());
            req.setCreatorName(mTradeCustomer.getCreatorName());
            req.setCustomerId(mTradeCustomer.getCustomerId());
            req.setTradeId(mSalesActionModel.getTradeId());
            req.setTradeUuid(mTradeEntity.getUuid());

            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("brand_identy", mSalesActionModel.getBrandIdenty());
            tempMap.put("shop_identy", mSalesActionModel.getShopIdenty());

            //获取积分成长规则
            List<CustomerScoreRuleEntity> scoreRule = mCustomerScoreRuleService.findScoreRule(tempMap);
            for(CustomerScoreRuleEntity cr : scoreRule){
                if(cr.getType() == 1){

                    //1.积分成长值增长值：1元消费增加多少成长值; 2.积分抵用规则：多少积分可抵用1元
                    Integer convertValue = cr.getConvertValue();
                    BigDecimal value = mTradeEntity.getTradeAmount().multiply(new BigDecimal(convertValue)).setScale(0, BigDecimal.ROUND_HALF_UP);;
                    req.setTradeIntegral(value.intValue());
                }
            }

            mCustomerIntegralService.income(req);
        }catch (Exception e){
            e.printStackTrace();
        }

        return mBaseDataModel;
    }

    public void sendWxMessage(Long tradeId,Long customerId) throws Exception{
        //支付成功小程序服务服务通知推送
        WxTemplateMessageHandler mWxTemplateMessageHandler = WxTemplateMessageHandler.create(WxTempMsg.msgType_OrderPay);

        TradeEntity mTradeEntity = mTradeService.queryBaseTradeById(tradeId);

        List<TradeItemEntity> listItem = mTradeItemService.querTradeItemByTradeId(tradeId);
        String dishList = "";
        for(TradeItemEntity item : listItem){
            if(dishList.equals("")){
                dishList = item.getDishName()+" X "+ ToolsUtil.subZeroAndDot(item.getQuantity().toString());
            }else{
                dishList = dishList +";"+ item.getDishName()+" X "+ToolsUtil.subZeroAndDot(item.getQuantity().toString());
            }
        }

        OrderPayMessage wxTempMsg = new OrderPayMessage();
        wxTempMsg.setTradeNo(mTradeEntity.getTradeNo());
        wxTempMsg.setTradePayAmount(mTradeEntity.getTradeAmount());
        wxTempMsg.setPayDate(DateFormatUtil.format(mTradeEntity.getServerUpdateTime(),DateFormatUtil.FORMAT_FULL_DATE));
        wxTempMsg.setDishList(dishList);
        wxTempMsg.setBrandIdenty(mTradeEntity.getBrandIdenty());
        wxTempMsg.setShopIdenty(mTradeEntity.getShopIdenty());
        wxTempMsg.setCustomerId(customerId);

        mWxTemplateMessageHandler.send(wxTempMsg);
    }

}

