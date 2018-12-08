package com.zhongmei.yunfu.controller.api;

import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.CustomerCommissionModel;
import com.zhongmei.yunfu.controller.model.ExpandedCommissionModel;
import com.zhongmei.yunfu.controller.model.ExpandedMarketingModel;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 全员推广
 */
@RestController
@RequestMapping("/wxapp/marketingExpanded")
public class MarketingExpandedApiController {

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
    TradeCustomerService mTradeCustomerService;

    @Autowired
    CustomerCouponService mCustomerCouponService;

    @GetMapping("/getDetail")
    public BaseDataModel marketingExpandedDetail(Model model, ExpandedMarketingModel expandedMarketingModel) {

        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            expandedMarketingModel.setStatusFlag(1);
            MarketingExpandedEntity mMarketingExpanded = mMarketingExpandedService.findMarketingExpanded(expandedMarketingModel);
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取全员推广数据成功");
            mBaseDataModel.setData(mMarketingExpanded);

        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取全员推广数据成功");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 发起推广
     *
     * @param model
     * @param mCustomerMarketingExpanded
     * @return
     */
    @GetMapping("/addExpanded")
    public BaseDataModel addCustomerExpanded(Model model, CustomerMarketingExpandedEntity mCustomerMarketingExpanded) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            Date m = new Date();

            String batchCode = Long.toString(m.getTime()) + mCustomerMarketingExpanded.getCustomerId();
            mCustomerMarketingExpanded.setExpandedCode(batchCode);
            mCustomerMarketingExpanded.setState(1);

            Boolean isSuccess = mCustomerMarketingExpandedService.addCustomerExpanded(mCustomerMarketingExpanded);
            if (isSuccess) {
                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("推广操作成功");
                mBaseDataModel.setData(mCustomerMarketingExpanded);
            } else {
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("推广操作失败");
                mBaseDataModel.setData(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("推广操作失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }


    /**
     * 处理推广操作
     *
     * @param model
     * @param mCustomerMarketingExpanded
     * @return
     */
    @GetMapping("/modifyExpanded")
    public BaseDataModel modifyExpanded(Model model, CustomerMarketingExpandedEntity mCustomerMarketingExpanded) {
        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            //验收受推广会员是否已经被推广过,如被推广过则不能再次被推广
            CustomerMarketingExpandedEntity isHad = mCustomerMarketingExpandedService.checkHadExpandedCustomer(mCustomerMarketingExpanded);
            if (isHad != null) {
                mBaseDataModel.setState("1002");
                mBaseDataModel.setMsg("已接受过其他好友的推荐");
                mBaseDataModel.setData(false);
            } else {
                Boolean isSuccess = mCustomerMarketingExpandedService.modifyExpanded(mCustomerMarketingExpanded);
                if (isSuccess) {
                    mBaseDataModel.setState("1000");
                    mBaseDataModel.setMsg("推广操作成功");
                    mBaseDataModel.setData(mCustomerMarketingExpanded);
                } else {
                    mBaseDataModel.setState("1001");
                    mBaseDataModel.setMsg("推广操作失败");
                    mBaseDataModel.setData(false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("推广操作失败");
            mBaseDataModel.setData(false);
        }
        return mBaseDataModel;
    }

    /**
     * 获取我的推广
     *
     * @param model
     * @param mCustomerMarketingExpanded
     * @return
     */
    @GetMapping("/queryExpanded")
    public BaseDataModel queryExpanded(Model model, CustomerMarketingExpandedEntity mCustomerMarketingExpanded) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            CustomerCommissionModel mCustomerCommissionModel = new CustomerCommissionModel();
            List<CustomerMarketingExpandedEntity> listData = mCustomerMarketingExpandedService.queryExpanded(mCustomerMarketingExpanded);
            mCustomerCommissionModel.setListExpanded(listData);

            ExpandedCommissionEntity mExpandedCommission = new ExpandedCommissionEntity();
            mExpandedCommission.setBrandIdenty(mCustomerMarketingExpanded.getBrandIdenty());
            mExpandedCommission.setShopIdenty(mCustomerMarketingExpanded.getShopIdenty());
            mExpandedCommission.setCustomerId(mCustomerMarketingExpanded.getCustomerId());
            mExpandedCommission = mExpandedCommissionService.queryNewCommission(mExpandedCommission);
            if(mExpandedCommission == null){
                mExpandedCommission = new ExpandedCommissionEntity();
                if(mExpandedCommission.getTotalAmount() == null){
                    mExpandedCommission.setTotalAmount(BigDecimal.ZERO);
                }
                if(mExpandedCommission.getChangeSalesAmount() == null){
                    mExpandedCommission.setChangeSalesAmount(BigDecimal.ZERO);
                }
                if(mExpandedCommission.getCommissionRatio() == null){
                    mExpandedCommission.setCommissionRatio(BigDecimal.ZERO);
                }
                if(mExpandedCommission.getCommissionAmount() == null){
                    mExpandedCommission.setCommissionAmount(BigDecimal.ZERO);
                }
                if(mExpandedCommission.getTotalCommission() == null){
                    mExpandedCommission.setTotalCommission(BigDecimal.ZERO);
                }
                if(mExpandedCommission.getCanExchange() == null){
                    mExpandedCommission.setCanExchange(BigDecimal.ZERO);
                }
                if(mExpandedCommission.getExchangeAmount() == null){
                    mExpandedCommission.setExchangeAmount(BigDecimal.ZERO);
                }
            }

            mCustomerCommissionModel.setExpandedCommission(mExpandedCommission);

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取我的推广数据成功");
            mBaseDataModel.setData(mCustomerCommissionModel);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取我的推广数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }


    /**
     * 添加推广消费提成
     * @param model
     * @param mExpandedCommissionModel
     * @return
     * @throws Exception
     */
    @GetMapping("/addCommission")
    public BaseDataModel addExpandedCommission(Model model, ExpandedCommissionModel mExpandedCommissionModel) throws Exception{

        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            Long shopIdenty = mExpandedCommissionModel.getShopIdenty();
            Long brandIdenty = mExpandedCommissionModel.getBrandIdenty();
            Long tradeId = mExpandedCommissionModel.getTradeId();

            //获取当前提测比例
            MarketingExpandedEntity mMarketingExpanded = new MarketingExpandedEntity();
            mMarketingExpanded.setShopIdenty(shopIdenty);
            mMarketingExpanded.setBrandIdenty(brandIdenty);
            mMarketingExpanded = mMarketingExpandedService.queryMarketingExpanded(mMarketingExpanded);

            //获取消费会信息
            TradeCustomerEntity mTradeCustomer = mTradeCustomerService.queryTradeCustomer(tradeId);

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
     * 扣除消费提成，当销货单被退货时需要退还该部分提成金额给商家
     * @param model
     * @param mExpandedCommissionModel
     * @return
     */
    @GetMapping("/subtractCommission")
    public BaseDataModel subtractCommission(Model model, ExpandedCommissionModel mExpandedCommissionModel){
        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            Long shopIdenty = mExpandedCommissionModel.getShopIdenty();
            Long brandIdenty = mExpandedCommissionModel.getBrandIdenty();
            Long tradeId = mExpandedCommissionModel.getTradeId();
            ExpandedCommissionEntity mExpandedCommission = new ExpandedCommissionEntity();
            mExpandedCommission.setBrandIdenty(brandIdenty);
            mExpandedCommission.setShopIdenty(shopIdenty);
            mExpandedCommission.setTradeId(tradeId);
            mExpandedCommission = mExpandedCommissionService.queryCommissionByTradeId(mExpandedCommission);
            if(mExpandedCommission.getId() != null){
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
     * 提成兑换
     * @param model
     * @param mExpandedCommissionModel
     * @return
     */
    @GetMapping("/exchangeCommission")
    public BaseDataModel exchangeCommission(Model model, ExpandedCommissionModel mExpandedCommissionModel){
        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            ExpandedCommissionEntity mExpandedCommissionEntity = new ExpandedCommissionEntity();

            mExpandedCommissionEntity.setBrandIdenty(mExpandedCommissionModel.getBrandIdenty());
            mExpandedCommissionEntity.setShopIdenty(mExpandedCommissionModel.getShopIdenty());
            mExpandedCommissionEntity.setCustomerId(mExpandedCommissionModel.getCustomerId());
            mExpandedCommissionEntity.setExchangeAmount(mExpandedCommissionModel.getExchangeAmount());

            Boolean isSuccess = mExpandedCommissionService.exchangeExpandedCommission(mExpandedCommissionEntity);
            if(isSuccess){
                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("提成兑换成功");
                mBaseDataModel.setData(isSuccess);
            }else{
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("提成兑换失败");
                mBaseDataModel.setData(false);
            }

        }catch (Exception e){
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("提成兑换失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }
}

