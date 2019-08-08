package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;

import java.util.List;


/**
 * <p>
 * 活动操作记录，查看、分享、购买
 * </p>
 */
public interface OperationalRecordsService extends IService<OperationalRecordsEntity> {

    /**
     * 添加会员操作记录
     * @return
     * @throws Exception
     */
    Boolean addOperational(OperationalRecordsEntity entity)throws Exception;

    /**
     * 编辑会员操作记录
     * @param entity
     * @return
     * @throws Exception
     */
    Boolean modiftyOperational(OperationalRecordsEntity entity)throws Exception;

    /**
     * 查询活动的分享信息
     * @param entity
     * @return
     * @throws Exception
     */
    Page<OperationalRecordsEntity> queryByActivityId(OperationalRecordsEntity entity,int pageNo, int pageSize)throws Exception;

    /**
     * 查询活动对应的用户
     * @param entity
     * @return
     * @throws Exception
     */
    List<OperationalRecordsEntity> queryCustomer(OperationalRecordsEntity entity)throws Exception;
    /**
     * 删除活动相关的操作记录
     * @param entity
     * @return
     * @throws Exception
     */
    Boolean deleteByAction(OperationalRecordsEntity entity)throws Exception;

}
