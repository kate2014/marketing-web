package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.model.CustomerSearchSettingVo;
import com.zhongmei.yunfu.domain.entity.CustomerSearchRuleEntity;
import com.zhongmei.yunfu.domain.mapper.CustomerSearchRuleMapper;
import com.zhongmei.yunfu.service.CustomerSearchRuleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员查询规则表 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-11-08
 */
@Service
public class CustomerSearchRuleServiceImpl extends ServiceImpl<CustomerSearchRuleMapper, CustomerSearchRuleEntity> implements CustomerSearchRuleService {

    @Override
    public CustomerSearchRuleEntity selectByShopId(Long brandId, Long shopId) {
        CustomerSearchRuleEntity searchRuleEntity = new CustomerSearchRuleEntity();
        searchRuleEntity.setBrandIdenty(brandId);
        searchRuleEntity.setShopIdenty(shopId);
        return selectOne(new EntityWrapper<>(searchRuleEntity));
    }

    @Override
    public void insertEntity(CustomerSearchSettingVo settingVo) {
        CustomerSearchRuleEntity customerSearchRuleEntity = selectByShopId(settingVo.getBrandIdenty(), settingVo.getShopIdenty());
        if (customerSearchRuleEntity == null) {
            customerSearchRuleEntity = new CustomerSearchRuleEntity();
            customerSearchRuleEntity.baseCreate(settingVo.getCreatorId(), settingVo.getCreatorName());
        }
        customerSearchRuleEntity.baseUpdate(settingVo.getCreatorId(), settingVo.getCreatorName());
        customerSearchRuleEntity.setBrandIdenty(settingVo.getBrandIdenty());
        customerSearchRuleEntity.setShopIdenty(settingVo.getShopIdenty());
        customerSearchRuleEntity.setConsumptionMainDay(settingVo.getConsumptionMainDay());
        customerSearchRuleEntity.setConsumptionMainAmount(settingVo.getConsumptionMainAmount());
        customerSearchRuleEntity.setConsumptionMainNumber(settingVo.getConsumptionMainNumber());
        customerSearchRuleEntity.setMembersWillDay(settingVo.getMembersWillDay());
        customerSearchRuleEntity.setMembersWillAmount(settingVo.getMembersWillAmount());
        customerSearchRuleEntity.setMembersWillNumber(settingVo.getMembersWillNumber());
        customerSearchRuleEntity.setMembersLossDay(settingVo.getMembersLossDay());
        customerSearchRuleEntity.setMembersLossAmount(settingVo.getMembersLossAmount());
        customerSearchRuleEntity.setMembersLossNumber(settingVo.getMembersLossNumber());
        customerSearchRuleEntity.setMembersNewIntervalDay(settingVo.getMembersNewIntervalDay());
        customerSearchRuleEntity.setMembersBirthdayBeforeDay(settingVo.getMembersBirthdayBeforeDay());
        customerSearchRuleEntity.setMembersAnniversaryBeforeDay(settingVo.getMembersAnniversaryBeforeDay());
        insertOrUpdate(customerSearchRuleEntity);
    }


}
