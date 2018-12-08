package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.BrandEntity;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author pigeon88
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
}
