package com.zhongmei.yunfu.erp;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.domain.entity.ShopDeviceEntity;
import com.zhongmei.yunfu.erp.model.ERPBrandModel;
import com.zhongmei.yunfu.erp.model.ERPDeviceModel;
import com.zhongmei.yunfu.service.ShopDeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Controller
@RequestMapping("/internal/erp/device")
public class ShopDeviceController extends BaseController {

    @Autowired
    ShopDeviceService mShopDeviceService;

    @RequestMapping("/list")
    public String deviceList(Model model, ERPDeviceModel mERPDeviceModel) {

        try {
            ShopDeviceEntity mShopDeviceEntity = new ShopDeviceEntity();
            mShopDeviceEntity.setBrandIdenty(mERPDeviceModel.getBrandIdenty());
            mShopDeviceEntity.setShopIdenty(mERPDeviceModel.getShopIdenty());
            mShopDeviceEntity.setDeviceMac(mERPDeviceModel.getDeviceMac());
            mShopDeviceEntity.setDeviceNo(mERPDeviceModel.getDeviceNo());

            Page<ShopDeviceEntity> listData = mShopDeviceService.queryShopDevice(mShopDeviceEntity,mERPDeviceModel.getPageNo(),mERPDeviceModel.getPageSize());

            setWebPage(model, "/internal/erp/device/list", listData, mERPDeviceModel);

            model.addAttribute("listData",listData.getRecords());

            model.addAttribute("mERPDeviceModel", mERPDeviceModel);
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "erp_devicelist";
    }

    /**
     * 绑定新设备
     * @param model
     * @param mERPDeviceModel
     * @return
     */
    @RequestMapping("/gotoBinding")
    public String gotoBindingDevice(Model model, ERPDeviceModel mERPDeviceModel){
        ShopDeviceEntity mShopDeviceEntity = new ShopDeviceEntity();
        try{
            if(mERPDeviceModel.getId() != null){
                mShopDeviceEntity = mShopDeviceService.queryShopDeviceById(mERPDeviceModel.getId());
            }
            model.addAttribute("mShopDeviceEntity", mShopDeviceEntity);
            model.addAttribute("mERPDeviceModel", mERPDeviceModel);
            return "erp_binding_device";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/bindingDevice")
    public String bindingDevice(Model model, ERPDeviceModel mERPDeviceModel){

        try{
            ShopDeviceEntity mShopDeviceEntity = new ShopDeviceEntity();
            mShopDeviceEntity.setBrandIdenty(mERPDeviceModel.getBrandIdenty());
            mShopDeviceEntity.setShopIdenty(mERPDeviceModel.getShopIdenty());
            mShopDeviceEntity.setDeviceNo(mERPDeviceModel.getDeviceNo());
            mShopDeviceEntity.setDeviceMac(mERPDeviceModel.getDeviceMac());


            Boolean isSuccess = true;
            if(mERPDeviceModel.getId() != null){
                mShopDeviceEntity.setId(mERPDeviceModel.getId());
                mShopDeviceEntity.setUpdatorId(mERPDeviceModel.getCreatorId());
                mShopDeviceEntity.setUpdatorName(mERPDeviceModel.getCreatorName());
                mShopDeviceEntity.setServerUpdateTime(new Date());
                mShopDeviceEntity.setStatusFlag(mERPDeviceModel.getStatusFlag());
                isSuccess = mShopDeviceService.modifyShopDevice(mShopDeviceEntity);

            }else{
                mShopDeviceEntity.setCreatorId(mERPDeviceModel.getCreatorId());
                mShopDeviceEntity.setCreatorName(mERPDeviceModel.getCreatorName());
                mShopDeviceEntity.setStatusFlag(1);
                isSuccess = mShopDeviceService.addShopDevice(mShopDeviceEntity);
            }

            if(isSuccess){
                return redirect("/internal/erp/device/list?creatorId="+mERPDeviceModel.getCreatorId()+"&creatorName="+mERPDeviceModel.getCreatorName());
            }else{
                return "fail";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/deleteDevice")
    public String deleteDevice(Model model, ERPDeviceModel mERPDeviceModel){

        try {
            if(mERPDeviceModel.getId() != null){
                Boolean isSuccess = mShopDeviceService.deleteShopDevice(mERPDeviceModel.getId());
                if(isSuccess){
                    return "success";
                }else{
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
}
