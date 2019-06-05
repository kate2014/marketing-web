package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.CustomerPrivilegeRuleModel;
import com.zhongmei.yunfu.domain.entity.CustomerPrivilegeRuleEntity;
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
public interface CustomerPrivilageRuleService extends IService<CustomerPrivilegeRuleEntity> {

    /**
     * 查询所有的会员权益数据
     * @param mCustomerPrivilageRuleModel
     * @return
     * @throws Exception
     */
    List<CustomerPrivilegeRuleEntity> queryAllRule(CustomerPrivilegeRuleModel mCustomerPrivilageRuleModel)throws Exception;

    /**
     * 添加会员权益
     * @param entity
     * @return
     * @throws Exception
     */
    boolean addRule(CustomerPrivilegeRuleEntity entity)throws Exception;

    /**
     * 批量添加
     * @param entity
     * @return
     * @throws Exception
     */
    boolean batchAddRule(List<CustomerPrivilegeRuleEntity> entity)throws Exception;

    /**
     * 根据Id删除数据
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteRuleById(Long id) throws Exception;

    /**
     * 更改规则设置
     * @param entity
     * @return
     * @throws Exception
     */
    boolean updateRule(CustomerPrivilegeRuleEntity entity)throws Exception;

    /**
     * 批量修改
     * @param listData
     * @return
     * @throws Exception
     */
    boolean batchUpdateRule(List<CustomerPrivilegeRuleEntity> listData)throws Exception;

    /**
     * 批量添加或修改
     * @param listData
     * @return
     * @throws Exception
     */
    boolean installOrUpdate(List<CustomerPrivilegeRuleEntity> listData)throws Exception;

    /**
     * 批量删除
     * @param brandIdenty
     * @param shopIdenty
     * @param ids
     * @return
     * @throws Exception
     */
    boolean batchDelete(Long brandIdenty,Long shopIdenty,String ids)throws Exception;
}
