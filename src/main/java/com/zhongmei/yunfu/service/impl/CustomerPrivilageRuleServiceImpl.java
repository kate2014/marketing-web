package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.model.CustomerPrivilegeRuleModel;
import com.zhongmei.yunfu.domain.entity.CustomerPrivilegeRuleEntity;
import com.zhongmei.yunfu.domain.mapper.CustomerPrivilegeRuleMapper;
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
public class CustomerPrivilageRuleServiceImpl extends ServiceImpl<CustomerPrivilegeRuleMapper, CustomerPrivilegeRuleEntity> implements CustomerPrivilageRuleService {

    @Override
    public List<CustomerPrivilegeRuleEntity> queryAllRule(CustomerPrivilegeRuleModel ruleModel) throws Exception {
        EntityWrapper<CustomerPrivilegeRuleEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy", ruleModel.getBrandIdenty());
        eWrapper.eq("shop_identy", ruleModel.getShopIdenty());
        if(ruleModel.getPrivilegeType() != null){
            eWrapper.eq("privilege_type", ruleModel.getPrivilegeType());
        }
        eWrapper.eq("status_flag", 1);
        eWrapper.orderBy("server_update_time", true);

        List<CustomerPrivilegeRuleEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public boolean addRule(CustomerPrivilegeRuleEntity entity) throws Exception {

        return insert(entity);
    }

    @Override
    public boolean batchAddRule(List<CustomerPrivilegeRuleEntity> entity) throws Exception {

        return insertBatch(entity);
    }

    @Override
    public boolean deleteRuleById(Long id) throws Exception {

        return deleteById(id);
    }

    @Override
    public boolean updateRule(CustomerPrivilegeRuleEntity entity) throws Exception {

        boolean isSuccess = updateById(entity);
        return isSuccess;
    }

    @Override
    public boolean batchUpdateRule(List<CustomerPrivilegeRuleEntity> listData) throws Exception {

        return updateBatchById(listData);
    }

    @Override
    public boolean installOrUpdate(List<CustomerPrivilegeRuleEntity> listData) throws Exception {

        return insertOrUpdateBatch(listData);
    }

    @Override
    public boolean batchDelete(Long brandIdenty,Long shopIdenty,String ids) throws Exception {
        EntityWrapper<CustomerPrivilegeRuleEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy", brandIdenty);
        eWrapper.eq("shop_identy", shopIdenty);
        eWrapper.in("id",ids);
        return delete(eWrapper);
    }
}
