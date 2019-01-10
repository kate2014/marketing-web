package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.api.ApiResponseStatusException;
import com.zhongmei.yunfu.erp.model.ERPCommercialModel;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;

import java.util.List;

/**
 * <p>
 * 商户信息表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface CommercialService extends IService<CommercialEntity> {

    /**
     * 根据设备获取门店信息
     *
     * @param device
     */
    CommercialEntity selectByDevice(String device, boolean isCheckState) throws ApiResponseStatusException;

    /**
     * 根据品牌id获取所以门店信息
     *
     * @param brandId
     * @return
     */
    List<CommercialEntity> queryCommercialByBrandId(Long brandId) throws Exception;

    /**
     * 根据id获取门店信息
     *
     * @param commercialId
     * @return
     * @throws Exception
     */
    CommercialEntity queryCommercialById(Long commercialId) throws Exception;

    /**
     * 编辑门店基本信息
     *
     * @return
     * @throws Exception
     */
    Boolean modifyCommercial(CommercialEntity mCommercialEntity) throws Exception;

    /**
     * 获取所有门店信息
     * @param mCommercialModel
     * @return
     * @throws Exception
     */
    Page<CommercialEntity> queryCommercialList(ERPCommercialModel mCommercialModel, int pageIdx, int pageSize)throws Exception;
}
