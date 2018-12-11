package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.domain.entity.CustomerMarketingExpandedEntity;
import com.zhongmei.yunfu.domain.mapper.CustomerMarketingExpandedMapper;
import com.zhongmei.yunfu.service.CustomerMarketingExpandedService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员关联推广回馈 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
@Service
public class CustomerMarketingExpandedServiceImpl extends ServiceImpl<CustomerMarketingExpandedMapper, CustomerMarketingExpandedEntity> implements CustomerMarketingExpandedService {

    @Override
    public Boolean addCustomerExpanded(CustomerMarketingExpandedEntity mCustomerMarketingExpanded) throws Exception {
        Boolean isSuccess = insert(mCustomerMarketingExpanded);
        return isSuccess;
    }

    @Override
    public Boolean modifyExpanded(CustomerMarketingExpandedEntity mCustomerMarketingExpanded) throws Exception {

        EntityWrapper<CustomerMarketingExpandedEntity> eWrapper = new EntityWrapper<>(new CustomerMarketingExpandedEntity());
        eWrapper.eq("expanded_code", mCustomerMarketingExpanded.getExpandedCode());
        Boolean isSuccess = update(mCustomerMarketingExpanded, eWrapper);
        return isSuccess;
    }

    @Override
    public CustomerMarketingExpandedEntity checkHadExpandedCustomer(CustomerMarketingExpandedEntity mCustomerMarketingExpanded) throws Exception {
        EntityWrapper<CustomerMarketingExpandedEntity> eWrapper = new EntityWrapper<>(new CustomerMarketingExpandedEntity());
        eWrapper.eq("expanded_customer_openid", mCustomerMarketingExpanded.getExpandedCustomerOpenid());
        eWrapper.eq("shop_identy", mCustomerMarketingExpanded.getShopIdenty());
        eWrapper.eq("brand_identy", mCustomerMarketingExpanded.getBrandIdenty());
        eWrapper.orderBy("id", false);
        CustomerMarketingExpandedEntity cme = selectOne(eWrapper);
        if (cme != null) {
            return cme;
        } else {
            return cme;
        }

    }

    @Override
    public List<CustomerMarketingExpandedEntity> queryExpanded(CustomerMarketingExpandedEntity mCustomerMarketingExpanded) throws Exception {
        EntityWrapper<CustomerMarketingExpandedEntity> eWrapper = new EntityWrapper<>(new CustomerMarketingExpandedEntity());
        eWrapper.eq("shop_identy", mCustomerMarketingExpanded.getShopIdenty());
        eWrapper.eq("brand_identy", mCustomerMarketingExpanded.getBrandIdenty());
        eWrapper.eq("customer_id", mCustomerMarketingExpanded.getCustomerId());
        eWrapper.isNotNull("expanded_customer_openid");
        eWrapper.setSqlSelect("expanded_customer_openid,expanded_customer_name,server_create_time,expanded_customer_pic");
        List<CustomerMarketingExpandedEntity> listData = selectList(eWrapper);
        return listData;
    }
}
