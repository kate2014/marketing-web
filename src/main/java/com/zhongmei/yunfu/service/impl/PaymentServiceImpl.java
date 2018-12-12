package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.PaymentEntity;
import com.zhongmei.yunfu.domain.entity.SalesReport;
import com.zhongmei.yunfu.domain.mapper.PaymentMapper;
import com.zhongmei.yunfu.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, PaymentEntity> implements PaymentService {
    @Override
    public Boolean installPayment(PaymentEntity mPaymentEntity)throws Exception {

        Boolean isSuccess = insert(mPaymentEntity);
        return isSuccess;
    }

    @Override
    public Boolean updatePayment(PaymentEntity mPaymentEntity) throws Exception{
        EntityWrapper<PaymentEntity> eWrapper = new EntityWrapper<>(new PaymentEntity());
        eWrapper.eq("id",mPaymentEntity.getId());
        Boolean isSuccess = update(mPaymentEntity, eWrapper);
        return isSuccess;
    }

    @Override
    public List<PaymentEntity> queryPayment(Long tradeId) throws Exception{
        EntityWrapper<PaymentEntity> eWrapper = new EntityWrapper<>(new PaymentEntity());
        eWrapper.eq("relate_id",tradeId);
        eWrapper.eq("status_flag",1);
        eWrapper.orderBy("id",false);
        eWrapper.setSqlSelect("id,payment_time");
        eWrapper.orderBy("server_create_time",false);
        List<PaymentEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public PaymentEntity queryPaymentByItemId(Long paymentItemId) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("i.id", paymentItemId);
        PaymentEntity mPaymentEntity = baseMapper.queryPaymentByItemId(eWrapper);
        return mPaymentEntity;
    }

    @Override
    public PaymentEntity queryPaymentByTradeId(Long tradeId) throws Exception {
        EntityWrapper<PaymentEntity> eWrapper = new EntityWrapper<>(new PaymentEntity());
        eWrapper.eq("relate_id",tradeId);
        eWrapper.eq("status_flag",1);
        PaymentEntity mPaymentEntity = selectOne(eWrapper);
        return mPaymentEntity;
    }

    @Override
    public Boolean deleteByTradeId(Long tradeId) throws Exception {
        EntityWrapper<PaymentEntity> eWrapper = new EntityWrapper<>(new PaymentEntity());
        eWrapper.eq("relate_id",tradeId);
        Boolean isSuccess = delete(eWrapper);
        return isSuccess;
    }
}
