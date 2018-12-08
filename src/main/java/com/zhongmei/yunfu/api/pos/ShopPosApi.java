package com.zhongmei.yunfu.api.pos;

import com.zhongmei.yunfu.api.ApiResult;
import com.zhongmei.yunfu.api.pos.vo.ShopInfoResp;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.service.CommercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pos/shop")
public class ShopPosApi {

    @Autowired
    CommercialService commercialService;

    @RequestMapping("/info/{device}")
    public Object info(@PathVariable String device) throws Exception {
        CommercialEntity commercialEntity = commercialService.selectByDevice(device, true);
        ShopInfoResp infoResp = new ShopInfoResp();
        infoResp.setDeviceId(device);
        infoResp.setShopId(commercialEntity.getCommercialId());
        infoResp.setShopName(commercialEntity.getCommercialName());
        infoResp.setBrandId(commercialEntity.getBrandId());
        infoResp.setBrandName(commercialEntity.getBranchName());
        infoResp.setShopPhone(commercialEntity.getCommercialPhone());
        infoResp.setShopAddress(commercialEntity.getCommercialAdress());
        infoResp.setShopLogo(commercialEntity.getCommercialLogo());
        infoResp.setLatitude(commercialEntity.getLatitude());
        infoResp.setLongitude(commercialEntity.getLongitude());
        return ApiResult.newSuccess(infoResp);
    }
}
