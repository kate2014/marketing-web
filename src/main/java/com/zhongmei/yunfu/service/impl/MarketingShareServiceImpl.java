package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.domain.entity.MarketingShareEntity;
import com.zhongmei.yunfu.domain.entity.PushPlanNewDishEntity;
import com.zhongmei.yunfu.domain.mapper.MarketingShareMapper;
import com.zhongmei.yunfu.service.MarketingShareService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分享营销 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-26
 */
@Service
public class MarketingShareServiceImpl extends ServiceImpl<MarketingShareMapper, MarketingShareEntity> implements MarketingShareService {


    @Override
    public List<MarketingShareEntity> findSharePlanByShopId(Long shopIdentity) {

        EntityWrapper<MarketingShareEntity> eWrapper = new EntityWrapper<>(new MarketingShareEntity());
        eWrapper.eq("shop_identy",shopIdentity);
        List<MarketingShareEntity> listData = selectList(eWrapper);

        return listData;
    }


    @Override
    public Boolean addShareMarketing(MarketingShareEntity marketingShare) {

        Boolean isSuccess = insert(marketingShare);
        return isSuccess;
    }

    @Override
    public Boolean editShareMarketing(MarketingShareEntity marketingShare) {

        Boolean isSuccess = updateById(marketingShare);
        return isSuccess;
    }

}
