package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CommissionSearchModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.CustomerExtraEntity;
import com.zhongmei.yunfu.domain.entity.ExpandedCommissionEntity;
import com.zhongmei.yunfu.domain.mapper.ExpandedCommissionMapper;
import com.zhongmei.yunfu.service.ExpandedCommissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 推广提成 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-16
 */
@Service
public class ExpandedCommissionServiceImpl extends ServiceImpl<ExpandedCommissionMapper, ExpandedCommissionEntity> implements ExpandedCommissionService {

    @Override
    public Boolean addExpandedCommission(ExpandedCommissionEntity mExpandedCommission) throws Exception {

        ExpandedCommissionEntity ecNew = queryNewCommission(mExpandedCommission);

        //如为空则表示是第一次参与提成
        if (ecNew == null) {

            mExpandedCommission.setTotalAmount(mExpandedCommission.getChangeSalesAmount());//累积消费总额
            mExpandedCommission.setChangeSalesAmount(mExpandedCommission.getChangeSalesAmount());//单次消费额度
            mExpandedCommission.setCommissionRatio(mExpandedCommission.getCommissionRatio());//提成比例
            //转换提 百分比转换为小数
            BigDecimal num = new BigDecimal("100");
            BigDecimal ratio = mExpandedCommission.getCommissionRatio().divide(num);
            BigDecimal commissionAmount = mExpandedCommission.getChangeSalesAmount().multiply(ratio);
            mExpandedCommission.setCommissionAmount(commissionAmount);//单次提成金额

            mExpandedCommission.setTotalCommission(commissionAmount);//累积提成总额
            mExpandedCommission.setCanExchange(commissionAmount);//可兑换提成金额
            mExpandedCommission.setExchangeAmount(BigDecimal.ZERO);//单次兑换提成金额

        } else {
            mExpandedCommission.setTotalAmount(ecNew.getTotalAmount().add(mExpandedCommission.getChangeSalesAmount()));//累积消费总额
            mExpandedCommission.setChangeSalesAmount(mExpandedCommission.getChangeSalesAmount());//单次消费额度
            mExpandedCommission.setCommissionRatio(mExpandedCommission.getCommissionRatio());//提成比例
            //转换提 百分比转换为小数
            BigDecimal num = new BigDecimal("100");
            BigDecimal ratio = mExpandedCommission.getCommissionRatio().divide(num);
            BigDecimal commissionAmount = mExpandedCommission.getChangeSalesAmount().multiply(ratio);
            mExpandedCommission.setCommissionAmount(commissionAmount);//单次提成金额

            mExpandedCommission.setTotalCommission(ecNew.getTotalCommission().add(commissionAmount));//累积提成总额
            mExpandedCommission.setCanExchange(ecNew.getCanExchange().add(commissionAmount));//可兑换提成金额
            mExpandedCommission.setExchangeAmount(BigDecimal.ZERO);//单次兑换提成金额
        }

        mExpandedCommission.setType(1);
        Boolean isSuccess = insert(mExpandedCommission);
        return isSuccess;
    }

    @Override
    public Boolean subtractCommission(ExpandedCommissionEntity mExpandedCommission) throws Exception {
        ExpandedCommissionEntity ecNew = queryNewCommission(mExpandedCommission);

        mExpandedCommission.setTotalAmount(ecNew.getTotalAmount().subtract(mExpandedCommission.getChangeSalesAmount()));//累积消费总额
        mExpandedCommission.setChangeSalesAmount(mExpandedCommission.getChangeSalesAmount());//单次消费额度
        mExpandedCommission.setCommissionRatio(mExpandedCommission.getCommissionRatio());//提成比例
        //转换提 百分比转换为小数
        BigDecimal num = new BigDecimal("100");
        BigDecimal ratio = mExpandedCommission.getCommissionRatio().divide(num);
        BigDecimal commissionAmount = mExpandedCommission.getChangeSalesAmount().multiply(ratio);
        mExpandedCommission.setCommissionAmount(commissionAmount);//单次提成金额

        mExpandedCommission.setTotalCommission(ecNew.getTotalCommission().subtract(commissionAmount));//累积提成总额
        mExpandedCommission.setCanExchange(ecNew.getCanExchange().subtract(commissionAmount));//可兑换提成金额
        mExpandedCommission.setExchangeAmount(BigDecimal.ZERO);//单次兑换提成金额

        Boolean isSuccess = insert(mExpandedCommission);
        return isSuccess;
    }

    /**
     * 获取会最新提成数据信息
     * @param mExpandedCommission
     * @return
     * @throws Exception
     */
    @Override
    public ExpandedCommissionEntity queryNewCommission(ExpandedCommissionEntity mExpandedCommission) throws Exception{

        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("shop_identy", mExpandedCommission.getShopIdenty());
        eWrapper.eq("brand_identy", mExpandedCommission.getBrandIdenty());
        eWrapper.eq("status_flag", 1);
        eWrapper.eq("customer_id", mExpandedCommission.getCustomerId());

        ExpandedCommissionEntity mExpandedCommissionEntity = baseMapper.queryCustomerCommission(eWrapper);
        return mExpandedCommissionEntity;
    }

    @Override
    public Boolean exchangeExpandedCommission(ExpandedCommissionEntity mExpandedCommission) throws Exception {

        ExpandedCommissionEntity ecNew = queryNewCommission(mExpandedCommission);

        if(ecNew.getCanExchange().compareTo(mExpandedCommission.getExchangeAmount())>0){
            mExpandedCommission.setTotalAmount(ecNew.getTotalAmount());//累积消费总额
            mExpandedCommission.setTotalCommission(ecNew.getTotalCommission());
            mExpandedCommission.setCanExchange(ecNew.getCanExchange().subtract(mExpandedCommission.getExchangeAmount()));//可兑换提成金额
            mExpandedCommission.setType(2); //业务类型 1：提成累加  2：兑换 3：扣减（退货时需要扣减）

            Boolean isSuccess = insert(mExpandedCommission);
            return isSuccess;
        }else{
            return false;
        }
    }


    @Override
    public ExpandedCommissionEntity queryCommissionByTradeId(ExpandedCommissionEntity mExpandedCommission) throws Exception {
        EntityWrapper<ExpandedCommissionEntity> eWrapper = new EntityWrapper<>(new ExpandedCommissionEntity());
        eWrapper.eq("shop_identy", mExpandedCommission.getShopIdenty());
        eWrapper.eq("brand_identy", mExpandedCommission.getBrandIdenty());
        eWrapper.eq("trade_id", mExpandedCommission.getTradeId());
        eWrapper.eq("type", 1);
        ExpandedCommissionEntity mec = selectOne(eWrapper);
        return mec;
    }

    @Override
    public Page<CommissionSearchModel> queryListCommission(CommissionSearchModel mCommissionSearchModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("e.shop_identy", mCommissionSearchModel.getShopIdenty());
        eWrapper.eq("e.brand_identy", mCommissionSearchModel.getBrandIdenty());
        eWrapper.eq("e.status_flag", 1);
        eWrapper.between("e.server_create_time", mCommissionSearchModel.getStartDate(), mCommissionSearchModel.getEndDate());
        if(mCommissionSearchModel.getCustomerId() != null){
            eWrapper.eq("e.customer_id", mCommissionSearchModel.getCustomerId());
        }
        Page<CommissionSearchModel> page = new Page<>(mCommissionSearchModel.getPageNo(), mCommissionSearchModel.getPageSize());
        page.setRecords(baseMapper.qeryListCommission(page,eWrapper));
        return page;
    }


}
