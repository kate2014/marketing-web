package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.DishReport;
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
}
