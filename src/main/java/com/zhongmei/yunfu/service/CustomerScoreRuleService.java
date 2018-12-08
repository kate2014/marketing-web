package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.CustomerScoreRuleModel;
import com.zhongmei.yunfu.domain.entity.CustomerScoreRuleEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员等级积分成长规则、积分使用规则 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-07
 */
public interface CustomerScoreRuleService extends IService<CustomerScoreRuleEntity> {

    /**
     * 获取会员等级积分成长规则、积分使用规则数据
     *
     * @param map
     * @return
     */
    List<CustomerScoreRuleEntity> findScoreRule(Map<String, Object> map);

    /**
     * 编辑会员等级积分成长规则、积分使用规则
     *
     * @param mCustomerScoreRuleMode
     * @return
     */
    Boolean modifyScoreRule(CustomerScoreRuleModel mCustomerScoreRuleMode) throws Exception;

}
