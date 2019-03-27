package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.domain.entity.MarketingTogetherEntity;
import com.zhongmei.yunfu.domain.mapper.MarketingTogetherMapper;
import com.zhongmei.yunfu.service.MarketingTogetherService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 同行特惠 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Service
public class MarketingTogetherServiceImpl extends ServiceImpl<MarketingTogetherMapper, MarketingTogetherEntity> implements MarketingTogetherService {

    @Override
    public MarketingTogetherEntity findMarketingTogether(Long shopIdentity, Integer statusFlag,Integer state) {
        Map<String, Object> map = new HashMap<>();
        map.put("shop_identy", shopIdentity);
        map.put("status_flag", statusFlag);
        if(state != null){
            map.put("state", state);
        }

        List<MarketingTogetherEntity> listPlan = selectByMap(map);
        MarketingTogetherEntity mMarketingTogether = null;
        if (listPlan != null && listPlan.size() != 0) {
            mMarketingTogether = listPlan.get(0);
        }
        return mMarketingTogether;
    }

    @Override
    public MarketingTogetherEntity findMarketingTogetherById(Long id) {
        MarketingTogetherEntity mMarketingTogether = selectById(id);
        return mMarketingTogether;
    }

    @Override
    public Boolean addShareMarketing(MarketingTogetherEntity mMarketingTogether) {
        Boolean isSuccess = insert(mMarketingTogether);
        return isSuccess;
    }

    @Override
    public Boolean editShareMarketing(MarketingTogetherEntity mMarketingTogether) {
        Boolean isSuccess = updateById(mMarketingTogether);
        return isSuccess;
    }

    @Override
    public Boolean updateNumber(Long id, Integer scanNumber, Integer shareNumber) {
        MarketingTogetherEntity mMarketingTogether = new MarketingTogetherEntity();
        mMarketingTogether.setScanNumber(scanNumber);
        if (shareNumber != null) {
            EntityWrapper<MarketingTogetherEntity> eWrapper = new EntityWrapper<>(new MarketingTogetherEntity());
            eWrapper.setSqlSelect("id, share_number");
            eWrapper.eq("id", id);
            MarketingTogetherEntity m = selectOne(eWrapper);
            mMarketingTogether.setShareNumber(m.getShareNumber() + 1);
        }

        EntityWrapper<MarketingTogetherEntity> eWrapper = new EntityWrapper<>(new MarketingTogetherEntity());
        eWrapper.eq("id", id);
        Boolean isSuccess = update(mMarketingTogether, eWrapper);
        return isSuccess;
    }
}
