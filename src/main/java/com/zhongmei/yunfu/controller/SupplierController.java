package com.zhongmei.yunfu.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.SupplierModel;
import com.zhongmei.yunfu.domain.entity.SupplierEntity;
import com.zhongmei.yunfu.domain.entity.base.SupperEntity;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * 供货商管理
 */
@Controller
@RequestMapping("/supplier")
public class SupplierController extends BaseController {

    @Autowired
    SupplierService mSupplierService;

    @RequestMapping("/list")
    public String dishShopMainPage(Model model, SupplierModel mSupplierModel) {
        try{
            Long brandIdenty = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdenty = LoginManager.get().getUser().getShopIdenty();

            SupplierEntity mSupplierEntity = new SupplierEntity();
            mSupplierEntity.setBrandIdenty(brandIdenty);
            mSupplierEntity.setShopIdenty(shopIdenty);
            mSupplierEntity.setName(mSupplierModel.getName());
            mSupplierEntity.setContacts(mSupplierModel.getContacts());
            mSupplierEntity.setContactsPhone(mSupplierModel.getContactsPhone());

            Page<SupplierEntity> listPage = mSupplierService.querySupplierPage(mSupplierEntity,mSupplierModel.getPageNo(),mSupplierModel.getPageSize());

            setWebPage(model, "/supplier/list", listPage, mSupplierEntity);

            model.addAttribute("listSupplier", listPage.getRecords());

            model.addAttribute("mSupplierModel", mSupplierModel);

            return "supplier_list";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/addOrUpdateSupplier")
    public String addOrUpdateSupplier(Model model, SupplierModel mSupplierModel) {

        String actionSuccess = "success";

        Long brandIdenty = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdenty = LoginManager.get().getUser().getShopIdenty();
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorname = LoginManager.get().getUser().getCreatorName();

        try {
            SupplierEntity mSupplierEntity = new SupplierEntity();
            mSupplierEntity.setBrandIdenty(brandIdenty);
            mSupplierEntity.setShopIdenty(shopIdenty);
            mSupplierEntity.setName(mSupplierModel.getName());
            mSupplierEntity.setAddress(mSupplierModel.getAddress());
            mSupplierEntity.setContacts(mSupplierModel.getContacts());
            mSupplierEntity.setContactsPhone(mSupplierModel.getContactsPhone());

            mSupplierEntity.setUpdatorId(creatorId);
            mSupplierEntity.setUpdatorName(creatorname);
            mSupplierEntity.setServerUpdateTime(new Date());
            if(mSupplierModel.getSupplierId() == null){
                mSupplierEntity.setCreatorId(creatorId);
                mSupplierEntity.setCreatorName(creatorname);
                mSupplierEntity.setServerCreateTime(new Date());
            }

            mSupplierService.addOrUpdateSupplier(mSupplierEntity);
        }catch (Exception e){
            e.printStackTrace();
        }

        return String.format("redirect:/supplier/list?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&successOrfail=%s",
                brandIdenty, shopIdenty, creatorId, creatorname,actionSuccess);
    }

    @RequestMapping("/deleteSupplier")
    public String deleteSupplier(Model model, SupplierModel mSupplierModel) {
        try {
            Boolean isSuccess = mSupplierService.deleteSupplierById(mSupplierModel.getSupplierId());
            if(isSuccess){
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
