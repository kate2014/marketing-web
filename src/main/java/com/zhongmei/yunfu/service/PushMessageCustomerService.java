package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.PushMessageCustomerEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 推送信息给顾客 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-13
 */
public interface PushMessageCustomerService extends IService<PushMessageCustomerEntity> {

    /**
     * 添加推送
     *
     * @param mPushMessageCustomer
     * @return
     * @throws Exception
     */
    Boolean installPush(PushMessageCustomerEntity mPushMessageCustomer) throws Exception;
    /**
     * 批量插入会员推送信息
     * @param type 推送类型：1：新品活动推送  2：门店活动推送  3：优惠券推送
     * @param listCustomerID  会员列表
     * @param relationId 活动、优惠券、新品id
     * @param creatorId
     *@param creatorName
     * @return
     * @throws Exception
     */
    Boolean installPushBatch(Integer type, List<Long> listCustomerID, Long relationId, Long brandIdentity, Long shopIdentity, Long creatorId, String creatorName) throws Exception;
    /**
     * 更改推送信息为已读
     *
     * @param pushId
     * @param state
     * @return
     * @throws Exception
     */
    Boolean modifyPushState(Long pushId, int state) throws Exception;

    /**
     * 获取会员未推送完成信息
     *
     * @param brandIdentity
     * @param shopIdentity
     * @param customerId
     * @return
     * @throws Exception
     */
    List<PushMessageCustomerEntity> queryPushByCustomer(Long brandIdentity, Long shopIdentity, String customerId) throws Exception;
}
