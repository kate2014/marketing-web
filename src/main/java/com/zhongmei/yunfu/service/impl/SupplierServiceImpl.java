package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.CollageMarketingEntity;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.zhongmei.yunfu.domain.entity.SupplierEntity;
import com.zhongmei.yunfu.domain.mapper.SupplierMapper;
import com.zhongmei.yunfu.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, SupplierEntity> implements SupplierService {

    @Override
    public List<SupplierEntity> querySupplier(SupplierEntity mSupplierEntity) throws Exception {
        List<SupplierEntity> listData = new ArrayList<>();
        EntityWrapper<SupplierEntity> eWrapper = new EntityWrapper<>(new SupplierEntity());
        eWrapper.eq("brand_identy",mSupplierEntity.getBrandIdenty());
        eWrapper.eq("shop_identy",mSupplierEntity.getShopIdenty());
        eWrapper.eq("status_flag",1);
        listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public Page<SupplierEntity> querySupplierPage(SupplierEntity mSupplierEntity, int pageNo, int pageSize) throws Exception {
        Page<SupplierEntity> listPage = new Page<>(pageNo, pageSize);
        EntityWrapper<SupplierEntity> eWrapper = new EntityWrapper<>(new SupplierEntity());
        eWrapper.eq("brand_identy",mSupplierEntity.getBrandIdenty());
        eWrapper.eq("shop_identy",mSupplierEntity.getShopIdenty());
        eWrapper.like("name",mSupplierEntity.getName());
        eWrapper.like("contacts",mSupplierEntity.getContacts());
        eWrapper.like("contacts_phone",mSupplierEntity.getContactsPhone());
        eWrapper.eq("status_flag",1);
        eWrapper.orderBy("server_create_time", false);
        Page<SupplierEntity> listData = selectPage(listPage, eWrapper);

        return listData;
    }

    @Override
    public boolean deleteSupplierById(Long id) throws Exception {

        return deleteById(id);
    }

    @Override
    public boolean addOrUpdateSupplier(SupplierEntity mSupplierEntity) throws Exception {
        return insertOrUpdate(mSupplierEntity);
    }
}
