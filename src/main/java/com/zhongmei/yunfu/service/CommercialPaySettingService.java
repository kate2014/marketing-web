package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.CommercialPaySettingEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 商户线上支付配置 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-26
 */
public interface CommercialPaySettingService extends IService<CommercialPaySettingEntity> {

    /**
     * 插入设置
     * @param mCommercialPaySetting
     * @return
     * @throws Exception
     */
    Boolean installData(CommercialPaySettingEntity mCommercialPaySetting)throws Exception;

    /**
     * 更新设置
     * @param mCommercialPaySetting
     * @return
     * @throws Exception
     */
    Boolean updateData(CommercialPaySettingEntity mCommercialPaySetting)throws Exception;

    /**
     * 查询设置
     * @param mCommercialPaySetting
     * @return
     * @throws Exception
     */
    CommercialPaySettingEntity queryData(CommercialPaySettingEntity mCommercialPaySetting)throws Exception;

    /**
     * 查询商家所有配置信息
     * @param mCommercialPaySetting
     * @return
     * @throws Exception
     */
    List<CommercialPaySettingEntity> queryDataList(CommercialPaySettingEntity mCommercialPaySetting)throws Exception;

}
