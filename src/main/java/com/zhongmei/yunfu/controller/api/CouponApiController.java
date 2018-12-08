package com.zhongmei.yunfu.controller.api;

import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.CouponModel;
import com.zhongmei.yunfu.domain.entity.CouponEntity;
import com.zhongmei.yunfu.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wxapp/couponApi")
public class CouponApiController {

    @Autowired
    CouponService couponServer;

    /**
     * 根据ID获优惠卷详情
     *
     * @param id
     * @return
     */
    @GetMapping("/query")
    public BaseDataModel queryByid(Long id) {
        BaseDataModel responseMode = new BaseDataModel();
        try {
            CouponEntity coupom = couponServer.queryByid(id);
            CouponModel couponModel = new CouponModel(coupom);

            responseMode.setMsg("数据获取成功");
            responseMode.setData(couponModel);
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
