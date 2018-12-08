package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CutDownModel;
import com.zhongmei.yunfu.controller.model.TradeItemModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

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

            List<TradeItemEntity> listItem = mTradeItemService.querTradeItemByTradeId(mTradeEntity.getId());

            List<TradePrivilegeEntity> listPrivilage = mTradePrivilegeService.queryPrivilegeByTradeId(mTradeModel.getBrandIdenty(),mTradeModel.getShopIdenty(),mTradeModel.getTradeId());

            List<TradeUserEntity> listTradeUser = mTradeUserService.queryDataByTradeId(mTradeModel.getTradeId());

            List<TradeItemModel> listTradeItem = new ArrayList<>();

            List<TradePrivilegeEntity> tradePrivilageForTrade = new ArrayList<>();

            List<TradeUserEntity> tradeUserForTrade = new ArrayList<>();

            for(TradeItemEntity item : listItem){
                TradeItemModel mTradeItemModel = new TradeItemModel();
                mTradeItemModel.setDishName(item.getDishName());
                mTradeItemModel.setPrice(item.getPrice());
                mTradeItemModel.setQuantity(item.getQuantity());
                mTradeItemModel.setAmount(item.getAmount());
                mTradeItemModel.setPrivilege("/");

                for(TradePrivilegeEntity privilage : listPrivilage){
                    if(privilage.getTradeItemId() != null && privilage.getTradeItemId().longValue() == item.getId().longValue()){
                        mTradeItemModel.setPrivilege(privilage.getPrivilegeName()+"："+privilage.getPrivilegeAmount());
                        break;
                    }
                }
                mTradeItemModel.setActualAmount(item.getActualAmount());
                mTradeItemModel.setTradeUser("/");
                for(TradeUserEntity user : listTradeUser){
                    if(user.getTradeItemId() != null && user.getTradeItemId().longValue() == item.getId().longValue()){
                        mTradeItemModel.setTradeUser(user.getUserName());
                        break;
                    }
                }
                listTradeItem.add(mTradeItemModel);
            }

            for(TradePrivilegeEntity privilage : listPrivilage){
                if(privilage.getTradeItemId() == null || privilage.getTradeItemId().equals("")){
                    tradePrivilageForTrade.add(privilage);
                }
            }
            for(TradeUserEntity user : listTradeUser){
                if(user.getTradeItemId() == null || user.getTradeItemId().equals("")){
                    tradeUserForTrade.add(user);
                }
            }

            List<PaymentItemEntity> listPayment = mPaymentItemService.queryPaymentItemsByTradeId(mTradeEntity.getId());

            model.addAttribute("listPayment", listPayment);

            model.addAttribute("listItem", listTradeItem);

            model.addAttribute("privilageForTrade", tradePrivilageForTrade);
            model.addAttribute("userForTrade", tradeUserForTrade);

            model.addAttribute("mTradeModel", mTradeModel);


        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "trade_detail";
    }
}

