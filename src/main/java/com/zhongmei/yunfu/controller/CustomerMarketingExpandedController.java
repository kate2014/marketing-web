package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.domain.entity.ExchangeCodeEntity;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import com.zhongmei.yunfu.controller.model.CommissionSearchModel;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerMarketingExpandedEntity;
import com.zhongmei.yunfu.domain.entity.ExpandedCommissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员关联推广回馈 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
@Controller
@RequestMapping("/pos/customerMarketingExpanded")
public class CustomerMarketingExpandedController extends BaseController{

    @Autowired
    ExpandedCommissionService mExpandedCommissionService;
    @Autowired
    CustomerService mCustomerService;
    @Autowired
    ExchangeCodeService mExchangeCodeService;

    /**
     * 进入提成主界面
     * @param model
     * @param mCommissionSearchModel
     * @return
     */
    @RequestMapping("/gotoMainPage")
    public String gotoExpandedMainPage(Model model, CommissionSearchModel mCommissionSearchModel){
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        mCommissionSearchModel.setShopIdenty(shopIdentity);
        mCommissionSearchModel.setBrandIdenty(brandIdentity);
        model.addAttribute("mCommissionSearchModel", mCommissionSearchModel);
        return "expanded_commission_main";
    }

    /**
     * 进入提成主界面，用于pos端嵌入
     * @param model
     * @param mCommissionSearchModel
     * @return
     */
    @RequestMapping("/gotoCommissonPage")
    public String gotoCommissonPage(Model model, CommissionSearchModel mCommissionSearchModel){
        Long brandIdentity = mCommissionSearchModel.getBrandIdenty();
        Long shopIdentity = mCommissionSearchModel.getShopIdenty();

        mCommissionSearchModel.setShopIdenty(shopIdentity);
        mCommissionSearchModel.setBrandIdenty(brandIdentity);
        model.addAttribute("mCommissionSearchModel", mCommissionSearchModel);
        return "pos_expanded_commission_main";
    }

    /**
     * 进入提成兑换界面
     * @param model
     * @param mCommissionSearchModel
     * @return
     */
    @RequestMapping("/gotoExchange")
    public String gotoExchangePage(Model model, CommissionSearchModel mCommissionSearchModel){
        model.addAttribute("mCommissionSearchModel", mCommissionSearchModel);

        model.addAttribute("totalAmount", "0.00￥");
        model.addAttribute("totalCommission", "0.00￥");
        model.addAttribute("exchangeTotolAmount", "0.00￥");
        model.addAttribute("canExchange", "0.00￥");
        model.addAttribute("canExchangeValue", 0);
        model.addAttribute("customer", new CustomerEntity());
        return "expanded_exchange_commission";
    }

    /**
     * 推广提成列表界面
     * @param model
     * @param mCommissionSearchModel
     * @return
     */
    @RequestMapping("/commission")
    public String commissionPage(Model model, CommissionSearchModel mCommissionSearchModel){

        try{
            model.addAttribute("errorMsg", "");
            if(mCommissionSearchModel.getStartDate() == null){
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, -15);
                mCommissionSearchModel.setStartDate(DateFormatUtil.format(c.getTime(), DateFormatUtil.FORMAT_FULL_DATE));

            }
            if(mCommissionSearchModel.getEndDate() == null){
                mCommissionSearchModel.setEndDate(DateFormatUtil.format(new Date(),DateFormatUtil.FORMAT_FULL_DATE));
            }

            if(mCommissionSearchModel.getMobile() != null && !mCommissionSearchModel.getMobile().equals("")){
                CustomerEntity mCustomer = mCustomerService.queryCustomerByMobile(mCommissionSearchModel.getBrandIdenty(), mCommissionSearchModel.getShopIdenty(), mCommissionSearchModel.getMobile());
                if(mCustomer != null){
                    CustomerEntity wxCustomerEntity = mCustomerService.queryCustomerByRelateId(mCommissionSearchModel.getBrandIdenty(), mCommissionSearchModel.getShopIdenty(),mCustomer.getId());
                    mCommissionSearchModel.setCustomerId(wxCustomerEntity.getId());
                }else{
                    model.addAttribute("errorMsg", "该手机号码不存在");
                }

            }

            Page<CommissionSearchModel> listData =  mExpandedCommissionService.queryListCommission(mCommissionSearchModel);
            setWebPage(model, "/customerMarketingExpanded/commission", listData, mCommissionSearchModel);

            model.addAttribute("list", listData.getRecords());
            model.addAttribute("mCommissionSearchModel", mCommissionSearchModel);

            return "expanded_commission";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * 获取提成信息
     * @param model
     * @param mCommissionSearchModel
     * @return
     */
    @RequestMapping("/queryCommission")
    public String queryExpandedCommission(Model model, CommissionSearchModel mCommissionSearchModel){
        try {
            String alertMag = "";

            model.addAttribute("totalAmount", "0.00￥");
            model.addAttribute("totalCommission", "0.00￥");
            model.addAttribute("exchangeTotolAmount", "0.00￥");
            model.addAttribute("canExchange", "0.00￥");
            model.addAttribute("canExchangeValue", 0);

            if(mCommissionSearchModel.getMobile() ==  null || mCommissionSearchModel.getMobile().equals("")){
                alertMag = "请输入会员手机号码";
                model.addAttribute("customer", new CustomerEntity());
            }else{
                CustomerEntity mCustomer = mCustomerService.queryCustomerByMobile(mCommissionSearchModel.getBrandIdenty(), mCommissionSearchModel.getShopIdenty(), mCommissionSearchModel.getMobile());

                if(mCustomer != null && mCustomer.getId() != null){

                    CustomerEntity wxCustomerEntity = mCustomerService.queryCustomerByRelateId(mCommissionSearchModel.getBrandIdenty(), mCommissionSearchModel.getShopIdenty(),mCustomer.getId());

                    ExpandedCommissionEntity mExpandedCommission = new ExpandedCommissionEntity();
                    mExpandedCommission.setBrandIdenty(mCommissionSearchModel.getBrandIdenty());
                    mExpandedCommission.setShopIdenty(mCommissionSearchModel.getShopIdenty());
                    mExpandedCommission.setCustomerId(wxCustomerEntity.getId());
                    ExpandedCommissionEntity mExpandedCommissionEntity = mExpandedCommissionService.queryNewCommission(mExpandedCommission);

                    if(mExpandedCommissionEntity != null){
                        BigDecimal exchangeTotolAmount = mExpandedCommissionEntity.getTotalCommission().subtract(mExpandedCommissionEntity.getCanExchange());
                        model.addAttribute("totalAmount", mExpandedCommissionEntity.getTotalAmount()+"￥");
                        model.addAttribute("totalCommission", mExpandedCommissionEntity.getTotalCommission()+"￥");
                        model.addAttribute("exchangeTotolAmount", exchangeTotolAmount+"￥");
                        model.addAttribute("canExchange", mExpandedCommissionEntity.getCanExchange()+"￥");
                        model.addAttribute("canExchangeValue", mExpandedCommissionEntity.getCanExchange());
                    }else{
                        model.addAttribute("totalAmount", "0.00￥");
                        model.addAttribute("totalCommission", "0.00￥");
                        model.addAttribute("exchangeTotolAmount", "0.00￥");
                        model.addAttribute("canExchange", "0.00￥");
                        model.addAttribute("canExchangeValue", 0);
                        alertMag = "该会员没有提成金额可进行兑换";
                    }

                    model.addAttribute("customer", wxCustomerEntity);
                }else{

                    alertMag = "手机号码输入错误，不存在该手机号对应的会员信息，请确认后再试！";
                    model.addAttribute("customer", new CustomerEntity());
                }
            }


            model.addAttribute("alertMsg", alertMag);
            model.addAttribute("mCommissionSearchModel", mCommissionSearchModel);

            return "expanded_exchange_commission";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * 兑换提成
     * @param model
     * @param mCommissionSearchModel
     * @return
     */
    @RequestMapping("/exchangeCommission")
    public String exchangeExpandedCommission(Model model, CommissionSearchModel mCommissionSearchModel){
        try {
//            CustomerEntity mCustomer = mCustomerService.queryCustomerByMobile(mCommissionSearchModel.getBrandIdenty(), mCommissionSearchModel.getShopIdenty(), mCommissionSearchModel.getMobile());

            CustomerEntity mCustomer = mCustomerService.queryWxCustomerByMobile(mCommissionSearchModel.getBrandIdenty(), mCommissionSearchModel.getShopIdenty(), mCommissionSearchModel.getMobile());

            ExchangeCodeEntity mExchangeCodeEntity = new ExchangeCodeEntity();
            mExchangeCodeEntity.setBrandIdenty(mCommissionSearchModel.getBrandIdenty());
            mExchangeCodeEntity.setShopIdenty(mCommissionSearchModel.getShopIdenty());
            mExchangeCodeEntity.setCustomerId(mCustomer.getId());

            mExchangeCodeEntity = mExchangeCodeService.queryCode(mExchangeCodeEntity);

            if(mExchangeCodeEntity != null && mExchangeCodeEntity.getId() != null){
            System.out.println("=====code===="+mExchangeCodeEntity.getExchangeCode()+"==="+mCommissionSearchModel.getPassword());
                if(!mExchangeCodeEntity.getExchangeCode().equals(mCommissionSearchModel.getPassword())){
                    mCommissionSearchModel.setSendMsg("兑换验证失败，请确认兑换凭证是否正确");
                    queryExpandedCommission(model,mCommissionSearchModel);
                    return "expanded_exchange_commission";
                }
                //获取24小时失效时间
                else if(mExchangeCodeEntity.getCreateTime() != null && mExchangeCodeEntity.getCreateTime().getTime()+6*60*60*1000 < new Date().getTime()){
                    mCommissionSearchModel.setSendMsg("兑换凭证已失效，请联系顾客重新获取提供");
                    queryExpandedCommission(model,mCommissionSearchModel);
                    return "expanded_exchange_commission";
                }else if(mExchangeCodeEntity.getStatusFlag() == 2){
                    mCommissionSearchModel.setSendMsg("兑换凭证已使用，请联系顾客重新获取提供");
                    queryExpandedCommission(model,mCommissionSearchModel);
                    return "expanded_exchange_commission";
                }else if(mExchangeCodeEntity.getExchangeCode().equals(mCommissionSearchModel.getPassword())){
                    ExpandedCommissionEntity mExpandedCommission = new ExpandedCommissionEntity();
                    mExpandedCommission.setBrandIdenty(mCommissionSearchModel.getBrandIdenty());
                    mExpandedCommission.setShopIdenty(mCommissionSearchModel.getShopIdenty());
                    mExpandedCommission.setCustomerId(mCommissionSearchModel.getCustomerId());
                    mExpandedCommission.setExchangeAmount(mCommissionSearchModel.getExchangeAmount());

                    Boolean isSuccess = mExpandedCommissionService.exchangeExpandedCommission(mExpandedCommission);
                    if(isSuccess){
                        mCommissionSearchModel.setSendMsg("提成兑换操作成功");
                        queryExpandedCommission(model,mCommissionSearchModel);

                        //将兑换凭证修改为已使用，避免重复兑换使用
                        mExchangeCodeEntity.setStatusFlag(2);
                        mExchangeCodeService.updateCode(mExchangeCodeEntity);
                        return "expanded_exchange_commission";
                    }else{
                        mCommissionSearchModel.setSendMsg("提成兑换操作失败");
                        queryExpandedCommission(model,mCommissionSearchModel);
                        return "expanded_exchange_commission";
                    }
                }else{
                    mCommissionSearchModel.setSendMsg("兑换验证失败，请确认兑换凭证是否正确");
                    queryExpandedCommission(model,mCommissionSearchModel);
                    return "expanded_exchange_commission";
                }


            }else{
                mCommissionSearchModel.setSendMsg("兑换验证失败，请确认兑换凭证是否正确");
                queryExpandedCommission(model,mCommissionSearchModel);
                return "expanded_exchange_commission";
            }

//            String inputPassWorld = ToolsUtil.encodeValue(mCommissionSearchModel.getPassword(),mCommissionSearchModel.getMobile());
//            if(inputPassWorld.equals(mCustomer.getPassword())){
//                ExpandedCommissionEntity mExpandedCommission = new ExpandedCommissionEntity();
//                mExpandedCommission.setBrandIdenty(mCommissionSearchModel.getBrandIdenty());
//                mExpandedCommission.setShopIdenty(mCommissionSearchModel.getShopIdenty());
//                mExpandedCommission.setCustomerId(mCommissionSearchModel.getCustomerId());
//                mExpandedCommission.setExchangeAmount(mCommissionSearchModel.getExchangeAmount());
//
//                Boolean isSuccess = mExpandedCommissionService.exchangeExpandedCommission(mExpandedCommission);
//                if(isSuccess){
//                    mCommissionSearchModel.setSendMsg("提成兑换操作成功");
//                    queryExpandedCommission(model,mCommissionSearchModel);
//                    return "expanded_exchange_commission";
//                }else{
//                    mCommissionSearchModel.setSendMsg("提成兑换操作失败");
//                    queryExpandedCommission(model,mCommissionSearchModel);
//                    return "expanded_exchange_commission";
//                }
//            }else{
//                mCommissionSearchModel.setSendMsg("兑换验证失败");
//                queryExpandedCommission(model,mCommissionSearchModel);
//                return "expanded_exchange_commission";
//
//            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }
}

