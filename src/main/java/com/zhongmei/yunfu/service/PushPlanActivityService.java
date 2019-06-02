package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.ActivitySearchModel;
import com.zhongmei.yunfu.domain.entity.PushPlanActivityEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 活动推送活动方案 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface PushPlanActivityService extends IService<PushPlanActivityEntity> {

    /**
     * 获取推广活动列表（支持分页）
     *
     * @param planState
     * @param name
     * @param pageIdx
     * @param pageSize
     * @return
     */
    Page<PushPlanActivityEntity> findListPage(Long brandIdentity, Long shopIdentity, Integer planState, String name,Integer sourceType, int pageIdx, int pageSize);

    Page<PushPlanActivityEntity> findListPage(ActivitySearchModel searchModel);


    /**
     * 根据id获取活动详情
     *
     * @param activityId
     * @return
     */
    PushPlanActivityEntity findActivityById(Long activityId);

    /**
     * 根据id获取活动基本信息
     * @param activityId
     * @return
     */
    PushPlanActivityEntity findActivityDetailById(Long activityId);
    /**
     * 添加活动信息
     *
     * @param mPushPlanActivity
     * @return
     */
    Boolean addActivity(PushPlanActivityEntity mPushPlanActivity);

    /**
     * 修改活动信息
     *
     * @return
     */
    Boolean modifyActivity(PushPlanActivityEntity mPushPlanActivity);

    /**
     * 修改浏览、分享数量
     *
     * @param id
     * @param scanNumber
     * @param shareNumber
     * @return
     */
    Boolean updateActivityNumber(Long id, Integer scanNumber, Integer shareNumber) throws Exception;

    /**
     * 修改活动方案状态
     *
     * @param id
     * @param planState
     * @return
     */
    Boolean updateActivityState(Long id, Integer planState, Long creatorId,String creatorname);

    /**
     * 删除活动
     *
     * @param activityId
     * @return
     */
    Boolean deleteActivity(Long activityId);

    /**
     * 根据sourceId获取投放门店的活动
     * @param brandIdenty
     * @param sourceId
     * @return
     * @throws Exception
     */
    List<PushPlanActivityEntity> queryListBySourceId(Long brandIdenty,Long sourceId)throws Exception;

    /**
     * 根据SourceId更新状态
     * @param brandIdenty
     * @param sourceId
     * @return
     * @throws Exception
     */
    boolean modiftyStateBySource(Long brandIdenty,Long sourceId,Integer state)throws Exception;

    /**
     * 根据sourceId批量更新
     * @param entity
     * @param ids
     * @return
     * @throws Exception
     */
    boolean batchModiftyBySource(PushPlanActivityEntity entity,String ids) throws Exception;

    /**
     * 批量插入
     * @param listData
     * @return
     * @throws Exception
     */
    boolean batchAdd(List<PushPlanActivityEntity> listData) throws Exception;

    /**
     * 批量删除
     * @param ids
     * @return
     * @throws Exception
     */
    boolean batchDelete(List<Long> ids)throws Exception;

    /**
     * 根据sourceId删除数据
     * @param brandIdenty
     * @param sourceId
     * @return
     * @throws Exception
     */
    boolean batchBySourceId(Long brandIdenty,Long sourceId)throws Exception;
}
