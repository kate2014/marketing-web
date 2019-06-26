package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.api.ApiRespStatusException;
import com.zhongmei.yunfu.domain.entity.TaskRemindEntity;
import com.zhongmei.yunfu.domain.mapper.TaskRemindMapper;
import com.zhongmei.yunfu.service.TaskRemindService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public boolean addTaskRemind(TaskRemindEntity mTaskRemindEntity) {
        return insert(mTaskRemindEntity);
    }

    @Override
    public Page<TaskRemindEntity> queryTaskRemindPage(TaskRemindEntity mTaskRemindEntity, int pageNo, int pageSize) throws Exception {
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
        if(mTaskRemindEntity.getStatus() != null && !mTaskRemindEntity.getStatus().equals("")){
            eWrapper.eq("status", mTaskRemindEntity.getStatus());
        }
        if(mTaskRemindEntity.getRemindTime() != null && !mTaskRemindEntity.getRemindTime().equals("")){

            Date startData = DateFormatUtil.parseDate(DateFormatUtil.format(mTaskRemindEntity.getRemindTime(),DateFormatUtil.FORMAT_DATE)+" 00:00:00",DateFormatUtil.FORMAT_FULL_DATE);
            Date endData = DateFormatUtil.parseDate(DateFormatUtil.format(mTaskRemindEntity.getRemindTime(),DateFormatUtil.FORMAT_DATE)+" 23:59:59",DateFormatUtil.FORMAT_FULL_DATE);

            eWrapper.between("remind_time",startData,endData);
            eWrapper.eq("remind_time", mTaskRemindEntity.getRemindTime());
        }

        eWrapper.eq("status_flag", 1);

        eWrapper.orderBy("server_create_time",false);

        Page<TaskRemindEntity> listPage = new Page<>(pageNo,pageSize);

        Page<TaskRemindEntity> listData = selectPage(listPage,eWrapper);
        return listData;
    }

    @Override
    public List<TaskRemindEntity> queryTaskRemindList(TaskRemindEntity mTaskRemindEntity) throws Exception {
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
    public boolean modfityTaskRemind(TaskRemindEntity mTaskRemindEntity){
        return updateById(mTaskRemindEntity);
    }

    @Override
    public boolean deleteTaskRemind(Long id) throws Exception {
        return deleteById(id);
    }

    @Override
    public List<TaskRemindEntity> queryByDocId(Long id){
        EntityWrapper<TaskRemindEntity> eWrapper = new EntityWrapper<>(new TaskRemindEntity());
        eWrapper.eq("customer_doc_id", id);
        eWrapper.eq("status_flag", 1);
        eWrapper.orderBy("server_create_time",false);
        return selectList(eWrapper);
    }
}
