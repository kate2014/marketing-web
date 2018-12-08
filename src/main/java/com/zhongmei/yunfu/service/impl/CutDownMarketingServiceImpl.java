package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CutDownModel;
import com.zhongmei.yunfu.controller.model.CutDownReportModel;
import com.zhongmei.yunfu.controller.model.ReportMarketingModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.CutDownCustomerEntity;
import com.zhongmei.yunfu.domain.entity.CutDownMarketingEntity;
import com.zhongmei.yunfu.domain.mapper.CutDownMarketingMapper;
import com.zhongmei.yunfu.service.CutDownMarketingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 砍价活动 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-10
 */
@Service
public class CutDownMarketingServiceImpl extends ServiceImpl<CutDownMarketingMapper, CutDownMarketingEntity> implements CutDownMarketingService {

    @Override
    public Boolean createCutDown(CutDownMarketingEntity mCutDownMarketing) throws Exception {

        Boolean isSuccess = insert(mCutDownMarketing);
        return isSuccess;
    }

    @Override
    public Page<CutDownMarketingEntity> queryCutDownList(CutDownModel mCutDownModel) throws Exception {
        CutDownMarketingEntity mCutDownMarketing = new CutDownMarketingEntity();
        Page<CutDownMarketingEntity> listPage = new Page<>(mCutDownModel.getPageNo(), mCutDownModel.getPageSize());
        EntityWrapper<CutDownMarketingEntity> eWrapper = new EntityWrapper<>(mCutDownMarketing);
        eWrapper.setSqlSelect("id,name,begin_time,end_time,start_price,end_price,validity_period,img_url,profile,sales_count,sold_count,enabled_flag");
        eWrapper.eq("brand_identity", mCutDownModel.getBrandIdentity());
        eWrapper.eq("shop_identity", mCutDownModel.getShopIdentity());
        eWrapper.eq("status_flag", 1);

        if (mCutDownModel.getEnabledFlag() != null) {
            eWrapper.eq("enabled_flag", mCutDownModel.getEnabledFlag());
        }
        if (mCutDownModel.getName() != null) {
            eWrapper.like("name", mCutDownModel.getName());
        }

        eWrapper.orderBy("server_create_time", false);

        Page<CutDownMarketingEntity> listData = selectPage(listPage, eWrapper);

        return listData;
    }

    @Override
    public CutDownMarketingEntity findCutDownById(Long id) throws Exception {
        CutDownMarketingEntity mCutDownMarketing = selectById(id);
        return mCutDownMarketing;
    }

    @Override
    public CutDownMarketingEntity findCutDownDatailById(Long id) throws Exception {
        EntityWrapper<CutDownMarketingEntity> eWrapper = new EntityWrapper<>(new CutDownMarketingEntity());
        eWrapper.eq("id", id);
        eWrapper.setSqlSelect("id,img_url,name,validity_period,begin_time,end_time,start_price,end_price,sales_count,sold_count,enabled_flag,product_id,product_name");
        CutDownMarketingEntity mCutDownMarketing = selectOne(eWrapper);
        return mCutDownMarketing;
    }

    @Override
    public CutDownMarketingEntity findCutPice(Long id) throws Exception {
        EntityWrapper<CutDownMarketingEntity> eWrapper = new EntityWrapper<>(new CutDownMarketingEntity());
        eWrapper.eq("id", id);
        eWrapper.setSqlSelect("random_price_mini,random_price_max,end_price");
        CutDownMarketingEntity mCutDownMarketing = selectOne(eWrapper);
        return mCutDownMarketing;
    }

    @Override
    public Boolean deleteCutDownById(Long id) throws Exception {
        Boolean isSuccess = deleteById(id);
        return isSuccess;
    }

    @Override
    public Boolean modifyCutDown(CutDownMarketingEntity mCutDownMarketing) throws Exception {
        EntityWrapper<CutDownMarketingEntity> eWrapper = new EntityWrapper<>(new CutDownMarketingEntity());
        eWrapper.eq("id", mCutDownMarketing.getId());
        Boolean isSuccess = update(mCutDownMarketing, eWrapper);
        return isSuccess;
    }

    @Override
    public void modifyCutDownCount(CutDownMarketingEntity mCutDownMarketing) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("id",mCutDownMarketing.getId());
        baseMapper.modifyCutDownCount(eWrapper);
    }

    @Override
    public Boolean updateState(Long id, Integer enabledFlag) throws Exception {
        CutDownMarketingEntity mCutDownMarketing = new CutDownMarketingEntity();
        mCutDownMarketing.setEnabledFlag(enabledFlag);
        EntityWrapper<CutDownMarketingEntity> eWrapper = new EntityWrapper<>(new CutDownMarketingEntity());
        eWrapper.eq("id", id);
        Boolean isSuccess = update(mCutDownMarketing, eWrapper);
        return isSuccess;
    }

    @Override
    public CutDownMarketingEntity queryNewCutDown(CutDownMarketingEntity mCutDownMarketing) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identity",mCutDownMarketing.getBrandIdentity());
        eWrapper.eq("shop_identity",mCutDownMarketing.getShopIdentity());
        eWrapper.eq("enabled_flag",1);
        eWrapper.eq("status_flag",1);
        CutDownMarketingEntity cc = baseMapper.queryNewCutDown(eWrapper);
        return cc;
    }

    @Override
    public List<CutDownReportModel> queryCutDownReport(ReportMarketingModel mReportMarketingModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("m.brand_identity", mReportMarketingModel.getBrandIdenty());
        eWrapper.eq("m.shop_identity", mReportMarketingModel.getShopIdenty());
        eWrapper.eq("m.status_flag", 1);
//        eWrapper.between("m.server_create_time", mReportMarketingModel.getStartDate(), mReportMarketingModel.getEndDate());
        List<CutDownReportModel> listData = baseMapper.queryCutDownReport(eWrapper);
        return listData;
    }


}
