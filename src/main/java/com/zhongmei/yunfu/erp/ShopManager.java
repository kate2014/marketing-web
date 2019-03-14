package com.zhongmei.yunfu.erp;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.erp.model.ERPBrandModel;
import com.zhongmei.yunfu.erp.model.ERPCommercialModel;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商户管理
 */
@Controller
@RequestMapping("/internal/erp/shopManager")
public class ShopManager extends BaseController {

    @Autowired
    CommercialService mCommercialService;
    @Autowired
    BrandService mBrandService;

    @RequestMapping("/brandList")
    public String brandList(Model model, ERPBrandModel mERPBrandModel){

        try {
            Page<BrandEntity> listBrand = mBrandService.queryBrandList(mERPBrandModel);
            model.addAttribute("listBrand",listBrand.getRecords());
            setWebPage(model, "/erp/shopManager/brandList", listBrand, mERPBrandModel);

        }catch (Exception e){
            e.printStackTrace();
        }

        return "brandlist";
    }

    @RequestMapping("/shopList")
    public String shopList(Model model, ERPCommercialModel mCommercialModel){

        try {

            Page<CommercialEntity> listCommercail = mCommercialService.queryCommercialList(mCommercialModel,mCommercialModel.getPageNo(), mCommercialModel.getPageSize());

            model.addAttribute("listCommercail",listCommercail.getRecords());

        }catch (Exception e){
            e.printStackTrace();

        }

        return "";
    }

    @RequestMapping("/createBrand")
    public String createBrand(){

        return "";
    }
}
