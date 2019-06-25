package com.zhongmei.yunfu.api.pos;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.api.ApiRespStatusException;
import com.zhongmei.yunfu.api.ApiResult;
import com.zhongmei.yunfu.api.PosApiController;
import com.zhongmei.yunfu.api.pos.vo.CustomerArchivesReq;
import com.zhongmei.yunfu.api.pos.vo.CustomerArchivesResp;
import com.zhongmei.yunfu.domain.entity.CustomerArchivesEntity;
import com.zhongmei.yunfu.domain.entity.TaskRemindEntity;
import com.zhongmei.yunfu.service.CustomerArchivesService;
import com.zhongmei.yunfu.service.TaskRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pos/customerArchives")
public class CustomerArchivesApi extends PosApiController {

    @Autowired
    CustomerArchivesService mCustomerArchivesService;
    @Autowired
    TaskRemindService mTaskRemindService;

    @RequestMapping("/archivesList")
    public ApiResult list(@RequestBody CustomerArchivesReq req) throws ApiRespStatusException {
        CustomerArchivesEntity mCustomerArchivesEntity = new CustomerArchivesEntity();
        try {
            mCustomerArchivesEntity.setBrandIdenty(req.getHeader().getBrandId());
            mCustomerArchivesEntity.setShopIdenty(req.getHeader().getShopId());
            mCustomerArchivesEntity.setCustomerId(req.getCustomerId());
            mCustomerArchivesEntity.setTitle(req.getTitle());

            Page<CustomerArchivesEntity> listPage = mCustomerArchivesService.queryArchivesPage(mCustomerArchivesEntity,req.getPageNo(),req.getPageSize());
            List<CustomerArchivesResp> result = new ArrayList<>();
            for (CustomerArchivesEntity entity : listPage.getRecords()) {
                CustomerArchivesResp archivesResp = new CustomerArchivesResp();
                archivesResp.setId(entity.getId());
                archivesResp.setTitle(entity.getTitle());
                archivesResp.setContent(entity.getContent());
                archivesResp.setCustomerId(entity.getCustomerId());
                archivesResp.setType(entity.getType());

                result.add(archivesResp);
            }
            return ApiResult.newSuccess(result, listPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiResult.newResult(1001,"接口异常");
        }

    }

    @RequestMapping("/addArchives")
    public ApiResult addArchives(@RequestBody CustomerArchivesReq req) throws ApiRespStatusException {
        try {
            CustomerArchivesEntity mCustomerArchivesEntity = new CustomerArchivesEntity();
            mCustomerArchivesEntity.setBrandIdenty(req.getHeader().getBrandId());
            mCustomerArchivesEntity.setShopIdenty(req.getHeader().getShopId());
            mCustomerArchivesEntity.setCustomerId(req.getCustomerId());
            mCustomerArchivesEntity.setTitle(req.getTitle());
            mCustomerArchivesEntity.setContent(req.getContent());
            mCustomerArchivesEntity.setType(1);
            mCustomerArchivesEntity.setStatusFlag(1);
            mCustomerArchivesEntity.setCreatorId(req.getCreatorId());
            mCustomerArchivesEntity.setCreatorName(req.getCreatorName());
            mCustomerArchivesEntity.setServerCreateTime(new Date());
            mCustomerArchivesEntity.setUpdatorId(req.getCreatorId());
            mCustomerArchivesEntity.setUpdatorName(req.getCreatorName());
            mCustomerArchivesEntity.setServerUpdateTime(new Date());

            mCustomerArchivesService.addCustomerArchives(mCustomerArchivesEntity);

            return ApiResult.newSuccess(1000);
        }catch (Exception e){
            e.printStackTrace();
            return ApiResult.newResult(1001,"接口异常");
        }
    }

    @RequestMapping("/queryById")
    public ApiResult queryById(@RequestBody CustomerArchivesReq req) throws ApiRespStatusException {
        try {

            CustomerArchivesResp mCustomerArchivesResp = new CustomerArchivesResp();
            CustomerArchivesEntity mCustomerArchivesEntity = mCustomerArchivesService.queryById(req.getArchivesId());
            mCustomerArchivesResp.setType(mCustomerArchivesEntity.getType());
            mCustomerArchivesResp.setId(mCustomerArchivesEntity.getId());
            mCustomerArchivesResp.setTitle(mCustomerArchivesEntity.getTitle());
            mCustomerArchivesResp.setContent(mCustomerArchivesEntity.getContent());
            mCustomerArchivesResp.setCustomerId(mCustomerArchivesEntity.getCustomerId());
            List<TaskRemindEntity> listTask = mTaskRemindService.queryByDocId(req.getArchivesId());
            mCustomerArchivesResp.setListTask(listTask);
            return ApiResult.newSuccess(mCustomerArchivesResp);

        }catch (Exception e){
            e.printStackTrace();
            return ApiResult.newResult(1001,"接口异常");
        }
    }

}
