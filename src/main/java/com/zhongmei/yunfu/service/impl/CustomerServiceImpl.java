package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.api.ApiResponseStatus;
import com.zhongmei.yunfu.api.ApiResponseStatusException;
import com.zhongmei.yunfu.api.pos.vo.CustomerLoginReq;
import com.zhongmei.yunfu.api.pos.vo.CustomerSearchReq;
import com.zhongmei.yunfu.controller.model.CustomerDrainSearchModel;
import com.zhongmei.yunfu.controller.model.CustomerSearchModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.core.mybatis.mapper.EntityWrapperFilter;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerReport;
import com.zhongmei.yunfu.domain.enums.EnabledFlag;
import com.zhongmei.yunfu.domain.enums.StatusFlag;
import com.zhongmei.yunfu.domain.mapper.CustomerMapper;
import com.zhongmei.yunfu.service.CouponService;
import com.zhongmei.yunfu.service.CustomerCardTimeService;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.TradeService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, CustomerEntity> implements CustomerService {

    @Autowired
    CustomerCardTimeService customerCardTimeService;

    @Autowired
    CouponService couponService;

    @Autowired
    TradeService tradeService;

    @Override
    public CustomerEntity getCustomerEntity(Long customerId, boolean isCheckState) throws Exception {
        CustomerEntity customerEntity = selectById(customerId);
        if (isCheckState) {
            //checkState(customerEntity);
        }
        return customerEntity;
    }

    @Override
    public CustomerEntity getCustomerByMobile(Long shopIdenty, String mobile) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setShopIdenty(shopIdenty);
        customerEntity.setMobile(mobile);
        //Wrapper<CustomerEntity> entityWrapper = new EntityWrapper<>(customerEntity).in("mobile", mobile);
        return selectOne(new EntityWrapper<>(customerEntity));
    }

    @Override
    public void checkState(CustomerEntity customerEntity) throws Exception {
        if (customerEntity == null) {
            throw new ApiResponseStatusException(ApiResponseStatus.CUSTOMER_FOUND);
        }
        if (customerEntity.getStatusFlag() == StatusFlag.INVALID.value()) {
            throw new ApiResponseStatusException(ApiResponseStatus.CUSTOMER_INVALID);
        }
        if (customerEntity.getEnabledFlag() == EnabledFlag.DISABLED.value()) {
            throw new ApiResponseStatusException(ApiResponseStatus.CUSTOMER_DISABLED);
        }
    }

    @Override
    public Collection<Long> getCustomerIdsById(Long id) {
        List<CustomerEntity> customerEntityIdsList = getCustomerRelationById(id, "id");
        Set<Long> customerIds = new HashSet<>();
        for (CustomerEntity entity : customerEntityIdsList) {
            customerIds.add(entity.getId());
        }

        return customerIds;
    }

    @Override
    public List<CustomerEntity> getCustomerRelationById(Long id, String sqlSelect) {
        Wrapper<CustomerEntity> customerEntityWrapper = new EntityWrapper<CustomerEntity>()
                .setSqlSelect(sqlSelect)
                .eq("id", id)
                .or()
                .eq("relate_id", id);

        return selectList(customerEntityWrapper);
    }

    @Override
    public Page<CustomerEntity> findListPage(CustomerSearchReq search) {
        CustomerEntity coupon = new CustomerEntity();
        coupon.setRelateId(0L); //表示主会员记录
        coupon.setShopIdenty(search.getHeader().getShopId());
        EntityWrapper condition = new EntityWrapperFilter<>(coupon);
        //condition.setSqlSelect()
        condition.like("name", search.getNameOrMobile(), SqlLike.RIGHT);
        condition.or();
        condition.like("mobile", search.getNameOrMobile(), SqlLike.RIGHT);
        Page<CustomerEntity> page = new Page<>(search.getPageNo(), search.getPageSize());
        //page.setRecords(baseMapper.findByPage(page, condition));
        return selectPage(page, condition);
    }

    @Override
    public int selectCountByShop(Long shopIdenty) {
        CustomerEntity coupon = new CustomerEntity();
        coupon.setRelateId(0L); //表示主会员记录
        coupon.setShopIdenty(shopIdenty);
        EntityWrapper<CustomerEntity> eWrapper = new EntityWrapperFilter<>(coupon);
        return super.selectCount(eWrapper);
    }

    @Override
    public Page<CustomerEntity> findListPage(CustomerSearchModel searchModel, int pageSize) {
        CustomerEntity coupon = new CustomerEntity();
        coupon.setRelateId(0L); //表示主会员记录
        coupon.setShopIdenty(searchModel.getUser().getShopIdenty());
        coupon.setSourceId(searchModel.getSourceId());
        coupon.setGroupLevelId(searchModel.getGroupLevelId());
        //coupon.setName(searchModel.getName());
        Page<CustomerEntity> page = new Page<>(searchModel.getPageNo(), pageSize);
        EntityWrapper<CustomerEntity> eWrapper = new EntityWrapperFilter<>(coupon);
        eWrapper.like("name", searchModel.getName(), SqlLike.RIGHT);
        eWrapper.like("mobile", searchModel.getMobile(), SqlLike.RIGHT);
        eWrapper.orderBy("consumption_last_time", false);
        Page<CustomerEntity> roleDOList = selectPage(page, eWrapper);
        return roleDOList;
    }

    @Override
    public Page<CustomerEntity> findListPage(CustomerDrainSearchModel searchModel) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setRelateId(0L);
        customerEntity.setShopIdenty(searchModel.getUser().getShopIdenty());
        EntityWrapperFilter eWrapper = new EntityWrapperFilter<>(customerEntity);
        //eWrapper.eq("relate_id", 0L);
        eWrapper.gt("stored_balance", searchModel.getStoredBalance());
        eWrapper.gt("card_residue_count", searchModel.getCardResidueCount());
        eWrapper.gt("consumption_last_time", searchModel.getConsumptionLastTime());
        Page<CustomerEntity> page = new Page<>(searchModel.getPageNo(), searchModel.getPageSize());
        return selectPage(page, eWrapper);
    }

    @Override
    public Boolean addCustomer(CustomerEntity mCustomer) throws Exception {
        Boolean isSuccess = insert(mCustomer);
        return isSuccess;
    }

    @Override
    public Boolean bindCustomerByMobile(CustomerEntity mCustomer) throws Exception {
        EntityWrapper<CustomerEntity> eWrapper = new EntityWrapper<>(new CustomerEntity());

        mCustomer.setServerUpdateTime(new Date());
        eWrapper.eq("brand_identy", mCustomer.getBrandIdenty());
        eWrapper.eq("shop_identy", mCustomer.getShopIdenty());
        if (mCustomer.getId() != null) {
            eWrapper.eq("id", mCustomer.getId());
        }
        //一定要添加判断，避免更新全部会员
        if (mCustomer.getThirdId() != null) {
            eWrapper.eq("third_id", mCustomer.getThirdId());
            Boolean isSuccess = update(mCustomer, eWrapper);
            return isSuccess;
        }

        return false;
    }

    @Override
    public Boolean relieveBind(CustomerEntity mCustomer) throws Exception {
        EntityWrapper<CustomerEntity> eWrapper = new EntityWrapper<>(new CustomerEntity());
        CustomerEntity cus = new CustomerEntity();
        cus.setRelateId(0l);
        cus.setServerUpdateTime(new Date());

        eWrapper.eq("brand_identy", mCustomer.getBrandIdenty());
        eWrapper.eq("shop_identy", mCustomer.getShopIdenty());
        //一定要添加判断，避免更新全部会员
        if (mCustomer.getThirdId() != null) {
            eWrapper.eq("third_id", mCustomer.getThirdId());
            Boolean isSuccess = update(cus, eWrapper);
            return isSuccess;
        }
        return false;

    }

    @Override
    public CustomerEntity queryCustomerByThirdId(Long brandIdenty, Long shopIdenty, String thirdId) throws Exception {
        EntityWrapper<CustomerEntity> eWrapper = new EntityWrapper<>(new CustomerEntity());
        eWrapper.eq("brand_identy", brandIdenty);
        eWrapper.eq("shop_identy", shopIdenty);
        eWrapper.eq("third_id", thirdId);
        eWrapper.setSqlSelect("id,relate_id,name,gender,mobile,group_level,consumption_last_time,stored_balance,password,relate_id,third_id,brand_identy,shop_identy,status_flag");
        CustomerEntity mCustomer = selectOne(eWrapper);
        return mCustomer;
    }

    @Override
    public CustomerEntity queryCustomerByMobile(Long brandIdenty, Long shopIdenty, String mobile) throws Exception {
        EntityWrapper<CustomerEntity> eWrapper = new EntityWrapper<>(new CustomerEntity());
        eWrapper.eq("brand_identy", brandIdenty);
        eWrapper.eq("shop_identy", shopIdenty);
        eWrapper.eq("mobile", mobile);
        eWrapper.setSqlSelect("id,relate_id,name,gender,mobile,group_level,consumption_last_time,stored_balance,password,relate_id,third_id,brand_identy,shop_identy,status_flag");
        CustomerEntity mCustomer = selectOne(eWrapper);
        return mCustomer;
    }

    @Override
    public CustomerEntity queryCustomerByRelateId(Long brandIdenty, Long shopIdenty, Long customerId) throws Exception {
        EntityWrapper<CustomerEntity> eWrapper = new EntityWrapper<>(new CustomerEntity());
        eWrapper.eq("brand_identy", brandIdenty);
        eWrapper.eq("shop_identy", shopIdenty);
        eWrapper.eq("relate_id", customerId);
        eWrapper.setSqlSelect("id,relate_id,name,gender,mobile,group_level,consumption_last_time,stored_balance,password,relate_id,third_id,brand_identy,shop_identy,status_flag");
        CustomerEntity mCustomer = selectOne(eWrapper);
        return mCustomer;
    }

    @Override
    public CustomerEntity queryCustomerById(Long brandIdenty, Long shopIdenty, Long id) throws Exception {
        EntityWrapper<CustomerEntity> eWrapper = new EntityWrapper<>(new CustomerEntity());
        eWrapper.eq("brand_identy", brandIdenty);
        eWrapper.eq("shop_identy", shopIdenty);
        eWrapper.eq("id", id);
        eWrapper.setSqlSelect("id,relate_id,name,gender,mobile,group_level,group_level_id,consumption_last_time,stored_balance,password,relate_id,third_id,brand_identy,shop_identy,status_flag");
        CustomerEntity mCustomer = selectOne(eWrapper);
        return mCustomer;
    }

    @Override
    public List<CustomerReport> queryCustomerReport(Long brandIdenty, Long shopIdenty, Date start, Date end, Integer sourceId) throws Exception {

        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", brandIdenty);
        eWrapper.eq("shop_identy", shopIdenty);
        eWrapper.eq("source_id", sourceId);
        eWrapper.eq("enabled_flag", 1);
        eWrapper.eq("status_flag", 1);
        eWrapper.between("server_create_time", start, end);

        List<CustomerReport> listCustomerReport = baseMapper.queryCustomerGroup(eWrapper);

        return listCustomerReport;
    }

    @Override
    public Integer queryCustomerCount(Long brandIdenty, Long shopIdenty, Date end, Integer sourceId) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", brandIdenty);
        eWrapper.eq("shop_identy", shopIdenty);
        eWrapper.eq("enabled_flag", 1);
        eWrapper.eq("status_flag", 1);
        eWrapper.eq("source_id", sourceId);
        Date start = DateFormatUtil.parseDate("2018-08-08", "yyyy-MM-dd");
        eWrapper.between("server_create_time", start, end);

        Integer customerCount = baseMapper.queryCustomerCount(eWrapper);

        return customerCount;
    }

    @Override
    public CustomerEntity login(CustomerLoginReq.LoginType loginType, String loginId, String password, boolean isNeedPwd, Long shopId) throws Exception {
        CustomerEntity customerEntity = null;
        switch (loginType) {
            case MOBILE:
                customerEntity = loginMobile(loginId, shopId);
                break;
            case MEMBER_ID:
                customerEntity = loginCustomerId(loginId, shopId);
                break;
            case WECHAT_OPENID:
                customerEntity = loginWxOpenId(loginId, shopId);
                break;
        }

        if (isNeedPwd && !password.equals(customerEntity.getPassword())) {
            throw new ApiResponseStatusException(ApiResponseStatus.CUSTOMER_ERROR_PASSWORD);
        }

        return customerEntity;

        //获取关联的同id
        /*Collection<Long> customerIdsById = getCustomerIdsById(customerEntity.getId());

        //获取优惠券信息
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setStatusFlag(1);
        Wrapper<CouponEntity> couponEntityWrapper = new EntityWrapper<>(couponEntity)
                .in("customer_id", customerIdsById);
        List<CouponEntity> couponEntities = couponService.selectList(couponEntityWrapper);

        //获取次卡信息
        CustomerCardTimeEntity customerCardTimeEntity = new CustomerCardTimeEntity();
        customerCardTimeEntity.setCustomerId(customerEntity.getId());
        customerCardTimeEntity.setStatusFlag(1);
        EntityWrapper<CustomerCardTimeEntity> cardTimeEntityEntityWrapper = new EntityWrapper<>(customerCardTimeEntity);
        List<CustomerCardTimeEntity> customerCardTimeEntitieList = customerCardTimeService.selectList(cardTimeEntityEntityWrapper);*/


    }

    private CustomerEntity loginMobile(String loginId, Long shopId) throws Exception {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setShopIdenty(shopId);
        customerEntity.setMobile(loginId);
        return selectOne(new EntityWrapper<>(customerEntity));
    }

    private CustomerEntity loginCustomerId(String loginId, Long shopId) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setShopIdenty(shopId);
        customerEntity.setId(Long.parseLong(loginId));
        return selectOne(new EntityWrapper<>(customerEntity));
    }

    private CustomerEntity loginWxOpenId(String loginId, Long shopId) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setShopIdenty(shopId);
        customerEntity.setThirdId(loginId);
        return selectOne(new EntityWrapper<>(customerEntity));
    }

    @Override
    public Map<String, String> expenseReport(Long customerId) {
        Collection<Long> customerIdsById = getCustomerIdsById(customerId);
        Map<String, String> stringStringMap = tradeService.queryCustomerExpenseList(customerIdsById);
        return stringStringMap;
    }

    @Override
    public Integer selectCountByTrade(Long shop_identy, Integer recentDay, Integer tradeCount, Integer tradeAmountSum) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -recentDay);
        String format = DateFormatUtil.format(calendar.getTime(), DateFormatUtil.FORMAT_DATE) + " 00:00:00";
        return baseMapper.selectCountByTrade(shop_identy, format, tradeCount, tradeAmountSum);
    }

    @Override
    public Page<CustomerEntity> selectByTrade(CustomerSearchModel searchModel, Integer recentDay, Integer tradeCount, Integer tradeAmountSum) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -recentDay);
        String format = DateFormatUtil.format(calendar.getTime(), DateFormatUtil.FORMAT_DATE) + " 00:00:00";

        //CustomerEntity customerEntity = new CustomerEntity();
        //customerEntity.setRelateId(0L);
        //customerEntity.setShopIdenty(searchModel.getUser().getShopIdenty());
        EntityWrapper<CustomerEntity> wrapper = new EntityWrapperFilter<>();
        wrapper.eq("c.source_id", searchModel.getSourceId());
        wrapper.eq("c.group_level_id", searchModel.getGroupLevelId());
        wrapper.like("c.name", searchModel.getName(), SqlLike.RIGHT);
        wrapper.like("c.mobile", searchModel.getMobile(), SqlLike.RIGHT);
        wrapper.orderBy("c.consumption_last_time", false);
        Page<CustomerEntity> page = new Page<>(searchModel.getPageNo(), searchModel.getPageSize());
        wrapper = (EntityWrapper<CustomerEntity>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.selectByTrade(page, wrapper, searchModel.getUser().getShopIdenty(), format, tradeCount, tradeAmountSum));
        return page;
    }

    @Override
    public Integer selectCountByBirthday(Long shop_identy, Integer recentDay) {
        int betweenRight2 = recentDay - 365;
        return baseMapper.selectCountByBirthday(shop_identy, recentDay, betweenRight2);
    }

    // "WHERE shop_identy = ${shopId} AND relate_id = 0"
    @Override
    public Page<CustomerEntity> selectByBirthday(CustomerSearchModel searchModel, Integer recentDay) {
        int betweenRight2 = recentDay - 365;
        //CustomerEntity customerEntity = new CustomerEntity();
        //customerEntity.setRelateId(0L);
        //customerEntity.setShopIdenty(searchModel.getUser().getShopIdenty());
        EntityWrapper<CustomerEntity> wrapper = new EntityWrapperFilter<>();
        wrapper.eq("c.source_id", searchModel.getSourceId());
        wrapper.eq("c.group_level_id", searchModel.getGroupLevelId());
        wrapper.like("c.name", searchModel.getName(), SqlLike.RIGHT);
        wrapper.like("c.mobile", searchModel.getMobile(), SqlLike.RIGHT);
        wrapper.orderBy("c.consumption_last_time", false);
        Page<CustomerEntity> page = new Page<>(searchModel.getPageNo(), searchModel.getPageSize());
        wrapper = (EntityWrapper<CustomerEntity>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.selectByBirthday(page, wrapper, searchModel.getUser().getShopIdenty(), recentDay, betweenRight2));
        return page;
    }

    @Override
    public Integer selectCountByNewMember(Long shop_identy, Integer recentDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -recentDay);
        String format = DateFormatUtil.format(calendar.getTime(), DateFormatUtil.FORMAT_DATE) + " 00:00:00";
        return baseMapper.selectCountByNewMember(shop_identy, format);
    }

    //shop_identy = ${shopId} AND relate_id = 0 AND server_create_time >= #{serverCreateTime}
    @Override
    public Page<CustomerEntity> selectByNewMember(CustomerSearchModel searchModel, Integer recentDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -recentDay);
        String format = DateFormatUtil.format(calendar.getTime(), DateFormatUtil.FORMAT_DATE) + " 00:00:00";
        EntityWrapper<CustomerEntity> wrapper = new EntityWrapperFilter<>(new CustomerEntity());
        wrapper.eq("c.source_id", searchModel.getSourceId());
        wrapper.eq("c.group_level_id", searchModel.getGroupLevelId());
        wrapper.like("c.name", searchModel.getName(), SqlLike.RIGHT);
        wrapper.like("c.mobile", searchModel.getMobile(), SqlLike.RIGHT);
        wrapper.orderBy("c.consumption_last_time", false);
        Page<CustomerEntity> page = new Page<>(searchModel.getPageNo(), searchModel.getPageSize());
        //return selectPage(page, wrapper);
        wrapper = (EntityWrapper<CustomerEntity>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.selectByNewMember(page, wrapper, searchModel.getUser().getShopIdenty(), format));
        return page;
    }

    @Override
    public Integer selectCountByAnniversary(Long shop_identy, Integer recentDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        String startTime = DateFormatUtil.format(calendar.getTime(), DateFormatUtil.FORMAT_DATE) + " 00:00:00";
        calendar.add(Calendar.DAY_OF_MONTH, recentDay);
        String endTime = DateFormatUtil.format(calendar.getTime(), DateFormatUtil.FORMAT_DATE) + " 00:00:00";
        return baseMapper.selectCountByAnniversary(shop_identy, startTime, endTime);
    }

    //shop_identy = ${shopId} AND relate_id = 0 AND server_create_time >= #{startTime} AND server_create_time <= #{endTime}
    @Override
    public Page<CustomerEntity> selectByAnniversary(CustomerSearchModel searchModel, Integer recentDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        String startTime = DateFormatUtil.format(calendar.getTime(), DateFormatUtil.FORMAT_DATE) + " 00:00:00";
        calendar.add(Calendar.DAY_OF_MONTH, recentDay);
        String endTime = DateFormatUtil.format(calendar.getTime(), DateFormatUtil.FORMAT_DATE) + " 00:00:00";

        EntityWrapper<CustomerEntity> wrapper = new EntityWrapperFilter<>(new CustomerEntity());
        wrapper.eq("c.source_id", searchModel.getSourceId());
        wrapper.eq("c.group_level_id", searchModel.getGroupLevelId());
        wrapper.like("c.name", searchModel.getName(), SqlLike.RIGHT);
        wrapper.like("c.mobile", searchModel.getMobile(), SqlLike.RIGHT);
        wrapper.orderBy("c.consumption_last_time", false);
        Page<CustomerEntity> page = new Page<>(searchModel.getPageNo(), searchModel.getPageSize());
        //return selectPage(page, wrapper);
        wrapper = (EntityWrapper<CustomerEntity>) SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.selectByAnniversary(page, wrapper, searchModel.getUser().getShopIdenty(), startTime, endTime));
        return page;
    }
}
