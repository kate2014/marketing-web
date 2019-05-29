package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.NewDishPushSearchModel;
import com.zhongmei.yunfu.domain.entity.PushPlanNewDishEntity;
import com.zhongmei.yunfu.domain.enums.StatusFlag;
import com.zhongmei.yunfu.domain.mapper.PushPlanNewDishMapper;
import com.zhongmei.yunfu.service.PushPlanNewDishService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 新品推送方案 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Service
public class PushPlanNewDishServiceImpl extends ServiceImpl<PushPlanNewDishMapper, PushPlanNewDishEntity> implements PushPlanNewDishService {

    @Override
    public Page<PushPlanNewDishEntity> list(NewDishPushSearchModel searchModel) {
        PushPlanNewDishEntity newDishPlan = new PushPlanNewDishEntity();

        if (searchModel.getPlanState() != 0) {
            newDishPlan.setPlanState(searchModel.getPlanState());
        }

        newDishPlan.setStatusFlag(StatusFlag.VALiD.value());
        newDishPlan.setBrandIdentity(searchModel.getBrandIdenty());
        newDishPlan.setShopIdentity(searchModel.getShopIdenty());
        newDishPlan.setSourceType(searchModel.getSourceType());

        EntityWrapper<PushPlanNewDishEntity> eWrapper = new EntityWrapper<>(newDishPlan);
        eWrapper.setSqlSelect("id", "name", "shop_identity", "brand_identity", "plan_state", "begin_time", "scan_number", "share_number", "img_url","plan_desc","dish_id","dish_name","source_type","source_id");
        eWrapper.and().like("name", searchModel.getKeyWord());
        eWrapper.orderBy("server_create_time",false);
        Page<PushPlanNewDishEntity> page = new Page<>(searchModel.getPageNo(), searchModel.getPageSize());

        return selectPage(page, eWrapper);
    }

    @Override
    public boolean addNewDishPushPlan(PushPlanNewDishEntity newDishPushPlan) {
        return insert(newDishPushPlan);
    }

    @Override
    public boolean batchAddNewDishPushPlan(List<PushPlanNewDishEntity> listNewDishPushPlan) throws Exception {
        boolean isSuccess = insertBatch(listNewDishPushPlan);
        return isSuccess;
    }

    @Override
    public boolean batchDeleteDishPushPlan(List<Long> ids) throws Exception {
        boolean isSuccess = deleteBatchIds(ids);
        return isSuccess;
    }

    @Override
    public boolean batchUpdateDishPushPlan(List<PushPlanNewDishEntity> listNewDishPushPlan) throws Exception {
        for(PushPlanNewDishEntity entity : listNewDishPushPlan){
            updateById(entity);
        }
        return true;
    }

    @Override
    public boolean updateNewDishPushPlan(PushPlanNewDishEntity newDishPushPlan) {
        return updateById(newDishPushPlan);
    }

    @Override
    public boolean deleteNewDishPushPlan(Long id) {
        return deleteById(id);
    }

    @Override
    public PushPlanNewDishEntity queryByid(Long id) {
        return selectById(id);
    }

    @Override
    public PushPlanNewDishEntity queryDetailById(Long id) {
        EntityWrapper<PushPlanNewDishEntity> eWrapper = new EntityWrapper<>(new PushPlanNewDishEntity());
        eWrapper.eq("id",id);
        eWrapper.setSqlSelect("id,name,plan_desc,img_url,begin_time,end_time");
        return selectOne(eWrapper);
    }

    @Override
    public boolean enableNewDishPushPlan(Long id, int planState) {
        PushPlanNewDishEntity newDishPush = selectById(id);
        newDishPush.setPlanState(planState);
        return updateById(newDishPush);
    }

    @Override
    public boolean refreshShareNumber(Long id) {
        PushPlanNewDishEntity newDishPlan = queryByid(id);
        newDishPlan.setShareNumber(newDishPlan.getShareNumber() + 1);
        newDishPlan.setId(id);
        Boolean isSuccess = updateById(newDishPlan);
        return isSuccess;
    }

    @Override
    public List<PushPlanNewDishEntity> queryBySourceId(Long brandIdentity,Long sourceId) throws Exception {
        EntityWrapper<PushPlanNewDishEntity> eWrapper = new EntityWrapper<>(new PushPlanNewDishEntity());
        eWrapper.eq("brand_identity",brandIdentity);
        eWrapper.eq("source_id",sourceId);
        eWrapper.eq("source_type",3);
        eWrapper.setSqlSelect("id,name,shop_identity");
        List<PushPlanNewDishEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public int getDataCount(String where) {
        return 0;
    }
}
