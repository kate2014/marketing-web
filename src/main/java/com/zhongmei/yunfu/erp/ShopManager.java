package com.zhongmei.yunfu.erp;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.BrandModel;
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
            model.addAttribute("brandlist",listBrand.getRecords());
            setWebPage(model, "/internal/erp/shopManager/brandList", listBrand, mERPBrandModel);

            model.addAttribute("mERPBrandModel",mERPBrandModel);
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

    @RequestMapping("/gotoCreateBrand")
    public String gotoCreateBrand(Model model, ERPBrandModel mERPBrandModel){

        try {
            BrandEntity mBrandEntity = new BrandEntity ();
            if(mERPBrandModel.getId() != null){
                mBrandEntity = mBrandService.queryBrandById(mERPBrandModel.getId());
            }

            model.addAttribute("mERPBrandModel",mERPBrandModel);

            model.addAttribute("mBrandModel",mBrandEntity);

            return "brand_create";

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/createBrand")
    public String createBrand(Model model, ERPBrandModel mERPBrandModel){
        try {
            Boolean isSuccess = true;
            if(mERPBrandModel.getId() == null){
                mERPBrandModel.setStatusFlag(1);
                isSuccess = mBrandService.createBrand(mERPBrandModel);
            }else{
                isSuccess = mBrandService.modifyBrandById(mERPBrandModel);
            }
            if(isSuccess){
                return redirect("/internal/erp/shopManager/brandList?creatorId="+mERPBrandModel.getCreatorId()+"&creatorName="+mERPBrandModel.getCreatorName());
            }else{
                return "fail";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping("/deleteBrand")
    public String deleteBrand(Model model, ERPBrandModel mERPBrandModel){
        try {
            if(mERPBrandModel.getId()!=null){
                mBrandService.deleteBrandById(mERPBrandModel.getId());
                return "success";
            }else{
                return "fail";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

}
