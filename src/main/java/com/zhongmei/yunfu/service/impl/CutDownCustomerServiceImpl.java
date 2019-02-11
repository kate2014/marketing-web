package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.model.CollageCustomerModel;
import com.zhongmei.yunfu.controller.model.CutDownCustomerModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.CutDownCustomerEntity;
import com.zhongmei.yunfu.domain.mapper.CutDownCustomerMapper;
import com.zhongmei.yunfu.service.CutDownCustomerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员发起砍价记录 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-13
 */
@Service
public class CutDownCustomerServiceImpl extends ServiceImpl<CutDownCustomerMapper, CutDownCustomerEntity> implements CutDownCustomerService {

    @Override
    public Boolean installData(CutDownCustomerEntity mCutDownCustomer) throws Exception {

        Boolean isSuccess = insert(mCutDownCustomer);
        return isSuccess;
    }

    @Override
    public Boolean updateData(CutDownCustomerEntity mCutDownCustomer) throws Exception {
        Boolean isSuccess = updateById(mCutDownCustomer);
        return isSuccess;
    }

    @Override
    public List<CutDownCustomerModel> queryDataList(CutDownCustomerEntity mCutDownCustomer) throws Exception {

        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("c.brand_identity",mCutDownCustomer.getBrandIdentity());
        eWrapper.eq("c.shop_identity",mCutDownCustomer.getShopIdentity());
        eWrapper.eq("c.customer_id",mCutDownCustomer.getCustomerId());
//        eWrapper.in("c.state","1,2,4");
        eWrapper.eq("c.status_flag",1);
        List<CutDownCustomerModel> listData = baseMapper.queryCutDowmByCustomer(eWrapper);

        return listData;
    }

    @Override
    public CutDownCustomerEntity queryDataById(Long id) throws Exception {
        EntityWrapper<CutDownCustomerEntity> eWrapper = new EntityWrapper<>(new CutDownCustomerEntity());
        eWrapper.eq("id", id);
        eWrapper.setSqlSelect("id,cut_down_id,customer_id,wx_name,wx_photo,state,current_price,join_count,brand_identity,shop_identity,status_flag,server_create_time,server_update_time");
        CutDownCustomerEntity mCutDownCustomer = selectOne(eWrapper);
        return mCutDownCustomer;
    }

}
