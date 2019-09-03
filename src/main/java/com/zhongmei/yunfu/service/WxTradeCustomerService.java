package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CollageReportModel;
import com.zhongmei.yunfu.controller.model.ReportMarketingModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.WxTradeCustomerEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 微信小程序购买使用记录 服务类
 * </p>
 *
 * @author yangyp
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
     * 根据relateId获取信息
     * @param relateId
     * @return
     */
    WxTradeCustomerEntity queryByRelateId(Long relateId)throws Exception;

    /**
     * 查询会员在该门店下购买的微信小程序服务
     * @param mWxTradeCustomer
     * @return
     */
    List<WxTradeCustomerEntity> queryListDataByCustomerId(WxTradeCustomerEntity mWxTradeCustomer)throws Exception;

    /**
     * 查询活动的购买情况
     * @param mWxTradeCustomer
     * @return
     * @throws Exception
     */
    List<WxTradeCustomerEntity> queryListByActivity(WxTradeCustomerEntity mWxTradeCustomer)throws Exception;

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

    /**
     * 获取拼团活动购买、使用情况
     * @param mReportMarketingModel
     * @return
     * @throws Exception
     */
    List<CollageReportModel> queryCollageData(ReportMarketingModel mReportMarketingModel)throws Exception;

    /**
     * 查询活动售卖量
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    Integer querySalesCount(TradeModel mTradeModel)throws Exception;

    /**
     * 查询特价活动售卖情况
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    public List<WxTradeCustomerEntity> querySalseDetail(TradeModel mTradeModel)throws Exception;

    /**
     * 查询活动购买数量
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    public Integer querySalseCount(TradeModel mTradeModel)throws Exception;

    /**
     * 查询活动售卖情况
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    public List<WxTradeCustomerEntity> querySalesList(TradeModel mTradeModel)throws Exception;

    /**
     * 根据code获取用户购买的活动
     * @param mWxTradeCustomer
     * @return
     * @throws Exception
     */
    public WxTradeCustomerEntity queryWxTradeByCode(WxTradeCustomerEntity mWxTradeCustomer)throws Exception;

    /**
     * 获取门店验证码，用来判断唯一性
     * @param brandIdenty
     * @param shopIdenty
     * @param code
     * @return
     * @throws Exception
     */
    public Integer queryCountByCode(Long brandIdenty, Long shopIdenty,String code)throws Exception;

    /**
     * 活动报表
     * @param mReportMarketingModel
     * @return
     * @throws Exception
     */
    public List<WxTradeCustomerEntity> queryReport(ReportMarketingModel mReportMarketingModel, Integer status)throws Exception;

    /**
     * 查询所以未到期活动
     * @param mReportMarketingModel
     * @return
     * @throws Exception
     */
    public Page<WxTradeCustomerEntity> queryAllMarketing(ReportMarketingModel mReportMarketingModel)throws Exception;

    /**
     * 查收门店所有活动数据，可用于数据导出
     * @param mReportMarketingModel
     * @return
     * @throws Exception
     */
    public List<WxTradeCustomerEntity> queryAllData(ReportMarketingModel mReportMarketingModel)throws Exception;
}
