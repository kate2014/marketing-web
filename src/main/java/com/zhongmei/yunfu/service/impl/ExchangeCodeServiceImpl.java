package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.ExchangeCodeEntity;
import com.zhongmei.yunfu.domain.entity.ExpandedCommissionEntity;
import com.zhongmei.yunfu.domain.mapper.ExchangeCodeMapper;
import com.zhongmei.yunfu.service.ExchangeCodeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 提成兑换编码
 * </p>
 *
 */
@Service
public class ExchangeCodeServiceImpl extends ServiceImpl<ExchangeCodeMapper, ExchangeCodeEntity> implements ExchangeCodeService {


    @Override
    public ExchangeCodeEntity queryCode(ExchangeCodeEntity entity) throws Exception {

        EntityWrapper<ExchangeCodeEntity> eWrapper = new EntityWrapper<>(new ExchangeCodeEntity());
        eWrapper.eq("shop_identy", entity.getShopIdenty());
        eWrapper.eq("brand_identy", entity.getBrandIdenty());
        if(entity.getCustomerId() != null && !entity.getCustomerId().equals("")){
            eWrapper.eq("customer_id", entity.getCustomerId());
        }
        if(entity.getMobile() != null && !entity.getMobile().equals("")){
            eWrapper.eq("mobile", entity.getMobile());
        }

        return selectOne(eWrapper);
    }

    @Override
    public ExchangeCodeEntity installCode(ExchangeCodeEntity entity) throws Exception {

        insert(entity);

        return entity;
    }

    @Override
    public ExchangeCodeEntity updateCode(ExchangeCodeEntity entity) throws Exception {
        EntityWrapper<ExchangeCodeEntity> eWrapper = new EntityWrapper<>(new ExchangeCodeEntity());
        eWrapper.eq("shop_identy", entity.getShopIdenty());
        eWrapper.eq("brand_identy", entity.getBrandIdenty());
        if(entity.getCustomerId() != null && !entity.getCustomerId().equals("")){
            eWrapper.eq("customer_id", entity.getCustomerId());
        }
        if(entity.getMobile() != null && !entity.getMobile().equals("")){
            eWrapper.eq("mobile", entity.getMobile());
        }

        update(entity,eWrapper);

        return entity;
    }

    @Override
    public Boolean deleteCode(ExchangeCodeEntity entity) throws Exception {
        EntityWrapper<ExchangeCodeEntity> eWrapper = new EntityWrapper<>(new ExchangeCodeEntity());
        eWrapper.eq("shop_identy", entity.getShopIdenty());
        eWrapper.eq("brand_identy", entity.getBrandIdenty());
        if(entity.getCustomerId() != null && !entity.getCustomerId().equals("")){
            eWrapper.eq("customer_id", entity.getCustomerId());
        }
        if(entity.getMobile() != null && !entity.getMobile().equals("")){
            eWrapper.eq("mobile", entity.getMobile());
        }
        return delete(eWrapper);
    }
}
