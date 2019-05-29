package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.NewDishPushSearchModel;
import com.zhongmei.yunfu.domain.entity.PushPlanNewDishEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 新品推送方案 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface PushPlanNewDishService extends IService<PushPlanNewDishEntity> {

    /**
     * 根据关键字查询新品推送方案
     *
     * @param model
     * @return
     */
    public Page<PushPlanNewDishEntity> list(NewDishPushSearchModel model);

    /**
     * 新增新品推送计划
     *
     * @param newDishPushPlan
     * @return
     */
    public boolean addNewDishPushPlan(PushPlanNewDishEntity newDishPushPlan);

    /**
     * 批量添加新品推送
     * @param listNewDishPushPlan
     * @return
     * @throws Exception
     */
    public boolean batchAddNewDishPushPlan(List<PushPlanNewDishEntity> listNewDishPushPlan)throws Exception;

    /**
     * 批量删除
     * @param ids
     * @return
     * @throws Exception
     */
    public boolean batchDeleteDishPushPlan(List<Long> ids)throws Exception;

    /**
     * 批量更新
     * @param listNewDishPushPlan
     * @return
     * @throws Exception
     */
    public boolean batchUpdateDishPushPlan(List<PushPlanNewDishEntity> listNewDishPushPlan)throws Exception;

    /**
     * 更新商品推送计划
     *
     * @param newDishPushPlan
     * @return
     */
    public boolean updateNewDishPushPlan(PushPlanNewDishEntity newDishPushPlan);

    /**
     * 删除新品推送计划
     *
     * @param id
     * @return
     */
    public boolean deleteNewDishPushPlan(Long id);

    /**
     * 根据id查询新品推送
     *
     * @param id
     * @return
     */
    public PushPlanNewDishEntity queryByid(Long id);

    /**
     * 根据id获取新品活动基本信息
     * @param id
     * @return
     */
    public PushPlanNewDishEntity queryDetailById(Long id);

    /**
     * 删除新品推送计划
     *
     * @param id
     * @return
     */
    public boolean enableNewDishPushPlan(Long id, int planState);


    public int getDataCount(String where);


    /**
     * 更新分享次数，每次增加1
     *
     * @param id
     * @return
     */
    public boolean refreshShareNumber(Long id);

    /**
     * 获取该活动下发的所以门店活动
     * @param sourceId
     * @return
     * @throws Exception
     */
    public List<PushPlanNewDishEntity> queryBySourceId(Long brandIdentity,Long sourceId) throws Exception;

}
