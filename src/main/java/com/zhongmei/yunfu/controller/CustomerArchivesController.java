package com.zhongmei.yunfu.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CustomerArchivesModel;
import com.zhongmei.yunfu.domain.entity.CustomerArchivesEntity;
import com.zhongmei.yunfu.service.CustomerArchivesService;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.service.TaskRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * 会员档案
 */
@Controller
@RequestMapping("/customerArchives")
public class CustomerArchivesController extends BaseController{

    @Autowired
    CustomerArchivesService mCustomerArchivesService;
    @Autowired
    TaskRemindService mTaskRemindService;

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

    @RequestMapping("/addArchives")
    public String addArchives(Model model, CustomerArchivesModel mCustomerArchivesModel){
        String actionSuccess = "success";
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorname = LoginManager.get().getUser().getCreatorName();

        try {

            if(mCustomerArchivesModel.getArchivesId() != null){
                updateArchives(mCustomerArchivesModel);
            }else{
                CustomerArchivesEntity mCustomerArchivesEntity = new CustomerArchivesEntity();
                mCustomerArchivesEntity.setBrandIdenty(brandIdentity);
                mCustomerArchivesEntity.setShopIdenty(shopIdentity);
                mCustomerArchivesEntity.setCustomerId(mCustomerArchivesModel.getCustomerId());
                mCustomerArchivesEntity.setTitle(mCustomerArchivesModel.getTitle());
                mCustomerArchivesEntity.setContent(mCustomerArchivesModel.getContent());
                mCustomerArchivesEntity.setType(1);
                mCustomerArchivesEntity.setStatusFlag(1);
                mCustomerArchivesEntity.setCreatorId(creatorId);
                mCustomerArchivesEntity.setCreatorName(creatorname);
                mCustomerArchivesEntity.setServerCreateTime(new Date());
                mCustomerArchivesEntity.setUpdatorId(creatorId);
                mCustomerArchivesEntity.setUpdatorName(creatorname);
                mCustomerArchivesEntity.setServerUpdateTime(new Date());

                mCustomerArchivesService.addCustomerArchives(mCustomerArchivesEntity);
            }

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return String.format("redirect:/customerArchives/list?brandIdenty=%d&shopIdenty=%d&customerId=%d&successOrfail=%s",
                brandIdentity, shopIdentity,mCustomerArchivesModel.getCustomerId(),actionSuccess);
    }

    @RequestMapping("/deleteArchives")
    public String deleteArchives(Model model, CustomerArchivesModel mCustomerArchivesModel){

        try {

            mCustomerArchivesService.deleteArchives(mCustomerArchivesModel.getId());
            mTaskRemindService.deleteTaskByDocId(mCustomerArchivesModel.getId());

        }catch (Exception e){
            e.printStackTrace();

        }
        return "success";

    }

    public boolean updateArchives(CustomerArchivesModel mCustomerArchivesModel)throws Exception{

        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorname = LoginManager.get().getUser().getCreatorName();

        CustomerArchivesEntity mCustomerArchivesEntity = new CustomerArchivesEntity();
        mCustomerArchivesEntity.setId(mCustomerArchivesModel.getArchivesId());
        mCustomerArchivesEntity.setTitle(mCustomerArchivesModel.getTitle());
        mCustomerArchivesEntity.setContent(mCustomerArchivesModel.getContent());
        mCustomerArchivesEntity.setUpdatorId(creatorId);
        mCustomerArchivesEntity.setUpdatorName(creatorname);
        mCustomerArchivesEntity.setServerUpdateTime(new Date());
        return mCustomerArchivesService.modfityArchives(mCustomerArchivesEntity);

    }
}
