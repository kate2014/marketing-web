package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.api.ApiRespStatus;
import com.zhongmei.yunfu.api.ApiRespStatusException;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.domain.mapper.CustomerExtraMapper;
import com.zhongmei.yunfu.domain.mapper.CustomerStoredMapper;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.thirdlib.wxapp.WxTemplateMessageHandler;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.MemberChargeMessage;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

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

    @Autowired
    CustomerPrivilegeRuleService customerPrivilegeRuleService;

    @Autowired
    CustomerExtraMapper customerExtraMapper;

    @Override
    public CustomerStoredEntity getByPaymentItemId(Long shopId, Long tradeId, Long paymentItemId) {
        return null;
    }

    @Override
    public void recharge(CustomerStoredEntity customerStored) throws Exception {
        tradeStored(CustomerStoredEntity.RecordType.RECHARGE, customerStored);

        boolean isInsert = false;
        CustomerExtraEntity customerExtraEntity = customerExtraMapper.selectById(customerStored.getCustomerId());
        if (customerExtraEntity == null) {
            isInsert = true;
            customerExtraEntity = new CustomerExtraEntity();
            customerExtraEntity.baseCreate(customerStored.getUpdatorId(), customerStored.getUpdatorName());
            customerExtraEntity.setCustomerId(customerStored.getCustomerId());
        }
        if (customerStored.getTradeAmount() == null) {
            customerStored.setTradeAmount(BigDecimal.ZERO);
        }
        if (customerExtraEntity.getStoredGive() == null) {
            customerExtraEntity.setStoredGive(BigDecimal.ZERO);
        }

        customerExtraEntity.baseUpdate(customerStored.getUpdatorId(), customerStored.getUpdatorName());
        BigDecimal storedAmount = customerExtraEntity.getStoredAmount()
                .add(customerStored.getTradeAmount())
                .add(customerStored.getGiveAmount());
        customerExtraEntity.setStoredAmount(storedAmount);
        customerExtraEntity.setStoredGive(customerExtraEntity.getStoredGive().add(customerStored.getGiveAmount()));

        //设置储值优惠值
        List<CustomerPrivilegeRuleEntity> byPrivilegeType = customerPrivilegeRuleService.getByPrivilegeType(customerStored.getShopIdenty(), 3, 4);
        if (byPrivilegeType != null) {
            //从大到小排序
            Collections.sort(byPrivilegeType, (o1, o2) -> o2.getSaveAmount().compareTo(o1.getSaveAmount()));
            for (CustomerPrivilegeRuleEntity privilegeRuleEntity : byPrivilegeType) {
                if (customerStored.getTradeAmount().compareTo(privilegeRuleEntity.getSaveAmount()) >= 0) {
                    customerExtraEntity.setStoredPaymentCheck(privilegeRuleEntity.getIsNeedSavePayment());
                    customerExtraEntity.setStoredPrivilegeType(privilegeRuleEntity.getPrivilegeType());
                    customerExtraEntity.setStoredPrivilegeValue(privilegeRuleEntity.getPrivilegeValue());
                    customerExtraEntity.setStoredFullAmount(privilegeRuleEntity.getFullAmount());
                    break;
                }
            }
        }
        if (isInsert) {
            customerExtraMapper.insert(customerExtraEntity);
        } else {
            customerExtraMapper.updateById(customerExtraEntity);
        }

        CustomerEntity customerEntity = customerService.selectById(customerStored.getCustomerId());
        CommercialEntity commercialEntity = commercialService.selectById(customerStored.getShopIdenty());
        TradeEntity tradeEntity = tradeService.selectById(customerStored.getTradeId());
        MemberChargeMessage wxTempMsg = new MemberChargeMessage();
        wxTempMsg.setChargeType("余额储值");
        wxTempMsg.setMobileAccount(customerEntity.getMobile());
        wxTempMsg.setChargeTime(DateFormatUtil.format(tradeEntity.getServerCreateTime(), DateFormatUtil.FORMAT_FULL_DATE));
        wxTempMsg.setChargeAmount(customerStored.getTradeAmount() + "元");
        wxTempMsg.setTradeNo(tradeEntity.getTradeNo());
        wxTempMsg.setGivenAmount(customerStored.getGiveAmount() + "元");
        wxTempMsg.setAccountBalance(storedAmount + "元");
        wxTempMsg.setMemo("对此次充值信息如有异议，烦请即时联系商家");
        wxTempMsg.setShopName(commercialEntity.getBranchName());
        wxTempMsg.setBrandIdenty(customerStored.getBrandIdenty());
        wxTempMsg.setShopIdenty(customerStored.getShopIdenty());
        wxTempMsg.setCustomerId(customerStored.getCustomerId());
        WxTemplateMessageHandler.sendWxTemplateMessage(wxTempMsg);
    }

    @Override
    public void expense(CustomerStoredEntity customerStored) throws Exception {
        CustomerExtraEntity customerExtraEntity = customerExtraMapper.selectById(customerStored.getCustomerId());
        //储值余额是否够用
        if (customerExtraEntity.getStoredBalance()
                .compareTo(customerStored.getTradeAmount()) < 0) {
            throw new ApiRespStatusException(ApiRespStatus.CUSTOMER_STORED_EXPENSE_INSUFFICIENT);
        }

        BigDecimal storedUsed = customerExtraEntity.getStoredUsed().add(customerStored.getTradeAmount());
        customerExtraEntity.setStoredUsed(storedUsed);
        customerExtraMapper.updateById(customerExtraEntity);

        tradeStored(CustomerStoredEntity.RecordType.EXPENSE, customerStored);
    }

    @Override
    public void refund(CustomerStoredEntity customerStored) throws Exception {
        tradeStored(CustomerStoredEntity.RecordType.REFUND, customerStored);
        CustomerExtraEntity customerExtraEntity = customerExtraMapper.selectById(customerStored.getCustomerId());
        BigDecimal storedAmount = customerExtraEntity.getStoredAmount()
                .subtract(customerStored.getTradeAmount())
                .subtract(customerStored.getGiveAmount());
        customerExtraEntity.setStoredAmount(storedAmount);
        customerExtraEntity.setStoredGive(customerExtraEntity.getStoredGive().subtract(customerStored.getGiveAmount()));
        //恢复上次的储值折扣
        //customerExtraEntity.setStoredPaymentCheck(privilegeRuleEntity.getIsNeedSavePayment());
        //customerExtraEntity.setStoredPrivilegeType(privilegeRuleEntity.getPrivilegeType());
        //customerExtraEntity.setStoredPrivilegeValue(privilegeRuleEntity.getPrivilegeValue());

        customerExtraMapper.updateById(customerExtraEntity);
    }

    @Override
    public CustomerStoredEntity queryByTradeId(Long tradeId) throws Exception {

        EntityWrapper<CustomerStoredEntity> eWrapper = new EntityWrapper<>(new CustomerStoredEntity());
        eWrapper.eq("trade_id", tradeId);
        eWrapper.eq("status_flag", 1);
        eWrapper.setSqlSelect("id,customer_id,record_type,last_usable_amout,trade_amount,give_amount,residue_balance,trade_id,payment_item_id");
        CustomerStoredEntity mCustomerStoredEntity = selectOne(eWrapper);
        return mCustomerStoredEntity;
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
        customerStored.setLastUsableAmout(residueBalance);
        BigDecimal countResidueBalance = countResidueBalance(recordType, residueBalance, customerStored.getTradeAmount(), customerStored.getGiveAmount());
        customerStored.setResidueBalance(countResidueBalance);
        insert(customerStored);
    }

    /**
     * 获取最近一次可用余额
     *
     * @param customerStored
     * @return
     */
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
     * @param giveAmount
     * @return
     */
    private BigDecimal countResidueBalance(CustomerStoredEntity.RecordType recordType,
                                           BigDecimal residueBalance,
                                           BigDecimal tradeAmount,
                                           BigDecimal giveAmount) {
        giveAmount = giveAmount != null ? giveAmount : BigDecimal.ZERO;
        switch (recordType) {
            case RECHARGE:
                return residueBalance.add(tradeAmount).add(giveAmount);
            case EXPENSE:
            case REFUND:
                return residueBalance.subtract(tradeAmount).subtract(giveAmount);
            default:
                return null;
        }
    }
}
