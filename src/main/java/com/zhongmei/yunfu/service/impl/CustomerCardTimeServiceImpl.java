package com.zhongmei.yunfu.service.impl;

import com.alibaba.fastjson.JSON;
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
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 会员次卡表 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
@Service
public class CustomerCardTimeServiceImpl extends ServiceImpl<CustomerCardTimeMapper, CustomerCardTimeEntity> implements CustomerCardTimeService {

    static final int UNLIMITED_TIMES = -1; //不限制次数

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
    public Page<CustomerCardTimeEntity> getCardValidByCustomerId(Long customerId, Integer pageNo, Integer pageSize) {
        CustomerCardTimeEntity cardTimeEntity = new CustomerCardTimeEntity();
        cardTimeEntity.setCustomerId(customerId);
        //cardTimeEntity.setShopIdenty();
        cardTimeEntity.setStatusFlag(StatusFlag.VALiD.value());
        EntityWrapper<CustomerCardTimeEntity> entityWrapper = new EntityWrapper<>(cardTimeEntity);
        entityWrapper.ne("residue_count", "0");
        entityWrapper.andNew("card_expire_date >= {0} OR card_expire_date IS NULL", DateFormatUtil.formatDate(new Date()));
        return selectPage(new Page<>(pageNo, pageSize), new EntityWrapper<>(cardTimeEntity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void buy(CustomerCardTimeBuyReq req) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(req.getCustomerId(), true);
        Date cardExpireDate = customerEntity.getCardExpireDate();
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
                cardTimeEntity.setCardType(dish.getType());
                CardInfo cardInfo = CardInfo.parse(dish.getBatchNo());
                if (cardInfo != null) {
                    Integer serversCount = cardInfo.getServersCount(dish.getTradeCount());
                    cardTimeEntity.setTradeCount(serversCount);
                    cardTimeEntity.setResidueCount(serversCount);
                    cardTimeEntity.setCardExpireDate(cardInfo.getExpireDate());
                }
                result.add(cardTimeEntity);
                if (cardTimeEntity.getTradeCount() != UNLIMITED_TIMES) {
                    cardResidueCount += cardTimeEntity.getTradeCount();
                }

                //统计最近的服务过期时间
                Calendar current = Calendar.getInstance();
                current.set(Calendar.HOUR_OF_DAY, 0);
                current.set(Calendar.MINUTE, 0);
                current.set(Calendar.SECOND, 0);
                current.set(Calendar.MILLISECOND, 0);

                if (cardExpireDate != null && cardExpireDate.before(current.getTime())) {
                    cardExpireDate = null;
                }
                if (cardTimeEntity.getCardExpireDate() != null && cardTimeEntity.getCardExpireDate().compareTo(current.getTime()) >= 0) {
                    if (cardExpireDate == null || cardTimeEntity.getCardExpireDate().before(cardExpireDate)) {
                        cardExpireDate = cardTimeEntity.getCardExpireDate();
                    }
                }
            }
        }

        insertBatch(result);
        customerEntity.setCardExpireDate(cardExpireDate);
        customerEntity.setCardResidueCount(cardResidueCount);
        customerService.updateById(customerEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void expense(CustomerCardTimeExpenseReq req) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(req.getCustomerId(), true);

        //System.out.println("============ConsumptionNumber=" + customerEntity.getConsumptionNumber());
        //int consumptionNumber = customerEntity.getConsumptionNumber();
        int cardResidueCount = customerEntity.getCardResidueCount();
        List<CustomerCardTimeExpendEntity> result = new ArrayList<>();
        if (req.getDishs() != null) {
            for (CustomerCardTimeExpenseReq.Dish dish : req.getDishs()) {
                CustomerCardTimeEntity cardTimeEntity = getCustomerCardTimeEntity(dish.getCustomerCardTimeId(), true);
                //判断剩余次数
                if (cardTimeEntity.getTradeCount() != UNLIMITED_TIMES
                        && dish.getTradeCount() > cardTimeEntity.getTradeCount() - cardTimeEntity.getUsedCount()) {
                    throw new ApiResponseStatusException(ApiResponseStatus.CUSTOMER_CARD_TIME_INSUFFICIENT);
                }

                //判断是否过期
                if (cardTimeEntity.getCardExpireDate() != null) {
                    Date expireDate = cardTimeEntity.getCardExpireDate();
                    Calendar calendarExpire = Calendar.getInstance();
                    calendarExpire.setTime(expireDate);

                    Calendar current = Calendar.getInstance();
                    current.set(Calendar.HOUR_OF_DAY, 0);
                    current.set(Calendar.MINUTE, 0);
                    current.set(Calendar.SECOND, 0);
                    current.set(Calendar.MILLISECOND, 0);
                    if (current.after(expireDate)) {
                        throw new ApiResponseStatusException(ApiResponseStatus.CUSTOMER_CARD_TIME_EXPIRED);
                    }
                }

                //consumptionNumber += dish.getTradeCount();
                int expendResidueCount = cardTimeEntity.getResidueCount();
                if (cardTimeEntity.getTradeCount() != UNLIMITED_TIMES) {
                    cardResidueCount -= dish.getTradeCount();
                    expendResidueCount = cardTimeEntity.getResidueCount() - dish.getTradeCount();
                }

                cardTimeEntity.setUsedCount(cardTimeEntity.getUsedCount() + dish.getTradeCount());
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
        //customerEntity.setConsumptionLastTime(new Date());
        //customerEntity.setConsumptionNumber(consumptionNumber);
        customerEntity.setCardResidueCount(cardResidueCount);
        customerService.updateById(customerEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void refund(CustomerCardTimeRefundReq req) throws Exception {
        CustomerEntity customerEntity = customerService.getCustomerEntity(req.getCustomerId(), true);

        //int consumptionNumber = customerEntity.getConsumptionNumber();
        int cardResidueCount = customerEntity.getCardResidueCount();

        List<CustomerCardTimeExpendEntity> result = new ArrayList<>();
        List<CustomerCardTimeExpendEntity> byTradeId = customerCardTimeExpendService.findByTradeId(req.getTradeId(), RecordType.EXPENSE);
        for (CustomerCardTimeExpendEntity entity : byTradeId) {
            CustomerCardTimeEntity cardTimeEntity = getCustomerCardTimeEntity(entity.getCustomerCardTimeId(), true);

            //consumptionNumber -= entity.getTradeCount();
            int expendResidueCount = cardTimeEntity.getResidueCount();
            if (cardTimeEntity.getTradeCount() != UNLIMITED_TIMES) {
                cardResidueCount += entity.getTradeCount();
                expendResidueCount = cardTimeEntity.getResidueCount() + entity.getTradeCount();
            }

            cardTimeEntity.setUsedCount(cardTimeEntity.getUsedCount() - entity.getTradeCount());
            cardTimeEntity.setResidueCount(expendResidueCount);
            updateById(cardTimeEntity);

            //写入次卡交易记录
            CustomerCardTimeExpendEntity expendEntity = new CustomerCardTimeExpendEntity();
            expendEntity.baseCreate(req.getCreatorId(), req.getCreatorName());
            expendEntity.setRecordType(RecordType.REFUND.value());
            expendEntity.setCustomerId(req.getCustomerId());
            expendEntity.setCustomerCardTimeId(cardTimeEntity.getId());
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
        //customerEntity.setConsumptionLastTime(new Date());
        //customerEntity.setConsumptionNumber(consumptionNumber);
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

    static class CardInfo {
        public Integer timeValue;
        public Integer timeType;
        public Integer serversCount;

        public static CardInfo parse(String json) {
            CardInfo result = new CardInfo();
            CardInfo temp = JSON.parseObject(json, CardInfo.class);
            if (temp != null) {
                result.timeType = temp.timeType;
                result.timeValue = temp.timeValue;
                result.serversCount = temp.serversCount;
            }
            return result;
        }

        public Integer getServersCount(Integer tradeCount) {
            if (serversCount != UNLIMITED_TIMES) {
                return serversCount * tradeCount;
            }
            return serversCount;
        }

        public Date getExpireDate() {
            //1天，2周，3月。
            if (timeType != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                switch (timeType) {
                    case 1:
                        calendar.add(Calendar.DAY_OF_MONTH, timeValue);
                        return calendar.getTime();
                    case 2:
                        calendar.add(Calendar.WEEK_OF_MONTH, timeValue);
                        return calendar.getTime();
                    case 3:
                        calendar.add(Calendar.MONTH, timeValue);
                        return calendar.getTime();
                }
            }
            return null;
        }
    }
}
