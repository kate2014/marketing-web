package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.domain.entity.TradePrivilegeEntity;
import com.zhongmei.yunfu.domain.mapper.TradePrivilegeMapper;
import com.zhongmei.yunfu.service.TradePrivilegeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 优惠信息 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-17
 */
@Service
public class TradePrivilegeServiceImpl extends ServiceImpl<TradePrivilegeMapper, TradePrivilegeEntity> implements TradePrivilegeService {

    @Override
    public List<TradePrivilegeEntity> queryPrivilegeByTradeId(Long brandIdenty, Long shopIdenty, Long tradeId) {
        EntityWrapper<TradePrivilegeEntity> eWrapper = new EntityWrapper<>(new TradePrivilegeEntity());
        eWrapper.eq("brand_identy", brandIdenty);
        eWrapper.eq("shop_identy", shopIdenty);
        eWrapper.eq("trade_id", tradeId);
        eWrapper.eq("status_flag", 1);
        eWrapper.setSqlSelect("id,trade_id,trade_item_id,trade_uuid,trade_item_uuid,privilege_type,privilege_value,privilege_amount,privilege_name,promo_id,surcharge_name,use_status,status_flag,shop_identy,brand_identy");
        eWrapper.orderBy("server_create_time");
        List<TradePrivilegeEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public Boolean modifyUseStatus(Long tradePrivilegeId, Integer useStatus) {
        EntityWrapper<TradePrivilegeEntity> eWrapper = new EntityWrapper<>(new TradePrivilegeEntity());
        eWrapper.eq("id", tradePrivilegeId);
        TradePrivilegeEntity mTradePrivilege = new TradePrivilegeEntity();
        mTradePrivilege.setUseStatus(useStatus);
        mTradePrivilege.setServerUpdateTime(new Date());
        Boolean isSuccess = update(mTradePrivilege, eWrapper);
        return isSuccess;
    }
}
