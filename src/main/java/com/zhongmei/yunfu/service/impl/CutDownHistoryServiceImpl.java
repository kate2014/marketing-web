package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.domain.entity.CutDownCustomerEntity;
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
 * @author yangyp
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
    public Page<CutDownHistoryEntity> queryJoinData(CutDownHistoryEntity mCutDownHistory, Integer pageNo, Integer pageSize) throws Exception {
        Page<CutDownHistoryEntity> listPage = new Page<>(pageNo, pageSize);
        EntityWrapper<CutDownHistoryEntity> eWrapper = new EntityWrapper<>(new CutDownHistoryEntity());
        eWrapper.eq("brand_identity",mCutDownHistory.getBrandIdentity());
        eWrapper.eq("shop_identity",mCutDownHistory.getShopIdentity());
        eWrapper.eq("cut_down_id",mCutDownHistory.getCutDownId());
        if(mCutDownHistory.getCutPrice() != null && !mCutDownHistory.getCutPrice().equals("")){
            eWrapper.ge("cut_price",mCutDownHistory.getCutPrice());
        }
        eWrapper.setSqlSelect("id,cut_down_id,customer_id,open_id,wx_name,wx_photo,server_create_time,cut_price");
        eWrapper.orderBy("server_create_time",false);
        Page<CutDownHistoryEntity> listData = selectPage(listPage, eWrapper);
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
