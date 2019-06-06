package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.CustomerDishPrivilegeEntity;
import com.zhongmei.yunfu.domain.mapper.CustomerDishPrivilegeMapper;
import com.zhongmei.yunfu.service.CustomerDishPrivilegeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDishPrivilegeServiceImpl extends ServiceImpl<CustomerDishPrivilegeMapper, CustomerDishPrivilegeEntity> implements CustomerDishPrivilegeService {

    @Override
    public List<CustomerDishPrivilegeEntity> queryDishPrivilege(CustomerDishPrivilegeEntity entity) throws Exception {
        EntityWrapper<CustomerDishPrivilegeEntity> eWrapper = new EntityWrapper<>(new CustomerDishPrivilegeEntity());
        eWrapper.eq("brand_identy",entity.getBrandIdenty());
        eWrapper.eq("shop_identy",entity.getShopIdenty());
        eWrapper.eq("level_id",entity.getLevelId());
        eWrapper.eq("status_flag",1);
        List<CustomerDishPrivilegeEntity> listData = selectList(eWrapper);
        return listData;
    }
}
