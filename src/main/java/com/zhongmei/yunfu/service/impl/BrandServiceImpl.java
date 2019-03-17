package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.BrandModel;
import com.zhongmei.yunfu.erp.model.ERPBrandModel;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.mapper.BrandMapper;
import com.zhongmei.yunfu.service.BrandService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, BrandEntity> implements BrandService {

    @Override
    public BrandEntity queryBrandByAppId(Long id) throws Exception {
        EntityWrapper<BrandEntity> eWrapper = new EntityWrapper<>(new BrandEntity());
        if(id != null){
            eWrapper.eq("id", id);
            eWrapper.eq("status", 0);
            eWrapper.eq("status_flag", 1);
            eWrapper.setSqlSelect("id,name,logo,contacts,contacts_phone,contacts_mail,commercial_group_adress,parent_id,type");
            BrandEntity mBrand = selectOne(eWrapper);
            return mBrand;
        }else{
            return null;
        }
    }

    @Override
    public Page<BrandEntity> queryBrandList(ERPBrandModel mERPBrandModel) throws Exception {

        EntityWrapper<BrandEntity> eWrapper = new EntityWrapper<>(new BrandEntity());

        if(mERPBrandModel.getStatus() != null && !mERPBrandModel.getStatus().equals("")){
            eWrapper.eq("status", mERPBrandModel.getStatus());
        }
        if(mERPBrandModel.getName() != null && !mERPBrandModel.getName().equals("")){
            eWrapper.like("name", mERPBrandModel.getName());
        }
        if(mERPBrandModel.getProvince() != null && !mERPBrandModel.getProvince().equals("")){
            eWrapper.eq("province", mERPBrandModel.getProvince());
        }
        if(mERPBrandModel.getCity() != null && !mERPBrandModel.getCity().equals("")){
            eWrapper.eq("city", mERPBrandModel.getCity());
        }
        if(mERPBrandModel.getArea() != null && !mERPBrandModel.getArea().equals("")){
            eWrapper.eq("area", mERPBrandModel.getArea());
        }
        eWrapper.orderBy("server_create_time",false);
        eWrapper.setSqlSelect("id,name,contacts,contacts_phone,province,city,area,status,server_create_time");

        Page<BrandEntity> listPage = new Page<>(mERPBrandModel.getPageNo(),mERPBrandModel.getPageSize());

        Page<BrandEntity> listBrand = selectPage(listPage,eWrapper);

        return listBrand;
    }

    @Override
    public Boolean createBrand(ERPBrandModel mERPBrandModel) throws Exception {

        BrandEntity mBrandEntity = new BrandEntity();
        mBrandEntity.setName(mERPBrandModel.getName());
        mBrandEntity.setLogo(mERPBrandModel.getLogo());
        mBrandEntity.setContacts(mERPBrandModel.getContacts());
        mBrandEntity.setContactsPhone(mERPBrandModel.getContactsPhone());
        mBrandEntity.setContactsMail(mERPBrandModel.getContactsMail());
        mBrandEntity.setCommercialGroupAdress(mERPBrandModel.getCommercialGroupAdress());
        mBrandEntity.setStatus(mERPBrandModel.getStatus());
        mBrandEntity.setProvince(mERPBrandModel.getProvince());
        mBrandEntity.setCity(mERPBrandModel.getCity());
        mBrandEntity.setArea(mERPBrandModel.getArea());
        mBrandEntity.setCreatorId(mERPBrandModel.getCreatorId());
        mBrandEntity.setCreatorName(mERPBrandModel.getCreatorName());

        Boolean isSuccess = insert(mBrandEntity);
        return isSuccess;
    }

    @Override
    public BrandEntity queryBrandById(Long brandId) throws Exception {
        EntityWrapper<BrandEntity> eWrapper = new EntityWrapper<>(new BrandEntity());
        eWrapper.eq("id", brandId);

        BrandEntity mBrandEntity = selectOne(eWrapper);
        return mBrandEntity;
    }

    @Override
    public Boolean deleteBrandById(Long brandId) throws Exception {

        Boolean isSuccess = deleteById(brandId);
        return isSuccess;
    }

    @Override
    public Boolean modifyBrandById(ERPBrandModel mERPBrandModel) throws Exception {
        BrandEntity mBrandEntity = new BrandEntity();
        mBrandEntity.setId(mERPBrandModel.getId());
        mBrandEntity.setName(mERPBrandModel.getName());
        mBrandEntity.setLogo(mERPBrandModel.getLogo());
        mBrandEntity.setContacts(mERPBrandModel.getContacts());
        mBrandEntity.setContactsPhone(mERPBrandModel.getContactsPhone());
        mBrandEntity.setContactsMail(mERPBrandModel.getContactsMail());
        mBrandEntity.setCommercialGroupAdress(mERPBrandModel.getCommercialGroupAdress());
        mBrandEntity.setStatus(mERPBrandModel.getStatus());
        mBrandEntity.setProvince(mERPBrandModel.getProvince());
        mBrandEntity.setCity(mERPBrandModel.getCity());
        mBrandEntity.setArea(mERPBrandModel.getArea());
        mBrandEntity.setUpdatorId(mERPBrandModel.getCreatorId());
        mBrandEntity.setUpdatorName(mERPBrandModel.getCreatorName());
        mBrandEntity.setServerUpdateTime(new Date());

        Boolean isSuccess = updateById(mBrandEntity);
        return isSuccess;
    }

}
