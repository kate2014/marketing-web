package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.domain.entity.MarketingPutOnEntity;
import com.zhongmei.yunfu.domain.mapper.MarketingPutOnMapper;
import com.zhongmei.yunfu.service.MarketingPutOnService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券投放 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-26
 */
@Service
public class MarketingPutOnServiceImpl extends ServiceImpl<MarketingPutOnMapper, MarketingPutOnEntity> implements MarketingPutOnService {

    @Override
    public List<MarketingPutOnEntity> queryMarketingPutOn(Long brandIdentity, Long shopIdentity) {

        Map<String, Object> map = new HashMap<>();
        map.put("brand_identy", brandIdentity);
        map.put("shop_identy", shopIdentity);
        map.put("status", 1);
        map.put("status_flag", 1);
        List<MarketingPutOnEntity> listData = selectByMap(map);

        return listData;
    }

    @Override
    public MarketingPutOnEntity queryMarketingPutOnByType(Long brandIdentity, Long shopIdentity, Integer palnId) {
        EntityWrapper<MarketingPutOnEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy", brandIdentity);
        eWrapper.eq("shop_identy", shopIdentity);
        eWrapper.eq("paln_id", palnId);
        eWrapper.eq("status", 1);
        eWrapper.eq("status_flag", 1);

        MarketingPutOnEntity mMarketingPutOnEntity = selectOne(eWrapper);

        return mMarketingPutOnEntity;
    }

    @Override
    public Boolean addMarketingPutOn(MarketingPutOnEntity mMarketingPutOn) {
        Boolean isSuccess = insert(mMarketingPutOn);
        return isSuccess;
    }

    @Override
    public Boolean updateMarketingPutOn(MarketingPutOnEntity mMarketingPutOn) {

        Boolean isSuccess = updateById(mMarketingPutOn);
        return isSuccess;
    }
}
