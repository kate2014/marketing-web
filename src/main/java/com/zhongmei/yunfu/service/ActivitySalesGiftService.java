package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.controller.api.model.ActivityGiftEffectResp;
import com.zhongmei.yunfu.controller.model.ActivityEffectModel;
import com.zhongmei.yunfu.controller.model.CustomerGiftModel;
import com.zhongmei.yunfu.domain.entity.ActivitySalesEntity;
import com.zhongmei.yunfu.domain.entity.ActivitySalesGiftEntity;

import java.util.List;

/**
 * <p>
 * 特价活动 服务类
 * </p>
 */
public interface ActivitySalesGiftService extends IService<ActivitySalesGiftEntity> {

    /**
     * 添加门店活动礼品赠送规则
     * @param mActivitySalesGiftEntity
     * @return
     */
    ActivitySalesGiftEntity addActivityGift(ActivitySalesGiftEntity mActivitySalesGiftEntity) throws Exception;

    /**
     * 删除活动礼品赠送规则
     * @param id
     * @return
     */
    boolean deleteGiftById(Long id)throws Exception;

    /**
     * 根据活动id删除礼品设置
     * @param activityId
     * @return
     * @throws Exception
     */
    Boolean deleteByActivityId(Long activityId)throws Exception;

    /**
     * 查询活动下所以赠送规则
     * @return
     */
    List<ActivitySalesGiftEntity> queryListData(ActivitySalesGiftEntity mActivitySalesGiftEntity)throws Exception;

    /**
     * 获取活动礼品赠送情况
     * @param mActivityEffectModel
     * @return
     * @throws Exception
     */
    List<CustomerGiftModel> queryActivityGift(ActivityEffectModel mActivityEffectModel)throws Exception;

    /**
     * 获取会员参与获取礼品情况
     * @param mActivityEffectModel
     * @return
     * @throws Exception
     */
    List<ActivityGiftEffectResp> queryCustomerGift(ActivityEffectModel mActivityEffectModel)throws Exception;

}
