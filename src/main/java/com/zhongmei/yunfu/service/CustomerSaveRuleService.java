package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.CustomerSaveRuleEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 会员储值规则表 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2019-06-04
 */
public interface CustomerSaveRuleService extends IService<CustomerSaveRuleEntity> {

    /**
     * 查询门店下所以的储值规则
     * @param mCustomerSaveRuleEntity
     * @return
     * @throws Exception
     */
    public List<CustomerSaveRuleEntity> queryAllRule(CustomerSaveRuleEntity mCustomerSaveRuleEntity)throws Exception;

    /**
     * 添加储赠规则
     * @param mCustomerSaveRuleEntity
     * @return
     * @throws Exception
     */
    public boolean addSaveRule(CustomerSaveRuleEntity mCustomerSaveRuleEntity)throws Exception;

    /**
     * 删除储增规则
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deleteRuleById(Long id)throws Exception;

}
