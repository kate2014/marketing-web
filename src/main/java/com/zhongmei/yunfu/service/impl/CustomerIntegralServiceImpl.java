package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.api.ApiResponseStatus;
import com.zhongmei.yunfu.api.ApiResponseStatusException;
import com.zhongmei.yunfu.api.pos.vo.CustomerIntegralTradeReq;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerIntegralEntity;
import com.zhongmei.yunfu.domain.entity.CustomerLevelRuleEntity;
import com.zhongmei.yunfu.domain.enums.RecordType;
import com.zhongmei.yunfu.domain.mapper.CustomerIntegralMapper;
import com.zhongmei.yunfu.service.CustomerIntegralService;
import com.zhongmei.yunfu.service.CustomerLevelRuleService;
import com.zhongmei.yunfu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员积分表 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-10-02
 */
@Service
public class CustomerIntegralServiceImpl extends ServiceImpl<CustomerIntegralMapper, CustomerIntegralEntity> implements CustomerIntegralService {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerLevelRuleService customerLevelRuleService;

    @Override
    public void checkIntegral(Long customerId, Long integral) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(customerId, true);
        if (integral > customerEntity.getIntegralTotal() - customerEntity.getIntegralUsed()) {
            throw new ApiResponseStatusException(ApiResponseStatus.CUSTOMER_INTEGRAL_INSUFFICIENT);
        }
    }

    @Override
    public void income(CustomerIntegralTradeReq req) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(req.getCustomerId(), true);
        customerEntity.setConsumptionLastTime(new Date());
        Integer integral = customerEntity.getIntegralTotal();
        int currentIntegral = integral + req.getTradeIntegral();
        updateIntegralOfCustomer(req, customerEntity, currentIntegral, customerEntity.getIntegralUsed());
        addCustomerIntegralRecord(integral, customerEntity, req, RecordType.BUY);
    }

    @Override
    public void refundIncome(CustomerIntegralTradeReq req) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(req.getCustomerId(), true);
        List<CustomerIntegralEntity> selectByTradeId = selectByTradeId(req.getTradeId(), RecordType.BUY);
        for (CustomerIntegralEntity entity : selectByTradeId) {
            Integer integral = customerEntity.getIntegralTotal();
            int currentIntegral = integral - entity.getTradeIntegral();
            int integralUsed = customerEntity.getIntegralUsed();
            updateIntegralOfCustomer(req, customerEntity, currentIntegral, integralUsed);
            addCustomerIntegralRecord(integral, customerEntity, req, RecordType.REFUND);
        }
    }

    @Override
    public void expend(CustomerIntegralTradeReq req) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(req.getCustomerId(), true);
        if (req.getTradeIntegral() > customerEntity.getIntegralTotal() - customerEntity.getIntegralUsed()) {
            throw new ApiResponseStatusException(ApiResponseStatus.CUSTOMER_INTEGRAL_INSUFFICIENT);
        }

        int currentIntegral = customerEntity.getIntegralTotal();
        int consumptionIntegral = customerEntity.getIntegralUsed() + req.getTradeIntegral();
        updateIntegralOfCustomer(req, customerEntity, currentIntegral, consumptionIntegral);
        addCustomerIntegralRecord(currentIntegral, customerEntity, req, RecordType.EXPENSE);
    }

    @Override
    public void refundExpend(CustomerIntegralTradeReq req) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(req.getCustomerId(), true);
        List<CustomerIntegralEntity> selectByTradeId = selectByTradeId(req.getTradeId(), RecordType.EXPENSE);
        for (CustomerIntegralEntity entity : selectByTradeId) {
            int currentIntegral = customerEntity.getIntegralTotal();
            int integralUsed = customerEntity.getIntegralUsed() - entity.getTradeIntegral();
            updateIntegralOfCustomer(req, customerEntity, currentIntegral, integralUsed);
            addCustomerIntegralRecord(currentIntegral, customerEntity, req, RecordType.REFUND);
        }
    }

    private void updateIntegralOfCustomer(CustomerIntegralTradeReq req, CustomerEntity customerEntity, int totalIntegral, int integralUsed) {
        customerEntity.baseUpdate(req.getCreatorId(), req.getCreatorName());
        customerEntity.setIntegralTotal(totalIntegral);
        customerEntity.setIntegralUsed(integralUsed);
        customerEntity.setIntegral(totalIntegral - integralUsed);
        //更新会员等级
        CustomerLevelRuleEntity customerLevelRuleEntity = customerLevelRuleService.getCustomerLevelRuleEntity(req.getShopId(), req.getBrandId(), totalIntegral);
        customerEntity.setGroupLevelId(Long.valueOf(customerLevelRuleEntity.getLevelCode()));
        customerEntity.setGroupLevel(customerLevelRuleEntity.getLevelName());
        customerService.updateById(customerEntity);

    }

    private List<CustomerIntegralEntity> selectByTradeId(Long tradeId, RecordType recordType) {
        CustomerIntegralEntity expendEntity = new CustomerIntegralEntity();
        expendEntity.setTradeId(tradeId);
        expendEntity.setRecordType(recordType.value());
        EntityWrapper<CustomerIntegralEntity> entityWrapper = new EntityWrapper();
        return selectList(entityWrapper);
    }

    /**
     * 更新会员积分
     *
     * @param currentIntegral
     * @param customerEntity
     * @param req
     */
    private void addCustomerIntegralRecord(int currentIntegral, CustomerEntity customerEntity, CustomerIntegralTradeReq req, RecordType recordType) {
        Integer residueIntegral = customerEntity.getIntegralTotal() - customerEntity.getIntegralUsed();
        CustomerIntegralEntity customerIntegralEntity = new CustomerIntegralEntity();
        customerIntegralEntity.baseCreate(req.getCreatorId(), req.getCreatorName());
        customerIntegralEntity.setCustomerId(req.getCustomerId());
        customerIntegralEntity.setRecordType(recordType.value());
        customerIntegralEntity.setCurrentIntegral(currentIntegral);
        customerIntegralEntity.setTradeIntegral(req.getTradeIntegral());
        customerIntegralEntity.setTradeId(req.getTradeId());
        customerIntegralEntity.setShopIdenty(req.getShopId());
        customerIntegralEntity.setBrandIdenty(req.getBrandId());
        customerIntegralEntity.setResidueIntegral(residueIntegral);
        insert(customerIntegralEntity);
    }

    /**
     * 冻结积分
     *
     * @param customerId
     * @param integral
     */
    public void frozen(Long customerId, int integral) {

    }
}
