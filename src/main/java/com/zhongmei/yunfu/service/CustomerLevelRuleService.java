package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.CustomerLevelRuleModel;
import com.zhongmei.yunfu.domain.entity.CustomerLevelRuleEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 会员等级积分表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-07
 */
public interface CustomerLevelRuleService extends IService<CustomerLevelRuleEntity> {

    /**
     * 编辑会员等级积分规则
     *
     * @param mCustomerLevelRuleModel
     * @return
     */
    Boolean modifyCustomerLevelRule(CustomerLevelRuleModel mCustomerLevelRuleModel) throws Exception;

    /**
     * 查询会员等级规则
     *
     * @param mCustomerLevelRuleModel
     * @return
     */
    List<CustomerLevelRuleEntity> queryRuleData(CustomerLevelRuleModel mCustomerLevelRuleModel) throws Exception;

    /**
     * 根据积分获取会员等级
     *
     * @param shopIdenty
     * @param brandIdenty
     * @param integral
     * @return
     */
    CustomerLevelRuleEntity getCustomerLevelRuleEntity(Long shopIdenty, Long brandIdenty, int integral);

    /**
     * @param score
     * @return
     * @throws Exception
     */
    CustomerLevelRuleEntity queryLevelByScore(Long shopIdenty, Long brandIdenty, Integer score) throws Exception;
}
