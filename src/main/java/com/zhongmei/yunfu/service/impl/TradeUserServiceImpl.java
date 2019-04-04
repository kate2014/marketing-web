package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.TradeUserEntity;
import com.zhongmei.yunfu.domain.mapper.TradeUserMapper;
import com.zhongmei.yunfu.service.TradeUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单用户关联表 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-17
 */
@Service
public class TradeUserServiceImpl extends ServiceImpl<TradeUserMapper, TradeUserEntity> implements TradeUserService {

    @Override
    public List<TradeUserEntity> queryDataByTradeId(Long tradeId) throws Exception {
        EntityWrapper<TradeUserEntity> eWrapper = new EntityWrapper<>(new TradeUserEntity());
        eWrapper.eq("trade_id",tradeId);
        eWrapper.eq("status_flag", 1);
        List<TradeUserEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public List<TradeUserEntity> queryTradeUserList(TradeModel mTradeModel) throws Exception {
        EntityWrapper<TradeUserEntity> eWrapper = new EntityWrapper<>(new TradeUserEntity());
        eWrapper.eq("brand_identy", mTradeModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mTradeModel.getShopIdenty());
        if(mTradeModel.getTradeUser() != null && !mTradeModel.getTradeUser().equals("")){
            eWrapper.like("user_name", mTradeModel.getTradeUser());
        }
        eWrapper.eq("status_flag", 1);
        eWrapper.between("server_create_time", mTradeModel.getStartDate(), mTradeModel.getEndDate());
        eWrapper.setSqlSelect("trade_item_id,user_name,user_id");
        eWrapper.orderBy("server_create_time",false);

        List<TradeUserEntity> listData = selectList(eWrapper);
        return listData;
    }
}
