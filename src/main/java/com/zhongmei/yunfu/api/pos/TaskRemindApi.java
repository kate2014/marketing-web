package com.zhongmei.yunfu.api.pos;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.api.ApiRespStatusException;
import com.zhongmei.yunfu.api.ApiResult;
import com.zhongmei.yunfu.api.PosApiController;
import com.zhongmei.yunfu.api.pos.vo.CustomerArchivesReq;
import com.zhongmei.yunfu.api.pos.vo.CustomerArchivesResp;
import com.zhongmei.yunfu.api.pos.vo.TaskRemindReq;
import com.zhongmei.yunfu.api.pos.vo.TaskRemindResp;
import com.zhongmei.yunfu.domain.entity.CustomerArchivesEntity;
import com.zhongmei.yunfu.domain.entity.TaskRemindEntity;
import com.zhongmei.yunfu.service.TaskRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pos/taskRemind")
public class TaskRemindApi extends PosApiController {

    @Autowired
    TaskRemindService mTaskRemindService;

    @RequestMapping("/list")
    public ApiResult list(@RequestBody TaskRemindReq req) throws Exception {

        TaskRemindEntity mTaskRemindEntity = new TaskRemindEntity();
        mTaskRemindEntity.setBrandIdenty(req.getHeader().getBrandId());
        mTaskRemindEntity.setShopIdenty(req.getHeader().getShopId());
        mTaskRemindEntity.setCustomerId(req.getCustomerId());
        mTaskRemindEntity.setUserId(req.getUserId());
        mTaskRemindEntity.setTitle(req.getTitle());
        mTaskRemindEntity.setStatus(req.getStatus());
        mTaskRemindEntity.setRemindTime(req.getRemindTime());

        Page<TaskRemindEntity> listPage = mTaskRemindService.queryTaskRemindPage(mTaskRemindEntity,req.getPageNo(),req.getPageSize());
        List<TaskRemindResp> result = new ArrayList<>();
        for (TaskRemindEntity entity : listPage.getRecords()) {
            TaskRemindResp taskRemind = new TaskRemindResp();
            taskRemind.setId(entity.getId());
            taskRemind.setTitle(entity.getTitle());
            taskRemind.setContent(entity.getContent());
            taskRemind.setRemindTime(entity.getRemindTime());
            taskRemind.setCustomerId(entity.getCustomerId());
            taskRemind.setCustomerName(entity.getCustomerName());
            taskRemind.setCustomerMobile(entity.getCustomerMobile());
            taskRemind.setUserId(entity.getUserId());
            taskRemind.setUserName(entity.getUserName());
            taskRemind.setType(entity.getType());
            taskRemind.setServerCreateTime(entity.getServerCreateTime());
            taskRemind.setServerUpdateTime(entity.getServerUpdateTime());

            result.add(taskRemind);
        }
        return ApiResult.newSuccess(result, listPage);

    }

    @RequestMapping("/addTaskRemind")
    public ApiResult addTaskRemind(@RequestBody TaskRemindReq req) throws ApiRespStatusException {
        TaskRemindEntity mTaskRemindEntity = new TaskRemindEntity();
        mTaskRemindEntity.setBrandIdenty(req.getHeader().getBrandId());
        mTaskRemindEntity.setShopIdenty(req.getHeader().getShopId());
        mTaskRemindEntity.setCustomerId(req.getCustomerId());
        mTaskRemindEntity.setCustomerName(req.getCustomerName());
        mTaskRemindEntity.setCustomerMobile(req.getCustomerMobile());
        mTaskRemindEntity.setUserId(req.getUserId());
        mTaskRemindEntity.setUserName(req.getUserName());
        mTaskRemindEntity.setRemindTime(req.getRemindTime());
        mTaskRemindEntity.setTitle(req.getTitle());
        mTaskRemindEntity.setContent(req.getContent());
        mTaskRemindEntity.setStatus(req.getStatus());
        mTaskRemindEntity.setCustomerDocId(req.getCustomerDocId());
        mTaskRemindEntity.setTaskResult(req.getTaskResult());
        mTaskRemindEntity.setType(1);
        mTaskRemindEntity.setStatusFlag(1);
        mTaskRemindEntity.setCreatorId(req.getCreatorId());
        mTaskRemindEntity.setCreatorName(req.getCreatorName());
        mTaskRemindEntity.setServerCreateTime(new Date());
        mTaskRemindEntity.setUpdatorId(req.getCreatorId());
        mTaskRemindEntity.setUpdatorName(req.getCreatorName());
        mTaskRemindEntity.setServerUpdateTime(new Date());

        mTaskRemindService.addTaskRemind(mTaskRemindEntity);
        return ApiResult.newSuccess(mTaskRemindEntity);
    }

    @RequestMapping("/modfityTaskRemind")
    public ApiResult modfityTaskRemind(@RequestBody TaskRemindReq req) throws ApiRespStatusException {

        TaskRemindEntity mTaskRemindEntity = new TaskRemindEntity();
        mTaskRemindEntity.setTaskResult(req.getTaskResult());
        mTaskRemindEntity.setUpdatorId(req.getCreatorId());
        mTaskRemindEntity.setUpdatorName(req.getCreatorName());
        mTaskRemindEntity.setServerUpdateTime(new Date());
        mTaskRemindEntity.setId(req.getTaskId());
        mTaskRemindEntity.setCustomerId(req.getCustomerId());
        mTaskRemindEntity.setCustomerName(req.getCustomerName());
        mTaskRemindEntity.setCustomerMobile(req.getCustomerMobile());
        mTaskRemindEntity.setUserId(req.getUserId());
        mTaskRemindEntity.setUserName(req.getUserName());
        mTaskRemindEntity.setRemindTime(req.getRemindTime());
        mTaskRemindEntity.setTitle(req.getTitle());
        mTaskRemindEntity.setContent(req.getContent());
        mTaskRemindEntity.setStatus(req.getStatus());
        mTaskRemindEntity.setTaskResult(req.getTaskResult());
        mTaskRemindEntity.setType(req.getType());

        mTaskRemindService.modfityTaskRemind(mTaskRemindEntity);
        return ApiResult.newSuccess(mTaskRemindEntity);
    }


}
