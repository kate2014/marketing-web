package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.model.PaymentItemModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.PaymentItemEntity;
import com.zhongmei.yunfu.domain.mapper.PaymentItemMapper;
import com.zhongmei.yunfu.service.PaymentItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentItemServiceImpl extends ServiceImpl<PaymentItemMapper, PaymentItemEntity> implements PaymentItemService {

    @Override
    public Boolean installPaymentItem(PaymentItemEntity mPaymentItemEntity) throws Exception{
        Boolean isSuccess = insert(mPaymentItemEntity);
        return isSuccess;
    }

    @Override
    public Boolean updatePaymentItem(PaymentItemEntity mPaymentItemEntity) throws Exception{
        EntityWrapper<PaymentItemEntity> eWrapper = new EntityWrapper<>(new PaymentItemEntity());
        eWrapper.eq("id",mPaymentItemEntity.getId());
        Boolean isSuccess = update(mPaymentItemEntity,eWrapper);
        return isSuccess;
    }

    @Override
    public List<PaymentItemEntity> queryPaymentItem(Long paymentId) throws Exception{
        EntityWrapper<PaymentItemEntity> eWrapper = new EntityWrapper<>(new PaymentItemEntity());
        eWrapper.eq("payment_id",paymentId);
        eWrapper.eq("status_flag",1);
        eWrapper.setSqlSelect("id,uuid,pay_mode_id,pay_mode_name,pay_status,useful_amount,pay_source");
        eWrapper.orderBy("server_create_time",false);
        List<PaymentItemEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public List<PaymentItemModel> queryPaymentItemReport(PaymentItemModel mPaymentItemModel) throws Exception{
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", mPaymentItemModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mPaymentItemModel.getShopIdenty());
        eWrapper.eq("pay_status",3);
        eWrapper.eq("status_flag",1);
        eWrapper.between("server_create_time", mPaymentItemModel.getStartDate(), mPaymentItemModel.getEndDate());
        List<PaymentItemModel> listData = baseMapper.querySalesAmount(eWrapper);

        return listData;
    }

    @Override
    public PaymentItemEntity queryPaymentItemByTradeId(Long tradeId) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("p.relate_id", tradeId);
        eWrapper.eq("p.status_flag", 1);
        eWrapper.eq("i.status_flag", 1);
        PaymentItemEntity mPaymentItemEntity = baseMapper.queryPaymentItemByTradeId(eWrapper);
        return mPaymentItemEntity;
    }

    @Override
    public List<PaymentItemEntity> queryPaymentItemsByTradeId(Long tradeId) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("p.relate_id", tradeId);
        eWrapper.eq("p.status_flag", 1);
        eWrapper.eq("i.status_flag", 1);
        List<PaymentItemEntity> listData = baseMapper.queryPaymentItemsByTradeId(eWrapper);
        return listData;
    }

    @Override
    public PaymentItemEntity queryPaymentItemById(Long id) throws Exception {
        PaymentItemEntity mPaymentItemEntity = selectById(id);
        return mPaymentItemEntity;
    }
}
