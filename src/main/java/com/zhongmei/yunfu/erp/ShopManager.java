package com.zhongmei.yunfu.erp;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.erp.model.ERPBrandModel;
import com.zhongmei.yunfu.erp.model.ERPCommercialModel;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    AuthUserService mAuthUserService;
    @Autowired
    AuthRolePermissionService mAuthRolePermissionService;
    @Autowired
    AuthRoleService mAuthRoleService;
    @Autowired
    AuthPermissionService mAuthPermissionService;

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

        return "erp_brandlist";
    }

    @RequestMapping("/shopList")
    public String shopList(Model model, ERPCommercialModel mCommercialModel){

        try {

            Page<CommercialEntity> listCommercail = mCommercialService.queryCommercialList(mCommercialModel,mCommercialModel.getPageNo(), mCommercialModel.getPageSize());

            setWebPage(model, "/internal/erp/shopManager/shopList", listCommercail, mCommercialModel);

            model.addAttribute("listCommercail",listCommercail.getRecords());
            model.addAttribute("mCommercialModel",mCommercialModel);

            return "erp_shoplist";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

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

            return "erp_brand_create";

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

    @RequestMapping("/gotoCreateShop")
    public String gotoCreateShop(Model model, ERPCommercialModel mCommercialModel){

        try {
            CommercialEntity mCommercialEntity = new CommercialEntity();

            if(mCommercialModel.getCommercialId() != null && !mCommercialModel.getCommercialId().equals("")){
                mCommercialEntity = mCommercialService.queryCommercialById(mCommercialModel.getCommercialId());
            }

            model.addAttribute("mCommercialModel",mCommercialModel);

            model.addAttribute("mCommercialEntity",mCommercialEntity);

            return "erp_shop_create";

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/createShop")
    public String createShop(Model model, CommercialEntity mCommercialEntity){

        try {
            Boolean isSuccess = true;
            if(mCommercialEntity.getCommercialId() == null){
                isSuccess = mCommercialService.createCommercial(mCommercialEntity);
            }else{
                isSuccess = mCommercialService.modifyCommercial(mCommercialEntity);
            }

            if(isSuccess){
                return redirect("/internal/erp/shopManager/shopList?creatorId="+mCommercialEntity.getCreatorId()+"&creatorName="+mCommercialEntity.getCreatorName());
            }else{
                return "fail";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/deleteShop")
    public String deleteShop(Model model, ERPCommercialModel mCommercialModel){

        try {

            if(mCommercialModel.getCommercialId()!=null){
                Boolean isSuccess = mCommercialService.deleteCommercial(mCommercialModel);
                if(isSuccess){
                    return "success";
                }else {
                    return "fail";
                }

            }else{
                return "fail";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * 门店数据初始化界面
     * @param model
     * @param mCommercialModel
     * @return
     */
    @RequestMapping("/gotoInitialize")
    public String gotoInitializeShop(Model model, ERPCommercialModel mCommercialModel){

        model.addAttribute("mCommercialModel",mCommercialModel);

        return "erp_shop_initialize";
    }

    /**
     * 门店数据初始化
     * @param model
     * @param mCommercialModel
     * @return
     */
    @RequestMapping("/initialize")
    public String initializeShop(Model model, ERPCommercialModel mCommercialModel){

        try {
            Boolean isSuccess = true;

            //查询是否有过系统角色、用户、权限初始化
            AuthRoleEntity are = new AuthRoleEntity();
            are.setBrandIdenty(mCommercialModel.getBrandId());
            are.setShopIdenty(mCommercialModel.getCommercialId());
            List<AuthRoleEntity> listSystem = mAuthRoleService.querySystemAuthRole(are);

            //已设置过，直接返回成功
            if(listSystem != null && listSystem.size()!=0){
                model.addAttribute("mCommercialModel",mCommercialModel);
                model.addAttribute("successOrfail","success");
                return "erp_shop_initialize";
            }

            //创建角色
            AuthRoleEntity mAuthRoleEntity = new AuthRoleEntity();
            mAuthRoleEntity.setName("超级管理员");
            mAuthRoleEntity.setCode("10001");
            mAuthRoleEntity.setSort(1);
            mAuthRoleEntity.setSourceFlag(2);
            mAuthRoleEntity.setEnableFlag(1);
            mAuthRoleEntity.setCreateAccountByDealer(1);
            mAuthRoleEntity.setCreateAccountByShop(1);
            mAuthRoleEntity.setStatusFlag(1);
            mAuthRoleEntity.setShopIdenty(mCommercialModel.getCommercialId());
            mAuthRoleEntity.setBrandIdenty(mCommercialModel.getBrandId());

            isSuccess = mAuthRoleService.addAuthRole(mAuthRoleEntity);

            //创建员工
            AuthUserEntity mAuthUserEntity = new AuthUserEntity();
            mAuthUserEntity.setName("超级管理员");
            mAuthUserEntity.setMobile("1000000000");
            mAuthUserEntity.setJobNumber("100001");
            mAuthUserEntity.setJobEmployeeType("1");
            mAuthUserEntity.setRoleId(mAuthRoleEntity.getId());
            mAuthUserEntity.setJobEntryTime(DateFormatUtil.format(new Date(),DateFormatUtil.FORMAT_FULL_DATE));
            mAuthUserEntity.setJobPosition(mAuthRoleEntity.getId()+"");
            mAuthUserEntity.setSalaryBase("0");
            mAuthUserEntity.setSalaryPost("0");
            mAuthUserEntity.setAccount("admin");
            mAuthUserEntity.setPassword("40561397f0540e3a2d0273ed9ec3bbc2738a3385");//123456加密后的数据
            mAuthUserEntity.setPasswordNum("40561397f0540e3a2d0273ed9ec3bbc2738a3385");
            mAuthUserEntity.setStatusFlag(1);
            mAuthUserEntity.setBrandIdenty(mCommercialModel.getBrandId());
            mAuthUserEntity.setShopIdenty(mCommercialModel.getCommercialId());

            isSuccess = mAuthUserService.addAuthUser(mAuthUserEntity);

            //初始化分配权限
            List<AuthRolePermissionEntity> listARP = new ArrayList<>();
            List<AuthPermissionEntity> listData = mAuthPermissionService.queryAuthPermission();
            for(AuthPermissionEntity ap:listData){
                AuthRolePermissionEntity mAuthRolePermissionEntity = new AuthRolePermissionEntity();
                mAuthRolePermissionEntity.setRoleId(mAuthUserEntity.getId());
                mAuthRolePermissionEntity.setPermissionId(ap.getId());
                mAuthRolePermissionEntity.setGroupFlag(2);
                mAuthRolePermissionEntity.setPlatform(ap.getPlatform());
                mAuthRolePermissionEntity.setStatusFlag(1);
                mAuthRolePermissionEntity.setBrandIdenty(mCommercialModel.getBrandId());
                mAuthRolePermissionEntity.setShopIdenty(mCommercialModel.getCommercialId());
                listARP.add(mAuthRolePermissionEntity);
            }

            isSuccess = mAuthRolePermissionService.insertBatchData(listARP);

            if(isSuccess){
                model.addAttribute("mCommercialModel",mCommercialModel);
                mCommercialModel.setSuccessOrfail("success");
            }else{
                mCommercialModel.setSuccessOrfail("fail");
            }
            gotoInitializeShop(model, mCommercialModel);
            return "erp_shop_initialize";
        }catch (Exception e){
            e.printStackTrace();
            mCommercialModel.setSuccessOrfail("fail");
            gotoInitializeShop(model, mCommercialModel);
            return "erp_shop_initialize";
        }


    }
}
