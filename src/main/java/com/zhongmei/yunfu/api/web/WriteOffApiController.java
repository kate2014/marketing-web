package com.zhongmei.yunfu.api.web;


import com.zhongmei.yunfu.api.ApiRespStatus;
import com.zhongmei.yunfu.api.ApiRespStatusException;
import com.zhongmei.yunfu.api.PosApiController;
import com.zhongmei.yunfu.api.internal.vo.CustomerCardTimeExpenseReq;
import com.zhongmei.yunfu.api.internal.vo.CustomerCardTimeRefundReq;
import com.zhongmei.yunfu.api.pos.vo.CustomerIntegralTradeReq;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.WriteOffModel;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.service.impl.CouponServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 业务核销接口
 */
@RestController
@RequestMapping("/internal/writeOff")
public class WriteOffApiController extends PosApiController {

//    private static final Logger log = LoggerFactory.getLogger(WriteOffApiController.class);

    @Autowired
    TradeService mTradeService;
    @Autowired
    TradeCustomerService mTradeCustomerService;
    @Autowired
    CouponServiceImpl mCouponServiceImpl;
    @Autowired
    CustomerCouponService mCustomerCouponService;
    @Autowired
    WxTradeCustomerService mWxTradeCustomerService;
    @Autowired
    CollageMarketingService mCollageMarketingService;
    @Autowired
    CutDownMarketingService mCutDownMarketingService;
    @Autowired
    FlashSalesMarketingService mFlashSalesMarketingService;
    @Autowired
    TradePrivilegeService mTradePrivilegeService;
    @Autowired
    CustomerCardTimeService mCustomerCardTimeService;
    @Autowired
    CustomerIntegralService mCustomerIntegralService;

    /**
     * 核销接口
     *
     * @param mWriteOffModel
     * @return
     */
    @RequestMapping(value = "/useData",method =  RequestMethod.POST)
    public BaseDataModel useData(@RequestBody WriteOffModel mWriteOffModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            List<TradePrivilegeEntity> listTP = mTradePrivilegeService.queryPrivilegeByTradeId(mWriteOffModel.getBrandIdenty(), mWriteOffModel.getShopIdenty(), mWriteOffModel.getTradeId());

            String returnMsg = "";
            if(listTP != null && listTP.size() > 0){

                TradeCustomerEntity mTradeCustomerEntity = mTradeCustomerService.queryTradeCustomer(mWriteOffModel.getTradeId());
                //验证积分、优惠券、小程序购买服务、次卡是否都有效
                String checkMsg = checkValid(listTP,mTradeCustomerEntity);
                if(!checkMsg.equals("")){
                    mBaseDataModel.setState("1001");
                    mBaseDataModel.setMsg(checkMsg);
                    return mBaseDataModel;
                }

                //对积分、优惠券、小程序购买服务进行核销
                for (TradePrivilegeEntity tp : listTP) {
                    Integer type = tp.getPrivilegeType();

                    if (tp.getStatusFlag() == 1) {
                        //优惠券核销
                        if (type == 4) {
                            Integer state = useCoupon(tp);
                            if (state == 1001) {
                                String message = "优惠券:" + tp.getPrivilegeName() + "使用失败";
                                mBaseDataModel.setState("1001");
                                mBaseDataModel.setMsg(message);
                                return mBaseDataModel;
                            }
                            continue;
                        }
                        //积分抵现核销
                        if (type == 5) {
                            Integer state = expendScore(tp,mTradeCustomerEntity);
                            if(state == 1001){
                                String message = "积分抵现使用失败";
                                mBaseDataModel.setState("1001");
                                mBaseDataModel.setMsg(message);
                                return mBaseDataModel;
                            }else if(state == 1002){
                                String message = "剩余积分不够";
                                mBaseDataModel.setState("1001");
                                mBaseDataModel.setMsg(message);
                                return mBaseDataModel;
                            }
                            continue;
                        }
                        //次卡服务核销
                        if (type == 22) {
                            Integer state = expenseCardime(tp,mTradeCustomerEntity);
                            if(state == 1001){
                                String message = "服务次卡：" + tp.getPrivilegeName() + "剩余次数不够";
                                mBaseDataModel.setState("1001");
                                mBaseDataModel.setMsg(message);
                                return mBaseDataModel;
                            }else if(state == 1002){
                                String message = "服务次卡：" + tp.getPrivilegeName() + "核销使用失败";
                                mBaseDataModel.setState("1001");
                                mBaseDataModel.setMsg(message);
                                return mBaseDataModel;
                            }

                            continue;
                        }
                        //拼团核销
                        if (type == 23) {
                            Integer state = userWxTrade(tp);
                            if (state == 1001) {
                                String message = "拼团活动:" + tp.getPrivilegeName() + "使用失败";
                                mBaseDataModel.setState("1001");
                                mBaseDataModel.setMsg(message);
                                return mBaseDataModel;
                            }
                            continue;
                        }
                        //秒杀核销
                        if (type == 24) {
                            Integer state = userWxTrade(tp);
                            if (state == 1001) {
                                String message = "秒杀活动:" + tp.getPrivilegeName() + "使用失败";
                                mBaseDataModel.setState("1001");
                                mBaseDataModel.setMsg(message);
                                return mBaseDataModel;
                            }
                            continue;
                        }
                        //砍价核销
                        if (type == 25) {
                            Integer state = userWxTrade(tp);
                            if (state == 1001) {
                                String message = "砍价活动:" + tp.getPrivilegeName() + "使用失败";
                                mBaseDataModel.setState("1001");
                                mBaseDataModel.setMsg(message);
                                return mBaseDataModel;
                            }
                            continue;
                        }

                        //特价活动
                        if (type == 29) {
                            Integer state = userWxTrade(tp);
                            if (state == 1001) {
                                String message = "特价活动:" + tp.getPrivilegeName() + "核销失败";
                                mBaseDataModel.setState("1001");
                                mBaseDataModel.setMsg(message);
                                return mBaseDataModel;
                            }
                            continue;
                        }
                    }


                }
            }


            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg(returnMsg);
            return mBaseDataModel;
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("操作失败");
            return mBaseDataModel;
        }


    }


    /**
     * 反核销
     *
     * @return
     */
    @RequestMapping("/returnPrivilege")
    public BaseDataModel returnPrivilege(@RequestBody WriteOffModel mWriteOffModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            List<TradePrivilegeEntity> listTP = mTradePrivilegeService.queryPrivilegeByTradeId(mWriteOffModel.getBrandIdenty(), mWriteOffModel.getShopIdenty(), mWriteOffModel.getTradeId());

            if(listTP != null && listTP.size() > 0){
                TradeCustomerEntity mTradeCustomerEntity = mTradeCustomerService.queryTradeCustomer(mWriteOffModel.getTradeId());
                String message = "退回成功";
                Integer state = 1000;
                for (TradePrivilegeEntity tp : listTP) {
                    Integer type = tp.getPrivilegeType();
//                    Integer useStatus = tp.getUseStatus();

//                    if (tp.getStatusFlag() == 1 && useStatus == 2) {
                        //反核销优惠券
                    if (tp.getStatusFlag() == 1) {
                        if (type == 4) {
                            state = returnCoupon(tp);
                            if (state == 1001) {
                                message += "优惠券:" + tp.getPrivilegeName() + "退回失败;";

                            }
                            continue;
                        }
                        if(type == 5){
                            state= refundCustomerScore(tp,mTradeCustomerEntity);
                            if (state == 1001) {
                                message += "积分退回失败";
                            }
                            continue;
                        }
                        if(type == 22){
                            state = refundCardTime(tp,mTradeCustomerEntity);
                            if(state == 1001){
                                message += "次卡退回失败";
                            }
                            continue;
                        }
                        //反核销拼团活动
                        if (type == 23) {
                            state = returnWxTrade(tp);
                            if (state == 1001) {
                                message += "拼团:" + tp.getPrivilegeName() + "退回失败;";
                            }
                            continue;
                        }
                        //反核销秒杀
                        if (type == 24) {
                            state = returnWxTrade(tp);
                            if (state == 1001) {
                                message += "秒杀:" + tp.getPrivilegeName() + "退回失败;";
                            }
                            continue;
                        }
                        //反核销砍价
                        if (type == 25) {
                            state = returnWxTrade(tp);
                            if (state == 1001) {
                                message += "砍价:" + tp.getPrivilegeName() + "退回失败;";
                            }
                            continue;
                        }
                        continue;
                    }
                }

                mBaseDataModel.setState(Integer.toString(state));
                mBaseDataModel.setMsg(message);
                return mBaseDataModel;
            }else{
                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("没有需要反核销的信息");
                return mBaseDataModel;
            }

        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("优惠退回失败");
            return mBaseDataModel;

        }

    }

    public String checkValid(List<TradePrivilegeEntity> listTP,TradeCustomerEntity mTradeCustomerEntity)throws Exception {
        String checkMessage = "";
        //验证积分、优惠券、小程序购买服务、次卡是否都有效
        for (TradePrivilegeEntity tp : listTP) {
            //获取核销状态
//            int useStatus = tp.getUseStatus();

            int type = tp.getPrivilegeType();

            //对有效并未进行核销过的进行验证
//            if (tp.getStatusFlag() == 1 && useStatus == 1) {
              if (tp.getStatusFlag() == 1) {
                //优惠券 未被核销使用
                if(type == 4) {
                    //验证优惠券是否有效
                    Integer useState = checkCouponValid(tp);
                    if (useState == 1001) {

                        checkMessage = "优惠券" + tp.getPrivilegeName() + "已过期";
                    } else if (useState == 1002) {
                        checkMessage = "优惠券" + tp.getPrivilegeName() + "已被使用";
                    }

                    continue;
                }
                //积分抵现
                if(type == 5){
                    Integer status = checkScore(tp,mTradeCustomerEntity);
                    if(status == 1001){
                        checkMessage = "积分抵用验证失败";
                    }else if(status == 1002){
                        checkMessage = "会员当前积分不够本次订单扣减";
                    }
                    continue;
                }
                //次卡服务
                if(type == 22){
                    Integer state = checkCardTimeValid(tp);
                    if(state == 1001){
                        checkMessage += "次卡服务:" + tp.getPrivilegeName() + "验证失败";
                    }else if(state == 1002){
                        checkMessage += "次卡服务:" + tp.getPrivilegeName() + "不存在";
                    }else if(state == 1003){
                        checkMessage += "次卡服务:" + tp.getPrivilegeName() + "已失效";
                    }else if(state == 1004){
                        checkMessage += "次卡服务:" + tp.getPrivilegeName() + "剩余可使用次数不足";
                    }
                    continue;
                }
                //拼团
                if (type == 23) {
                    Integer useState = checkCollageValid(tp);
                    if (useState == 2001) {
                        checkMessage += "拼团活动:" + tp.getPrivilegeName() + "已过期";
                    } else if (useState == 2002) {
                        checkMessage += "拼团活动:" + tp.getPrivilegeName() + "已被使用";
                    }

                    continue;
                }
                //秒杀
                if (type == 24) {
                    Integer useState = checkFlashSalesValid(tp);
                    if (useState == 4001) {
                        checkMessage += "秒杀活动:" + tp.getPrivilegeName() + "已过期";
                    } else if (useState == 4002) {
                        checkMessage += "秒杀活动:" + tp.getPrivilegeName() + "已被使用";
                    }

                    continue;
                }
                //砍价
                if (type == 25) {
                    Integer useState = checkCutDownValid(tp);
                    if (useState == 3001) {
                        checkMessage += "砍价活动:" + tp.getPrivilegeName() + "已过期";
                    } else if (useState == 3002) {
                        checkMessage += "砍价活动:" + tp.getPrivilegeName() + "已被使用";
                    }

                    continue;
                }

                //特价活动
                if(type == 29){
                    Integer useState = checkActvivitySalseValid(tp);
                    if (useState == 5001) {
                        checkMessage += "砍价活动:" + tp.getPrivilegeName() + "已过期";
                    } else if (useState == 5001) {
                        checkMessage += "砍价活动:" + tp.getPrivilegeName() + "已被使用";
                    }

                    continue;
                }

            }
        }

        return checkMessage;
    }



    /**
     * 优惠券的验证核销
     *
     * @param mTradePrivilege
     * @return
     */
    public Integer checkCouponValid(TradePrivilegeEntity mTradePrivilege) throws Exception {

        CustomerCouponEntity mCustomerCoupon = mCustomerCouponService.queryCouponById(mTradePrivilege.getPromoId());

        //验证优惠券是否已使用
        if (mCustomerCoupon.getStatus() == 1) {
            //获取对应优惠券详情
            CouponEntity mCoupon = mCouponServiceImpl.queryByid(mCustomerCoupon.getCouponId());

            //验证优惠券是否在有效期类
            if (mCoupon.getEndTime().getTime() < new Date().getTime()) {
                //优惠券已过期
                return 1001;
            }
        } else {
            //优惠券已被使用
            return 1002;
        }
        return 1000;
    }

    /**
     * 验证积分是否够核销
     * @param mTradePrivilege
     * @return
     */
    public Integer checkScore(TradePrivilegeEntity mTradePrivilege,TradeCustomerEntity mTradeCustomerEntity){
        try {
            mCustomerIntegralService.checkIntegral(mTradeCustomerEntity.getCustomerId(),mTradePrivilege.getPrivilegeValue().longValue());

        }catch (ApiRespStatusException e){
            e.printStackTrace();
            return 1002;

        }catch (Exception e){
            e.printStackTrace();
            return 1001;
        }


        return 1000;
    }

    public Integer checkCardTimeValid(TradePrivilegeEntity mTradePrivilege){
        try{
            CustomerCardTimeEntity mCustomerCardTimeEntity = mCustomerCardTimeService.getCustomerCardTimeEntity(mTradePrivilege.getPromoId(),true);
            if(mCustomerCardTimeEntity.getResidueCount() < 1 && mCustomerCardTimeEntity.getResidueCount() != -1){
                return 1004;
            }
        }catch (ApiRespStatusException e){
            e.printStackTrace();
            if(e.getStatus() == ApiRespStatus.FOUND){
                return 1002;
            }else if(e.getStatus() == ApiRespStatus.CUSTOMER_CARD_TIME_INVALID){
                return 1003;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1001;
        }
        return 1000;

    }

    /**
     * 次卡服务反核销
     * @return
     * @throws Exception
     */
    public Integer refundCardTime(TradePrivilegeEntity tp,TradeCustomerEntity mTradeCustomerEntity) throws Exception{
        CustomerCardTimeRefundReq req = new CustomerCardTimeRefundReq();
        TradeEntity mTradeEntity = mTradeService.queryTradeById(tp.getTradeId());
        req.setCustomerId(mTradeCustomerEntity.getCustomerId());
        req.setTradeId(mTradeEntity.getId());
        req.setCreatorId(mTradeEntity.getCreatorId());
        req.setCreatorName(mTradeEntity.getCreatorName());
        req.setTradeUuid(mTradeEntity.getUuid());
        req.setShopId(mTradeEntity.getShopIdenty());
        req.setBrandId(mTradeEntity.getBrandIdenty());

        mCustomerCardTimeService.refund(req);
        return 1000;

    }

    /**
     * 退回积分
     * @param tp
     * @return
     */
    public Integer refundCustomerScore(TradePrivilegeEntity tp,TradeCustomerEntity mTradeCustomerEntity){
        try {

            CustomerIntegralTradeReq req = new CustomerIntegralTradeReq();
            req.setShopId(tp.getShopIdenty());
            req.setBrandId(tp.getBrandIdenty());
            req.setCreatorId(tp.getCreatorId());
            req.setCreatorName(tp.getCreatorName());
            req.setCustomerId(mTradeCustomerEntity.getCustomerId());
            req.setTradeId(tp.getTradeId());
            req.setTradeUuid(tp.getTradeUuid());

            mCustomerIntegralService.refundIncome(req);

            return 1000;
        }catch (Exception e){
            e.printStackTrace();
            return 1001;
        }

    }

    /**
     * 积分核销
     * @param mTradePrivilegeEntity
     * @param mTradeCustomerEntity
     * @return
     */
    public Integer expendScore(TradePrivilegeEntity mTradePrivilegeEntity,TradeCustomerEntity mTradeCustomerEntity){
        try{
            CustomerIntegralTradeReq req = new CustomerIntegralTradeReq();
            req.setShopId(mTradePrivilegeEntity.getShopIdenty());
            req.setBrandId(mTradePrivilegeEntity.getBrandIdenty());
            req.setCreatorId(mTradePrivilegeEntity.getCreatorId());
            req.setCreatorName(mTradePrivilegeEntity.getCreatorName());
            req.setCustomerId(mTradeCustomerEntity.getCustomerId());
            req.setTradeId(mTradePrivilegeEntity.getTradeId());
            req.setTradeUuid(mTradePrivilegeEntity.getTradeUuid());
            req.setTradeIntegral(mTradePrivilegeEntity.getPrivilegeValue().setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
            mCustomerIntegralService.expend(req);

            return 1000;
        }catch (ApiRespStatusException e){
            e.printStackTrace();
            return 1002;
        }catch (Exception e){
            e.printStackTrace();
            return 1001;
        }

    }
    /**
     * 次卡服务核销
     * @param
     * @return
     */
    public Integer expenseCardime(TradePrivilegeEntity tp,TradeCustomerEntity mTradeCustomerEntity){

        try {
            CustomerCardTimeExpenseReq req = new CustomerCardTimeExpenseReq();
            req.setCustomerId(mTradeCustomerEntity.getCustomerId());
            req.setTradeId(tp.getTradeId());
            req.setTradeUuid(tp.getTradeUuid());
            req.setShopId(tp.getShopIdenty());
            req.setBrandId(tp.getBrandIdenty());
            req.setCreatorId(tp.getCreatorId());
            req.setCreatorName(tp.getCreatorName());

            List<CustomerCardTimeExpenseReq.Dish> listData = new ArrayList<>();
            CustomerCardTimeExpenseReq.Dish dish = new CustomerCardTimeExpenseReq.Dish();
            dish.setCustomerCardTimeId(tp.getPromoId());
            dish.setTradeCount(1);
            dish.setCreatorId(tp.getCreatorId());
            dish.setCreatorName(tp.getCreatorName());
            listData.add(dish);
            req.setDishs(listData);
            mCustomerCardTimeService.expense(req);
            return 1000;

        }catch (ApiRespStatusException e){
            e.getStatus().getReason();
            return 1001;
        }catch (Exception e){
            e.printStackTrace();
            return 1002;
        }

    }


    /**
     * 优惠券核销
     *
     * @param mTradePrivilege
     * @return
     */
    public Integer useCoupon(TradePrivilegeEntity mTradePrivilege) throws Exception {
        CustomerCouponEntity mCustomerCoupon = new CustomerCouponEntity();

        mCustomerCoupon.setStatus(2);
        mCustomerCoupon.setId(mTradePrivilege.getPromoId());
        Boolean isSuccess = mCustomerCouponService.modfiyCouponState(mCustomerCoupon);

        if (isSuccess) {

            return 1000;
        } else {
            return 1001;
        }
    }

    /**
     * 优惠券反核销
     * @param mTradePrivilege
     * @return
     * @throws Exception
     */
    public Integer returnCoupon(TradePrivilegeEntity mTradePrivilege) throws Exception {
        CustomerCouponEntity mCustomerCoupon = new CustomerCouponEntity();
        mCustomerCoupon.setStatus(1);
        mCustomerCoupon.setId(mTradePrivilege.getPromoId());
        Boolean isSuccess = mCustomerCouponService.modfiyCouponState(mCustomerCoupon);

        if (isSuccess) {

            return 1000;
        } else {
            return 1001;
        }
    }

    /**
     * 验证拼团服务有效性
     *
     * @param mTradePrivilege
     * @return
     * @throws Exception
     */
    public Integer checkCollageValid(TradePrivilegeEntity mTradePrivilege) throws Exception {
        WxTradeCustomerEntity mWxTradeCustomer = mWxTradeCustomerService.queryDetailById(mTradePrivilege.getPromoId());
        if (mWxTradeCustomer.getStatus() == 1) {

//            CollageMarketingEntity mCollageMarketing = mCollageMarketingService.queryCollageById(mWxTradeCustomer.getMarketingId());
            //验证是否已过有效期
            if (mWxTradeCustomer.getValidityPeriod().getTime() < new Date().getTime()) {
                //购买的拼团活动已过期
                return 2001;
            }
        } else {
            //拼团服务已被使用
            return 2002;
        }

        return 1000;
    }

    /**
     * 验证砍价活动的有效性
     *
     * @param mTradePrivilege
     * @return
     * @throws Exception
     */
    public Integer checkCutDownValid(TradePrivilegeEntity mTradePrivilege) throws Exception {
        WxTradeCustomerEntity mWxTradeCustomer = mWxTradeCustomerService.queryDetailById(mTradePrivilege.getPromoId());
        if (mWxTradeCustomer.getStatus() == 1) {

//            CutDownMarketingEntity mCutDownMarketing = mCutDownMarketingService.findCutDownDatailById(mWxTradeCustomer.getMarketingId());
            if (mWxTradeCustomer.getValidityPeriod().getTime() < new Date().getTime()) {
                //购买的砍价活动已过期
                return 3001;
            }

        } else {
            //砍价服务已被使用
            return 3002;
        }


        return 1000;
    }

    /**
     * 秒杀
     *
     * @param mTradePrivilege
     * @return
     */
    public Integer checkFlashSalesValid(TradePrivilegeEntity mTradePrivilege) throws Exception {
        WxTradeCustomerEntity mWxTradeCustomer = mWxTradeCustomerService.queryDetailById(mTradePrivilege.getPromoId());
        if (mWxTradeCustomer.getStatus() == 1) {
//            FlashSalesMarketingEntity mFlashSalesMarketing = mFlashSalesMarketingService.queryFlashSalesById(mWxTradeCustomer.getMarketingId());
            if (mWxTradeCustomer.getValidityPeriod().getTime() < new Date().getTime()) {
                //购买的秒杀活动已过期
                return 4001;
            }
        } else {
            //秒杀服务已被使用
            return 4002;
        }
        return 1000;
    }

    /**
     * 特价活动
     *
     * @param mTradePrivilege
     * @return
     */
    public Integer checkActvivitySalseValid(TradePrivilegeEntity mTradePrivilege) throws Exception {
        WxTradeCustomerEntity mWxTradeCustomer = mWxTradeCustomerService.queryDetailById(mTradePrivilege.getPromoId());
        if (mWxTradeCustomer.getStatus() == 1 && mWxTradeCustomer.getEnabledFlag() == 1) {
            if (mWxTradeCustomer.getValidityPeriod().getTime() < new Date().getTime()) {
                //购买的特价活动已过期
                return 5001;
            }
        } else {
            //特价服务已被使用
            return 5002;
        }
        return 1000;
    }

    /**
     * 核销微信小程序购物的服务
     *
     * @param mTradePrivilege
     * @return
     */
    public Integer userWxTrade(TradePrivilegeEntity mTradePrivilege) throws Exception {
        try {
            WxTradeCustomerEntity mWxTradeCustomer = new WxTradeCustomerEntity();
            mWxTradeCustomer.setStatus(2);
            mWxTradeCustomer.setId(mTradePrivilege.getPromoId());
            Boolean isSuccess = mWxTradeCustomerService.modfiyWxTradeCustomer(mWxTradeCustomer);

            if (isSuccess) {
                return 1000;
            } else {
                return 1001;
            }
        }catch (Exception e){
            e.printStackTrace();
            return 1001;
        }

    }

    /**
     * 反核销微信小程序购物的服务
     *
     * @param mTradePrivilege
     * @return
     */
    public Integer returnWxTrade(TradePrivilegeEntity mTradePrivilege){
        try {
            WxTradeCustomerEntity mWxTradeCustomer = new WxTradeCustomerEntity();
            mWxTradeCustomer.setStatus(1);
            mWxTradeCustomer.setId(mTradePrivilege.getPromoId());
            Boolean isSuccess = mWxTradeCustomerService.modfiyWxTradeCustomer(mWxTradeCustomer);

            if (isSuccess) {
                return 1000;
            } else {
                return 1001;
            }
        }catch (Exception e){
            e.printStackTrace();
            return 1001;

        }
    }


}
