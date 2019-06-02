package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.controller.model.CustomerSearchSettingVo;
import com.zhongmei.yunfu.domain.entity.CustomerSearchRuleEntity;

/**
 * <p>
 * 会员查询规则表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-11-08
 */
public interface CustomerSearchRuleService extends IService<CustomerSearchRuleEntity> {

    /**
     * 根据门店获取当前配置的search规则
     *
     * @param shopId
     */
    CustomerSearchRuleEntity selectByShopId(Long brandId, Long shopId);

    boolean insertEntity(CustomerSearchSettingVo settingVo);

}
