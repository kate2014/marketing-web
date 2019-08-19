package com.zhongmei.yunfu.controller.api;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.api.model.NewDishPushReq;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.NewDishPushModel;
import com.zhongmei.yunfu.controller.model.NewDishPushSearchModel;
import com.zhongmei.yunfu.controller.model.PageResonseModel;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.domain.entity.PushPlanNewDishEntity;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.OperationalRecordsService;
import com.zhongmei.yunfu.service.PushPlanNewDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/wxapp/pushPlanNewDishApi")
public class PushPlanNewDishApiController {

    @Autowired
    PushPlanNewDishService mPushNewDishService;

    @Autowired
    OperationalRecordsService mOperationalRecordsService;

    @Autowired
    CustomerService mCustomerService;

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
                orEntity.setCustomerId(req.getCustomerId());
                orEntity.setActivityId(req.getId());
                orEntity.setSource(2);
                orEntity.setType(1);
                OperationalRecordsEntity recordEntity = mOperationalRecordsService.queryByCustomer(orEntity);
                //判断用户是否是第一次浏览
                if(recordEntity == null){
                    //获取查看用户基本信息
                    CustomerEntity mCustomerEntity = new CustomerEntity();
                    mCustomerEntity.setBrandIdenty(req.getBrandIdenty());
                    mCustomerEntity.setShopIdenty(req.getShopIdenty());
                    mCustomerEntity.setId(req.getCustomerId());
                    Map<String, String> tempMap =  mCustomerService.queryByWxCustomerId(mCustomerEntity);

                    orEntity = new OperationalRecordsEntity();
                    orEntity.setBrandIdenty(req.getBrandIdenty());
                    orEntity.setShopIdenty(req.getShopIdenty());
                    orEntity.setCustomerId(req.getCustomerId());
                    orEntity.setCustomerPhone(tempMap.get("pPhone"));
                    orEntity.setCustomerName(tempMap.get("pName"));
                    orEntity.setWxOpenId(req.getWxOpenId());
                    orEntity.setWxPhoto(tempMap.get("photo"));
                    orEntity.setWxName(tempMap.get("wName"));
                    orEntity.setActivityId(req.getId());
                    orEntity.setOperationalCount(1);
                    orEntity.setType(1);
                    orEntity.setSource(2);
                    orEntity.setServerCreateTime(new Date());
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.addOperational(orEntity);
                }else{

                    OperationalRecordsEntity modifityEntity = new OperationalRecordsEntity();

                    //如果之前用户浏览信息中没有会员电话或名称，这可以进行后续补全
                    if(recordEntity.getCustomerPhone() == null || recordEntity.getCustomerPhone().equals("")){
                        //获取查看用户基本信息
                        CustomerEntity mCustomerEntity = new CustomerEntity();
                        mCustomerEntity.setBrandIdenty(req.getBrandIdenty());
                        mCustomerEntity.setShopIdenty(req.getShopIdenty());
                        mCustomerEntity.setId(req.getCustomerId());
                        Map<String, String> tempMap =  mCustomerService.queryByWxCustomerId(mCustomerEntity);

                        modifityEntity.setCustomerPhone(tempMap.get("pPhone"));
                        modifityEntity.setCustomerName(tempMap.get("pName"));
                    }

                    modifityEntity.setId(recordEntity.getId());
                    modifityEntity.setOperationalCount(recordEntity.getOperationalCount()+1);
                    modifityEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.modiftyById(modifityEntity);
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
