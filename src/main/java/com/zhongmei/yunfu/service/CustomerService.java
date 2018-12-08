package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.api.pos.vo.CustomerLoginReq;
import com.zhongmei.yunfu.api.pos.vo.CustomerSearchReq;
import com.zhongmei.yunfu.controller.model.CustomerDrainSearchModel;
import com.zhongmei.yunfu.controller.model.CustomerSearchModel;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerReport;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-29
 */
public interface CustomerService extends IService<CustomerEntity> {

    /**
     * 是否校验顾客状态
     *
     * @param customerId
     * @param isCheckState
     * @return
     * @throws Exception
     */
    CustomerEntity getCustomerEntity(Long customerId, boolean isCheckState) throws Exception;

    /**
     * 根据手机号获取顾客信息
     *
     * @param shopIdenty
     * @param mobile
     * @return
     * @throws Exception
     */
    CustomerEntity getCustomerByMobile(Long shopIdenty, String mobile) throws Exception;

    /**
     * 检查状态
     *
     * @param customerEntity
     * @throws Exception
     */
    void checkState(CustomerEntity customerEntity) throws Exception;

    /**
     * 获取会员关联的相关id
     *
     * @param id
     * @return
     */
    Collection<Long> getCustomerIdsById(Long id);

    /**
     * 根据ID查询所有关联会员的实体
     *
     * @param id
     * @param sqlSelect
     * @return
     */
    List<CustomerEntity> getCustomerRelationById(Long id, String sqlSelect);

    Page<CustomerEntity> findListPage(CustomerSearchReq search);

    /**
     * 查询门店会员总数
     *
     * @param shopIdenty
     * @return
     */
    int selectCountByShop(Long shopIdenty);

    Page<CustomerEntity> findListPage(CustomerSearchModel searchModel, int pageSize);

    Page<CustomerEntity> findListPage(CustomerDrainSearchModel searchModel);

    /**
     * 添加会员
     *
     * @param mCustomer
     * @return
     */
    Boolean addCustomer(CustomerEntity mCustomer) throws Exception;

    /**
     * 根据绑定手机号
     *
     * @param mCustomer
     * @return
     */
    Boolean bindCustomerByMobile(CustomerEntity mCustomer) throws Exception;

    /**
     * 解除绑定
     *
     * @param mCustomer
     * @return
     * @throws Exception
     */
    Boolean relieveBind(CustomerEntity mCustomer) throws Exception;

    /**
     * 根据会员mobile查询会员信息
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param thirdId
     * @return
     * @throws Exception
     */
    CustomerEntity queryCustomerByThirdId(Long brandIdenty, Long shopIdenty, String thirdId) throws Exception;

    /**
     * 根据会员mobile查询会员信息
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param mobile
     * @return
     * @throws Exception
     */
    CustomerEntity queryCustomerByMobile(Long brandIdenty, Long shopIdenty, String mobile) throws Exception;

    /**
     * 查询电话号码绑定的微信账号
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param customerId
     * @return
     * @throws Exception
     */
    CustomerEntity queryCustomerByRelateId(Long brandIdenty, Long shopIdenty, Long customerId) throws Exception;

    /**
     * 根据会员Id获取会员信息
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param customerId
     * @return
     * @throws Exception
     */
    CustomerEntity queryCustomerById(Long brandIdenty, Long shopIdenty, Long customerId) throws Exception;

    /**
     * 获取时间段内的会员增长情况
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param start
     * @param end
     * @return
     */
    public List<CustomerReport> queryCustomerReport(Long brandIdenty, Long shopIdenty, Date start, Date end, Integer sourceId) throws Exception;

    /**
     * 获取时间点会员总数
     *
     * @param brandIdenty
     * @param shopIdenty
     * @param end
     * @return
     */
    public Integer queryCustomerCount(Long brandIdenty, Long shopIdenty, Date end, Integer sourceId) throws Exception;

    /**
     * 会员信息
     *
     * @param loginType
     * @param loginId
     * @param password
     * @param shopId
     */
    CustomerEntity login(CustomerLoginReq.LoginType loginType, String loginId, String password, boolean isNeedPwd, Long shopId) throws Exception;

    /**
     * 会员消费报表
     *
     * @param customerId
     */
    Map<String, String> expenseReport(Long customerId);

    /**
     * 统计最近一段时间内交易次数大于等于目标值或交易金额大于等于目标金额
     *
     * @param shop_identy
     * @param recentDay
     * @param tradeCount
     * @param tradeAmountSum
     * @return
     */
    Integer selectCountByTrade(Long shop_identy, Integer recentDay, Integer tradeCount, Integer tradeAmountSum);

    Page<CustomerEntity> selectByTrade(CustomerSearchModel searchModel, Integer recentDay, Integer tradeCount, Integer tradeAmountSum);

    /**
     * 统计最近几天将要过生日的会员
     *
     * @param shop_identy
     * @param recentDay
     * @return
     */
    Integer selectCountByBirthday(Long shop_identy, Integer recentDay);

    Page<CustomerEntity> selectByBirthday(CustomerSearchModel searchModel, Integer recentDay);

    /**
     * 统计最近几天注册的新会员
     *
     * @param shop_identy
     * @return
     */
    Integer selectCountByNewMember(Long shop_identy, Integer recentDay);

    Page<CustomerEntity> selectByNewMember(CustomerSearchModel searchModel, Integer recentDay);

    /**
     * 统计最近将要满周年的会员
     *
     * @param shop_identy
     * @return
     */
    Integer selectCountByAnniversary(Long shop_identy, Integer recentDay);

    Page<CustomerEntity> selectByAnniversary(CustomerSearchModel searchModel, Integer recentDay);
}
