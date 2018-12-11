package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.model.TradeDataModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.domain.mapper.TradeMapper;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 交易记录主单（相当于ORDERS）。
 * 主单及其所有子单中的数量、金额在退货时都取反 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
@Service
public class TradeServiceImpl extends ServiceImpl<TradeMapper, TradeEntity> implements TradeService {

    @Autowired
    TradeService mTradeService;
    @Autowired
    TradeItemService mTradeItemService;
    @Autowired
    PaymentItemService mPaymentItemService;
    @Autowired
    PaymentService mPaymentService;
    @Autowired
    TradePrivilegeService mTradePrivilegeService;

    @Override
    public TradeEntity addTrade(TradeEntity mTrade) throws Exception {
        insert(mTrade);
        return mTrade;
    }

    @Override
    public Boolean deleteTradeById(Long id) throws Exception {
        Boolean isSuccess = deleteById(id);
        return isSuccess;
    }

    @Override
    public Boolean updateTrade(TradeEntity mTrade) throws Exception {
        EntityWrapper<TradeEntity> eWrapper = new EntityWrapper<>(new TradeEntity());
        eWrapper.eq("id", mTrade.getId());
        Boolean isSuccess = update(mTrade, eWrapper);
        return isSuccess;
    }

    @Override
    public TradeEntity queryTradeById(Long id) throws Exception {
        TradeEntity mTrade = selectById(id);
        return mTrade;
    }

    @Override
    public TradeEntity queryTradeByRelateId(Long relateId) throws Exception {
        EntityWrapper<TradeEntity> eWrapper = new EntityWrapper<>(new TradeEntity());
        eWrapper.eq("relate_trade_id", relateId);
        TradeEntity mTradeEntity = selectOne(eWrapper);
        return mTradeEntity;
    }

    @Override
    public TradeEntity queryTradeBaseData(Long id) throws Exception {
        EntityWrapper<TradeEntity> eWrapper = new EntityWrapper<>(new TradeEntity());
        eWrapper.eq("id", id);
        eWrapper.setSqlSelect("id,uuid,trade_type,serial_number,trade_time,trade_status,trade_pay_status,trade_no,sale_amount,privilege_amount,trade_amount,trade_amount_before,trade_memo");
        TradeEntity mTradeEntity = selectOne(eWrapper);
        return mTradeEntity;
    }

    @Override
    public List<TradeEntity> queryTradeByCustomer(Long brandIdenty, Long shopIdenty, String tradeId, Integer tradeStatus) throws Exception {
        EntityWrapper<TradeEntity> eWrapper = new EntityWrapper<>(new TradeEntity());
        eWrapper.eq("shop_identy", shopIdenty);
        eWrapper.eq("brand_identy", brandIdenty);
        if (tradeStatus != null) {
            eWrapper.eq("trade_status", tradeStatus);
        }
        eWrapper.eq("status_flag", 1);
        eWrapper.in("id", tradeId);
        eWrapper.orderBy("server_create_time", false);
        eWrapper.setSqlSelect("id,uuid,trade_type,serial_number,trade_time,trade_status,trade_pay_status,trade_no,sale_amount,privilege_amount,trade_amount,trade_amount_before,trade_memo");
        List<TradeEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public List<SalesReport> querySalesReport(TradeModel mTradeModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", mTradeModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mTradeModel.getShopIdenty());
        eWrapper.eq("source", mTradeModel.getSource());
        eWrapper.eq("business_type", mTradeModel.getBusinessType());
        eWrapper.eq("trade_type", mTradeModel.getTradeType());

        eWrapper.eq("trade_status", mTradeModel.getTradeStatus());
        eWrapper.eq("status_flag", 1);
        eWrapper.between("server_create_time", mTradeModel.getStartDate(), mTradeModel.getEndDate());
        List<SalesReport> listSalse = baseMapper.queryTradeSales(eWrapper);
        return listSalse;
    }

    @Override
    public BigDecimal querySalesAmount(TradeModel mTradeModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", mTradeModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mTradeModel.getShopIdenty());
        eWrapper.eq("source", mTradeModel.getSource());
        eWrapper.eq("business_type", mTradeModel.getBusinessType());
        eWrapper.eq("trade_type", mTradeModel.getTradeType());

        eWrapper.eq("trade_status", mTradeModel.getTradeStatus());
        eWrapper.eq("status_flag", 1);
        eWrapper.between("server_create_time", mTradeModel.getStartDate(), mTradeModel.getEndDate());
        BigDecimal salseAmount = baseMapper.querySalesAmount(eWrapper);
        return salseAmount;
    }

    @Override
    public List<TradeEntity> queryListTrade(TradeModel mTradeModel) throws Exception {
        EntityWrapper<TradeEntity> eWrapper = new EntityWrapper<>(new TradeEntity());
        if(mTradeModel.getShopIdenty() != null){
            eWrapper.eq("shop_identy", mTradeModel.getShopIdenty());
        }
        if(mTradeModel.getBrandIdenty() != null){
            eWrapper.eq("brand_identy", mTradeModel.getBrandIdenty());
        }
        if (mTradeModel.getSource() != null) {
            eWrapper.eq("source", mTradeModel.getSource());
        }
        if (mTradeModel.getBusinessType() != null) {
            eWrapper.eq("business_type", mTradeModel.getBusinessType());
        }
        if (mTradeModel.getTradeType() != null) {
            eWrapper.eq("trade_type", mTradeModel.getTradeType());
        }
        if (mTradeModel.getTradeStatus() != null) {
            eWrapper.eq("trade_status", mTradeModel.getTradeStatus());
        }

        eWrapper.eq("status_flag", 1);
        eWrapper.between("server_create_time", mTradeModel.getStartDate(), mTradeModel.getEndDate());
        eWrapper.orderBy("server_create_time", false);
        eWrapper.setSqlSelect("id,biz_date,business_type,trade_type,trade_status,trade_pay_status,source,trade_no,dish_kind_count,trade_amount,server_create_time,server_update_time");
        List<TradeEntity> listTrade = selectList(eWrapper);
        return listTrade;
    }

    @Override
    public Page<TradeEntity> queryListTradePage(TradeModel mTradeModel) throws Exception {
        EntityWrapper<TradeEntity> eWrapper = new EntityWrapper<>(new TradeEntity());
        eWrapper.eq("shop_identy", mTradeModel.getShopIdenty());
        eWrapper.eq("brand_identy", mTradeModel.getBrandIdenty());
        if (mTradeModel.getSource() != null) {
            eWrapper.eq("source", mTradeModel.getSource());
        }
        if (mTradeModel.getBusinessType() != null) {
            eWrapper.eq("business_type", mTradeModel.getBusinessType());
        }
        if (mTradeModel.getTradeType() != null) {
            eWrapper.eq("trade_type", mTradeModel.getTradeType());
        }
        if (mTradeModel.getTradeStatus() != null) {
            eWrapper.eq("trade_status", mTradeModel.getTradeStatus());
        }
        if (mTradeModel.getTradePayStatus() != null) {
            eWrapper.eq("trade_pay_status", mTradeModel.getTradePayStatus());
        }
        if (mTradeModel.getTradeNo() != null && !mTradeModel.getTradeNo().equals("")) {
            eWrapper.eq("trade_no", mTradeModel.getTradeNo());
        }

        eWrapper.eq("status_flag", 1);
        eWrapper.between("server_create_time", mTradeModel.getStartDate(), mTradeModel.getEndDate());
        eWrapper.orderBy("server_create_time", false);
        eWrapper.setSqlSelect("id,biz_date,business_type,trade_type,trade_status,trade_pay_status,source,trade_no,dish_kind_count,trade_amount,server_create_time,server_update_time");

        Page<TradeEntity> page = new Page<>(mTradeModel.getPageNo(), mTradeModel.getPageSize());

        Page<TradeEntity> listData = selectPage(page, eWrapper);

        return listData;
    }

    @Override
    public Map<String, String> queryCustomerExpenseList(Collection<Long> customerIdsById) {
        Map<String, String> result = new LinkedHashMap<>();
        Wrapper wrapper = Condition.create().in("customer_id", customerIdsById);
        List<TradeEntity> maps = baseMapper.queryCustomerExpenseList(wrapper);
        for (TradeEntity map : maps) {
            result.put(DateFormatUtil.format(map.getServerCreateTime(), "MM月dd日"), map.getTradeAmount().toString());
        }
        return result;
    }

    @Override
    public List<TradeEntity> getTradeEntityBy(Long customerId, Long shopId) {
        return baseMapper.getTradeEntityBy(customerId, shopId);
    }

    @Override
    public TradeDataModel queryTradeDetail(TradeModel mTradeModel) throws Exception{

        TradeDataModel mTradeDataModel = new TradeDataModel();

        TradeEntity mTradeEntity = mTradeService.queryTradeBaseData(mTradeModel.getTradeId());
        mTradeDataModel.setTrade(mTradeEntity);

        List<TradeItemEntity> tradeItems = mTradeItemService.querTradeItemByTradeId(mTradeModel.getTradeId());

        List<TradePrivilegeEntity> listTempTP = mTradePrivilegeService.queryPrivilegeByTradeId(mTradeModel.getBrandIdenty(),mTradeModel.getShopIdenty(),mTradeModel.getTradeId());

        List<Map<String,Object>> listTradeItem = new ArrayList<>();
        List<TradePrivilegeEntity> listPrivilage = new ArrayList<>();
        for(TradeItemEntity ti : tradeItems){
            Map<String,Object> itemMap = new HashMap<>();
            itemMap.put("dishName",ti.getDishName());
            itemMap.put("price",ti.getPrice());
            itemMap.put("quantity",ti.getQuantity());
            itemMap.put("amount",ti.getAmount());
            itemMap.put("propertyAmount",ti.getPropertyAmount());
            itemMap.put("actualAmount",ti.getActualAmount());
            itemMap.put("feedsAmount",ti.getFeedsAmount());
            for(TradePrivilegeEntity tp : listTempTP){
                if(tp.getTradeItemId() != null && tp.getTradeItemId() != 0){
                    if(tp.getTradeItemId() == ti.getId()){
                        itemMap.put("privilegeName",tp.getPrivilegeName());
                        itemMap.put("privilegeValue",tp.getPrivilegeValue());
                        itemMap.put("privilegeAmount",tp.getPrivilegeAmount());
                        break;
                    }
                }else{
                    listPrivilage.add(tp);
                    break;
                }

            }
            listTradeItem.add(itemMap);
        }

        mTradeDataModel.setListTradeItem(listTradeItem);

        List<PaymentEntity> listPaymment = mPaymentService.queryPayment(mTradeModel.getTradeId());

        List<PaymentItemEntity> listPaymentItem = new ArrayList<>();

        if(listPaymment != null && listPaymment.size() > 0){
            for(PaymentEntity pe : listPaymment){
                listPaymentItem.addAll(mPaymentItemService.queryPaymentItem(pe.getId()));
            }
        }

        mTradeDataModel.setmPaymentItemEntity(listPaymentItem);

        return mTradeDataModel;
    }

    @Override
    public TradeEntity queryBaseTradeById(Long tradeId) throws Exception {
        EntityWrapper<TradeEntity> eWrapper = new EntityWrapper<>(new TradeEntity());
        eWrapper.eq("id", tradeId);
        eWrapper.setSqlSelect("id,trade_status,trade_pay_status,trade_amount,trade_no,shop_identy,brand_identy,server_update_time");
        TradeEntity mTradeEntity = selectOne(eWrapper);
        return mTradeEntity;
    }
}
