package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.CustomerArchivesEntity;
import com.zhongmei.yunfu.domain.entity.TaskRemindEntity;

import java.util.List;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface TaskRemindService extends IService<TaskRemindEntity> {

    /**
     * 添加任务提醒
     * @param mTaskRemindEntity
     * @return
     * @throws Exception
     */
    boolean addTaskRemind(TaskRemindEntity mTaskRemindEntity)throws Exception;

    /**
     * 获取任务提醒分页列表
     * @param mTaskRemindEntity
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<TaskRemindEntity> queryArchivesPage(TaskRemindEntity mTaskRemindEntity, int pageNo, int pageSize)throws Exception;

    /**
     * 获取任务提醒列表
     * @param mTaskRemindEntity
     * @return
     * @throws Exception
     */
    List<TaskRemindEntity> queryArchivesList(TaskRemindEntity mTaskRemindEntity)throws Exception;

    /**
     * 更新会员档案
     * @param mTaskRemindEntity
     * @return
     * @throws Exception
     */
    boolean modfityTaskRemind(TaskRemindEntity mTaskRemindEntity)throws Exception;

    /**
     * 根据档案id删除档案内容
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteTaskRemind(Long id)throws Exception;
}
