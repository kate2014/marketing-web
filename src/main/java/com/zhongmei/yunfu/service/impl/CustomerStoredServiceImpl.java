package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerStoredEntity;
import com.zhongmei.yunfu.domain.mapper.CustomerStoredMapper;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.CustomerStoredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 * 会员储值、储值消费记录表 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
@Service
public class CustomerStoredServiceImpl extends ServiceImpl<CustomerStoredMapper, CustomerStoredEntity> implements CustomerStoredService {

    @Autowired
    CustomerService customerService;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void recharge(CustomerStoredEntity customerStored) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(customerStored.getCustomerId(), true);
        tradeStored(CustomerStoredEntity.RecordType.RECHARGE, customerStored);
        BigDecimal storedBalance = customerEntity.getStoredBalance();
        storedBalance = storedBalance.add(customerStored.getTradeAmount());
        customerEntity.setStoredBalance(storedBalance);
        customerService.updateById(customerEntity);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void expense(CustomerStoredEntity customerStored) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(customerStored.getCustomerId(), true);
        tradeStored(CustomerStoredEntity.RecordType.EXPENSE, customerStored);
        BigDecimal storedBalance = customerEntity.getStoredBalance();
        storedBalance = storedBalance.subtract(customerStored.getTradeAmount());
        customerEntity.setStoredBalance(storedBalance);
        customerService.updateById(customerEntity);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void refund(CustomerStoredEntity customerStored) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(customerStored.getCustomerId(), true);
        tradeStored(CustomerStoredEntity.RecordType.REFUND, customerStored);
        BigDecimal storedBalance = customerEntity.getStoredBalance();
        storedBalance = storedBalance.add(customerStored.getTradeAmount());
        customerEntity.setStoredBalance(storedBalance);
        customerService.updateById(customerEntity);
    }

    /**
     * 储值交易
     *
     * @param recordType
     * @param customerStored
     */
    private void tradeStored(CustomerStoredEntity.RecordType recordType, CustomerStoredEntity customerStored) {
        BigDecimal residueBalance = getResidueBalanceByLastId(customerStored);
        customerStored.setRecordType(recordType.getValue());
        BigDecimal countResidueBalance = countResidueBalance(recordType, residueBalance, customerStored.getTradeAmount());
        customerStored.setResidueBalance(countResidueBalance);
        insert(customerStored);
    }

    private BigDecimal getResidueBalanceByLastId(CustomerStoredEntity customerStored) {
        CustomerStoredEntity byLastServerUpdateTime = baseMapper.getByLastServerUpdateTime(customerStored.getCustomerId());
        return byLastServerUpdateTime != null ? byLastServerUpdateTime.getResidueBalance() : BigDecimal.ZERO;
    }

    /**
     * 计算剩余余额
     *
     * @param recordType
     * @param residueBalance
     * @param tradeAmount
     * @return
     */
    private BigDecimal countResidueBalance(CustomerStoredEntity.RecordType recordType, BigDecimal residueBalance, BigDecimal tradeAmount) {
        switch (recordType) {
            case RECHARGE:
            case REFUND:
                return residueBalance.add(tradeAmount);
            case EXPENSE:
                return residueBalance.subtract(tradeAmount);
            default:
                return null;
        }
    }
}
