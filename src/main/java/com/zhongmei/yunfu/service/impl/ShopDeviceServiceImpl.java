package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.ShopDeviceEntity;
import com.zhongmei.yunfu.domain.mapper.ShopDeviceMapper;
import com.zhongmei.yunfu.service.ShopDeviceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2019-02-26
 */
@Service
public class ShopDeviceServiceImpl extends ServiceImpl<ShopDeviceMapper, ShopDeviceEntity> implements ShopDeviceService {


    @Override
    public ShopDeviceEntity queryShopDeviceById(Long id) throws Exception {
        ShopDeviceEntity mShopDeviceEntity = selectById(id);
        return mShopDeviceEntity;
    }

    @Override
    public Boolean addShopDevice(ShopDeviceEntity mShopDeviceEntity) throws Exception {

        Boolean isSuccess = insert(mShopDeviceEntity);
        return isSuccess;
    }

    @Override
    public Boolean modifyShopDevice(ShopDeviceEntity mShopDeviceEntity) throws Exception {

        Boolean isSuccess = updateById(mShopDeviceEntity);
        return isSuccess;
    }

    @Override
    public Boolean deleteShopDevice(Long id) throws Exception {
        Boolean isSuccess = deleteById(id);
        return isSuccess;
    }

    @Override
    public Page<ShopDeviceEntity> queryShopDevice(ShopDeviceEntity mShopDeviceEntity, int pageIdx, int pageSize) throws Exception {
        EntityWrapper<ShopDeviceEntity> eWrapper = new EntityWrapper<>(new ShopDeviceEntity());
        if(mShopDeviceEntity.getBrandIdenty() != null){
            eWrapper.eq("brand_identy",mShopDeviceEntity.getBrandIdenty());
        }
        if(mShopDeviceEntity.getShopIdenty() != null){
            eWrapper.eq("shop_identy",mShopDeviceEntity.getShopIdenty());
        }
        if(mShopDeviceEntity.getDeviceNo() != null){
            eWrapper.eq("device_no",mShopDeviceEntity.getDeviceNo());
        }
        if(mShopDeviceEntity.getDeviceMac() != null){
            eWrapper.eq("device_mac",mShopDeviceEntity.getDeviceMac());
        }

        eWrapper.orderBy("server_update_time",false);

        Page<ShopDeviceEntity> listPage = new Page<>(pageIdx,pageSize);

        Page<ShopDeviceEntity> listData = selectPage(listPage,eWrapper);

        return listData;
    }
}
