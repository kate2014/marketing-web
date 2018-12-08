package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.model.ExpandedMarketingModel;
import com.zhongmei.yunfu.domain.entity.MarketingExpandedEntity;
import com.zhongmei.yunfu.domain.mapper.MarketingExpandedMapper;
import com.zhongmei.yunfu.service.MarketingExpandedService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 全员推广营销 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Service
public class MarketingExpandedServiceImpl extends ServiceImpl<MarketingExpandedMapper, MarketingExpandedEntity> implements MarketingExpandedService {


    @Override
    public MarketingExpandedEntity findMarketingExpanded(ExpandedMarketingModel expandedMarketingModel) {
        Map<String, Object> map = new HashMap<>();
        map.put("brand_identy", expandedMarketingModel.getBrandIdenty());
        map.put("shop_identy", expandedMarketingModel.getShopIdenty());
        map.put("status_flag", expandedMarketingModel.getStatusFlag());
        List<MarketingExpandedEntity> listPlan = selectByMap(map);
        MarketingExpandedEntity mMarketingExpanded = null;
        if (listPlan != null && listPlan.size() != 0) {
            mMarketingExpanded = listPlan.get(0);
        }
        return mMarketingExpanded;
    }

    @Override
    public MarketingExpandedEntity queryMarketingExpanded(MarketingExpandedEntity mMarketingExpanded) {
        EntityWrapper<MarketingExpandedEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy", mMarketingExpanded.getBrandIdenty());
        eWrapper.eq("shop_identy", mMarketingExpanded.getShopIdenty());
        eWrapper.eq("status_flag", 1);
        eWrapper.eq("expanded_state", 1);
        eWrapper.setSqlSelect("id,first_level_discount,dicount_type,end_date,start_date");
        MarketingExpandedEntity me = selectOne(eWrapper);
        return me;
    }

    @Override
    public Boolean addMarketingExpanded(MarketingExpandedEntity mMarketingExpanded) {
        Boolean isSuccess = insert(mMarketingExpanded);
        return isSuccess;
    }

    @Override
    public Boolean editMarketingExpanded(MarketingExpandedEntity mMarketingExpanded) {
        Boolean isSuccess = updateById(mMarketingExpanded);
        return isSuccess;
    }
}
