package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
 * @author pigeon88
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
}
