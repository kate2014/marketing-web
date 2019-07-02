package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CardTimeModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.zhongmei.yunfu.domain.entity.PushPlanActivityEntity;
import com.zhongmei.yunfu.domain.mapper.DishShopMapper;
import com.zhongmei.yunfu.service.DishShopService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 门店菜品 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2019-01-10
 */
@Service
public class DishShopServiceImpl extends ServiceImpl<DishShopMapper, DishShopEntity> implements DishShopService {

    @Override
    public Page<DishShopEntity> queryDishShopList(CardTimeModel mCardTimeModel) throws Exception{

        if(mCardTimeModel.getShopIdenty() == null || mCardTimeModel.getShopIdenty().equals("")){
            return null;
        }

        DishShopEntity mDishShopEntity = new DishShopEntity();
        mDishShopEntity.setBrandIdenty(mCardTimeModel.getBrandIdenty());
        mDishShopEntity.setShopIdenty(mCardTimeModel.getShopIdenty());
        mDishShopEntity.setStatusFlag(1);

        Page<DishShopEntity> listPage = new Page<>(mCardTimeModel.getPageNo(), mCardTimeModel.getPageSize());
        EntityWrapper<DishShopEntity> eWrapper = new EntityWrapper<>(mDishShopEntity);
        eWrapper.setSqlSelect("id,name,type,market_price,sale_total,dish_increase_unit,valid_time,unvalid_time,min_num,max_num,server_create_time");
        eWrapper.in("type","3,4");
        eWrapper.orderBy("server_create_time", false);
        Page<DishShopEntity> listData = selectPage(listPage, eWrapper);

        return listData;

    }

    @Override
    public DishShopEntity queryDishShopById(CardTimeModel mCardTimeModel) throws Exception {
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        EntityWrapper<DishShopEntity> eWrapper = new EntityWrapper<>(new DishShopEntity());
        eWrapper.eq("brand_identy",brandIdentity);
        eWrapper.eq("shop_identy",shopIdentity);
        eWrapper.eq("id",mCardTimeModel.getId());
        DishShopEntity mDishShopEntity = selectOne(eWrapper);
        return mDishShopEntity;
    }

    @Override
    public DishShopEntity addDishShop(CardTimeModel mCardTimeModel) throws Exception {

        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorName = LoginManager.get().getUser().getCreatorName();

        if(shopIdentity == null || shopIdentity.equals("")){
            return null;
        }

        DishShopEntity mDishShopEntity = new DishShopEntity();
        mDishShopEntity.setUuid(ToolsUtil.genOnlyIdentifier());
        mDishShopEntity.setName(mCardTimeModel.getName());
        mDishShopEntity.setType(mCardTimeModel.getType());
        mDishShopEntity.setMarketPrice(mCardTimeModel.getMarketPrice());
        mDishShopEntity.setDishIncreaseUnit(BigDecimal.ONE);
        mDishShopEntity.setStepNum(BigDecimal.ONE);

        if(mCardTimeModel.getSaleTotal() == null || mCardTimeModel.getSaleTotal().equals("")){//如果起卖份数为空 则表示不限次数使用，我们使用默认值-1
            mDishShopEntity.setSaleTotal(new BigDecimal(-1));
        }else{
            mDishShopEntity.setSaleTotal(mCardTimeModel.getSaleTotal());
        }
        //如MinNum为空这标识不限制时间
        if(mCardTimeModel.getMinNum() == null || mCardTimeModel.getMinNum().equals("")){
            mDishShopEntity.setMaxNum(-1);
            mDishShopEntity.setMinNum(-1);
        }else{
            mDishShopEntity.setMaxNum(mCardTimeModel.getMaxNum());
            mDishShopEntity.setMinNum(mCardTimeModel.getMinNum());
        }


        mDishShopEntity.setShopIdenty(shopIdentity);
        mDishShopEntity.setBrandIdenty(brandIdentity);
        mDishShopEntity.setCreatorId(creatorId);
        mDishShopEntity.setCreatorName(creatorName);
//        mDishShopEntity.setSkuKey();
        mDishShopEntity.setDishQty(mCardTimeModel.getDishQty());
        mDishShopEntity.setStatusFlag(1);

        mDishShopEntity.setBrandDishId(1l);
        mDishShopEntity.setDishTypeId(-1l);
        mDishShopEntity.setBrandDishUuid(ToolsUtil.genOnlyIdentifier());
        mDishShopEntity.setSort(1);
        mDishShopEntity.setSingle(1);
        mDishShopEntity.setDiscountAll(1);
        mDishShopEntity.setSource(1);
        mDishShopEntity.setOrder(1);
        mDishShopEntity.setSaleType(2);
        mDishShopEntity.setSendOutside(1);
        mDishShopEntity.setDefProperty(1);
        mDishShopEntity.setClearStatus(1);
        mDishShopEntity.setResidueTotal(mCardTimeModel.getDishQty());
        mDishShopEntity.setSaleTotalWechat(mCardTimeModel.getDishQty());
        mDishShopEntity.setResidueTotalWechat(mCardTimeModel.getDishQty());
        mDishShopEntity.setValidTime(new Date());
        mDishShopEntity.setUnvalidTime(new Date());
        mDishShopEntity.setScene("1");
        mDishShopEntity.setHasStandard(2);
        mDishShopEntity.setBoxQty(0);

        EntityWrapper<DishShopEntity> eWrapper = new EntityWrapper<>(mDishShopEntity);

        insert(mDishShopEntity);

        return mDishShopEntity;
    }

    @Override
    public Boolean modifyDishShop(CardTimeModel mCardTimeModel) throws Exception {
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorName = LoginManager.get().getUser().getCreatorName();

        //用于判断门店和登录用户是否为空，避免更新到其他门店数据
        if(brandIdentity == null || creatorId == null){
            return false;
        }

        DishShopEntity mDishShopEntity = new DishShopEntity();
        mDishShopEntity.setId(mCardTimeModel.getId());

        mDishShopEntity.setUpdatorId(creatorId);
        mDishShopEntity.setUpdatorName(creatorName);
        mDishShopEntity.setServerUpdateTime(new Date());

        mDishShopEntity.setName(mCardTimeModel.getName());
        mDishShopEntity.setType(mCardTimeModel.getType());
        mDishShopEntity.setMarketPrice(mCardTimeModel.getMarketPrice());

        mDishShopEntity.setDishIncreaseUnit(BigDecimal.ONE);
        mDishShopEntity.setStepNum(BigDecimal.ONE);

        if(mCardTimeModel.getSaleTotal() == null || mCardTimeModel.getSaleTotal().equals("")){//如果起卖份数为空 则表示不限次数使用，我们使用默认值1000
            mDishShopEntity.setSaleTotal(new BigDecimal(-1));
        }else{
            mDishShopEntity.setSaleTotal(mCardTimeModel.getSaleTotal());
        }

        //如MinNum为空这标识不限制时间
        if(mCardTimeModel.getMinNum() == null || mCardTimeModel.getMinNum().equals("")){
            mDishShopEntity.setMaxNum(-1);
            mDishShopEntity.setMinNum(-1);
        }else{
            mDishShopEntity.setMaxNum(mCardTimeModel.getMaxNum());
            mDishShopEntity.setMinNum(mCardTimeModel.getMinNum());
        }

        EntityWrapper<DishShopEntity> eWrapper = new EntityWrapper<>(new DishShopEntity());
        eWrapper.eq("brand_identy",brandIdentity);
        eWrapper.eq("shop_identy",shopIdentity);
        eWrapper.eq("id",mCardTimeModel.getId());

        Boolean isSuccess = update(mDishShopEntity,eWrapper);

        return isSuccess;
    }

    @Override
    public Boolean deleteDishShop(Long dishId) throws Exception {
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        if(dishId == null || shopIdentity == null){
            return false;
        }

        DishShopEntity mDishShopEntity = new DishShopEntity();
        mDishShopEntity.setBrandIdenty(brandIdentity);
        mDishShopEntity.setShopIdenty(shopIdentity);
        mDishShopEntity.setId(dishId);
        mDishShopEntity.setStatusFlag(2);
        Boolean isSuccess = updateById(mDishShopEntity);
        return isSuccess;
    }

    @Override
    public List<DishShopEntity> listDishShop(Long dishId) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);

        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        eWrapper.eq("s.brand_identy",brandIdentity);
        eWrapper.eq("s.shop_identy",shopIdentity);
        eWrapper.eq("s.dish_id",dishId);

        List<DishShopEntity> listDishShop = baseMapper.queryDishList(eWrapper);

        return listDishShop;
    }

    @Override
    public List<DishShopEntity> queryAllDishShop(DishShopEntity mDishShopEntity) throws Exception {
        EntityWrapper<DishShopEntity> eWrapper = new EntityWrapper<>(new DishShopEntity());

        eWrapper.eq("brand_identy",mDishShopEntity.getBrandIdenty());
        eWrapper.eq("shop_identy",mDishShopEntity.getShopIdenty());

        if(mDishShopEntity.getType() != null){
            eWrapper.eq("type",mDishShopEntity.getType());
        }else{
            eWrapper.in("type","0,1");
        }
        if(mDishShopEntity.getName() != null){
            eWrapper.like("name",mDishShopEntity.getName());
        }
        if(mDishShopEntity.getDishCode() != null){
            eWrapper.eq("dish_code",mDishShopEntity.getDishCode());
        }
        if(mDishShopEntity.getDishTypeId() != null){
            eWrapper.eq("dish_type_id",mDishShopEntity.getDishTypeId());
        }
        eWrapper.orderBy("server_create_time",true);

        eWrapper.setSqlSelect("id,name,dish_code,type,dish_type_id,market_price,unit_name,sale_total,dish_qty,dish_increase_unit,valid_time,unvalid_time,min_num,max_num,server_create_time");


        List<DishShopEntity> listData = selectList(eWrapper);

        return listData;
    }

    @Override
    public Page<DishShopEntity> queryAllDishShop(DishShopEntity mDishShopEntity,int pageNo, int pageSize) throws Exception {

        Page<DishShopEntity> listPage = new Page<>(pageNo, pageSize);
        EntityWrapper<DishShopEntity> eWrapper = new EntityWrapper<>(new DishShopEntity());
        eWrapper.eq("brand_identy",mDishShopEntity.getBrandIdenty());
        eWrapper.eq("shop_identy",mDishShopEntity.getShopIdenty());
        if(mDishShopEntity.getType() != null){
            eWrapper.eq("type",mDishShopEntity.getType());
        }else{
            eWrapper.in("type","0,1");
        }
        if(mDishShopEntity.getName() != null && !mDishShopEntity.getName().equals("")){
            eWrapper.like("name",mDishShopEntity.getName());
        }
        if(mDishShopEntity.getDishCode() != null && !mDishShopEntity.getDishCode().equals("")){
            eWrapper.eq("dish_code",mDishShopEntity.getDishCode());
        }
        if(mDishShopEntity.getDishTypeId() != null){
            eWrapper.eq("dish_type_id",mDishShopEntity.getDishTypeId());
        }

        eWrapper.eq("status_flag",1);
        eWrapper.orderBy("server_create_time",false);

        eWrapper.setSqlSelect("id,name,dish_code,type,dish_type_id,market_price,unit_name,sale_total,dish_qty,dish_increase_unit,valid_time,unvalid_time,min_num,max_num,server_create_time");

        Page<DishShopEntity> listData = selectPage(listPage, eWrapper);

        return listData;
    }

    @Override
    public DishShopEntity addDishShop(DishShopEntity mDishShopEntity) throws Exception {
        insert(mDishShopEntity);
        return mDishShopEntity;
    }

    @Override
    public DishShopEntity addOrUpdateDishShop(DishShopEntity mDishShopEntity) throws Exception {
        insertOrUpdate(mDishShopEntity);
        return mDishShopEntity;
    }

    @Override
    public boolean modfityDishShop(DishShopEntity mDishShopEntity) throws Exception {

        return updateById(mDishShopEntity);
    }

    @Override
    public boolean deleteDishShopData(Long dishShopId) throws Exception {
        return deleteById(dishShopId);
    }

    @Override
    public DishShopEntity queryDishShopById(Long dishShopId) throws Exception {
        DishShopEntity mDishShopEntity = selectById(dishShopId);
        return mDishShopEntity;
    }
}
