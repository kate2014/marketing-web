package com.zhongmei.yunfu.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.DishSetmealModel;
import com.zhongmei.yunfu.controller.model.DishShopModel;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.domain.entity.base.SupperEntity;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.jooq.util.derby.sys.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * 商品管理控制器
 */
@Controller
@RequestMapping("/dishShop")
public class DishShopController extends BaseController{

    @Autowired
    DishShopService mDishShopService;
    @Autowired
    DishBrandTypeService mDishBrandTypeService;
    @Autowired
    DishPropertyService mDishPropertyService;
    @Autowired
    DishSetmealService mDishSetmealService;
    @Autowired
    DishSetmealGroupService mDishSetmealGroupService;
    @Autowired
    SupplierService mSupplierService;
    @Autowired
    PurchaseAndSaleService mPurchaseAndSaleService;
    @Autowired
    DishTimeChargingRuleService mDishTimeChargingRuleService;

    @RequestMapping("/dishShopMain")
    public String dishShopList(Model model, DishShopModel mDishShopModel){
        try {
            //获取品项类别
            DishBrandTypeEntity mDishBrandTypeEntity = new DishBrandTypeEntity();
            mDishBrandTypeEntity.setBrandIdenty(mDishShopModel.getBrandIdenty());
            mDishBrandTypeEntity.setShopIdenty(mDishShopModel.getShopIdenty());
            List<DishBrandTypeEntity> listType = mDishBrandTypeService.queryAllDishType(mDishBrandTypeEntity);

            List<DishShopModel> listData = new LinkedList<>();
            Map<Long,List<DishBrandTypeEntity>> tempMap = new HashMap<>();

            for(DishBrandTypeEntity entity : listType){
                if(entity.getParentId() == null || entity.getParentId().equals("")){
                    DishShopModel mModel = new DishShopModel();
                    mModel.setDishBrandTypeEntity(entity);
                    listData.add(mModel);
                }else{
                    List<DishBrandTypeEntity> listValue = tempMap.get(entity.getParentId());
                    if(listValue == null){
                        listValue = new LinkedList<>();
                        tempMap.put(entity.getParentId(),listValue);
                    }
                    listValue.add(entity);
                }
            }

            Long selectIndex = null;
            String typeName = "";
            for(DishShopModel dishModel : listData){
                DishBrandTypeEntity entity = dishModel.getDishBrandTypeEntity();
                List<DishBrandTypeEntity> listChild = tempMap.get(entity.getId());
                if(listChild != null && listChild.size()>0 && selectIndex == null){
                    DishBrandTypeEntity childType = listChild.get(0);
                    selectIndex = childType.getId();
                    typeName = childType.getName();
                }
                dishModel.setListType(listChild);
            }

            mDishShopModel.setDishTypeId(selectIndex);
            mDishShopModel.setTypeName(typeName);
            model.addAttribute("listData", listData);
            model.addAttribute("mDishShopModel", mDishShopModel);
            return "dish_shop_manager";

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping("/dishShopList")
    public String queryDishAction(Model model, DishShopModel mDishShopModel){
        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            SupplierEntity mSupplierEntity = new SupplierEntity();
            mSupplierEntity.setBrandIdenty(brandIdentity);
            mSupplierEntity.setShopIdenty(shopIdentity);

            List<SupplierEntity> listSupplier = mSupplierService.querySupplier(mSupplierEntity);
            model.addAttribute("listSupplier", listSupplier);

            DishShopEntity mDishShopEntity = new DishShopEntity();
            mDishShopEntity.setShopIdenty(shopIdentity);
            mDishShopEntity.setBrandIdenty(brandIdentity);
            mDishShopEntity.setDishTypeId(mDishShopModel.getDishTypeId());
            mDishShopEntity.setType(mDishShopModel.getType());
            mDishShopEntity.setName(mDishShopModel.getName());
            mDishShopEntity.setDishCode(mDishShopModel.getDishCode());
            Page<DishShopEntity> listData = mDishShopService.queryAllDishShop(mDishShopEntity,mDishShopModel.getPageNo(),mDishShopModel.getPageSize());
            setWebPage(model, "/dishShop/dishShopList", listData, mDishShopEntity);
            model.addAttribute("listDishData", listData.getRecords());
            model.addAttribute("mDishShopModel", mDishShopModel);

            return "dish_shop_list";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/intoAddDishMain")
    public String intoAddDishMain(Model model, DishShopModel mDishShopModel){
        model.addAttribute("mDishShopModel", mDishShopModel);
        return "dish_shop_add_main";
    }

    /**
     * 添加单品
     * @param model
     * @param mDishShopModel
     * @return
     */
    @RequestMapping("/intoAddOrUpdateSingleDish")
    public String intoAddSingleDish(Model model, DishShopModel mDishShopModel){

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            SupplierEntity mSupplierEntity = new SupplierEntity();
            mSupplierEntity.setBrandIdenty(brandIdentity);
            mSupplierEntity.setShopIdenty(shopIdentity);

            List<SupplierEntity> listSupplier = mSupplierService.querySupplier(mSupplierEntity);
            model.addAttribute("listSupplier", listSupplier);

            DishTimeChargingRuleEntity mDishTimeChargingRuleEntity = new DishTimeChargingRuleEntity();
            DishShopEntity mDishShopEntity = new DishShopEntity();
            List<DishPropertyEntity> listProperty = new LinkedList<>();

            //默认为单次计费销售
            mDishShopModel.setPriceModle(0);

            if(mDishShopModel.getDishShopId() != null){

                mDishShopEntity = mDishShopService.queryDishShopById(mDishShopModel.getDishShopId());

                List<DishPropertyEntity> listProperties = mDishPropertyService.queryPropertyByDishId(brandIdentity,shopIdentity,mDishShopModel.getDishShopId());
                listProperty.addAll(listProperties);

                //是否选择计时销售
                if(mDishShopEntity.getSaleType() == 3){
                    mDishShopModel.setPriceModle(3);
                    mDishTimeChargingRuleEntity = mDishTimeChargingRuleService.queryByDishId(brandIdentity,shopIdentity,mDishShopModel.getDishShopId());
                    model.addAttribute("mDishTimeChargingRuleEntity", mDishTimeChargingRuleEntity);
                }

            }

            model.addAttribute("mDishShopEntity", mDishShopEntity);
            model.addAttribute("listProperty", listProperty);
            model.addAttribute("mDishTimeChargingRuleEntity", mDishTimeChargingRuleEntity);

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        model.addAttribute("mDishShopModel", mDishShopModel);
        return "dish_shop_add_single";
    }

    /**
     * 添加套餐
     * @param model
     * @param mDishShopModel
     * @return
     */
    @RequestMapping("/intoAddOrUpdatePackageDish")
    public String intoAddPackageDish(Model model, DishShopModel mDishShopModel){
        model.addAttribute("mDishShopModel", mDishShopModel);

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            SupplierEntity mSupplierEntity = new SupplierEntity();
            mSupplierEntity.setBrandIdenty(brandIdentity);
            mSupplierEntity.setShopIdenty(shopIdentity);

            List<SupplierEntity> listSupplier = mSupplierService.querySupplier(mSupplierEntity);
            model.addAttribute("listSupplier", listSupplier);

            mDishShopModel.setPriceModle(0);

            DishShopEntity mDishShopEntity = new DishShopEntity();

            List<DishSetmealModel> listSetmealModel = new LinkedList<>();

            DishTimeChargingRuleEntity mDishTimeChargingRuleEntity = new DishTimeChargingRuleEntity();

            if(mDishShopModel.getDishShopId() != null){
                mDishShopEntity = mDishShopService.queryDishShopById(mDishShopModel.getDishShopId());

                if(mDishShopEntity.getSaleType() == 3){
                    mDishShopModel.setPriceModle(3);
                    mDishTimeChargingRuleEntity = mDishTimeChargingRuleService.queryByDishId(brandIdentity,shopIdentity,mDishShopModel.getDishShopId());

                }

                List<DishSetmealGroupEntity> listSetmealGroup = mDishSetmealGroupService.querySetmealTypeByDishId(brandIdentity,shopIdentity,mDishShopModel.getDishShopId());

                List<DishSetmealModel> listSetmeal = mDishSetmealService.querySetmealList(mDishShopModel.getDishShopId());

                Map<Long,List<DishSetmealModel>> tempMap = new HashMap<>();

                for(DishSetmealModel setmeal: listSetmeal){
                    List<DishSetmealModel> tempListSetmeal = tempMap.get(setmeal.getComboDishTypeId());
                    if(tempListSetmeal == null){
                        List<DishSetmealModel> newList = new ArrayList<>();
                        newList.add(setmeal);
                        tempMap.put(setmeal.getComboDishTypeId(),newList);
                    }else{
                        tempListSetmeal.add(setmeal);
                    }
                }

                for(DishSetmealGroupEntity group : listSetmealGroup){
                    DishSetmealModel mDishSetmealModel = new DishSetmealModel();
                    mDishSetmealModel.setmDishSetmealGroup(group);
                    tempMap.get(group.getId());
                    List<DishSetmealModel> listSetmealData = tempMap.get(group.getId());
                    if(listSetmealData == null){
                        listSetmealData = new ArrayList<>();
                    }
                    mDishSetmealModel.setListSetmeal(listSetmealData);
                    listSetmealModel.add(mDishSetmealModel);
                }

            }

            model.addAttribute("listSetmealModel", listSetmealModel);
            model.addAttribute("mDishShopEntity", mDishShopEntity);
            model.addAttribute("mDishTimeChargingRuleEntity", mDishTimeChargingRuleEntity);
            model.addAttribute("mDishShopModel", mDishShopModel);

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "dish_shop_add_package";
    }

    @RequestMapping("/addOrUpdateSingleDishShop")
    public String addOrUpdateSingleDishShop(Model model, DishShopModel mDishShopModel){


        String actionSuccess = "success";
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorname = LoginManager.get().getUser().getCreatorName();
        try {

            Date currentDate = new Date();

            DishShopEntity mDishShopEntity = new DishShopEntity();

            if(mDishShopModel.getDishShopId() != null){
                mDishShopEntity.setId(mDishShopModel.getDishShopId());
            }else {
                mDishShopEntity.setUuid(ToolsUtil.genOnlyIdentifier());
                mDishShopEntity.setCreatorId(creatorId);
                mDishShopEntity.setCreatorName(creatorname);
                mDishShopEntity.setServerCreateTime(currentDate);
            }

            mDishShopEntity.setDishTypeId(mDishShopModel.getDishTypeId());
            mDishShopEntity.setDishCode(mDishShopModel.getDishCode());
            mDishShopEntity.setType(0);
            mDishShopEntity.setName(mDishShopModel.getName());
            mDishShopEntity.setUnitName(mDishShopModel.getUnitName());
            if(mDishShopModel.getSaleType() == 3){
                mDishShopEntity.setSaleType(3);
                mDishShopEntity.setMarketPrice(mDishShopModel.getStartChargingPrice());
            }else{
                mDishShopEntity.setSaleType(0);
                mDishShopEntity.setMarketPrice(mDishShopModel.getMarketPrice());
            }

            mDishShopEntity.setDishIncreaseUnit(BigDecimal.ONE);
            mDishShopEntity.setSingle(1);
            mDishShopEntity.setDiscountAll(1);
            mDishShopEntity.setSource(1);
            mDishShopEntity.setSendOutside(1);
            mDishShopEntity.setOrder(1);
            mDishShopEntity.setDefProperty(1);
            mDishShopEntity.setStepNum(BigDecimal.ONE);

            if(mDishShopModel.getDishQty() == null){
                mDishShopEntity.setDishQty(BigDecimal.ZERO);
            }else{
                mDishShopEntity.setDishQty(mDishShopModel.getDishQty());
            }

//            mDishShopEntity.setMinNum();
//            mDishShopEntity.setMaxNum();
            mDishShopEntity.setClearStatus(1);

            mDishShopEntity.setValidTime(currentDate);
            mDishShopEntity.setUnvalidTime(currentDate);
            mDishShopEntity.setScene("100");
            mDishShopEntity.setSort(1);
            mDishShopEntity.setBrandIdenty(brandIdentity);
            mDishShopEntity.setShopIdenty(shopIdentity);

            mDishShopEntity.setUpdatorId(creatorId);
            mDishShopEntity.setUpdatorName(creatorname);
            mDishShopEntity.setServerUpdateTime(currentDate);
            mDishShopEntity.setStatusFlag(1);
            mDishShopEntity.setEnabledFlag(1);
            mDishShopEntity.setBrandDishId(1l);
            mDishShopEntity.setBrandDishUuid(ToolsUtil.genOnlyIdentifier());

            mDishShopService.addOrUpdateDishShop(mDishShopEntity);

            //添加计时收费规则
            addOrUpdateTimeCharging(mDishShopModel,mDishShopEntity);
            //添加加项
            addOrUpdateDishProperty(mDishShopModel,mDishShopEntity);
            //添加进销存
            addOrUpdatePurchase(mDishShopModel,mDishShopEntity);


        }catch (Exception e){
            e.printStackTrace();
            actionSuccess = "fail";
        }
        return String.format("redirect:/dishShop/dishShopList?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&dishTypeId=%d&successOrfail=%s",
                brandIdentity, shopIdentity, creatorId, creatorname,mDishShopModel.getDishTypeId(),actionSuccess);

    }

    /**
     * 计时规则操作
     * @return
     */
    public void addOrUpdateTimeCharging(DishShopModel mDishShopModel,DishShopEntity mDishShopEntity) throws Exception{
        Long brandIdentity = mDishShopEntity.getBrandIdenty();
        Long shopIdentity = mDishShopEntity.getShopIdenty();
        Long creatorId = mDishShopEntity.getCreatorId();
        String creatorname = mDishShopEntity.getCreatorName();

        if(mDishShopModel.getSaleType() == 3){
            DishTimeChargingRuleEntity mDishTimeChargingRuleEntity = new DishTimeChargingRuleEntity();

            if(mDishShopModel.getTimeChargingId() != null){
                mDishTimeChargingRuleEntity.setId(mDishShopModel.getTimeChargingId());
            }else{
                mDishTimeChargingRuleEntity.setCreatorId(creatorId);
                mDishTimeChargingRuleEntity.setCreatorName(creatorname);
                mDishTimeChargingRuleEntity.setServerCreateTime(new Date());
            }

            mDishTimeChargingRuleEntity.setDishId(mDishShopEntity.getId());
            mDishTimeChargingRuleEntity.setStartChargingTimes(mDishShopModel.getStartChargingTimes());
            mDishTimeChargingRuleEntity.setStartChargingPrice(mDishShopModel.getStartChargingPrice());
            mDishTimeChargingRuleEntity.setChargingUnit(mDishShopModel.getChargingUnit());
            mDishTimeChargingRuleEntity.setChargingPrice(mDishShopModel.getChargingPrice());
            mDishTimeChargingRuleEntity.setFullUnit(mDishShopModel.getFullUnit());
            mDishTimeChargingRuleEntity.setFullUnitCharging(mDishShopModel.getFullUnitCharging());
            mDishTimeChargingRuleEntity.setNoFullUnit(mDishShopModel.getNoFullUnit());
            mDishTimeChargingRuleEntity.setNoFullUnitCharging(mDishShopModel.getNoFullUnitCharging());
            mDishTimeChargingRuleEntity.setUpdatorId(creatorId);
            mDishTimeChargingRuleEntity.setUpdatorName(creatorname);
            mDishTimeChargingRuleEntity.setServerUpdateTime(new Date());
            mDishTimeChargingRuleEntity.setBrandIdenty(brandIdentity);
            mDishTimeChargingRuleEntity.setShopIdenty(shopIdentity);

            mDishTimeChargingRuleService.addOrUpdateRule(mDishTimeChargingRuleEntity);
        }else{
            if(mDishShopModel.getOldSaleType() == 3){
                mDishTimeChargingRuleService.deleteByDishId(brandIdentity,shopIdentity,mDishShopEntity.getId());
            }
        }
    }

    /**
     * 添加加项
     * @return
     */
    public void addOrUpdateDishProperty(DishShopModel mDishShopModel,DishShopEntity mDishShopEntity) throws Exception{

        Long brandIdentity = mDishShopEntity.getBrandIdenty();
        Long shopIdentity = mDishShopEntity.getShopIdenty();
        Long creatorId = mDishShopEntity.getCreatorId();
        String creatorname = mDishShopEntity.getCreatorName();

        List<DishPropertyEntity> listData = new ArrayList<>();
        LinkedList<String> listAdditionName = mDishShopModel.getAdditionName();
        LinkedList<String> listAdditionPrice = mDishShopModel.getAdditionPrice();
        if(listAdditionName != null){
            for(int i =0; i<listAdditionName.size();i++){
                String additionName = listAdditionName.get(i);
                String additionPrice = listAdditionPrice.get(i);

                DishPropertyEntity mDishPropertyEntity = new DishPropertyEntity();
                mDishPropertyEntity.setUuid(ToolsUtil.genOnlyIdentifier());
                mDishPropertyEntity.setPropertyTypeId(0l);
                mDishPropertyEntity.setName(additionName);
                mDishPropertyEntity.setReprice(new BigDecimal(additionPrice));
                mDishPropertyEntity.setPropertyKind(1);
                mDishPropertyEntity.setSort(1);
                mDishPropertyEntity.setDishShopId(mDishShopEntity.getId());
                mDishPropertyEntity.setBrandIdenty(brandIdentity);
                mDishPropertyEntity.setShopIdenty(shopIdentity);
                mDishPropertyEntity.setCreatorId(creatorId);
                mDishPropertyEntity.setCreatorName(creatorname);
                mDishPropertyEntity.setServerCreateTime(new Date());
                mDishPropertyEntity.setUpdatorId(creatorId);
                mDishPropertyEntity.setUpdatorName(creatorname);
                mDishPropertyEntity.setServerUpdateTime(new Date());

                listData.add(mDishPropertyEntity);
            }
        }

        if(listData != null && listData.size() > 0){
            mDishPropertyService.deletePropertyByDishId(brandIdentity,shopIdentity,mDishShopEntity.getId());
            mDishPropertyService.batchAddDishProperty(listData);
        }
    }

    /**
     * 添加经销存
     * @return
     */
    public void addOrUpdatePurchase(DishShopModel mDishShopModel,DishShopEntity mDishShopEntity) throws Exception{
        if(mDishShopModel.getSupplierCount() != null && mDishShopModel.getSupplierPrice() != null){
            PurchaseAndSaleEntity mPurchaseAndSaleEntity = new PurchaseAndSaleEntity();
            mPurchaseAndSaleEntity.setBrandIdenty(mDishShopEntity.getBrandIdenty());
            mPurchaseAndSaleEntity.setShopIdenty(mDishShopEntity.getShopIdenty());
            mPurchaseAndSaleEntity.setType(1);
            mPurchaseAndSaleEntity.setSourceId(mDishShopModel.getSupplierId());
            mPurchaseAndSaleEntity.setSourceName(mDishShopModel.getSupplierName());
            mPurchaseAndSaleEntity.setDishShopId(mDishShopEntity.getId());
            mPurchaseAndSaleEntity.setNumber(mDishShopModel.getSupplierCount());
            mPurchaseAndSaleEntity.setPurchasePrice(mDishShopModel.getSupplierPrice());
            mPurchaseAndSaleEntity.setTotalPurchasePrice(mDishShopModel.getSupplierCount().multiply(mDishShopModel.getSupplierPrice()));
            mPurchaseAndSaleEntity.setServerCreateTime(new Date());
            mPurchaseAndSaleEntity.setCreatorId(mDishShopEntity.getCreatorId());
            mPurchaseAndSaleEntity.setCreatorName(mDishShopEntity.getCreatorName());
            mPurchaseAndSaleEntity.setUpdatorId(mDishShopEntity.getCreatorId());
            mPurchaseAndSaleEntity.setUpdatorName(mDishShopEntity.getCreatorName());
            mPurchaseAndSaleEntity.setServerUpdateTime(new Date());

            mPurchaseAndSaleService.addPurchaseAndSale(mPurchaseAndSaleEntity);
        }

    }

    @RequestMapping("/addOrUpdatePackage")
    public String addOrUpdatePackage(Model model, DishShopModel mDishShopModel){

        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorname = LoginManager.get().getUser().getCreatorName();
        try {

            Date currentDate = new Date();

            DishShopEntity mDishShopEntity = new DishShopEntity();

            if (mDishShopModel.getDishShopId() != null) {
                mDishShopEntity.setId(mDishShopModel.getDishShopId());

            } else {
                mDishShopEntity.setUuid(ToolsUtil.genOnlyIdentifier());
                mDishShopEntity.setCreatorId(creatorId);
                mDishShopEntity.setCreatorName(creatorname);
                mDishShopEntity.setServerCreateTime(currentDate);
            }

            mDishShopEntity.setDishTypeId(mDishShopModel.getDishTypeId());
            mDishShopEntity.setDishCode(mDishShopModel.getDishCode());
            mDishShopEntity.setType(1);
            mDishShopEntity.setName(mDishShopModel.getName());
            mDishShopEntity.setUnitName(mDishShopModel.getUnitName());
            if(mDishShopModel.getSaleType() == 3){
                mDishShopEntity.setSaleType(3);
                mDishShopEntity.setMarketPrice(mDishShopModel.getStartChargingPrice());
            }else{
                mDishShopEntity.setSaleType(0);
                mDishShopEntity.setMarketPrice(mDishShopModel.getMarketPrice());
            }
            mDishShopEntity.setDishIncreaseUnit(BigDecimal.ONE);
            mDishShopEntity.setSingle(1);
            mDishShopEntity.setDiscountAll(1);
            mDishShopEntity.setSource(1);
            mDishShopEntity.setSendOutside(1);
            mDishShopEntity.setOrder(1);
            mDishShopEntity.setDefProperty(1);
            mDishShopEntity.setStepNum(BigDecimal.ONE);

            if(mDishShopModel.getDishQty() == null){
                mDishShopEntity.setDishQty(BigDecimal.ZERO);
            }else{
                mDishShopEntity.setDishQty(mDishShopModel.getDishQty());
            }

//            mDishShopEntity.setMinNum();
//            mDishShopEntity.setMaxNum();
            mDishShopEntity.setClearStatus(1);

            mDishShopEntity.setValidTime(currentDate);
            mDishShopEntity.setUnvalidTime(currentDate);
            mDishShopEntity.setScene("100");
            mDishShopEntity.setSort(1);

            mDishShopEntity.setBrandIdenty(brandIdentity);
            mDishShopEntity.setShopIdenty(shopIdentity);

            mDishShopEntity.setUpdatorId(creatorId);
            mDishShopEntity.setUpdatorName(creatorname);
            mDishShopEntity.setServerUpdateTime(currentDate);
            mDishShopEntity.setStatusFlag(1);
            mDishShopEntity.setEnabledFlag(1);
            mDishShopEntity.setBrandDishId(1l);
            mDishShopEntity.setBrandDishUuid(ToolsUtil.genOnlyIdentifier());

            mDishShopService.addOrUpdateDishShop(mDishShopEntity);

            //添加进销存
            addOrUpdatePurchase(mDishShopModel,mDishShopEntity);
            //添加计时规则
            addOrUpdateTimeCharging(mDishShopModel,mDishShopEntity);
            //添加子项
            addOrUpdateSetmeal(mDishShopEntity,mDishShopModel);

        }catch (Exception e){
            e.printStackTrace();
        }
        String actionSuccess = "success";
        return String.format("redirect:/dishShop/dishShopList?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&dishTypeId=%d&successOrfail=%s",
                brandIdentity, shopIdentity, creatorId, creatorname,mDishShopModel.getDishTypeId(),actionSuccess);
    }

    /**
     * 添加子项分组和子品
     * @throws Exception
     */
    public void addOrUpdateSetmeal(DishShopEntity mDishShopEntity,DishShopModel mDishShopModel)throws Exception{

        Long brandIdentity = mDishShopEntity.getBrandIdenty();
        Long shopIdentity = mDishShopEntity.getShopIdenty();
        Long creatorId = mDishShopEntity.getCreatorId();
        String creatorname = mDishShopEntity.getCreatorName();

        //子品分组
        List<String> typeIds = mDishShopModel.getSetmealTypeId();
        List<String> typeNames = mDishShopModel.getSetmealTypeName();
        List<String> orderMins = mDishShopModel.getTypeOrderMin();
        List<String> orderMaxs = mDishShopModel.getTypeOrderMax();

        //子品
        List<String> listSetmealId = mDishShopModel.getSetmealId();
        List<String> listComboDishTypeId = mDishShopModel.getComboDishTypeId();
        List<String> childDishId = mDishShopModel.getChildDishId();
        List<String> isReplaces = mDishShopModel.getIsReplace();
        List<String> isDefaults = mDishShopModel.getIsDefault();
        List<String> isMultis = mDishShopModel.getIsMulti();
        List<String> leastCellNums = mDishShopModel.getLeastCellNum();
        List<String> setmealPrices = mDishShopModel.getSetmealPrice();

        //当修改套餐时才判断是否有删除，新增不需要执行该过程
        if(mDishShopModel.getDishShopId() != null){
            //获取更新或添加前的子项类别和子项
            List<DishSetmealGroupEntity> listOldSetmealGroup = mDishSetmealGroupService.querySetmealTypeByDishId(brandIdentity,shopIdentity,mDishShopModel.getDishShopId());
            List<DishSetmealModel> listOldSetmeal = mDishSetmealService.querySetmealList(mDishShopModel.getDishShopId());


            //获取被删除的类别 进行删除操作
            if(listOldSetmealGroup != null && listOldSetmealGroup.size()>0){
                List<Long> listTypeIds = new ArrayList<>();
                for(DishSetmealGroupEntity entity:listOldSetmealGroup){
                    boolean isHad = false;
                    if(typeIds != null && typeIds.size() > 0){
                        for(String typeId : typeIds){
                            Long tempId = Long.valueOf(typeId);
                            if(tempId.longValue() == entity.getId().longValue()){
                                isHad = true;
                                break;
                            }
                        }
                    }

                    if(!isHad){
                        listTypeIds.add(entity.getId());
                    }

                }
                if(listTypeIds.size() > 0){
                    mDishSetmealGroupService.batchDelete(listTypeIds);
                }
            }


            //获取被删除的子项 进行删除操作
            if(listOldSetmeal != null && listOldSetmeal.size() > 0){
                List<Long> listSetmealIds = new ArrayList<>();
                for(DishSetmealModel entity:listOldSetmeal){
                    boolean isHad = false;
                    if(listSetmealId != null && listSetmealId.size() > 0){
                        for(String setmealId : listSetmealId){
                            Long tempId = Long.valueOf(setmealId);
                            if(tempId.longValue() == entity.getId().longValue()){
                                isHad = true;
                                break;
                            }
                        }
                    }

                    if(!isHad){
                        listSetmealIds.add(entity.getId());
                    }
                }
                if(listSetmealIds.size()>0){
                    mDishSetmealService.batchDelete(listSetmealIds);
                }
            }
        }

        Map<String,Long> setmealTypeId = new HashMap<>();

        if(typeIds != null && typeIds.size() > 0){
            for(int i=typeIds.size()-1;i>=0;i--){
                String typeId = typeIds.get(i);
                if(typeId != null){
                    long id = Long.valueOf(typeId);
                    //添加、编辑子项中类
                    DishSetmealGroupEntity mDishSetmealGroupEntity = new DishSetmealGroupEntity();
                    if(id >0){
                        mDishSetmealGroupEntity.setId(id);
                    }else{
                        mDishSetmealGroupEntity.setCreatorId(creatorId);
                        mDishSetmealGroupEntity.setCreatorName(creatorname);

                        mDishSetmealGroupEntity.setBrandIdenty(brandIdentity);
                        mDishSetmealGroupEntity.setShopIdenty(shopIdentity);
                        mDishSetmealGroupEntity.setServerCreateTime(new Date());
                    }
                    mDishSetmealGroupEntity.setSetmealDishId(mDishShopEntity.getId());
                    mDishSetmealGroupEntity.setName(typeNames.get(i));
                    mDishSetmealGroupEntity.setOrderMin(new BigDecimal(orderMins.get(i)));
                    mDishSetmealGroupEntity.setOrderMax(new BigDecimal(orderMaxs.get(i)));
                    mDishSetmealGroupEntity.setSort(0);
                    mDishSetmealGroupEntity.setStatusFlag(1);
                    mDishSetmealGroupEntity.setUpdatorId(creatorId);
                    mDishSetmealGroupEntity.setUpdatorName(creatorname);
                    mDishSetmealGroupEntity.setServerUpdateTime(new Date());
                    mDishSetmealGroupEntity = mDishSetmealGroupService.addOrUpdate(mDishSetmealGroupEntity);
                    setmealTypeId.put(typeId,mDishSetmealGroupEntity.getId());
                }
            }
        }

        if(listSetmealId != null && listSetmealId.size() > 0){
            List<DishSetmealEntity> listSetmeal = new ArrayList<>();

            for(int i=0;i<listSetmealId.size();i++){
                DishSetmealEntity msetmeal = new DishSetmealEntity();
                Long setmealId = Long.valueOf(listSetmealId.get(i));

                if(setmealId>0){
                    msetmeal.setId(setmealId);
                }else {
                    msetmeal.setCreatorId(creatorId);
                    msetmeal.setCreatorName(creatorname);
                    msetmeal.setServerCreateTime(new Date());
                    msetmeal.setShopIdenty(shopIdentity);
                    msetmeal.setBrandIdenty(brandIdentity);
                }
                msetmeal.setDishId(mDishShopEntity.getId());
                String comboDishTypeId = listComboDishTypeId.get(i);
                msetmeal.setComboDishTypeId(setmealTypeId.get(comboDishTypeId));

                msetmeal.setChildDishId(Long.valueOf(childDishId.get(i)));
                msetmeal.setChildDishType(0);

                msetmeal.setReplace(0);
                if(isReplaces != null){
                    for(String sIsReplace : isReplaces){
                        int isReplace = Integer.parseInt(sIsReplace);
                        if(isReplace == setmealId){
                            msetmeal.setReplace(1);
                        }
                    }
                }

                msetmeal.setDefault(0);
                if(isDefaults != null){
                    for(String sIsDefault : isDefaults){
                        if(sIsDefault != null){
                            int isDefault = Integer.parseInt(sIsDefault);
                            if(isDefault == setmealId){
                                msetmeal.setDefault(1);
                            }
                        }
                    }
                }

                msetmeal.setMulti(0);
                if(isMultis != null){
                    for(String sIsMulti : isMultis){
                        int isMulti = Integer.parseInt(sIsMulti);
                        if(isMulti == setmealId){
                            msetmeal.setMulti(1);
                        }
                    }
                }

                msetmeal.setLeastCellNum(new BigDecimal(leastCellNums.get(i)));
                msetmeal.setPrice(new BigDecimal(setmealPrices.get(i)));

                msetmeal.setSort(0);
                msetmeal.setStatusFlag(1);
                msetmeal.setUpdatorId(creatorId);
                msetmeal.setUpdatorName(creatorname);
                msetmeal.setServerUpdateTime(new Date());

                listSetmeal.add(msetmeal);
            }

            mDishSetmealService.batchAddOrUpdateSetmeal(listSetmeal);
        }
    }

    @RequestMapping("/deleteDishShop")
    public String deleteType(Model model, DishShopModel mDishShopModel){
        try {

            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            boolean isSuccess = mDishShopService.deleteDishShopData(mDishShopModel.getDishShopId());

            if(mDishShopModel.getType() == 0){
                //删除单品加项
                mDishPropertyService.deletePropertyByDishId(brandIdentity,shopIdentity,mDishShopModel.getDishShopId());
            }else if(mDishShopModel.getType() == 1){
                //删除套餐子项
                mDishSetmealGroupService.delectSetmealGroup(mDishShopModel.getDishShopId());
                mDishSetmealService.delectSetmealByDishId(mDishShopModel.getDishShopId());
            }
            //删除计时销售规则
            mDishTimeChargingRuleService.deleteByDishId(brandIdentity,shopIdentity,mDishShopModel.getDishShopId());

            if(isSuccess){
                return "success";
            }else {
                return "fail";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/modiftyPurchase")
    public String modiftyPurchase(Model model, DishShopModel mDishShopModel){
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorname = LoginManager.get().getUser().getCreatorName();
        String actionSuccess = "success";

        try {

            addPurchase(mDishShopModel);
            //修改产品库存
            DishShopEntity mDishShopEntity = new DishShopEntity();
            mDishShopEntity.setId(mDishShopModel.getDishShopId());

            DishShopEntity entity = mDishShopService.queryDishShopById(mDishShopModel.getDishShopId());
            if(entity.getDishQty() == null){
                entity.setDishQty(BigDecimal.ZERO);
            }
            if(mDishShopModel.getSupplierCount() != null){
                mDishShopEntity.setDishQty(entity.getDishQty().add(mDishShopModel.getSupplierCount()));
            }


            mDishShopEntity.setUpdatorId(creatorId);
            mDishShopEntity.setUpdatorName(creatorname);
            mDishShopEntity.setServerUpdateTime(new Date());
            mDishShopService.modfityDishShop(mDishShopEntity);


        }catch (Exception e){
            e.printStackTrace();
            actionSuccess = "fail";
        }

        return String.format("redirect:/dishShop/dishShopList?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&dishTypeId=%d&successOrfail=%s",
                brandIdentity, shopIdentity, creatorId, creatorname,mDishShopModel.getDishTypeId(),actionSuccess);

    }

    @RequestMapping("/modfitySales")
    public String modfitySales(Model model, DishShopModel mDishShopModel){
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorname = LoginManager.get().getUser().getCreatorName();
        String actionSuccess = "success";

        try {


            addSales(mDishShopModel);
            //修改产品库存
            DishShopEntity mDishShopEntity = new DishShopEntity();
            mDishShopEntity.setId(mDishShopModel.getSalesDishShopId());

            DishShopEntity entity = mDishShopService.queryDishShopById(mDishShopModel.getSalesDishShopId());
            if(entity.getDishQty() == null){
                entity.setDishQty(BigDecimal.ZERO);
            }
            if(mDishShopModel.getSalesSupplierCount() != null){
                mDishShopEntity.setDishQty(entity.getDishQty().subtract(mDishShopModel.getSalesSupplierCount()));
            }

            mDishShopEntity.setUpdatorId(creatorId);
            mDishShopEntity.setUpdatorName(creatorname);
            mDishShopEntity.setServerUpdateTime(new Date());
            mDishShopService.modfityDishShop(mDishShopEntity);


        }catch (Exception e){
            e.printStackTrace();
            actionSuccess = "fail";
        }

        return String.format("redirect:/dishShop/dishShopList?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&dishTypeId=%d&successOrfail=%s",
                brandIdentity, shopIdentity, creatorId, creatorname,mDishShopModel.getSalesDishTypeId(),actionSuccess);
    }

    /**
     * 新增进货、销货数据信息
     * @param mDishShopModel
     * @return
     * @throws Exception
     */
    public boolean addPurchase(DishShopModel mDishShopModel)throws Exception{
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorname = LoginManager.get().getUser().getCreatorName();
        PurchaseAndSaleEntity mPurchaseAndSaleEntity = new PurchaseAndSaleEntity();
        mPurchaseAndSaleEntity.setBrandIdenty(brandIdentity);
        mPurchaseAndSaleEntity.setShopIdenty(shopIdentity);
        mPurchaseAndSaleEntity.setType(1);
        mPurchaseAndSaleEntity.setSourceId(mDishShopModel.getSupplierId());
        mPurchaseAndSaleEntity.setSourceName(mDishShopModel.getSupplierName());
        mPurchaseAndSaleEntity.setDishShopId(mDishShopModel.getDishShopId());
        mPurchaseAndSaleEntity.setNumber(mDishShopModel.getSupplierCount());
        mPurchaseAndSaleEntity.setPurchasePrice(mDishShopModel.getSupplierPrice());

        if(mDishShopModel.getDishQty() == null){
            mDishShopModel.setDishQty(BigDecimal.ZERO);
        }

        if(mDishShopModel.getSupplierCount() != null){
            mPurchaseAndSaleEntity.setTotalPurchasePrice(mDishShopModel.getDishQty().multiply(mDishShopModel.getSupplierPrice()));
        }

        mPurchaseAndSaleEntity.setServerCreateTime(new Date());
        mPurchaseAndSaleEntity.setCreatorId(creatorId);
        mPurchaseAndSaleEntity.setCreatorName(creatorname);
        mPurchaseAndSaleEntity.setUpdatorId(creatorId);
        mPurchaseAndSaleEntity.setUpdatorName(creatorname);
        mPurchaseAndSaleEntity.setServerUpdateTime(new Date());
        return mPurchaseAndSaleService.addPurchaseAndSale(mPurchaseAndSaleEntity);
    }

    /**
     * 新增进货、销货数据信息
     * @param mDishShopModel
     * @return
     * @throws Exception
     */
    public boolean addSales(DishShopModel mDishShopModel)throws Exception{
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorname = LoginManager.get().getUser().getCreatorName();
        PurchaseAndSaleEntity mPurchaseAndSaleEntity = new PurchaseAndSaleEntity();
        mPurchaseAndSaleEntity.setBrandIdenty(brandIdentity);
        mPurchaseAndSaleEntity.setShopIdenty(shopIdentity);
        mPurchaseAndSaleEntity.setType(2);
        mPurchaseAndSaleEntity.setSourceId(mDishShopModel.getSupplierId());
        mPurchaseAndSaleEntity.setSourceName(mDishShopModel.getSupplierName());
        mPurchaseAndSaleEntity.setDishShopId(mDishShopModel.getSalesDishShopId());
        mPurchaseAndSaleEntity.setNumber(mDishShopModel.getSalesSupplierCount());

        mPurchaseAndSaleEntity.setServerCreateTime(new Date());
        mPurchaseAndSaleEntity.setCreatorId(creatorId);
        mPurchaseAndSaleEntity.setCreatorName(creatorname);
        mPurchaseAndSaleEntity.setUpdatorId(creatorId);
        mPurchaseAndSaleEntity.setUpdatorName(creatorname);
        mPurchaseAndSaleEntity.setServerUpdateTime(new Date());
        return mPurchaseAndSaleService.addPurchaseAndSale(mPurchaseAndSaleEntity);
    }

}
