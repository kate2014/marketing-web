package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.CustomerPrivilageRuleModel;
import com.zhongmei.yunfu.domain.entity.CustomerPrivilageRuleEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 会员权益设置表 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2019-06-04
 */
public interface CustomerPrivilageRuleService extends IService<CustomerPrivilageRuleEntity> {

    /**
     * 查询所有的会员权益数据
     * @param mCustomerPrivilageRuleModel
     * @return
     * @throws Exception
     */
    List<CustomerPrivilageRuleEntity> queryAllRule(CustomerPrivilageRuleModel mCustomerPrivilageRuleModel)throws Exception;

    /**
     * 添加会员权益
     * @param entity
     * @return
     * @throws Exception
     */
    boolean addRule(CustomerPrivilageRuleEntity entity)throws Exception;

    /**
     * 根据Id删除数据
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteRuleById(Long id) throws Exception;

    /**
     * 更改规则设置
     * @param CustomerPrivilageRuleEntity
     * @return
     * @throws Exception
     */
    boolean updateRule(CustomerPrivilageRuleEntity CustomerPrivilageRuleEntity)throws Exception;
}
