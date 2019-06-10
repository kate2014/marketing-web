package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.model.CommercailSettingModel;
import com.zhongmei.yunfu.domain.entity.CommercialCustomSettingsEntity;
import com.zhongmei.yunfu.domain.mapper.CommercialCustomSettingsMapper;
import com.zhongmei.yunfu.service.CommercialCustomSettingsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 商户设置表 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-26
 */
@Service
public class CommercialCustomSettingsServiceImpl extends ServiceImpl<CommercialCustomSettingsMapper, CommercialCustomSettingsEntity> implements CommercialCustomSettingsService {

    @Override
    public Boolean installSetting(CommercialCustomSettingsEntity mCommercialCustomSettingsEntity)  throws Exception{

        Boolean isSuccess = insert(mCommercialCustomSettingsEntity);
        return isSuccess;
    }

    @Override
    public Boolean modfitySettingbyKey(CommercialCustomSettingsEntity mCommercialCustomSettingsEntity)  throws Exception{

        mCommercialCustomSettingsEntity.setServerUpdateTime(new Date());
        EntityWrapper<CommercialCustomSettingsEntity> eWrapper = new EntityWrapper<>(new CommercialCustomSettingsEntity());

        eWrapper.eq("brand_identy", mCommercialCustomSettingsEntity.getBrandIdenty());
        eWrapper.eq("shop_identy", mCommercialCustomSettingsEntity.getShopIdenty());
        eWrapper.eq("setting_key", mCommercialCustomSettingsEntity.getSettingKey());

        Boolean isSuccess = update(mCommercialCustomSettingsEntity,eWrapper);
        return isSuccess;
    }

    @Override
    public Boolean modfityById(CommercialCustomSettingsEntity mCommercialCustomSettingsEntity)  throws Exception{
        Boolean isSuccess = updateById(mCommercialCustomSettingsEntity);
        return isSuccess;
    }

    @Override
    public CommercialCustomSettingsEntity queryByKey(CommercailSettingModel mCommercailSettingModel)  throws Exception{
        EntityWrapper<CommercialCustomSettingsEntity> eWrapper = new EntityWrapper<>(new CommercialCustomSettingsEntity());
        eWrapper.eq("brand_identy", mCommercailSettingModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mCommercailSettingModel.getShopIdenty());
        eWrapper.eq("setting_key", mCommercailSettingModel.getSettingKey());
        eWrapper.eq("status_flag", 1);
        CommercialCustomSettingsEntity mCommercialCustomSettingsEntity = selectOne(eWrapper);
        return mCommercialCustomSettingsEntity;
    }

    @Override
    public boolean installOrUpdate(CommercialCustomSettingsEntity mCommercialCustomSettingsEntity) throws Exception {

        boolean isSuccess = insertOrUpdate(mCommercialCustomSettingsEntity);
        return isSuccess;
    }
}
