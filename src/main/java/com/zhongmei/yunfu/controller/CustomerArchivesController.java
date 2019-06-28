package com.zhongmei.yunfu.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CustomerArchivesModel;
import com.zhongmei.yunfu.domain.entity.CustomerArchivesEntity;
import com.zhongmei.yunfu.service.CustomerArchivesService;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 会员档案
 */
@Controller
@RequestMapping("/customerArchives")
public class CustomerArchivesController extends BaseController{

    @Autowired
    CustomerArchivesService mCustomerArchivesService;

    @RequestMapping("/list")
    public String list(Model model, CustomerArchivesModel mCustomerArchivesModel){

        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        CustomerArchivesEntity mCustomerArchivesEntity = new CustomerArchivesEntity();

        mCustomerArchivesEntity.setBrandIdenty(brandIdentity);
        mCustomerArchivesEntity.setShopIdenty(shopIdentity);
        mCustomerArchivesEntity.setCustomerId(mCustomerArchivesModel.getCustomerId());

        Page<CustomerArchivesEntity> listData = mCustomerArchivesService.queryArchivesPage(mCustomerArchivesEntity,mCustomerArchivesModel.getPageNo(),mCustomerArchivesModel.getPageSize());
        setWebPage(model, "/customerArchives/list", listData, mCustomerArchivesEntity);
        model.addAttribute("listData", listData.getRecords());
        model.addAttribute("mCustomerArchivesModel", mCustomerArchivesModel);
        if(listData != null && listData.getRecords().size()>0){
            model.addAttribute("havaData", '1');
        }else{
            model.addAttribute("havaData", '0');
        }

        return "customer_archives_list";
    }
}
