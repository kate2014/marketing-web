package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.api.model.WxTempMsgFormId;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.WxFormEntity;
import com.zhongmei.yunfu.domain.mapper.WxFormMapper;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.WxFormService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-11-09
 */
@Service
public class WxFormServiceImpl extends ServiceImpl<WxFormMapper, WxFormEntity> implements WxFormService {

    @Autowired
    CustomerService customerService;

    @Override
    public Boolean addOrUpdateWxForm(WxTempMsgFormId wxTempMsgFormId) throws Exception {
        /*WxFormEntity wxFormEntityQuery = new WxFormEntity();
        wxFormEntityQuery.setBrandIdenty(formId.getBrandIdenty());
        wxFormEntityQuery.setShopIdenty(formId.getShopIdenty());
        wxFormEntityQuery.setOpenId(formId.getOpenId());
        WxFormEntity wxFormEntity = queryFormByOpenId(wxFormEntityQuery);
        if (wxFormEntity == null) {
            wxFormEntity = new WxFormEntity();
            wxFormEntity.baseCreate(formId.getCreatorId(), formId.getCreatorName());
        }
        wxFormEntity.baseUpdate(formId.getCreatorId(), formId.getCreatorName());
        wxFormEntity.setServerCreateTime(new Date());
        Boolean isSuccess = insertOrUpdate(wxFormEntity);*/

        CustomerEntity customerEntity = customerService.queryCustomerByThirdId(wxTempMsgFormId.getBrandIdenty(), wxTempMsgFormId.getShopIdenty(), wxTempMsgFormId.getOpenId());
        WxFormEntity wxFormEntity = new WxFormEntity();
        wxFormEntity.setCustomerId(customerEntity.getId());
        wxFormEntity.setOpenId(wxTempMsgFormId.getOpenId());
        wxFormEntity.setFormId(wxTempMsgFormId.getFormId());
        wxFormEntity.setBrandIdenty(wxTempMsgFormId.getBrandIdenty());
        wxFormEntity.setShopIdenty(wxTempMsgFormId.getShopIdenty());
        //wxFormEntity.baseCreate(wxTempMsgFormId.getCreatorId(), wxTempMsgFormId.getCreatorName());
        wxFormEntity.setServerCreateTime(new Date());
        Boolean isSuccess = insertOrUpdate(wxFormEntity);
        return isSuccess;
    }

    @Override
    public Boolean updateWxForm(WxFormEntity mWxFormEntity) throws Exception {
        EntityWrapper<WxFormEntity> eWrapper = new EntityWrapper<>(new WxFormEntity());
        eWrapper.eq("brand_identy", mWxFormEntity.getBrandIdenty());
        eWrapper.eq("shop_identy", mWxFormEntity.getShopIdenty());
        eWrapper.eq("open_id", mWxFormEntity.getOpenId());
        Boolean isSuccess = update(mWxFormEntity, eWrapper);
        return isSuccess;
    }

    @Override
    public WxFormEntity queryFormByOpenId(WxFormEntity mWxFormEntity) throws Exception {
        EntityWrapper<WxFormEntity> eWrapper = new EntityWrapper<>(new WxFormEntity());
        eWrapper.eq("brand_identy", mWxFormEntity.getBrandIdenty());
        eWrapper.eq("shop_identy", mWxFormEntity.getShopIdenty());
        eWrapper.eq("open_id", mWxFormEntity.getOpenId());
        WxFormEntity wxFormEntity = selectOne(eWrapper);
        return wxFormEntity;
    }

    @Override
    public WxFormEntity queryFormUnusedByOpenId(Long shopIdenty, Long brandIdenty, Long customerId) {
        Collection<Long> customerIdsById = customerService.getCustomerIdsById(customerId);
        WxFormEntity wxFormEntity = new WxFormEntity();
        wxFormEntity.setBrandIdenty(brandIdenty);
        wxFormEntity.setShopIdenty(shopIdenty);
        wxFormEntity.setStatus(1);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7 + 1);
        String format = DateFormatUtil.format(calendar.getTime(), DateFormatUtil.FORMAT_DATE) + " 00:00:00";
        EntityWrapper<WxFormEntity> wrapper = new EntityWrapper<>(wxFormEntity);
        wrapper.ge("server_create_time", format)
                .in("customer_id", customerIdsById)
        .orderBy("server_create_time");
        return selectOne(wrapper);
    }

    @Override
    public List<WxFormEntity> queryFormList(WxFormEntity mWxFormEntity) throws Exception {

        return null;
    }
}
