package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CustomerArchivesEntity;
import com.zhongmei.yunfu.domain.mapper.BrandMapper;
import com.zhongmei.yunfu.domain.mapper.CustomerArchivesMapper;
import com.zhongmei.yunfu.erp.model.ERPBrandModel;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CustomerArchivesService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Service
public class CustomerArchivesServiceImpl extends ServiceImpl<CustomerArchivesMapper, CustomerArchivesEntity> implements CustomerArchivesService {


    @Override
    public boolean addCustomerArchives(CustomerArchivesEntity mCustomerArchivesEntity) throws Exception {
        return insert(mCustomerArchivesEntity);
    }

    @Override
    public Page<CustomerArchivesEntity> queryArchivesPage(CustomerArchivesEntity mCustomerArchivesEntity,int pageNo,int pageSize) throws Exception {

        EntityWrapper<CustomerArchivesEntity> eWrapper = new EntityWrapper<>(new CustomerArchivesEntity());

        eWrapper.eq("brand_identy", mCustomerArchivesEntity.getBrandIdenty());
        eWrapper.eq("shop_identy", mCustomerArchivesEntity.getShopIdenty());
        eWrapper.eq("customer_id", mCustomerArchivesEntity.getCustomerId());

        if(mCustomerArchivesEntity.getType() != null && !mCustomerArchivesEntity.getType().equals("")){
            eWrapper.eq("type", mCustomerArchivesEntity.getType());
        }
        if(mCustomerArchivesEntity.getTitle() != null && !mCustomerArchivesEntity.getTitle().equals("")){
            eWrapper.like("title", mCustomerArchivesEntity.getTitle());
        }

        eWrapper.eq("status_flag", 1);

        eWrapper.orderBy("server_create_time",false);

        Page<CustomerArchivesEntity> listPage = new Page<>(pageNo,pageSize);

        Page<CustomerArchivesEntity> listData = selectPage(listPage,eWrapper);

        return listData;
    }

    @Override
    public List<CustomerArchivesEntity> queryArchivesList(CustomerArchivesEntity mCustomerArchivesEntity) throws Exception {
        EntityWrapper<CustomerArchivesEntity> eWrapper = new EntityWrapper<>(new CustomerArchivesEntity());
        eWrapper.eq("brand_identy", mCustomerArchivesEntity.getBrandIdenty());
        eWrapper.eq("shop_identy", mCustomerArchivesEntity.getShopIdenty());
        eWrapper.eq("customer_id", mCustomerArchivesEntity.getCustomerId());

        if(mCustomerArchivesEntity.getType() != null && !mCustomerArchivesEntity.getType().equals("")){
            eWrapper.eq("type", mCustomerArchivesEntity.getType());
        }
        if(mCustomerArchivesEntity.getTitle() != null && !mCustomerArchivesEntity.getTitle().equals("")){
            eWrapper.like("title", mCustomerArchivesEntity.getTitle());
        }

        eWrapper.eq("status_flag", 1);

        eWrapper.orderBy("server_create_time",false);

        List<CustomerArchivesEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public boolean modfityArchives(CustomerArchivesEntity mCustomerArchivesEntity) throws Exception {
        return updateById(mCustomerArchivesEntity);
    }

    @Override
    public boolean deleteArchives(Long id) throws Exception {
        return deleteById(id);
    }
}
