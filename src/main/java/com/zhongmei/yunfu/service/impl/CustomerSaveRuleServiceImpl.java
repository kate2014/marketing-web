package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.CustomerSaveRuleEntity;
import com.zhongmei.yunfu.domain.mapper.CustomerSaveRuleMapper;
import com.zhongmei.yunfu.service.CustomerSaveRuleService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 会员储值规则表 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2019-06-04
 */
@Service
public class CustomerSaveRuleServiceImpl extends ServiceImpl<CustomerSaveRuleMapper, CustomerSaveRuleEntity> implements CustomerSaveRuleService {

    @Override
    public List<CustomerSaveRuleEntity> queryAllRule(CustomerSaveRuleEntity mCustomerSaveRuleEntity) throws Exception {

        EntityWrapper<CustomerSaveRuleEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy", mCustomerSaveRuleEntity.getBrandIdenty());
        eWrapper.eq("shop_identy", mCustomerSaveRuleEntity.getShopIdenty());
        eWrapper.eq("status_flag", 1);
        eWrapper.orderBy("stored_value", true);

        List<CustomerSaveRuleEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public boolean addSaveRule(CustomerSaveRuleEntity mCustomerSaveRuleEntity) throws Exception {

        return insert(mCustomerSaveRuleEntity);
    }

    @Override
    public boolean deleteRuleById(Long id) throws Exception {
        EntityWrapper<CustomerSaveRuleEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("id", id);
        return delete(eWrapper);
    }

    @Override
    public BigDecimal getShopGiveValue(Long shopId, BigDecimal tradeAmount) {
        BigDecimal shopGiveValue = baseMapper.getShopGiveValue(shopId, tradeAmount);
        if (shopGiveValue != null) {
            return shopGiveValue;
        }
        return BigDecimal.ZERO;
    }
}
