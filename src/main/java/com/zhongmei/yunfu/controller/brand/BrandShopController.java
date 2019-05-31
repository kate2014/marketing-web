package com.zhongmei.yunfu.controller.brand;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.CouponEntity;
import com.zhongmei.yunfu.domain.entity.PushPlanNewDishEntity;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.CouponService;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.service.PushPlanNewDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/internal/brand")
public class BrandShopController extends BaseController {

    @Autowired
    CommercialService mCommercialService;

    @Autowired
    PushPlanNewDishService mPushNewDishService;
    @Autowired
    CouponService couponService;

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
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Integer type = mShopSearchModel.getType();
            Long sourceId = mShopSearchModel.getRelatedId();

            Map<Long,Long> tempMap = new HashMap<>();
            //获取已推送新品的门店信息
            if(type == 1){
                List<PushPlanNewDishEntity> listData = mPushNewDishService.queryBySourceId(brandIdentity,sourceId);
                for(PushPlanNewDishEntity entity : listData){
                    tempMap.put(entity.getShopIdentity(),entity.getShopIdentity());
                }
            }else if(type == 2){
                List<CouponEntity> listData = couponService.queryDataBySourceId(brandIdentity,sourceId);
                for(CouponEntity mCouponEntity : listData){
                    tempMap.put(mCouponEntity.getShopIdenty(),mCouponEntity.getShopIdenty());
                }
            }

            List<CommercialEntity> listCommercail = mCommercialService.queryCommercialByBrandId(brandIdentity);

            if(tempMap.size() != 0){
                for(CommercialEntity entity : listCommercail){
                    Long value = tempMap.get(entity.getCommercialId());
                    if(value != null){
                        entity.setChecked(1);
                    }
                }
            }

            model.addAttribute("listShop",listCommercail);

        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("mShopSearchModel", mShopSearchModel);
        return "brand_shop_select_list";
    }

}
