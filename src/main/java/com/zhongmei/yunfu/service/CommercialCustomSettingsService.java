package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.CommercailSettingModel;
import com.zhongmei.yunfu.domain.entity.CommercialCustomSettingsEntity;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 商户设置表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-26
 */
public interface CommercialCustomSettingsService extends IService<CommercialCustomSettingsEntity> {

    /**
     * 添加设置
     * @param mCommercialCustomSettingsEntity
     * @return
     */
    Boolean installSetting(CommercialCustomSettingsEntity mCommercialCustomSettingsEntity) throws Exception;

    /**
     * 根据Key编辑设置
     * @param mCommercialCustomSettingsEntity
     * @return
     */
    Boolean modfitySettingbyKey(CommercialCustomSettingsEntity mCommercialCustomSettingsEntity) throws Exception;

    /**
     * 根本Id编辑设置
     * @param mCommercialCustomSettingsEntity
     * @return
     */
    Boolean modfityById(CommercialCustomSettingsEntity mCommercialCustomSettingsEntity) throws Exception;

    /**
     * 查询设置信息
     * @param mCommercailSettingModel
     * @return
     */
    CommercialCustomSettingsEntity queryByKey(CommercailSettingModel mCommercailSettingModel) throws Exception;
}
