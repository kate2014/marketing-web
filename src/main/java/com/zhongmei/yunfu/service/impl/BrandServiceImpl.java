package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.erp.model.ERPBrandModel;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.mapper.BrandMapper;
import com.zhongmei.yunfu.service.BrandService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
        eWrapper.eq("status", mERPBrandModel.getStatus());

        Page<BrandEntity> listPage = new Page<>(mERPBrandModel.getPageNo(),mERPBrandModel.getPageSize());

        Page<BrandEntity> listBrand = selectPage(listPage,eWrapper);

        return listBrand;
    }

}
