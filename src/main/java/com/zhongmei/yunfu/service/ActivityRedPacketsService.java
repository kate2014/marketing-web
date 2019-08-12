package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.ActivityRedPacketsEntity;

/**
 * <p>
 * 特价活动 红包规则
 * </p>
 */
public interface ActivityRedPacketsService extends IService<ActivityRedPacketsEntity> {

    /**
     * 添加规则
     * @param mActivityRedPacketsEntity
     * @return
     * @throws Exception
     */
    Boolean addRedPacketsRule(ActivityRedPacketsEntity mActivityRedPacketsEntity)throws Exception;

    /**
     * 新增或编辑数据
     * @param mActivityRedPacketsEntity
     * @return
     * @throws Exception
     */
    Boolean addOrUpdateRule(ActivityRedPacketsEntity mActivityRedPacketsEntity)throws Exception;

    /**
     * 查询规则
     * @param mActivityRedPacketsEntity
     * @return
     * @throws Exception
     */
    ActivityRedPacketsEntity queryRule(ActivityRedPacketsEntity mActivityRedPacketsEntity)throws Exception;

    /**
     * 根据活动id删除红包赠送规则
     * @param activityId
     * @return
     * @throws Exception
     */
    Boolean deleteByActivityId(Long activityId)throws Exception;
}
