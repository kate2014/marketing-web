package com.zhongmei.yunfu.controller.api;


import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.CustomerCouponModel;
import com.zhongmei.yunfu.domain.entity.CouponEntity;
import com.zhongmei.yunfu.domain.entity.CustomerCouponEntity;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.service.CustomerCouponService;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.impl.CouponServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员优惠券关联表 前端控制器
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-29
 */
@RestController
@RequestMapping("/wxapp/customerCoupon")
public class CustomerCouponApiController {
    @Autowired
    CustomerCouponService mCustomerCouponService;
    @Autowired
    CouponServiceImpl mCouponServiceImpl;
    @Autowired
    CustomerService mCustomerService;

    @GetMapping("/addCustomerCoupon")
    public BaseDataModel addCustomerCoupon(ModelMap model, CustomerCouponModel mCustomerCouponModel) {

        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            CustomerCouponEntity mCustomerCoupon = new CustomerCouponEntity();
            mCustomerCoupon.setBrandIdenty(mCustomerCouponModel.getBrandIdenty());
            mCustomerCoupon.setShopIdenty(mCustomerCouponModel.getShopIdenty());
            mCustomerCoupon.setSourceId(mCustomerCouponModel.getSourceId());
            mCustomerCoupon.setCouponId(mCustomerCouponModel.getCouponId());
            mCustomerCoupon.setCouponName(mCustomerCouponModel.getCouponName());
            mCustomerCoupon.setCouponType(mCustomerCouponModel.getCouponType());
            mCustomerCoupon.setCustomerId(mCustomerCouponModel.getCustomerId());
            mCustomerCoupon.setWxCustomerOpenid(mCustomerCouponModel.getWxCustomerOpenid());
            mCustomerCoupon.setStatus(mCustomerCouponModel.getStatus());
            mCustomerCoupon.setEnabledFlag(mCustomerCouponModel.getEnabledFlag());
            mCustomerCoupon.setCreatorId(mCustomerCouponModel.getCreatorId());
            mCustomerCoupon.setCreatorName(mCustomerCouponModel.getCreatorName());
            mCustomerCoupon.setUpdatorId(mCustomerCouponModel.getUpdatorId());
            mCustomerCoupon.setUpdatorName(mCustomerCouponModel.getUpdatorName());
            mCustomerCoupon.setServerCreateTime(new Date());
            mCustomerCoupon.setServerUpdateTime(new Date());
            Boolean isSuccess = mCustomerCouponService.addCustomerCoupon(mCustomerCoupon);
            if (isSuccess) {
                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("发放优惠券成功");
                mBaseDataModel.setData(true);
            } else {
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("发放优惠券失败");
                mBaseDataModel.setData(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("发放优惠券失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    @GetMapping("/queryCustomerCoupon")
    public BaseDataModel queryCustomerCoupon(ModelMap model, CustomerCouponModel mCustomerCouponModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {

            String listCustomer = "";
            listCustomer = mCustomerCouponModel.getCustomerId().toString();

            CustomerEntity mCustomer= mCustomerService.queryCustomerById(mCustomerCouponModel.getBrandIdenty(),mCustomerCouponModel.getShopIdenty(),mCustomerCouponModel.getCustomerId());

            if(mCustomer != null){
                if(mCustomer.getRelateId() != null && mCustomer.getRelateId() != 0){
                    listCustomer += "," + mCustomer.getRelateId();
                }
            }
            List<CustomerCouponModel> listData = mCustomerCouponService.queryCouponByCustomerGroup(mCustomerCouponModel.getBrandIdenty(),mCustomerCouponModel.getShopIdenty(),listCustomer,mCustomerCouponModel.getStatus());

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取数据成功");
            mBaseDataModel.setData(listData);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 验证优惠券有效性
     *
     * @param model
     * @param mCustomerCouponModel
     * @return
     */
    @GetMapping("/checkCoupon")
    public BaseDataModel checkCoupon(ModelMap model, CustomerCouponModel mCustomerCouponModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            CustomerCouponEntity mCustomerCoupon = mCustomerCouponService.queryCouponById(mCustomerCouponModel.getId());

            //验证优惠券是否已使用
            if (mCustomerCoupon.getStatus() == 1) {
                //获取对应优惠券详情
                CouponEntity mCoupon = mCouponServiceImpl.queryByid(mCustomerCouponModel.getCouponId());

                //验证优惠券是否在有效期类
                if (mCoupon.getEndTime().getTime() >= new Date().getTime()) {
                    mBaseDataModel.setState("1000");
                    mBaseDataModel.setMsg("优惠券可使用");
                    mBaseDataModel.setData(true);
                } else {
                    mBaseDataModel.setState("1001");
                    mBaseDataModel.setMsg("优惠券已过期");
                    mBaseDataModel.setData(false);
                }
            } else {
                mBaseDataModel.setState("1002");
                mBaseDataModel.setMsg("优惠券已被使用");
                mBaseDataModel.setData(false);
            }
        } catch (Exception e) {
            mBaseDataModel.setState("1003");
            mBaseDataModel.setMsg("优惠券验证失败");
            mBaseDataModel.setData(false);
        }


        return mBaseDataModel;
    }

    /**
     * 优惠券核销
     *
     * @param model
     * @param mCustomerCouponModel
     * @return
     */
    @GetMapping("/useCoupon")
    public BaseDataModel useCoupon(ModelMap model, CustomerCouponModel mCustomerCouponModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            //先验证有效性
            CustomerCouponEntity mCustomerCoupon = mCustomerCouponService.queryCouponById(mCustomerCouponModel.getId());

            //验证优惠券是否已使用
            if (mCustomerCoupon.getStatus() == 1) {
                //验证优惠券是否在有效期类
                CustomerCouponEntity cc = new CustomerCouponEntity();
                cc.setId(mCustomerCouponModel.getId());
                cc.setStatus(2);
                Boolean isSuccess = mCustomerCouponService.modfiyCouponState(cc);
                if (isSuccess) {
                    mBaseDataModel.setState("1000");
                    mBaseDataModel.setMsg("优惠券核销成功");
                    mBaseDataModel.setData(true);
                } else {
                    mBaseDataModel.setState("1001");
                    mBaseDataModel.setMsg("优惠券核销失败");
                    mBaseDataModel.setData(false);
                }
            } else {
                mBaseDataModel.setState("1002");
                mBaseDataModel.setMsg("优惠券已被使用");
                mBaseDataModel.setData(false);
            }


        } catch (Exception e) {
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("优惠券核销失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 优惠券反核销
     *
     * @param model
     * @param mCustomerCouponModel
     * @return
     */
    @GetMapping("/returnCoupon")
    public BaseDataModel returnUseCoupon(ModelMap model, CustomerCouponModel mCustomerCouponModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            CustomerCouponEntity cc = new CustomerCouponEntity();
            cc.setId(mCustomerCouponModel.getId());
            cc.setStatus(1);
            Boolean isSuccess = mCustomerCouponService.modfiyCouponState(cc);
            if (isSuccess) {
                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("优惠券反核销成功");
                mBaseDataModel.setData(true);
            } else {
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("优惠券反核销失败");
                mBaseDataModel.setData(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("优惠券反核销失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }
}

