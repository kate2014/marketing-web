package com.zhongmei.yunfu.api.pos;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.api.ApiRespStatus;
import com.zhongmei.yunfu.api.ApiRespStatusException;
import com.zhongmei.yunfu.api.ApiResult;
import com.zhongmei.yunfu.api.PosApiController;
import com.zhongmei.yunfu.api.pos.vo.*;
import com.zhongmei.yunfu.core.security.Password;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.domain.enums.*;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.service.vo.CustomerCouponVo;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pos/customer")
public class CustomerPosApi extends PosApiController {

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerCardTimeService customerCardTimeService;
    @Autowired
    CustomerLevelRuleService customerLevelRuleService;
    @Autowired
    CustomerEntityCardService customerEntityCardService;

    @RequestMapping
    public ApiResult list(@RequestBody CustomerSearchReq search) throws ApiRespStatusException {
        Page<CustomerEntity> listPage = customerService.findListPage(search);
        List<CustomerListResp> result = new ArrayList<>();
        for (CustomerEntity customerEntity : listPage.getRecords()) {
            CustomerListResp customerListResp1 = new CustomerListResp();
            customerListResp1.customerId = customerEntity.getId();
            customerListResp1.name = customerEntity.getName();
            customerListResp1.sex = customerEntity.getGender();
            customerListResp1.mobile = customerEntity.getMobile();
            customerListResp1.levelId = customerEntity.getGroupLevelId();
            customerListResp1.levelName = customerEntity.getGroupLevel();
            customerListResp1.source = customerEntity.getSourceId();
            customerListResp1.sourceName = ValueEnums.toEnum(SourceId.class, customerEntity.getSourceId()).getDesc();
            customerListResp1.isDisable = (customerEntity.getEnabledFlag() == EnabledFlag.DISABLED.value()) ? 1 : 2;
            result.add(customerListResp1);
        }

        return ApiResult.newSuccess(result, listPage);
    }

    @RequestMapping("/info")
    public ApiResult info(@RequestBody CustomerInfoReq req) throws ApiRespStatusException {
        CustomerEntity customer = customerService.selectById(req.getCustomerId());
        CustomerInfoResp customerResp = new CustomerInfoResp();
        customerResp.customerId = customer.getId();
        customerResp.customerName = customer.getName();
        customerResp.name = customer.getName();
        customerResp.sex = customer.getGender();
        customerResp.birthday = DateFormatUtil.formatDate(customer.getBirthday());
        customerResp.mobile = customer.getMobile();
        customerResp.hobby = customer.getHobby();
        customerResp.address = customer.getAddress();
        customerResp.isDisable = (customer.getEnabledFlag() == EnabledFlag.DISABLED.value()) ? 1 : 2;
        customerResp.levelId = customer.getGroupLevelId();
        customerResp.level = 0L;
        customerResp.levelName = customer.getGroupLevel();
        customerResp.remainValue = customer.getStoredBalance();
        customerResp.integral = customer.getIntegralTotal() - customer.getIntegralUsed();
        customerResp.memo = customer.getProfile();
        int couponCount = customerCouponService.selectCouponEntityCount(customer.getId(), customer.getShopIdenty());
        int cardTimeCount = customerCardTimeService.selectCount(customer.getId(), customer.getShopIdenty());
        customerResp.coupCount = couponCount;
        customerResp.cardCount = cardTimeCount;

        List<CustomerEntityCardEntity> entityCardServiceByCustomerId = customerEntityCardService.getByCustomerId(customer.getId());
        customerResp.entityCards = new ArrayList<>();
        entityCardServiceByCustomerId.forEach(it -> {
            CustomerInfoResp.EntityCard entityCard = new CustomerInfoResp.EntityCard();
            entityCard.cardId = it.getId();
            entityCard.cardNo = it.getCardNo();
            customerResp.entityCards.add(entityCard);
        });


        return ApiResult.newSuccess(customerResp);
    }

    @RequestMapping("/save")
    public ApiResult save(@RequestBody CustomerSaveReq req) throws Exception {
        CustomerEntity mCustomer = new CustomerEntity();
        //mCustomer.setUuid(req);
        mCustomer.setName(req.getName());
        mCustomer.setGender(req.getSex());
        mCustomer.setMobile(req.getMobile());
        mCustomer.setBirthday(DateFormatUtil.parseDate(req.getBirthday(), DateFormatUtil.FORMAT_DATE));
        mCustomer.setHobby(req.getEnvironmentHobby());
        mCustomer.setAddress(req.getAddress());
        mCustomer.setProfile(req.getMemo());

        if (req.getCustomerId() == null) {
            if (StringUtils.isNotBlank(req.getMobile())) {
                //判断在数据库里是否存在
                if (customerService.existsMobile(req.getHeader().getShopId(), req.getMobile(), req.getCustomerId())) {
                    throw new ApiRespStatusException(ApiRespStatus.CUSTOMER_MOBILE_INVALID);
                }
            }

            String consumePassword = req.getConsumePassword();
            if (StringUtils.isNotBlank(consumePassword)) {
                consumePassword = Password.create().generate(req.getMobile(), consumePassword);
            }
            CustomerLevelRuleEntity levelRuleEntity = customerLevelRuleService.getCustomerLevelRuleEntity(req.getHeader().getShopId(), req.getHeader().getBrandId(), 0);
            mCustomer.baseCreate(req.getUserId(), req.getUserName());
            mCustomer.setPassword(consumePassword);
            mCustomer.setIntegralTotal(0);
            mCustomer.setGroupLevelId(levelRuleEntity.getId());
            mCustomer.setGroupLevel(levelRuleEntity.getLevelName());
            mCustomer.setSourceId(CustomerSourceId.POS.value());
            mCustomer.setStoredBalance(BigDecimal.ZERO);
            mCustomer.setShopIdenty(req.getHeader().getShopId());
            mCustomer.setBrandIdenty(req.getHeader().getBrandId());
            mCustomer.setEnabledFlag(EnabledFlag.ENABLED.value());
            customerService.save(mCustomer);
        } else {
            CustomerEntity current = customerService.selectById(req.getCustomerId());
            if (current != null && !StringUtils.equals(current.getMobile(), req.getMobile())) {
                if (StringUtils.isNotBlank(req.getMobile())) {
                    //判断在数据库里是否存在
                    if (customerService.existsMobile(req.getHeader().getShopId(), req.getMobile(), req.getCustomerId())) {
                        throw new ApiRespStatusException(ApiRespStatus.CUSTOMER_MOBILE_INVALID);
                    }
                }
            }
            mCustomer.baseUpdate(req.getUserId(), req.getUserName());
            mCustomer.setId(req.getCustomerId());
            customerService.save(mCustomer);
        }

        return ApiResult.newSuccess(mCustomer);
    }

    @Autowired
    CustomerIntegralService customerIntegralService;

    @RequestMapping("/integral")
    public ApiResult integral(@RequestBody CustomerIntegralReq req) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(req.getCustomerId(), true);

        CustomerIntegralEntity customerIntegralEntity = new CustomerIntegralEntity();
        customerIntegralEntity.setBrandIdenty(req.getHeader().getBrandId());
        customerIntegralEntity.setShopIdenty(req.getHeader().getShopId());
        customerIntegralEntity.setCustomerId(req.getCustomerId());
        EntityWrapper<CustomerIntegralEntity> integralEntityEntityWrapper = new EntityWrapper<>(customerIntegralEntity);
        integralEntityEntityWrapper.gt("trade_integral", 0);
        integralEntityEntityWrapper.orderBy("server_update_time", false);
        Page<CustomerIntegralEntity> listPage = customerIntegralService.selectPage(new Page<>(req.getPageNo(), req.getPageSize()), integralEntityEntityWrapper);

        CustomerIntegralResp integralResp = new CustomerIntegralResp();
        integralResp.setAggregateCount(customerEntity.getIntegralTotal());
        integralResp.setIntegralCount(customerEntity.getIntegralTotal() - customerEntity.getIntegralUsed());
        integralResp.setItems(new ArrayList<>());
        for (CustomerIntegralEntity integralEntity : listPage.getRecords()) {
            CustomerIntegralResp.NewIntegralRecord newIntegralRecord = new CustomerIntegralResp.NewIntegralRecord();
            newIntegralRecord.setId(integralEntity.getId());
            newIntegralRecord.setRecordType(integralEntity.getRecordType());
            newIntegralRecord.setBeforeIntegral(integralEntity.getCurrentIntegral());
            newIntegralRecord.setAddIntegral(integralEntity.getTradeIntegral());
            newIntegralRecord.setEndIntegral(integralEntity.getResidueIntegral());
            newIntegralRecord.setCreateDateTime(integralEntity.getServerCreateTime().getTime());
            newIntegralRecord.setModifyDateTime(integralEntity.getServerUpdateTime().getTime());
            newIntegralRecord.setStatus(integralEntity.getStatusFlag());
            newIntegralRecord.setReason(integralEntity.getTradeReason());
            newIntegralRecord.setUserName(integralEntity.getCreatorName());
            integralResp.getItems().add(newIntegralRecord);
        }

        return ApiResult.newSuccess(integralResp, listPage);
    }

    @Autowired
    CustomerStoredService customerStoredService;

    @RequestMapping("/balance")
    public Object balance(@RequestBody CustomerStoredBalanceReq req) throws Exception {
        CustomerStoredEntity customerStoredEntity = new CustomerStoredEntity();
        customerStoredEntity.setBrandIdenty(req.getHeader().getBrandId());
        customerStoredEntity.setShopIdenty(req.getHeader().getShopId());
        customerStoredEntity.setCustomerId(req.getCustomerId());
        EntityWrapper<CustomerStoredEntity> storedEntityEntityWrapper = new EntityWrapper<>(customerStoredEntity);
        storedEntityEntityWrapper.orderBy("server_Create_Time", false);
        Page<CustomerStoredEntity> listPage = customerStoredService.selectPage(new Page<>(req.getPageNo(), req.getPageSize()), storedEntityEntityWrapper);
        List<CustomerStoredBalanceResp> result = new ArrayList<>();
        for (CustomerStoredEntity storedEntity : listPage.getRecords()) {
            CustomerStoredBalanceResp balanceResp = new CustomerStoredBalanceResp();
            balanceResp.setType(storedEntity.getRecordType()); //0：充值，1：消费退款
            balanceResp.setSource(1);// 来源，1为Calm
            balanceResp.setUserId(storedEntity.getCustomerId());// 操作员账号
            balanceResp.setUserName(storedEntity.getCreatorName());
            balanceResp.setCreateDateTime(storedEntity.getServerCreateTime().getTime());
            balanceResp.setAddValuecard(storedEntity.getTradeAmount());
            balanceResp.setEndValuecard(storedEntity.getResidueBalance());
            result.add(balanceResp);
        }

        return ApiResult.newSuccess(result, listPage);
    }

    @Autowired
    CustomerCouponService customerCouponService;
    @Autowired
    CouponService couponService;

    @RequestMapping("/coups")
    public Object coups(@RequestBody CustomerStoredBalanceReq req) throws Exception {
        List<CustomerCouponVo> couponEntities = customerCouponService.getCouponEntityBy(req.getHeader().getShopId(), req.getCustomerId());
        return ApiResult.newSuccess(couponEntities);
    }

    @Autowired
    TradeService tradeService;

    @RequestMapping("/expense")
    public Object expense(@RequestBody CustomerStoredBalanceReq req) throws Exception {
        List<TradeEntity> couponEntities = tradeService.getTradeEntityBy(req.getCustomerId(), req.getHeader().getShopId());
        List<CustomerExpenseRecordResp> expenseRecordRespList = new ArrayList<>();
        for (TradeEntity entity : couponEntities) {
            CustomerExpenseRecordResp recordResp = new CustomerExpenseRecordResp();
            recordResp.setId(entity.getId());
            recordResp.setModifyDateTime(entity.getServerUpdateTime().getTime());// 修改时间
            recordResp.setTradeType(ValueEnums.toEnum(TradeTypeEnum.class, entity.getTradeType()).getDesc()); //记录类型1储值、2消费
            recordResp.setTradeId(entity.getId());
            recordResp.setTradeNo(String.format("编号: %s", entity.getTradeNo())); //交易值
            recordResp.setTradeValue(entity.getTradeAmount());//剩余值
            recordResp.setUserId(entity.getCreatorName());// 操作员
            expenseRecordRespList.add(recordResp);
        }
        return ApiResult.newSuccess(expenseRecordRespList);
    }
}
