package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.util.ToolsUtil;
import com.zhongmei.yunfu.controller.model.CustomerMTModel;
import com.zhongmei.yunfu.domain.entity.CustomerMarketingTogetherEntity;
import com.zhongmei.yunfu.domain.mapper.CustomerMarketingTogetherMapper;
import com.zhongmei.yunfu.service.CustomerMarketingTogetherService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员关联同行特惠 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-29
 */
@Service
public class CustomerMarketingTogetherServiceImpl extends ServiceImpl<CustomerMarketingTogetherMapper, CustomerMarketingTogetherEntity> implements CustomerMarketingTogetherService {

    @Override
    public CustomerMTModel installCMT(CustomerMTModel mCustomerMTModel) throws Exception {
        CustomerMarketingTogetherEntity mCMT = new CustomerMarketingTogetherEntity();
        mCMT.setCustomerId(mCustomerMTModel.getCustomerId());
        mCMT.setCustomerName(mCustomerMTModel.getCustomerName());
        mCMT.setOpenId(mCustomerMTModel.getOpenId());
        mCMT.setValidData(mCustomerMTModel.getValidData());
        mCMT.setCouponId(mCustomerMTModel.getCouponId());
        mCMT.setCouponName(mCustomerMTModel.getCouponName());
        mCMT.setTogetherId(mCustomerMTModel.getTogetherId());
        //生成6位同行码
        String togetherCode = ToolsUtil.getCard(6);
        mCustomerMTModel.setTogetherCode(togetherCode);

        mCMT.setTogetherCode(togetherCode);
        mCMT.setStatus(mCustomerMTModel.getStatus());

        //生成同行批次码
        Date m = new Date();

        String batchCode = Long.toString(m.getTime()) + mCustomerMTModel.getCustomerId();
        mCustomerMTModel.setBatchCode(batchCode);
        mCMT.setBatchCode(batchCode);

        mCMT.setBrandIdenty(mCustomerMTModel.getBrandIdenty());
        mCMT.setShopIdenty(mCustomerMTModel.getShopIdenty());
        mCMT.setStatusFlag(1);
        mCMT.setServerCreateTime(new Date());
        mCMT.setServerUpdateTime(new Date());

        insert(mCMT);

        return mCustomerMTModel;
    }

    @Override
    public Boolean modfiyCMT(CustomerMTModel mCustomerMTModel) throws Exception {

        CustomerMarketingTogetherEntity mCMT = new CustomerMarketingTogetherEntity();
        mCMT.setFCustomerId(mCustomerMTModel.getCustomerId());
        mCMT.setFOpenId(mCustomerMTModel.getOpenId());
        mCMT.setFCustomerName(mCustomerMTModel.getCustomerName());
        mCMT.setStatus(mCustomerMTModel.getStatus());
        mCMT.setCouponId(mCustomerMTModel.getCouponId());
        mCMT.setCouponName(mCustomerMTModel.getCouponName());
        EntityWrapper<CustomerMarketingTogetherEntity> eWrapper = new EntityWrapper<>(new CustomerMarketingTogetherEntity());
        eWrapper.eq("batch_code", mCustomerMTModel.getBatchCode());
        Boolean isSuccess = update(mCMT, eWrapper);
        return isSuccess;
    }

    @Override
    public List<CustomerMarketingTogetherEntity> findCMTList(CustomerMTModel mCustomerMTModel) throws Exception {

        EntityWrapper<CustomerMarketingTogetherEntity> eWrapper = new EntityWrapper<>(new CustomerMarketingTogetherEntity());
        eWrapper.eq("shop_identy", mCustomerMTModel.getShopIdenty());
        eWrapper.eq("brand_identy", mCustomerMTModel.getBrandIdenty());
//        eWrapper.eq("status", mCustomerMTModel.getStatus());
        eWrapper.eq("customer_id", mCustomerMTModel.getCustomerId());
        eWrapper.eq("status_flag", 1);
        eWrapper.orderBy("server_create_time", false);

        eWrapper.setSqlSelect("id,customer_name,f_customer_name,coupon_name,together_code,coupon_name,status,batch_code,shop_identy,brand_identy,server_create_time,valid_data");
        List<CustomerMarketingTogetherEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public CustomerMarketingTogetherEntity queryCMTByBatchCode(String batchCode) throws Exception {
        CustomerMarketingTogetherEntity mCustomerMarketingTogether = new CustomerMarketingTogetherEntity();
        EntityWrapper<CustomerMarketingTogetherEntity> eWrapper = new EntityWrapper<>(mCustomerMarketingTogether);
        eWrapper.eq("batch_code", batchCode);
        mCustomerMarketingTogether = selectOne(eWrapper);
        return mCustomerMarketingTogether;
    }

    @Override
    public List<CustomerMarketingTogetherEntity> findCMTInvited(CustomerMTModel mCustomerMTModel) throws Exception {
        EntityWrapper<CustomerMarketingTogetherEntity> eWrapper = new EntityWrapper<>(new CustomerMarketingTogetherEntity());
        eWrapper.eq("shop_identy", mCustomerMTModel.getShopIdenty());
        eWrapper.eq("brand_identy", mCustomerMTModel.getBrandIdenty());
//        eWrapper.eq("status", mCustomerMTModel.getStatus());
        eWrapper.eq("f_customer_id", mCustomerMTModel.getCustomerId());
        eWrapper.eq("status_flag", 1);
        eWrapper.orderBy("server_create_time", false);

        eWrapper.setSqlSelect("id,customer_name,f_customer_name,coupon_name,together_code,coupon_name,status,batch_code,shop_identy,brand_identy,server_create_time,valid_data");
        List<CustomerMarketingTogetherEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public CustomerMarketingTogetherEntity findCMTById(CustomerMTModel mCustomerMTModel) {
        return null;
    }

    @Override
    public Boolean writeOffCheck(CustomerMTModel mCustomerMTModel) throws Exception {
        EntityWrapper<CustomerMarketingTogetherEntity> eWrapper = new EntityWrapper<>(new CustomerMarketingTogetherEntity());
        eWrapper.eq("shop_identy", mCustomerMTModel.getShopIdenty());
        eWrapper.eq("brand_identy", mCustomerMTModel.getBrandIdenty());
        eWrapper.eq("togetherCode", mCustomerMTModel.getTogetherCode());
        eWrapper.eq("customer_id", mCustomerMTModel.getCustomerId());
        eWrapper.eq("status_flag", 1);
        eWrapper.eq("status", 3);
        eWrapper.eq("status_flag", 1);
        eWrapper.setSqlSelect("id,customer_id,open_id,f_customer_id,f_open_id,together_id,together_code,status,batch_code");
        CustomerMarketingTogetherEntity mm = selectOne(eWrapper);
        return null;
    }
}
