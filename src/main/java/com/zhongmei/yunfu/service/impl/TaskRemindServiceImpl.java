package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.TaskRemindEntity;
import com.zhongmei.yunfu.domain.mapper.TaskRemindMapper;
import com.zhongmei.yunfu.service.TaskRemindService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 任务提醒
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Service
public class TaskRemindServiceImpl extends ServiceImpl<TaskRemindMapper, TaskRemindEntity> implements TaskRemindService {


    @Override
    public boolean addTaskRemind(TaskRemindEntity mTaskRemindEntity) throws Exception {
        return insert(mTaskRemindEntity);
    }

    @Override
    public Page<TaskRemindEntity> queryArchivesPage(TaskRemindEntity mTaskRemindEntity, int pageNo, int pageSize) throws Exception {
        EntityWrapper<TaskRemindEntity> eWrapper = new EntityWrapper<>(new TaskRemindEntity());

        eWrapper.eq("brand_identy", mTaskRemindEntity.getBrandIdenty());
        eWrapper.eq("shop_identy", mTaskRemindEntity.getShopIdenty());

        if(mTaskRemindEntity.getCustomerId() != null && !mTaskRemindEntity.getCustomerId().equals("")){
            eWrapper.eq("customer_id", mTaskRemindEntity.getCustomerId());
        }

        if(mTaskRemindEntity.getUserId() != null && !mTaskRemindEntity.getUserId().equals("")){
            eWrapper.eq("user_id", mTaskRemindEntity.getUserId());
        }

        if(mTaskRemindEntity.getType() != null && !mTaskRemindEntity.getType().equals("")){
            eWrapper.eq("type", mTaskRemindEntity.getType());
        }
        if(mTaskRemindEntity.getTitle() != null && !mTaskRemindEntity.getTitle().equals("")){
            eWrapper.like("title", mTaskRemindEntity.getTitle());
        }

        eWrapper.eq("status_flag", 1);

        eWrapper.orderBy("server_create_time",false);

        Page<TaskRemindEntity> listPage = new Page<>(pageNo,pageSize);

        Page<TaskRemindEntity> listData = selectPage(listPage,eWrapper);
        return listData;
    }

    @Override
    public List<TaskRemindEntity> queryArchivesList(TaskRemindEntity mTaskRemindEntity) throws Exception {
        EntityWrapper<TaskRemindEntity> eWrapper = new EntityWrapper<>(new TaskRemindEntity());

        eWrapper.eq("brand_identy", mTaskRemindEntity.getBrandIdenty());
        eWrapper.eq("shop_identy", mTaskRemindEntity.getShopIdenty());

        if(mTaskRemindEntity.getCustomerId() != null && !mTaskRemindEntity.getCustomerId().equals("")){
            eWrapper.eq("customer_id", mTaskRemindEntity.getCustomerId());
        }

        if(mTaskRemindEntity.getUserId() != null && !mTaskRemindEntity.getUserId().equals("")){
            eWrapper.eq("user_id", mTaskRemindEntity.getUserId());
        }

        if(mTaskRemindEntity.getType() != null && !mTaskRemindEntity.getType().equals("")){
            eWrapper.eq("type", mTaskRemindEntity.getType());
        }
        if(mTaskRemindEntity.getTitle() != null && !mTaskRemindEntity.getTitle().equals("")){
            eWrapper.like("title", mTaskRemindEntity.getTitle());
        }

        eWrapper.eq("status_flag", 1);

        eWrapper.orderBy("server_create_time",false);

        List<TaskRemindEntity> listData = selectList(eWrapper);
        return listData;
    }

    @Override
    public boolean modfityTaskRemind(TaskRemindEntity mTaskRemindEntity) throws Exception {
        return updateById(mTaskRemindEntity);
    }

    @Override
    public boolean deleteTaskRemind(Long id) throws Exception {
        return deleteById(id);
    }
}
