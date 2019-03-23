package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.api.pos.vo.SystemVersionReq;
import com.zhongmei.yunfu.domain.entity.ShopDeviceEntity;
import com.zhongmei.yunfu.domain.entity.SystemVersionEntity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pigeon88
 * @since 2019-02-26
 */
public interface ShopDeviceService extends IService<ShopDeviceEntity> {

    /**
     * 根据id获取设备信息
     * @param id
     * @return
     * @throws Exception
     */
    ShopDeviceEntity queryShopDeviceById(Long id)throws Exception;
    /**
     * 绑定设备
     * @param mShopDeviceEntity
     * @return
     */
    Boolean addShopDevice(ShopDeviceEntity mShopDeviceEntity) throws Exception;

    /**
     * 编辑绑定设备
     * @param mShopDeviceEntity
     * @return
     * @throws Exception
     */
    Boolean modifyShopDevice(ShopDeviceEntity mShopDeviceEntity) throws Exception;

    /**
     * 根据id删除绑定的设备
     * @param id
     * @return
     * @throws Exception
     */
    Boolean deleteShopDevice(Long id) throws Exception;

    /**
     * 搜索绑定的设备
     * @param mShopDeviceEntity
     * @return
     * @throws Exception
     */
    Page<ShopDeviceEntity> queryShopDevice(ShopDeviceEntity mShopDeviceEntity, int pageIdx, int pageSize) throws Exception;

}
