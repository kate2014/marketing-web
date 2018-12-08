package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.domain.entity.CutDownHistoryEntity;
import com.zhongmei.yunfu.domain.mapper.CutDownHistoryMapper;
import com.zhongmei.yunfu.service.CutDownHistoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 砍价记录 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-13
 */
@Service
public class CutDownHistoryServiceImpl extends ServiceImpl<CutDownHistoryMapper, CutDownHistoryEntity> implements CutDownHistoryService {

    @Override
    public Boolean installData(CutDownHistoryEntity mCutDownHistory) throws Exception {
        Boolean isSuccess = insert(mCutDownHistory);
        return isSuccess;
    }

    @Override
    public List<CutDownHistoryEntity> queryDataList(CutDownHistoryEntity mCutDownHistory) throws Exception {
        EntityWrapper<CutDownHistoryEntity> eWrapper = new EntityWrapper<>(mCutDownHistory);
        eWrapper.eq("relation_id", mCutDownHistory.getRelationId());
        eWrapper.orderBy("server_create_time", false);
        List<CutDownHistoryEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public Integer queryJoinCount(CutDownHistoryEntity mCutDownHistory) throws Exception {
        EntityWrapper<CutDownHistoryEntity> eWrapper = new EntityWrapper<>(new CutDownHistoryEntity());
        eWrapper.eq("relation_id", mCutDownHistory.getRelationId());
        eWrapper.eq("customer_id", mCutDownHistory.getCustomerId());

        int count = selectCount(eWrapper);
        return count;
    }
}
