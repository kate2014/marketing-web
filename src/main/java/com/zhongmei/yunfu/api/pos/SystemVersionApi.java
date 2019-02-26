package com.zhongmei.yunfu.api.pos;

import com.zhongmei.yunfu.api.pos.vo.SystemVersionReq;
import com.zhongmei.yunfu.api.pos.vo.SystemVersionResp;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.SystemVersionEntity;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.SystemVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pos/systemVersion")
public class SystemVersionApi {

    @Autowired
    SystemVersionService mSystemVersionService;

    @Autowired
    CommercialService mCommercialService;

    @RequestMapping("/checkVersion")
    public SystemVersionResp queryWxTradeByCustomer(@RequestBody SystemVersionReq mSystemVersionReq){
        SystemVersionResp mSystemVersionResp = new SystemVersionResp();
        try {
            SystemVersionEntity mSystemVersionEntity = mSystemVersionService.checkVersion(mSystemVersionReq);
            //判断商户是否是最新版本:如果商户当时版本低于系统最新版本，则返回最新版本信息，进行提示升级
            if(mSystemVersionReq.getVersionCode()< mSystemVersionEntity.getVersionCode()){
                mSystemVersionResp.setContent(mSystemVersionEntity);
                mSystemVersionResp.setStateCode("1");
            }else{
                mSystemVersionResp.setStateCode("0");
            }
            mSystemVersionResp.setStatus("1000");
            //更新商户当前使用系统版本号
            CommercialEntity mCommercialEntity = new CommercialEntity();
            mCommercialEntity.setCommercialId(mSystemVersionReq.getShopId());
            mCommercialEntity.setCurrentVersion(mSystemVersionReq.getVersionCode());
            mCommercialService.modifyCommercial(mCommercialEntity);
        }catch (Exception e){
            e.printStackTrace();
            mSystemVersionResp.setStatus("1001");
            mSystemVersionResp.setMessage("服务器异常，请稍后再试");
        }

        return mSystemVersionResp;

    }
}
