package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.api.ApiResponseStatus;
import com.zhongmei.yunfu.api.ApiResponseStatusException;
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
        eWrapper.setSqlSelect("commercial_id,commercial_name,commercial_contact,commercial_phone,commercial_adress,commercial_desc,commercial_logo,branch_name,open_time");
        List<CommercialEntity> listData = selectList(eWrapper);
        return listData;
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


}
