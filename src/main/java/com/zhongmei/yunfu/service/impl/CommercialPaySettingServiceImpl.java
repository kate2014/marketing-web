package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.domain.entity.CommercialPaySettingEntity;
import com.zhongmei.yunfu.domain.mapper.CommercialPaySettingMapper;
import com.zhongmei.yunfu.service.CommercialPaySettingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商户线上支付配置 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-26
 */
@Service
public class CommercialPaySettingServiceImpl extends ServiceImpl<CommercialPaySettingMapper, CommercialPaySettingEntity> implements CommercialPaySettingService {

    @Override
    public Boolean installData(CommercialPaySettingEntity mCommercialPaySetting) throws Exception {
        Boolean isSuccess = insert(mCommercialPaySetting);
        return isSuccess;
    }

    @Override
    public Boolean updateData(CommercialPaySettingEntity mCommercialPaySetting) throws Exception {
        EntityWrapper<CommercialPaySettingEntity> eWrapper = new EntityWrapper<>(new CommercialPaySettingEntity());
        eWrapper.eq("brand_identy",mCommercialPaySetting.getBrandIdenty());
        eWrapper.eq("shop_identy",mCommercialPaySetting.getShopIdenty());
        eWrapper.eq("type",mCommercialPaySetting.getType());
        Boolean isSuccess = update(mCommercialPaySetting,eWrapper);
        return isSuccess;
    }

    @Override
    public CommercialPaySettingEntity queryData(CommercialPaySettingEntity mCommercialPaySetting) throws Exception {
        EntityWrapper<CommercialPaySettingEntity> eWrapper = new EntityWrapper<>(new CommercialPaySettingEntity());
        eWrapper.eq("brand_identy",mCommercialPaySetting.getBrandIdenty());
        eWrapper.eq("shop_identy",mCommercialPaySetting.getShopIdenty());
        eWrapper.eq("status_flag",mCommercialPaySetting.getStatusFlag());
        eWrapper.eq("type",mCommercialPaySetting.getType());
        CommercialPaySettingEntity settingData = selectOne(eWrapper);

        return settingData;
    }

    @Override
    public List<CommercialPaySettingEntity> queryDataList(CommercialPaySettingEntity mCommercialPaySetting) throws Exception {
        EntityWrapper<CommercialPaySettingEntity> eWrapper = new EntityWrapper<>(new CommercialPaySettingEntity());
        eWrapper.eq("brand_identy",mCommercialPaySetting.getBrandIdenty());
        eWrapper.eq("shop_identy",mCommercialPaySetting.getShopIdenty());
        eWrapper.eq("status_flag",mCommercialPaySetting.getStatusFlag());
        List<CommercialPaySettingEntity> listData = selectList(eWrapper);
        return listData;
    }
}
