package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.*;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.FlashSalesMarketingEntity;
import com.zhongmei.yunfu.domain.mapper.FlashSalesMarketingMapper;
import com.zhongmei.yunfu.service.FlashSalesMarketingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 秒杀活动 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-10
 */
@Service
public class FlashSalesMarketingServiceImpl extends ServiceImpl<FlashSalesMarketingMapper, FlashSalesMarketingEntity> implements FlashSalesMarketingService {

    @Override
    public Boolean createFlashSales(FlashSalesMarketingEntity mFlashSalesMarketing) throws Exception {
        Boolean isSuccess = insert(mFlashSalesMarketing);
        return isSuccess;
    }

    @Override
    public Page<FlashSalesMarketingEntity> queryFlashSalesList(FlashSalesModel mFlashSalesModel) throws Exception {
        FlashSalesMarketingEntity mCollageMarketing = new FlashSalesMarketingEntity();
        Page<FlashSalesMarketingEntity> listPage = new Page<>(mFlashSalesModel.getPageNo(), mFlashSalesModel.getPageSize());
        EntityWrapper<FlashSalesMarketingEntity> eWrapper = new EntityWrapper<>(mCollageMarketing);
        eWrapper.setSqlSelect("id,name,begin_time,end_time,original_price,flash_price,sales_count,sold_count,img_url,enabled_flag,profile,img_url");
        eWrapper.eq("brand_identity", mFlashSalesModel.getBrandIdentity());

        if(mFlashSalesModel.getShopIdentity() != null){
            eWrapper.eq("shop_identity", mFlashSalesModel.getShopIdentity());
        }

        if(mFlashSalesModel.getSourceType() != null){
            eWrapper.eq("source_type", mFlashSalesModel.getSourceType());
        }

        eWrapper.eq("status_flag", 1);

        if (mFlashSalesModel.getEnabledFlag() != null) {
            eWrapper.eq("enabled_flag", mFlashSalesModel.getEnabledFlag());
        }
        if (mFlashSalesModel.getName() != null) {
            eWrapper.like("name", mFlashSalesModel.getName());
        }

        eWrapper.orderBy("server_create_time", false);

        Page<FlashSalesMarketingEntity> listData = selectPage(listPage, eWrapper);

        return listData;
    }

    @Override
    public FlashSalesMarketingEntity findFlashSalesById(Long id) throws Exception {
        FlashSalesMarketingEntity mFlashSalesMarketing = selectById(id);
        return mFlashSalesMarketing;
    }

    @Override
    public FlashSalesMarketingEntity queryFlashSalesById(Long id) throws Exception {
        EntityWrapper<FlashSalesMarketingEntity> eWrapper = new EntityWrapper<>(new FlashSalesMarketingEntity());
        eWrapper.eq("id", id);
        eWrapper.setSqlSelect("id,name,validity_period,begin_time,end_time,sales_count,sold_count,enabled_flag");
        FlashSalesMarketingEntity mFlashSalesMarketing = selectOne(eWrapper);
        return mFlashSalesMarketing;
    }

    @Override
    public FlashSalesMarketingEntity queryNewFlashSales(FlashSalesMarketingEntity mFlashSalesMarketing) throws Exception {

        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identity",mFlashSalesMarketing.getBrandIdentity());
        eWrapper.eq("shop_identity",mFlashSalesMarketing.getShopIdentity());
        eWrapper.eq("enabled_flag",1);
        eWrapper.eq("status_flag",1);

        FlashSalesMarketingEntity fs = baseMapper.queryNewFlashSales(eWrapper);
        return fs;
    }

    @Override
    public Boolean deleteFlashSalesById(Long id) throws Exception {
        Boolean isSuccess = deleteById(id);
        return isSuccess;
    }

    @Override
    public Boolean modifyFlashSales(FlashSalesMarketingEntity mFlashSalesMarketing) throws Exception {

        EntityWrapper<FlashSalesMarketingEntity> eWrapper = new EntityWrapper<>(new FlashSalesMarketingEntity());
        eWrapper.eq("id", mFlashSalesMarketing.getId());
        Boolean isSuccess = update(mFlashSalesMarketing, eWrapper);

        return isSuccess;
    }

    @Override
    public void modifyFlashSalesCount(FlashSalesMarketingEntity mFlashSalesMarketing) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("id", mFlashSalesMarketing.getId());
        baseMapper.modifyFlashSalesCount(eWrapper);
    }

    @Override
    public Boolean updateState(Long id, Integer enabledFlag) throws Exception {
        FlashSalesMarketingEntity mFlashSalesMarketing = new FlashSalesMarketingEntity();
        mFlashSalesMarketing.setEnabledFlag(enabledFlag);
        EntityWrapper<FlashSalesMarketingEntity> eWrapper = new EntityWrapper<>(new FlashSalesMarketingEntity());
        eWrapper.eq("id", id);
        Boolean isSuccess = update(mFlashSalesMarketing, eWrapper);
        return isSuccess;
    }

    @Override
    public List<FlashSalesReportModel> queryFlashSalesReport(ReportMarketingModel mReportMarketingModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identity", mReportMarketingModel.getBrandIdenty());
        eWrapper.eq("shop_identity", mReportMarketingModel.getShopIdenty());
        eWrapper.eq("status_flag", 1);
//        eWrapper.between("server_create_time", mReportMarketingModel.getStartDate(), mReportMarketingModel.getEndDate());
        List<FlashSalesReportModel> listData = baseMapper.queryFlashSalesReport(eWrapper);
        return listData;
    }

    @Override
    public List<FlashSalesCustomerModel> queryFlashSalesByCustomer(Long brandIdenty, Long shopIdenty, Long customerId) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("w.brand_identy", brandIdenty);
        eWrapper.eq("w.shop_identy", shopIdenty);
        eWrapper.eq("w.customer_id", customerId);
        eWrapper.eq("w.type", 3);
        eWrapper.eq("w.enabled_flag", 1);
        eWrapper.eq("w.status_flag", 1);

        List<FlashSalesCustomerModel> listData = baseMapper.queryFlashSalesByCustomer(eWrapper);
        return listData;
    }
}
