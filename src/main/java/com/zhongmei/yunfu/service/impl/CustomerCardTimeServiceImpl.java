package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.api.ApiResponseStatus;
import com.zhongmei.yunfu.api.ApiResponseStatusException;
import com.zhongmei.yunfu.api.internal.vo.CustomerCardTimeBuyReq;
import com.zhongmei.yunfu.api.internal.vo.CustomerCardTimeExpenseReq;
import com.zhongmei.yunfu.api.internal.vo.CustomerCardTimeRefundReq;
import com.zhongmei.yunfu.domain.entity.CustomerCardTimeEntity;
import com.zhongmei.yunfu.domain.entity.CustomerCardTimeExpendEntity;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.enums.EnabledFlag;
import com.zhongmei.yunfu.domain.enums.RecordType;
import com.zhongmei.yunfu.domain.enums.StatusFlag;
import com.zhongmei.yunfu.domain.mapper.CustomerCardTimeMapper;
import com.zhongmei.yunfu.service.CustomerCardTimeExpendService;
import com.zhongmei.yunfu.service.CustomerCardTimeService;
import com.zhongmei.yunfu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员次卡表 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-29
 */
@Service
public class CustomerCardTimeServiceImpl extends ServiceImpl<CustomerCardTimeMapper, CustomerCardTimeEntity> implements CustomerCardTimeService {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerCardTimeExpendService customerCardTimeExpendService;

    @Override
    public CustomerCardTimeEntity getCustomerCardTimeEntity(Long id, boolean isCheckState) throws Exception {
        CustomerCardTimeEntity customerCardTimeEntity = selectById(id);
        if (isCheckState) {
            if (customerCardTimeEntity == null) {
                throw new ApiResponseStatusException(ApiResponseStatus.FOUND);
            }

            if (customerCardTimeEntity.getStatusFlag() == StatusFlag.INVALID.value()) {
                throw new ApiResponseStatusException(ApiResponseStatus.CUSTOMER_CARD_TIME_INVALID);
            }
        }

        return customerCardTimeEntity;
    }

    @Override
    public List<CustomerCardTimeEntity> getListPageByCustomerId(Long customerId) {
        CustomerCardTimeEntity cardTimeEntity = new CustomerCardTimeEntity();
        cardTimeEntity.setStatusFlag(StatusFlag.VALiD.value());
        cardTimeEntity.setCustomerId(customerId);
        return selectList(new EntityWrapper<>(cardTimeEntity));
    }

    @Override
    public Page<CustomerCardTimeEntity> getListPageByCustomerId(Long customerId, Integer pageNo, Integer pageSize) {
        CustomerCardTimeEntity cardTimeEntity = new CustomerCardTimeEntity();
        cardTimeEntity.setCustomerId(customerId);
        //cardTimeEntity.setShopIdenty();
        cardTimeEntity.setStatusFlag(StatusFlag.VALiD.value());
        return selectPage(new Page<>(pageNo, pageSize), new EntityWrapper<>(cardTimeEntity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void buy(CustomerCardTimeBuyReq req) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(req.getCustomerId(), true);
        int cardResidueCount = customerEntity.getCardResidueCount();
        List<CustomerCardTimeEntity> result = new ArrayList<>();
        if (req.getDishs() != null) {
            for (CustomerCardTimeBuyReq.Dish dish : req.getDishs()) {
                CustomerCardTimeEntity cardTimeEntity = new CustomerCardTimeEntity();
                cardTimeEntity.baseCreate(dish.getCreatorId(), dish.getCreatorName());
                cardTimeEntity.setRecordType(RecordType.BUY.value());
                cardTimeEntity.setCustomerId(req.getCustomerId());
                cardTimeEntity.setTradeId(req.getTradeId());
                cardTimeEntity.setTradeUuid(req.getTradeUuid());
                cardTimeEntity.setShopIdenty(req.getShopId());
                cardTimeEntity.setBrandIdenty(req.getBrandId());
                cardTimeEntity.setDishId(dish.getDishId());
                cardTimeEntity.setDishName(dish.getDishName());
                //cardTimeEntity.setGroupId(cardTimeEntity.getGroupId());
                //cardTimeEntity.setGroupName(cardTimeEntity.getGroupName());
                cardTimeEntity.setTradeCount(dish.getTradeCount());
                cardTimeEntity.setResidueCount(dish.getTradeCount());
                result.add(cardTimeEntity);
                cardResidueCount += dish.getTradeCount();
            }
        }

        insertBatch(result);
        customerEntity.setCardResidueCount(cardResidueCount);
        customerService.updateById(customerEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void expense(CustomerCardTimeExpenseReq req) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(req.getCustomerId(), true);

        System.out.println("============ConsumptionNumber=" + customerEntity.getConsumptionNumber());
        int consumptionNumber = customerEntity.getConsumptionNumber();
        int cardResidueCount = customerEntity.getCardResidueCount();
        List<CustomerCardTimeExpendEntity> result = new ArrayList<>();
        if (req.getDishs() != null) {
            for (CustomerCardTimeExpenseReq.Dish dish : req.getDishs()) {
                CustomerCardTimeEntity cardTimeEntity = getCustomerCardTimeEntity(dish.getCustomerCardTimeId(), true);
                if (dish.getTradeCount() > cardTimeEntity.getResidueCount()) {
                    throw new ApiResponseStatusException(ApiResponseStatus.CUSTOMER_CARD_TIME_INSUFFICIENT);
                }

                consumptionNumber += dish.getTradeCount();
                cardResidueCount -= dish.getTradeCount();
                int expendResidueCount = cardTimeEntity.getResidueCount() - dish.getTradeCount();
                cardTimeEntity.setResidueCount(expendResidueCount);
                updateById(cardTimeEntity);

                //写入次卡交易记录
                CustomerCardTimeExpendEntity expendEntity = new CustomerCardTimeExpendEntity();
                expendEntity.baseCreate(dish.getCreatorId(), dish.getCreatorName());
                expendEntity.setRecordType(RecordType.EXPENSE.value());
                expendEntity.setCustomerId(req.getCustomerId());
                expendEntity.setCustomerCardTimeId(cardTimeEntity.getId());
                expendEntity.setTradeId(req.getTradeId());
                expendEntity.setTradeUuid(req.getTradeUuid());
                expendEntity.setShopIdenty(req.getShopId());
                expendEntity.setBrandIdenty(req.getBrandId());
                expendEntity.setDishId(cardTimeEntity.getDishId());
                expendEntity.setDishName(cardTimeEntity.getDishName());
                //expendEntity.setGroupId(cardTimeEntity.getGroupId());
                //expendEntity.setGroupName(cardTimeEntity.getGroupName());
                expendEntity.setTradeCount(dish.getTradeCount());
                expendEntity.setResidueCount(expendResidueCount);
                result.add(expendEntity);
            }
        }

        customerCardTimeExpendService.insertBatch(result);
        //更新会员信息
        customerEntity.baseUpdate(req.getCreatorId(), req.getCreatorName());
        customerEntity.setConsumptionLastTime(new Date());
        customerEntity.setConsumptionNumber(consumptionNumber);
        customerEntity.setCardResidueCount(cardResidueCount);
        customerService.updateById(customerEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void refund(CustomerCardTimeRefundReq req) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(req.getCustomerId(), true);

        int consumptionNumber = customerEntity.getConsumptionNumber();
        int cardResidueCount = 0;

        List<CustomerCardTimeExpendEntity> result = new ArrayList<>();
        List<CustomerCardTimeExpendEntity> byTradeId = customerCardTimeExpendService.findByTradeId(req.getTradeId(), RecordType.EXPENSE);
        for (CustomerCardTimeExpendEntity entity : byTradeId) {
            CustomerCardTimeEntity customerCardTimeEntity = getCustomerCardTimeEntity(entity.getCustomerCardTimeId(), true);
            consumptionNumber -= entity.getTradeCount();
            cardResidueCount += entity.getTradeCount();
            int expendResidueCount = customerCardTimeEntity.getResidueCount() + entity.getTradeCount();

            //写入次卡交易记录
            CustomerCardTimeExpendEntity expendEntity = new CustomerCardTimeExpendEntity();
            expendEntity.baseCreate(req.getCreatorId(), req.getCreatorName());
            expendEntity.setRecordType(RecordType.REFUND.value());
            expendEntity.setCustomerId(req.getCustomerId());
            expendEntity.setCustomerCardTimeId(customerCardTimeEntity.getId());
            expendEntity.setTradeId(req.getTradeId());
            expendEntity.setTradeUuid(req.getTradeUuid());
            expendEntity.setShopIdenty(req.getShopId());
            expendEntity.setBrandIdenty(req.getBrandId());
            expendEntity.setDishId(entity.getDishId());
            expendEntity.setDishName(entity.getDishName());
            expendEntity.setGroupId(entity.getGroupId());
            expendEntity.setGroupName(entity.getGroupName());
            expendEntity.setTradeCount(entity.getTradeCount());
            expendEntity.setResidueCount(expendResidueCount);
            result.add(expendEntity);
        }

        customerCardTimeExpendService.insertBatch(result);

        //更新会员信息
        customerEntity.baseUpdate(req.getCreatorId(), req.getCreatorName());
        customerEntity.setConsumptionLastTime(new Date());
        customerEntity.setConsumptionNumber(consumptionNumber);
        customerEntity.setCardResidueCount(cardResidueCount);
        customerService.updateById(customerEntity);
    }

    @Override
    public int selectCount(Long customerId, Long shopId) {
        Collection<Long> idsById = customerService.getCustomerIdsById(customerId);
        CustomerCardTimeEntity cardTimeEntity = new CustomerCardTimeEntity();
        cardTimeEntity.setShopIdenty(shopId);
        cardTimeEntity.setStatusFlag(StatusFlag.VALiD.value());
        cardTimeEntity.setEnabledFlag(EnabledFlag.ENABLED.value());
        return selectCount(new EntityWrapper<>(cardTimeEntity)
                .in("customer_id", idsById));
    }
}
