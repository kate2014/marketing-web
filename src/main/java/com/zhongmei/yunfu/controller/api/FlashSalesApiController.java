package com.zhongmei.yunfu.controller.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.FlashSalesModel;
import com.zhongmei.yunfu.domain.entity.FlashSalesMarketingEntity;
import com.zhongmei.yunfu.service.FlashSalesMarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀活动接口
 */
@RestController
@RequestMapping("/wxapp/flashSales")
public class FlashSalesApiController {

    @Autowired
    FlashSalesMarketingService mFlashSalesMarketingService;

    @GetMapping("/getListData")
    public BaseDataModel getFlashSalesList(ModelMap model, FlashSalesModel mFlashSalesModel) {

        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            mFlashSalesModel.setEnabledFlag(1);
            Page<FlashSalesMarketingEntity> listPage = mFlashSalesMarketingService.queryFlashSalesList(mFlashSalesModel);
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取秒杀数据成功");
            mBaseDataModel.setData(listPage);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取秒杀数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 获取秒杀活动详情
     *
     * @param model
     * @param mFlashSalesModel
     * @return
     */
    @GetMapping("/flashSalesDetail")
    public BaseDataModel flashSalesDetail(ModelMap model, FlashSalesModel mFlashSalesModel) {

        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            FlashSalesMarketingEntity mFlashSalesMarketing = mFlashSalesMarketingService.findFlashSalesById(mFlashSalesModel.getId());
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取秒杀数据成功");
            mBaseDataModel.setData(mFlashSalesMarketing);

        } catch (Exception e) {
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取秒杀数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }
}
