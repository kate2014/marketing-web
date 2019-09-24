package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.api.pos.vo.CustomerECardSaveReq;
import com.zhongmei.yunfu.api.pos.vo.CustomerLoginReq;
import com.zhongmei.yunfu.api.pos.vo.CustomerSearchReq;
import com.zhongmei.yunfu.controller.model.CustomerDrainSearchModel;
import com.zhongmei.yunfu.controller.model.CustomerModel;
import com.zhongmei.yunfu.controller.model.CustomerSearchModel;
import com.zhongmei.yunfu.domain.bean.CustomerDrain;
import com.zhongmei.yunfu.domain.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
public interface CustomerService extends IService<CustomerEntity> {

    //CustomerInfo selectByKey(Serializable key);

    void save(CustomerEntity customerEntity);

    CustomerEntityCardEntity saveEntityCard(CustomerECardSaveReq req) throws Exception;

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

    //Page<CustomerEntity> findListPage(Page<CustomerEntity> page, CustomerSearchModel search);

    Page<CustomerDrain> findCustomerByDrain(CustomerDrainSearchModel searchModel);

    //Page<CustomerEntity> findListPage(CustomerDrainSearchModel searchModel, Page<CustomerEntity> page);

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
     * 根据电话号码获取微信会员信息
     * @param brandIdenty
     * @param shopIdenty
     * @param mobile
     * @return
     * @throws Exception
     */
    CustomerEntity queryWxCustomerByMobile(Long brandIdenty, Long shopIdenty, String mobile) throws Exception;

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
     * @param shopId
     * @param loginType
     * @param loginId
     * @param isNeedPwd
     * @param password
     * @return
     * @throws Exception
     */
    CustomerEntity login(Long shopId, CustomerLoginReq.LoginType loginType, String loginId, boolean isNeedPwd, String password) throws Exception;

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
    Integer selectCountByTrade(Long shop_identy, Integer recentDay, Integer tradeCount, Integer tradeCountMax, Integer tradeAmountSum, Integer tradeAmountSumMax);

    Page<CustomerEntity> selectByTrade(CustomerSearchModel searchModel, Integer recentDay, Integer tradeCount, Integer tradeCountMax, Integer tradeAmountSum, Integer tradeAmountSumMax);

    //Page<CustomerEntity> selectByTrade(Page<CustomerEntity> page, CustomerSearchModel searchModel, Integer recentDay, Integer tradeCount, Integer tradeAmountSum);

    /**
     * 统计最近几天将要过生日的会员
     *
     * @param shop_identy
     * @param recentDay
     * @return
     */
    Integer selectCountByBirthday(Long shop_identy, Integer recentDay);

    Page<CustomerEntity> selectByBirthday(CustomerSearchModel searchModel, Integer recentDay);

    //Page<CustomerEntity> selectByBirthday(Page<CustomerEntity> page, CustomerSearchModel searchModel, Integer recentDay);

    /**
     * 统计最近几天注册的新会员
     *
     * @param shop_identy
     * @return
     */
    Integer selectCountByNewMember(Long shop_identy, Integer recentDay);

    Page<CustomerEntity> selectByNewMember(CustomerSearchModel searchModel, Integer recentDay);

    //Page<CustomerEntity> selectByNewMember(Page<CustomerEntity> page, CustomerSearchModel searchModel, Integer recentDay);

    /**
     * 统计最近将要满周年的会员
     *
     * @param shop_identy
     * @return
     */
    Integer selectCountByAnniversary(Long shop_identy, Integer recentDay);

    Page<CustomerEntity> selectByAnniversary(CustomerSearchModel searchModel, Integer recentDay);

    //Page<CustomerEntity> selectByAnniversary(Page<CustomerEntity> page, CustomerSearchModel searchModel, Integer recentDay);

    boolean existsMobile(Long shopId, String mobile, Long id);

    /**
     * 查询门店所有会员
     *
     * @param brandIdenty
     * @param shopIdenty
     * @return
     * @throws Exception
     */
    public List<CustomerEntity> queryAllCustomer(Long brandIdenty, Long shopIdenty) throws Exception;

    /**
     * 获取会员到店统计报表
     *
     * @param mCustomerModel
     * @return
     * @throws Exception
     */
    List<CustomerReport> customerShopReport(CustomerModel mCustomerModel) throws Exception;

    /**
     * 获取会员到店统计详情报表
     *
     * @param mCustomerModel
     * @return
     * @throws Exception
     */
    List<CustomerReport> customerShopDetailReport(CustomerModel mCustomerModel) throws Exception;

    /**
     * 根据会员ID查询扩展表信息
     *
     * @param customerId
     * @return
     */
    CustomerExtraEntity getCustomerExtra(Long customerId);

    List<CustomerEntity> excelImportCustomer(MultipartFile file, Long shopId) throws Exception;

    /**
     * 查询门店会员储值、赠送、消费金额
     * @param brandIdenty
     * @param shopIdenty
     * @return
     * @throws Exception
     */
    CustomerExtraEntity queryCustomerSaveReport(Long brandIdenty, Long shopIdenty)throws Exception;

    /**
     * 更新会员信息
     * @param mCustomerEntity
     * @return
     * @throws Exception
     */
    Boolean midfityCustomer(CustomerEntity mCustomerEntity)throws Exception;

    /**
     * 根据会员微信会员记录Id查询会员基本信息
     * @param mCustomerEntity
     * @return
     * @throws Exception
     */
    Map<String,String> queryByWxCustomerId(CustomerEntity mCustomerEntity)throws Exception;
}
