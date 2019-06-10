package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CutDownModel;
import com.zhongmei.yunfu.controller.model.TradeItemModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.apache.velocity.util.ArrayListWrapper;
import org.jooq.util.derby.sys.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

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
@Controller
@RequestMapping("/internal/trade")
public class TradeController extends BaseController{

    @Autowired
    TradeService mTradeService;
    @Autowired
    TradeItemService mTradeItemService;
    @Autowired
    PaymentItemService mPaymentItemService;
    @Autowired
    TradePrivilegeService mTradePrivilegeService;
    @Autowired
    TradeUserService mTradeUserService;
    @Autowired
    TradeCustomerService mTradeCustomerService;
    @Autowired
    CustomerStoredService mCustomerStoredService;

    @RequestMapping("/listData")
    public String tradeListPage(Model model, TradeModel mTradeModel) {

        try {
            //设置默认查询时间
            if (mTradeModel.getStartDate() == null) {
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, -15);
                Date start = c.getTime();
                String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
                start = DateFormatUtil.parseDate(temp, DateFormatUtil.FORMAT_FULL_DATE);

                mTradeModel.setStartDate(temp);
            }
            if (mTradeModel.getEndDate() == null) {
                Date end = new Date();
                mTradeModel.setEndDate(DateFormatUtil.format(end, DateFormatUtil.FORMAT_FULL_DATE));
            }

            Page<TradeEntity> listTrade = mTradeService.queryListTradePage(mTradeModel);
            setWebPage(model, "/internal/trade/listData", listTrade, mTradeModel);
            model.addAttribute("mTradeModel", mTradeModel);
            model.addAttribute("listData", listTrade.getRecords());

        }catch (Exception e){
            e.printStackTrace();
            return "fail";

        }

        return "tradelist";
    }

    @RequestMapping("/tradeDetail")
    public String tradeDetail(Model model, TradeModel mTradeModel){

        try{
            TradeEntity mTradeEntity = mTradeService.queryTradeById(mTradeModel.getTradeId());
            model.addAttribute("mTradeEntity", mTradeEntity);

            //判断是否是余额储值，如是这查询出此次的储赠金额
            if(mTradeEntity.getBusinessType() == 2){
                CustomerStoredEntity mCustomerStoredEntity = mCustomerStoredService.queryByTradeId(mTradeEntity.getId());
                if(mCustomerStoredEntity == null){
                    mCustomerStoredEntity = new CustomerStoredEntity();
                    mCustomerStoredEntity.setTradeAmount(mTradeEntity.getTradeAmount());
                    mCustomerStoredEntity.setGiveAmount(BigDecimal.ZERO);
                }

                model.addAttribute("sumSaveAmount", mCustomerStoredEntity.getTradeAmount().add(mCustomerStoredEntity.getGiveAmount()));
                model.addAttribute("mCustomerStoredEntity", mCustomerStoredEntity);
            }

            List<TradeItemEntity> listItem = mTradeItemService.querTradeItemByTradeId(mTradeEntity.getId());

            List<TradePrivilegeEntity> listPrivilege = mTradePrivilegeService.queryPrivilegeByTradeId(mTradeModel.getBrandIdenty(),mTradeModel.getShopIdenty(),mTradeModel.getTradeId());

            List<TradeUserEntity> listTradeUser = mTradeUserService.queryDataByTradeId(mTradeModel.getTradeId());

            List<TradeItemModel> listTradeItem = new ArrayList<>();

            List<TradePrivilegeEntity> tradePrivilegeForTrade = new ArrayList<>();

            TradeUserEntity tradeUserForTrade = new TradeUserEntity();

            Map<Long,TradePrivilegeEntity> privilegeMap = new HashMap<>();
            Map<Long,TradeUserEntity> tradeUserMap = new HashMap<>();

            for(TradePrivilegeEntity privilege : listPrivilege){
                if(privilege.getTradeItemId() == null || privilege.getTradeItemId().equals("")){
                    tradePrivilegeForTrade.add(privilege);
                }else{
                    privilegeMap.put(privilege.getTradeItemId(),privilege);
                }

            }
            for(TradeUserEntity user : listTradeUser){
                if(user.getTradeItemId() == null || user.getTradeItemId().equals("")){
                    tradeUserForTrade = user;
                }else{
                    tradeUserMap.put(user.getTradeItemId(),user);
                }
            }

            for(TradeItemEntity item : listItem){
                TradeItemModel mTradeItemModel = new TradeItemModel();
                mTradeItemModel.setDishName(item.getDishName());
                mTradeItemModel.setPrice(item.getPrice());
                mTradeItemModel.setQuantity(item.getQuantity());
                mTradeItemModel.setAmount(item.getAmount());
                mTradeItemModel.setPrivilege("/");
                mTradeItemModel.setCreatorName(item.getCreatorName());

                TradePrivilegeEntity mTradePrivilegeEntity = privilegeMap.get(item.getId());
                if(mTradePrivilegeEntity != null){
                    mTradeItemModel.setPrivilege(mTradePrivilegeEntity.getPrivilegeName()+"："+mTradePrivilegeEntity.getPrivilegeAmount());
                    mTradeItemModel.setActualAmount(item.getActualAmount().add(mTradePrivilegeEntity.getPrivilegeAmount()));
                }else{
                    mTradeItemModel.setActualAmount(item.getActualAmount());
                }


                mTradeItemModel.setTradeUser("/");

                TradeUserEntity mTradeUserEntity = tradeUserMap.get(item.getId());
                if(mTradeUserEntity != null){
                    mTradeItemModel.setTradeUser(mTradeUserEntity.getUserName());
                }

                listTradeItem.add(mTradeItemModel);
            }


            List<PaymentItemEntity> listPayment = mPaymentItemService.queryPaymentItemsByTradeId(mTradeEntity.getId());


            List<TradeCustomerEntity> listTradeCustomer = mTradeCustomerService.queryTradeCustomerList(mTradeModel.getTradeId());

            model.addAttribute("listTradeCustomer", listTradeCustomer);

            model.addAttribute("listPayment", listPayment);

            model.addAttribute("listItem", listTradeItem);

            model.addAttribute("privilegeForTrade", tradePrivilegeForTrade);
            model.addAttribute("userForTrade", tradeUserForTrade);

            model.addAttribute("mTradeModel", mTradeModel);


        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "trade_detail";
    }

    /**
     * 查询订单退款结果
     * @param model
     * @param mTradeModel
     * @return
     */
    @RequestMapping("/refundquery")
    public String refundquery(Model model, TradeModel mTradeModel){
        try{
            PaymentItemEntity mPaymentItemEntity = mPaymentItemService.queryPaymentItemById(mTradeModel.getPaymentItemId());
            mPaymentItemEntity = mPaymentItemService.refundquery(mPaymentItemEntity, mTradeModel.getTradeId());

            return redirect("/internal/trade/tradeDetail?tradeId="+mTradeModel.getTradeId()+"&brandIdenty="+mPaymentItemEntity.getBrandIdenty()+"&shopIdenty="+mPaymentItemEntity.getShopIdenty());
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/refundTrade")
    public String refundTrade(Model model, TradeModel mTradeModel){

        try{
            PaymentItemEntity oldPaymentItem = mPaymentItemService.queryPaymentItemByTradeId(mTradeModel.getRelationTradeId());

            PaymentItemEntity mPaymentItemEntity = mPaymentItemService.queryPaymentItemById(mTradeModel.getPaymentItemId());

            mPaymentItemService.retrunPayment(oldPaymentItem.getId(),mPaymentItemEntity,mTradeModel.getTradeId());

            return redirect("/internal/trade/tradeDetail?tradeId="+mTradeModel.getTradeId()+"&brandIdenty="+mPaymentItemEntity.getBrandIdenty()+"&shopIdenty="+mPaymentItemEntity.getShopIdenty());

        }catch (Exception e){
            e.printStackTrace();
            return "fail";

        }

    }
}

