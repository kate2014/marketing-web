package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.WxTradeCustomerEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 微信小程序购买使用记录 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-17
 */
public interface WxTradeCustomerService extends IService<WxTradeCustomerEntity> {

    /**
     * 添加会员小程序服务购买记录
     *
     * @param mWxTradeCustomer
     * @return
     */
    Boolean addWxTradeCustomer(WxTradeCustomerEntity mWxTradeCustomer) throws Exception;

    /**
     * 更改会员小程序使用状态
     *
     * @param mWxTradeCustomer
     * @return
     * @throws Exception
     */
    Boolean modfiyWxTradeCustomer(WxTradeCustomerEntity mWxTradeCustomer) throws Exception;

    /**
     * 更改会员小程序购买服务生效状态
     *
     * @param mWxTradeCustomer
     * @return
     * @throws Exception
     */
    Boolean modfiyEnabledFlag(WxTradeCustomerEntity mWxTradeCustomer) throws Exception;

    /**
     * 根据id查询出小程序购买服务详情
     *
     * @param id
     * @return
     */
    WxTradeCustomerEntity queryDetailById(Long id);

    /**
     * 获取基本信息
     * @param trade
     * @return
     */
    WxTradeCustomerEntity queryByTradeId(Long trade)throws Exception;

    /**
     * 查询会员在该门店下购买的微信小程序服务
     * @param mWxTradeCustomer
     * @return
     */
    List<WxTradeCustomerEntity> queryListDataByCustomerId(WxTradeCustomerEntity mWxTradeCustomer)throws Exception;

    /**
     * 验证是否能参与活动
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    String checkMarketingVaild(TradeModel mTradeModel)throws Exception;

    /**
     * 获取同一活动同一会员参与次数
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    Integer queryJoinCountByCustomer(TradeModel mTradeModel) throws Exception;

    /**
     * 根据开团id更新参团信息
     * @param collageMainId
     * @return
     * @throws Exception
     */
    Boolean modfiyEnabledFlagByMainCollage(Long collageMainId)throws Exception;

}
