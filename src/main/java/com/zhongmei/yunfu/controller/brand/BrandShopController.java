package com.zhongmei.yunfu.controller.brand;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/internal/brand")
public class BrandShopController extends BaseController {

    @Autowired
    CommercialService mCommercialService;

    @RequestMapping({"/shopList"})
    public String shopList(Model model, ShopSearchModel mShopSearchModel) {

        try {
            Page<CommercialEntity> listCommercail = mCommercialService.queryCommercialList(mShopSearchModel,mShopSearchModel.getPageNo(), mShopSearchModel.getPageSize());

            model.addAttribute("listShop",listCommercail.getRecords());
            setWebPage(model, "/internal/brand/shopList", listCommercail, mShopSearchModel);

        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("mShopSearchModel", mShopSearchModel);
        return "brand_shop_list";
    }

    @RequestMapping({"/gotToSelectShop"})
    public String gotToSelectShop(Model model, ShopSearchModel mShopSearchModel) {

        model.addAttribute("mShopSearchModel", mShopSearchModel);
        return "brand_shop_list_dialog";
    }

    @RequestMapping({"/selectShopList"})
    public String shopListDialog(Model model, ShopSearchModel mShopSearchModel) {

        try {

            Long brandIdenty = LoginManager.get().getUser().getBrandIdenty();

            List<CommercialEntity> listCommercail = mCommercialService.queryCommercialByBrandId(brandIdenty);

            model.addAttribute("listShop",listCommercail);

        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("mShopSearchModel", mShopSearchModel);
        return "brand_shop_select_list";
    }

}
