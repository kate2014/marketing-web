package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.DishReport;
import com.zhongmei.yunfu.domain.entity.DishSaleReport;
import com.zhongmei.yunfu.domain.entity.TradeItemEntity;
import com.zhongmei.yunfu.domain.mapper.TradeItemMapper;
import com.zhongmei.yunfu.service.TradeItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 交易明细 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
@Service
public class TradeItemServiceImpl extends ServiceImpl<TradeItemMapper, TradeItemEntity> implements TradeItemService {

    @Override
    public Boolean addTradeItem(TradeItemEntity mTradeItem) throws Exception {
        Boolean isSuccess = insert(mTradeItem);
        return isSuccess;
    }

    @Override
    public List<TradeItemEntity> querTradeItemByTradeId(Long tradeId) {
        EntityWrapper<TradeItemEntity> eWrapper = new EntityWrapper<>(new TradeItemEntity());
        eWrapper.eq("trade_id", tradeId);
        eWrapper.setSqlSelect("id,trade_id,trade_uuid,parent_id,parent_uuid,dish_id,dish_setmeal_group_id,dish_name,type,sort,price,quantity,unit_name,amount,property_amount,actual_amount,trade_memo,creator_name");
        List<TradeItemEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public List<TradeItemEntity> queryTradeItemAllByTradeId(Long tradeId) {
        EntityWrapper<TradeItemEntity> eWrapper = new EntityWrapper<>(new TradeItemEntity());
        eWrapper.eq("trade_id", tradeId);
        List<TradeItemEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public List<DishReport> selectDishSalesReport(Long brandIdenty, Long shopIdenty, Date start, Date end) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", brandIdenty);
        eWrapper.eq("shop_identy", shopIdenty);
        eWrapper.eq("trade_status", 4);
        eWrapper.eq("business_type", 1);//美业销货
        eWrapper.eq("trade_type", 1);//售货
        eWrapper.eq("status_flag", 1);
        eWrapper.between("server_create_time", start, end);
        List<DishReport> listData = baseMapper.queryDishSales(eWrapper);
        return listData;
    }


    @Override
    public List<DishReport> selectCardTimeReport(Long brandIdenty, Long shopIdenty, Date start, Date end) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", brandIdenty);
        eWrapper.eq("shop_identy", shopIdenty);
        eWrapper.eq("trade_status", 4);
        eWrapper.eq("business_type", 3);//次卡销售
        eWrapper.eq("trade_type", 1);//售货
        eWrapper.eq("status_flag", 1);
        eWrapper.between("server_create_time", start, end);
        List<DishReport> listData = baseMapper.queryDishSales(eWrapper);
        return listData;
    }

    @Override
    public Boolean deleteByTradeId(Long tradeId) {
        EntityWrapper<TradeItemEntity> eWrapper = new EntityWrapper<>(new TradeItemEntity());
        eWrapper.eq("trade_id", tradeId);
        Boolean isSuccess = delete(eWrapper);
        return isSuccess;
    }

    @Override
    public List<DishReport> dishSalesExportExcel(TradeModel mTradeModel) throws Exception {

        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", mTradeModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mTradeModel.getShopIdenty());
        eWrapper.eq("trade_status", 4);
        eWrapper.eq("business_type", mTradeModel.getBusinessType());
        eWrapper.eq("trade_type", 1);//售货
        eWrapper.eq("status_flag", 1);
        eWrapper.between("server_create_time", mTradeModel.getStartDate(), mTradeModel.getEndDate());
        List<DishReport> listData = baseMapper.dishSalesExportExcel(eWrapper);
        return listData;
    }

    @Override
    public List<TradeItemEntity> dishSaleDetail(TradeModel mTradeModel) throws Exception {

        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", mTradeModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mTradeModel.getShopIdenty());
        eWrapper.eq("trade_status", 4);
        eWrapper.eq("business_type", 1);//美业销货
        eWrapper.eq("status_flag", 1);
        eWrapper.between("server_create_time", mTradeModel.getStartDate(), mTradeModel.getEndDate());
        List<TradeItemEntity> listData = baseMapper.dishSaleDetail(eWrapper);

        return listData;
    }

    @Override
    public List<TradeItemEntity> dishSaleData(TradeModel mTradeModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.eq("t.brand_identy", mTradeModel.getBrandIdenty());
        eWrapper.eq("t.shop_identy", mTradeModel.getShopIdenty());
        eWrapper.eq("t.trade_status", mTradeModel.getTradeStatus());
        eWrapper.eq("t.business_type", 1);//美业销货
        eWrapper.eq("t.trade_type", mTradeModel.getTradeType());//1销货  2退货
        eWrapper.eq("t.status_flag", 1);
        eWrapper.between("t.server_create_time", mTradeModel.getStartDate(), mTradeModel.getEndDate());
        List<TradeItemEntity> listData = baseMapper.dishSaleData(eWrapper);

        return listData;
    }

    @Override
    public List<DishSaleReport> listSaleReport(TradeModel mTradeModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("t.brand_identy", mTradeModel.getBrandIdenty());
        eWrapper.eq("t.shop_identy", mTradeModel.getShopIdenty());
        eWrapper.eq("t.business_type", 1);
        eWrapper.in("t.trade_status", "4,5");
        if(mTradeModel.getTradeType() == null){
            eWrapper.in("t.trade_type", "1,2");
        }else{
            eWrapper.eq("t.trade_type", mTradeModel.getTradeType());
        }
        if(mTradeModel.getDishName() != null){
            eWrapper.like("i.dish_name", mTradeModel.getDishName());
        }
        if(mTradeModel.getTradeUser() != null){
            eWrapper.like("u.user_name", mTradeModel.getTradeUser());
        }
        if(mTradeModel.getCustomerName() != null){
            eWrapper.like("c.customer_name", mTradeModel.getCustomerName());
        }
        eWrapper.eq("t.status_flag", 1);
        eWrapper.between("i.server_create_time", mTradeModel.getStartDate(), mTradeModel.getEndDate());
        List<DishSaleReport> listData = baseMapper.listDishSale(eWrapper);

        return listData;
    }


}
