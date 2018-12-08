package com.zhongmei.yunfu.controller.api;


import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.BrandModel;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 门店品牌
 */
@RestController
@RequestMapping("/wxapp/brand")
public class BrandApiController {

    @Autowired
    BrandService mBrandService;
    @Autowired
    CommercialService mCommercialService;

    @GetMapping("/queryBrand")
    public BaseDataModel queryBrandByAppId(Model model, BrandEntity mBrand) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            BrandModel mBrandModel = new BrandModel();
            BrandEntity brand = mBrandService.queryBrandByAppId(mBrand.getId());
            mBrandModel.setBrand(brand);
            List<CommercialEntity> mCommercial = mCommercialService.queryCommercialByBrandId(brand.getId());
            mBrandModel.setListCommercial(mCommercial);

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取门店信息成功");
            mBaseDataModel.setData(mBrandModel);
        } catch (Exception e) {
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取门店信息失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }
}
