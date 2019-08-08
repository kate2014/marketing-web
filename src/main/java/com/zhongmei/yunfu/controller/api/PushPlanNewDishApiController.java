package com.zhongmei.yunfu.controller.api;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.api.model.NewDishPushReq;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.NewDishPushModel;
import com.zhongmei.yunfu.controller.model.NewDishPushSearchModel;
import com.zhongmei.yunfu.controller.model.PageResonseModel;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.domain.entity.PushPlanNewDishEntity;
import com.zhongmei.yunfu.service.OperationalRecordsService;
import com.zhongmei.yunfu.service.PushPlanNewDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/wxapp/pushPlanNewDishApi")
public class PushPlanNewDishApiController {

    @Autowired
    PushPlanNewDishService mPushNewDishService;

    @Autowired
    OperationalRecordsService mOperationalRecordsService;

    /**
     * 获取活动推送列表
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public BaseDataModel list(NewDishPushSearchModel model) {

        BaseDataModel responseMode = new BaseDataModel();

        try {
            Page<PushPlanNewDishEntity> pushPlanNewDish = mPushNewDishService.list(model);

            ArrayList<NewDishPushModel> newDishPushModeList = new ArrayList<NewDishPushModel>();
            if (pushPlanNewDish.getRecords() != null) {
                for (PushPlanNewDishEntity planNewDish : pushPlanNewDish.getRecords()) {
                    newDishPushModeList.add(new NewDishPushModel(planNewDish));
                }
            }

            PageResonseModel<NewDishPushModel> pageResonseMode = new PageResonseModel<>();
            pageResonseMode.setData(newDishPushModeList);
            pageResonseMode.setPageNo(pushPlanNewDish.getCurrent());
            pageResonseMode.setPages(pushPlanNewDish.getPages());
            pageResonseMode.setPageSize(pushPlanNewDish.getSize());


            responseMode.setData(pageResonseMode);
            responseMode.setMsg("数据获取成功");
            responseMode.setState("1000");
        } catch (Exception ex) {
            ex.printStackTrace();
            responseMode.setData(null);
            responseMode.setMsg("数据获取失败：" + ex.getMessage());
            responseMode.setState("1001");
        }

        return responseMode;
    }


    /**
     * 根据ID获取新品推送详情
     *
     * @param req
     * @return
     */
    @GetMapping("/query")
    public BaseDataModel queryByid(NewDishPushReq req) {
        BaseDataModel responseMode = new BaseDataModel();
        try {
            PushPlanNewDishEntity newDishPlan = mPushNewDishService.queryByid(req.getId());

            NewDishPushModel newDishPushmodel = new NewDishPushModel(newDishPlan);

            //更新浏览次数
            PushPlanNewDishEntity updateDishPlan = new PushPlanNewDishEntity();
            updateDishPlan.setId(newDishPlan.getId());
            updateDishPlan.setScanNumber(newDishPlan.getScanNumber()+1);
            mPushNewDishService.updateNewDishPushPlan(updateDishPlan);

            //添加顾客查看记录,如该顾客对该条活跃已有同样的操作是，只需在原有操作次数的基础上+1
            if(req.getWxOpenId() != null){
                OperationalRecordsEntity orEntity = new OperationalRecordsEntity();
                orEntity.setBrandIdenty(req.getBrandIdenty());
                orEntity.setShopIdenty(req.getShopIdenty());
                orEntity.setWxOpenId(req.getWxOpenId());
                orEntity.setActivityId(req.getId());
                orEntity.setType(1);
                OperationalRecordsEntity recordEntity = mOperationalRecordsService.queryByCustomer(orEntity);
                if(recordEntity == null){
                    orEntity = new OperationalRecordsEntity();
                    orEntity.setBrandIdenty(req.getBrandIdenty());
                    orEntity.setShopIdenty(req.getShopIdenty());
                    orEntity.setCustomerId(req.getCustomerId());
                    orEntity.setCustomerName(req.getCustomerName());
                    orEntity.setWxOpenId(req.getWxOpenId());
                    orEntity.setWxPhoto(req.getWxPhoto());
                    orEntity.setWxName(req.getWxName());
                    orEntity.setActivityId(req.getId());
                    orEntity.setOperationalCount(1);
                    orEntity.setType(1);
                    orEntity.setServerCreateTime(new Date());
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.addOperational(orEntity);
                }else{
                    orEntity.setBrandIdenty(req.getBrandIdenty());
                    orEntity.setShopIdenty(req.getShopIdenty());
                    orEntity.setWxOpenId(req.getWxOpenId());
                    orEntity.setWxPhoto(req.getWxPhoto());
                    orEntity.setActivityId(req.getId());
                    orEntity.setOperationalCount(recordEntity.getOperationalCount()+1);
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.modiftyOperational(orEntity);
                }
            }

            responseMode.setMsg("数据获取成功");
            responseMode.setData(newDishPushmodel);
            responseMode.setState("1000");
        } catch (Exception ex) {
            ex.printStackTrace();
            responseMode.setData(false);
            responseMode.setMsg("数据获取失败：" + ex.getMessage());
            responseMode.setState("1001");
        }


        return responseMode;
    }

}
