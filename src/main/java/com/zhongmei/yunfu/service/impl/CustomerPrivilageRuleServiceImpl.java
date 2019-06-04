package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.model.CustomerPrivilageRuleModel;
import com.zhongmei.yunfu.domain.entity.CustomerPrivilageRuleEntity;
import com.zhongmei.yunfu.domain.entity.CustomerSaveRuleEntity;
import com.zhongmei.yunfu.domain.mapper.CustomerPrivilageRuleMapper;
import com.zhongmei.yunfu.service.CustomerPrivilageRuleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员权益设置表 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2019-06-04
 */
@Service
public class CustomerPrivilageRuleServiceImpl extends ServiceImpl<CustomerPrivilageRuleMapper, CustomerPrivilageRuleEntity> implements CustomerPrivilageRuleService {

    @Override
    public List<CustomerPrivilageRuleEntity> queryAllRule(CustomerPrivilageRuleModel ruleModel) throws Exception {
        EntityWrapper<CustomerPrivilageRuleEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy", ruleModel.getBrandIdenty());
        eWrapper.eq("shop_identy", ruleModel.getShopIdenty());
        eWrapper.eq("status_flag", 1);
        eWrapper.orderBy("server_update_time", true);

        List<CustomerPrivilageRuleEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public boolean addRule(CustomerPrivilageRuleEntity entity) throws Exception {

        return insert(entity);
    }

    @Override
    public boolean deleteRuleById(Long id) throws Exception {

        return deleteById(id);
    }

    @Override
    public boolean updateRule(CustomerPrivilageRuleEntity entity) throws Exception {

        boolean isSuccess = updateById(entity);
        return isSuccess;
    }
}
