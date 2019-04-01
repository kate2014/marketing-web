package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.controller.model.BrandModel;
import com.zhongmei.yunfu.erp.model.ERPBrandModel;
import com.zhongmei.yunfu.domain.entity.BrandEntity;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface BrandService extends IService<BrandEntity> {

    /**
     * 根据微信appid获取商家品牌信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    BrandEntity queryBrandByAppId(Long id) throws Exception;

    /**
     * 获取所有品牌数据
     * @param mERPBrandModel
     * @return
     * @throws Exception
     */
    Page<BrandEntity> queryBrandList(ERPBrandModel mERPBrandModel)throws Exception;

    /**
     * 新建品牌
     * @param mERPBrandModel
     * @return
     * @throws Exception
     */
    Boolean createBrand(ERPBrandModel mERPBrandModel)throws Exception;

    /**
     * 根据ID查询品牌信息
     * @param brandId
     * @return
     * @throws Exception
     */
    BrandEntity queryBrandById(Long brandId)throws Exception;

    /**
     * 根据id删除品牌
     * @return
     */
    Boolean deleteBrandById(Long brandId)throws Exception;

    /**
     * 编辑品牌信息
     * @param mERPBrandModel
     * @return
     * @throws Exception
     */
    Boolean modifyBrandById(ERPBrandModel mERPBrandModel)throws Exception;
}
