package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.domain.entity.PushPlanActivityEntity;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.TradeCustomerEntity;
import com.zhongmei.yunfu.domain.mapper.TradeCustomerMapper;
import com.zhongmei.yunfu.service.TradeCustomerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 交易的顾客信息 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
@Service
public class TradeCustomerServiceImpl extends ServiceImpl<TradeCustomerMapper, TradeCustomerEntity> implements TradeCustomerService {

    @Override
    public Boolean installTradeCustomer(TradeCustomerEntity mTradeCustomer) throws Exception {
        Boolean isSuccess = insert(mTradeCustomer);
        return isSuccess;
    }

    @Override
    public TradeCustomerEntity queryTradeCustomer(Long tradeId) throws Exception {
        EntityWrapper<TradeCustomerEntity> eWrapper = new EntityWrapper<>(new TradeCustomerEntity());
        eWrapper.eq("trade_id", tradeId);
        eWrapper.eq("customer_type", 3);
        eWrapper.eq("status_flag", 1);
        TradeCustomerEntity mTradeCustomer = selectOne(eWrapper);
        return mTradeCustomer;
    }

    @Override
    public List<TradeCustomerEntity> queryTradeCustomerList(Long tradeId) throws Exception {
        EntityWrapper<TradeCustomerEntity> eWrapper = new EntityWrapper<>(new TradeCustomerEntity());
        eWrapper.eq("trade_id", tradeId);
        eWrapper.eq("status_flag", 1);
        List<TradeCustomerEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public Page<TradeCustomerEntity> queryTradeByCustomer(TradeModel mTradeModel) throws Exception {
        EntityWrapper<TradeCustomerEntity> eWrapper = new EntityWrapper<>(new TradeCustomerEntity());
        eWrapper.eq("brand_identy", mTradeModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mTradeModel.getShopIdenty());
        eWrapper.eq("customer_id", mTradeModel.getCustomerId());
        eWrapper.eq("customer_type", 3);
        eWrapper.eq("status_flag", 1);

        Page<TradeCustomerEntity> listPage = new Page<>(mTradeModel.getPageNo(), mTradeModel.getPageSize());

        eWrapper.setSqlSelect("trade_id,trade_uuid,customer_type,customer_id,customer_name,customer_phone,customer_sex");

        eWrapper.orderBy("server_create_time",false);
        Page<TradeCustomerEntity> listData = selectPage(listPage, eWrapper);
        return listData;
    }

    @Override
    public Boolean deleteByTradeId(Long tradeId) throws Exception {
        EntityWrapper<TradeCustomerEntity> eWrapper = new EntityWrapper<>(new TradeCustomerEntity());
        eWrapper.eq("trade_id", tradeId);
        Boolean isSuccess = delete(eWrapper);
        return isSuccess;
    }

    @Override
    public List<TradeCustomerEntity> queryTradeCustomerList(TradeModel mTradeModel) throws Exception {
        EntityWrapper<TradeCustomerEntity> eWrapper = new EntityWrapper<>(new TradeCustomerEntity());
        eWrapper.eq("brand_identy", mTradeModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mTradeModel.getShopIdenty());
        eWrapper.eq("customer_type", mTradeModel.getCustomerType());

        if(mTradeModel.getCustomerName() != null && !mTradeModel.getCustomerName().equals("")){
            eWrapper.like("customer_name", mTradeModel.getCustomerName());
        }
        eWrapper.eq("status_flag", 1);
        eWrapper.between("server_create_time", mTradeModel.getStartDate(), mTradeModel.getEndDate());
        eWrapper.setSqlSelect("trade_id,customer_type,customer_id,customer_name,customer_phone,customer_sex");
        eWrapper.orderBy("server_create_time",false);
        List<TradeCustomerEntity> listData = selectList(eWrapper);

        return listData;
    }
}
