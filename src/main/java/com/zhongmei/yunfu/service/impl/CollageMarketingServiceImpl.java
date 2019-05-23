package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CollageMarketingModel;
import com.zhongmei.yunfu.controller.model.CollageReportModel;
import com.zhongmei.yunfu.controller.model.ReportMarketingModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.CollageMarketingEntity;
import com.zhongmei.yunfu.domain.mapper.CollageMarketingMapper;
import com.zhongmei.yunfu.service.CollageMarketingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 拼团活动 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-10
 */
@Service
public class CollageMarketingServiceImpl extends ServiceImpl<CollageMarketingMapper, CollageMarketingEntity> implements CollageMarketingService {

    @Override
    public Boolean createCollage(CollageMarketingEntity mCollageMarketing) throws Exception {

        Boolean isSuccess = insert(mCollageMarketing);
        return isSuccess;
    }

    @Override
    public Page<CollageMarketingEntity> queryCollageList(CollageMarketingModel mCollageMarketingModel) throws Exception {
        CollageMarketingEntity mCollageMarketing = new CollageMarketingEntity();
        Page<CollageMarketingEntity> listPage = new Page<>(mCollageMarketingModel.getPageNo(), mCollageMarketingModel.getPageSize());
        EntityWrapper<CollageMarketingEntity> eWrapper = new EntityWrapper<>(mCollageMarketing);
        eWrapper.setSqlSelect("id,name,begin_time,end_time,collage_people_count,collage_price,original_price,join_count,open_group_count,finish_group_count,enabled_flag,profile,img_url,product_id,product_name");
        eWrapper.eq("brand_identity", mCollageMarketingModel.getBrandIdentity());

        eWrapper.eq("status_flag", 1);

        if(mCollageMarketingModel.getShopIdentity() != null){
            eWrapper.eq("shop_identity", mCollageMarketingModel.getShopIdentity());
        }
        if (mCollageMarketingModel.getEnabledFlag() != null) {
            eWrapper.eq("enabled_flag", mCollageMarketingModel.getEnabledFlag());
        }
        if (mCollageMarketingModel.getName() != null) {
            eWrapper.like("name", mCollageMarketingModel.getName());
        }
        if(mCollageMarketingModel.getSourceType() != null){
            eWrapper.eq("source_type",mCollageMarketingModel.getSourceType());
        }

        eWrapper.orderBy("server_create_time", false);

        Page<CollageMarketingEntity> listData = selectPage(listPage, eWrapper);

        return listData;
    }

    @Override
    public CollageMarketingEntity findCollageById(Long id) throws Exception {

        CollageMarketingEntity mCollageMarketing = selectById(id);
        return mCollageMarketing;
    }

    @Override
    public CollageMarketingEntity queryCollageById(Long id) throws Exception {
        EntityWrapper<CollageMarketingEntity> eWrapper = new EntityWrapper<>(new CollageMarketingEntity());
        eWrapper.eq("id", id);
        eWrapper.setSqlSelect("id,name,validity_period,collage_price,original_price,img_url,profile,collage_people_count,max_open_group,open_group_count,finish_group_count,join_count,begin_time,end_time,enabled_flag");
        CollageMarketingEntity mCollageMarketing = selectOne(eWrapper);
        return mCollageMarketing;
    }

    @Override
    public CollageMarketingEntity queryNewCollage(CollageMarketingEntity mCollageMarketing) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identity", mCollageMarketing.getBrandIdentity());
        eWrapper.eq("shop_identity", mCollageMarketing.getShopIdentity());
        eWrapper.eq("enabled_flag", 1);
        eWrapper.eq("status_flag", 1);
        CollageMarketingEntity cm = baseMapper.queryNewCollage(eWrapper);

        return cm;
    }

    @Override
    public List<CollageReportModel> queryCollageReport(ReportMarketingModel mReportMarketingModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("m.brand_identity", mReportMarketingModel.getBrandIdenty());
        eWrapper.eq("m.shop_identity", mReportMarketingModel.getShopIdenty());
        eWrapper.eq("m.status_flag", 1);
//        eWrapper.between("m.server_create_time", mReportMarketingModel.getStartDate(), mReportMarketingModel.getEndDate());
        List<CollageReportModel> listData = baseMapper.queryCollageReport(eWrapper);
        return listData;
    }

    @Override
    public Boolean deleteCollageById(Long id) throws Exception {

        Boolean isSuccess = deleteById(id);
        return isSuccess;
    }

    @Override
    public Boolean modifyCollage(CollageMarketingEntity mCollageMarketing) throws Exception {

        EntityWrapper<CollageMarketingEntity> eWrapper = new EntityWrapper<>(new CollageMarketingEntity());
        eWrapper.eq("id", mCollageMarketing.getId());
        Boolean isSuccess = update(mCollageMarketing, eWrapper);

        return isSuccess;
    }

    @Override
    public Boolean updateState(Long id, Integer enabledFlag) throws Exception {
        CollageMarketingEntity mCollageMarketing = new CollageMarketingEntity();
        mCollageMarketing.setId(id);
        mCollageMarketing.setEnabledFlag(enabledFlag);
        Boolean isSuccess = updateById(mCollageMarketing);
        return isSuccess;
    }
}
