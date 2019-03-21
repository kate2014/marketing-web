package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.api.ApiResponseStatus;
import com.zhongmei.yunfu.api.ApiResponseStatusException;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import com.zhongmei.yunfu.erp.model.ERPCommercialModel;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.enums.StatusFlag;
import com.zhongmei.yunfu.domain.mapper.CommercialMapper;
import com.zhongmei.yunfu.service.CommercialService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商户信息表 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Service
public class CommercialServiceImpl extends ServiceImpl<CommercialMapper, CommercialEntity> implements CommercialService {

    @Override
    public CommercialEntity selectByDevice(String device, boolean isCheckState) throws ApiResponseStatusException {
        CommercialEntity commercialEntity = baseMapper.selectByDevice(device);
        if (isCheckState) {
            if (commercialEntity == null) {
                throw new ApiResponseStatusException(ApiResponseStatus.SHOP_FOUND);
            }

            if (commercialEntity.getStatusFlag() == StatusFlag.INVALID.value()) {
                throw new ApiResponseStatusException(ApiResponseStatus.SHOP_INVALID);
            }

            if (commercialEntity.getStatus() == -1) {
                throw new ApiResponseStatusException(ApiResponseStatus.SHOP_DISABLED);
            }
        }
        return commercialEntity;
    }

    @Override
    public List<CommercialEntity> queryCommercialByBrandId(Long brandId) throws Exception {
        EntityWrapper<CommercialEntity> eWrapper = new EntityWrapper<>(new CommercialEntity());
        eWrapper.eq("brand_id", brandId);
        eWrapper.eq("status", 0);
        eWrapper.eq("invalid_status", 1);
        eWrapper.setSqlSelect("commercial_id,commercial_name,commercial_contact,commercial_phone,commercial_adress,commercial_desc,commercial_logo,branch_name,open_time,province,city,area,invalid_status");
        List<CommercialEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public Page<CommercialEntity> queryCommercialList(ShopSearchModel mShopSearchModel, int pageIdx, int pageSize) throws Exception {

        EntityWrapper<CommercialEntity> eWrapper = new EntityWrapper<>(new CommercialEntity());
        eWrapper.eq("brand_id", mShopSearchModel.getBrandIdenty());

        if(mShopSearchModel.getInvalidStatus() != null && !mShopSearchModel.getInvalidStatus().equals("")){
            eWrapper.eq("invalid_status", mShopSearchModel.getInvalidStatus());
        }
        if(mShopSearchModel.getCommercialName() != null && !mShopSearchModel.getCommercialName().equals("")){
            eWrapper.like("commercial_name", mShopSearchModel.getCommercialName());
        }
        if(mShopSearchModel.getProvince() != null && !mShopSearchModel.getProvince().equals("")){
            eWrapper.eq("province", mShopSearchModel.getProvince());
        }
        if(mShopSearchModel.getCity() != null && !mShopSearchModel.getCity().equals("")){
            eWrapper.eq("city", mShopSearchModel.getCity());
        }
        if(mShopSearchModel.getArea() != null && !mShopSearchModel.getArea().equals("")){
            eWrapper.eq("area", mShopSearchModel.getArea());
        }
        eWrapper.setSqlSelect("commercial_id,commercial_name,commercial_contact,commercial_phone,province,city,area,invalid_status");

        Page<CommercialEntity> listPage = new Page<>(pageIdx,pageSize);

        Page<CommercialEntity> listCommercail = selectPage(listPage,eWrapper);

        return listCommercail;
    }

    @Override
    public CommercialEntity queryCommercialById(Long commercialId) throws Exception {
        EntityWrapper<CommercialEntity> eWrapper = new EntityWrapper<>(new CommercialEntity());
        eWrapper.eq("commercial_id", commercialId);
        CommercialEntity mCommercialEntity = selectOne(eWrapper);

        return mCommercialEntity;
    }

    @Override
    public Boolean modifyCommercial(CommercialEntity mCommercialEntity) throws Exception {
        EntityWrapper<CommercialEntity> eWrapper = new EntityWrapper<>(new CommercialEntity());
        eWrapper.eq("commercial_id", mCommercialEntity.getCommercialId());
        Boolean isSuccess = update(mCommercialEntity, eWrapper);
        return isSuccess;
    }

    @Override
    public Page<CommercialEntity> queryCommercialList(ERPCommercialModel mCommercialModel, int pageIdx, int pageSize) throws Exception {

        EntityWrapper<CommercialEntity> eWrapper = new EntityWrapper<>(new CommercialEntity());

        if(mCommercialModel.getCommercialId() != null && !mCommercialModel.getCommercialId().equals("")){
            eWrapper.eq("commercial_id", mCommercialModel.getCommercialId());
        }
        if(mCommercialModel.getCommercialName() != null && !mCommercialModel.getCommercialName().equals("")){
            eWrapper.like("commercial_name", mCommercialModel.getCommercialName());
        }
        if(mCommercialModel.getProvince() != null && !mCommercialModel.getProvince().equals("")){
            eWrapper.eq("province", mCommercialModel.getProvince());
        }
        if(mCommercialModel.getCity() != null && !mCommercialModel.getCity().equals("")){
            eWrapper.eq("city", mCommercialModel.getCity());
        }
        if(mCommercialModel.getArea() != null && !mCommercialModel.getArea().equals("")){
            eWrapper.eq("area", mCommercialModel.getArea());
        }
        if(mCommercialModel.getStatus() != null && !mCommercialModel.getStatus().equals("")){
            eWrapper.eq("status", mCommercialModel.getStatus());
        }
        if(mCommercialModel.getInvalidStatus() != null && !mCommercialModel.getInvalidStatus().equals("")){
            eWrapper.eq("invalid_status", mCommercialModel.getInvalidStatus());
        }
        if(mCommercialModel.getBrandId() != null && !mCommercialModel.getBrandId().equals("")){
            eWrapper.eq("brand_id", mCommercialModel.getBrandId());
        }

        eWrapper.orderBy("server_create_time",false);
        Page<CommercialEntity> listPage = new Page<>(pageIdx,pageSize);

        Page<CommercialEntity> listCommercail = selectPage(listPage,eWrapper);

        return listCommercail;
    }

    @Override
    public Boolean createCommercial(CommercialEntity mCommercialEntity) throws Exception {

        Boolean isSuccess = insert(mCommercialEntity);
        return isSuccess;
    }

    @Override
    public Boolean deleteCommercial(ERPCommercialModel mCommercialModel) throws Exception {
        if(mCommercialModel.getCommercialId() != null && !mCommercialModel.equals("")){
            Boolean isSuccess = deleteById(mCommercialModel.getCommercialId());
            return isSuccess;
        }else{
            return false;
        }
    }


}
