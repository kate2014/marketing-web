package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.controller.model.ActivityEffectModel;
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
     * 根据操作记录id编辑操作记录
     * @param entity
     * @return
     * @throws Exception
     */
    Boolean modiftyById(OperationalRecordsEntity entity)throws Exception;
    /**
     * 查询活动的效果信息
     * @param entity
     * @return
     * @throws Exception
     */
    Page<OperationalRecordsEntity> queryByActivityId(OperationalRecordsEntity entity,int pageNo, int pageSize)throws Exception;

    /**
     * 查询活动查看、分享、购买数据
     * @param entity
     * @return
     * @throws Exception
     */
    List<OperationalRecordsEntity> queryDataByActivityId(OperationalRecordsEntity entity)throws Exception;
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

    /**
     * 查询用户对该信息的操作记录
     * @param entity
     * @return
     * @throws Exception
     */
    OperationalRecordsEntity queryByCustomer(OperationalRecordsEntity entity)throws Exception;

    /**
     * 查询活动效果统计
     * @param entity
     * @return
     * @throws Exception
     */
    List<OperationalRecordsEntity> queryEffectCount(OperationalRecordsEntity entity)throws Exception;

    /**
     * 获取活动售卖情况
     * @param entity
     * @return
     * @throws Exception
     */
    List<OperationalRecordsEntity> querySalesEffect(OperationalRecordsEntity entity)throws Exception;

    /**
     * 获取活动参与情况，如拼团时则统计所以参与过拼团人信息
     * @param entity
     * @return
     * @throws Exception
     */
    List<OperationalRecordsEntity> queryJoinEffect(OperationalRecordsEntity entity)throws Exception;
}
