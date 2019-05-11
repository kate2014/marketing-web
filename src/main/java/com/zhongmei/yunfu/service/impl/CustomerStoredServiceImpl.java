package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerStoredEntity;
import com.zhongmei.yunfu.domain.entity.TradeEntity;
import com.zhongmei.yunfu.domain.mapper.CustomerStoredMapper;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.CustomerStoredService;
import com.zhongmei.yunfu.service.TradeService;
import com.zhongmei.yunfu.thirdlib.wxapp.WxTemplateMessageHandler;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.MemberChargeMessage;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.OrderPayMessage;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.WxTempMsg;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

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

    @Autowired
    TradeService tradeService;

    @Autowired
    CommercialService commercialService;

    @Override
    public void recharge(CustomerStoredEntity customerStored) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(customerStored.getCustomerId(), true);
        tradeStored(CustomerStoredEntity.RecordType.RECHARGE, customerStored);
        BigDecimal storedBalance = customerEntity.getStoredBalance();
        storedBalance = storedBalance.add(customerStored.getTradeAmount());
        customerEntity.setStoredBalance(storedBalance);
        customerService.updateById(customerEntity);

        CommercialEntity commercialEntity = commercialService.selectById(customerEntity.getShopIdenty());
        TradeEntity tradeEntity = tradeService.selectById(customerStored.getTradeId());
        MemberChargeMessage wxTempMsg = new MemberChargeMessage();
        wxTempMsg.setChargeType("余额储值");
        wxTempMsg.setMobileAccount(customerEntity.getMobile());
        wxTempMsg.setChargeTime(DateFormatUtil.format(tradeEntity.getServerCreateTime(), DateFormatUtil.FORMAT_FULL_DATE));
        wxTempMsg.setChargeAmount(customerStored.getTradeAmount() + "元");
        wxTempMsg.setTradeNo(tradeEntity.getTradeNo());
        wxTempMsg.setGivenAmount("0.00元");
        wxTempMsg.setAccountBalance(storedBalance + "元");
        wxTempMsg.setMemo("对此次充值信息如有异议，烦请即时联系商家");
        wxTempMsg.setShopName(commercialEntity.getBranchName());
        wxTempMsg.setBrandIdenty(customerStored.getBrandIdenty());
        wxTempMsg.setShopIdenty(customerStored.getShopIdenty());
        wxTempMsg.setCustomerId(customerStored.getCustomerId());
        WxTemplateMessageHandler.sendWxTemplateMessage(wxTempMsg);
    }

    @Override
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
