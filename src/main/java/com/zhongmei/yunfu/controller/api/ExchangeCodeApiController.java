package com.zhongmei.yunfu.controller.api;

import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.ExchangeCodeModel;
import com.zhongmei.yunfu.controller.model.ExpandedMarketingModel;
import com.zhongmei.yunfu.domain.entity.ExchangeCodeEntity;
import com.zhongmei.yunfu.service.ExchangeCodeService;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 提成兑换验证编码
 */
@RestController
@RequestMapping("/wxapp/exchange")
public class ExchangeCodeApiController {

    @Autowired
    ExchangeCodeService mExchangeCodeService;

    @GetMapping("/getCode")
    public BaseDataModel getCode(Model model, ExchangeCodeModel mExchangeCodeModel) {

        BaseDataModel mBaseDataModel = new BaseDataModel();

        try {
            ExchangeCodeEntity entity = new ExchangeCodeEntity();
            entity.setBrandIdenty(mExchangeCodeModel.getBrandIdenty());
            entity.setShopIdenty(mExchangeCodeModel.getShopIdenty());
            entity.setCustomerId(mExchangeCodeModel.getCustomerId());
            entity.setMobile(mExchangeCodeModel.getMobile());
            ExchangeCodeEntity mExchangeCodeEntity = mExchangeCodeService.queryCode(entity);

            //如果为空，这表示用户还未获取过兑现编码，则系统为其添加兑换编码
            if(mExchangeCodeEntity == null){
                String code = ToolsUtil.getCard(6);
                entity.setExchangeCode(code);
                entity.setCreateTime(new Date());
                entity.setStatusFlag(1);
                mExchangeCodeEntity = mExchangeCodeService.installCode(entity);
            }else{
                //获取24小时失效时间
                Long invalidTime = mExchangeCodeEntity.getCreateTime().getTime()+6*60*60*1000;

                //如果已失效，则自动生成新的兑换凭证
                if(invalidTime < new Date().getTime()){
                    String code = ToolsUtil.getCard(6);
                    mExchangeCodeEntity.setExchangeCode(code);
                    mExchangeCodeEntity.setCreateTime(new Date());
                    mExchangeCodeEntity.setStatusFlag(1);
                    mExchangeCodeService.updateCode(mExchangeCodeEntity);
                }else if(mExchangeCodeEntity.getStatusFlag() == 2){ //如已使用了该次兑换凭证，则自动生成新的凭证
                    String code = ToolsUtil.getCard(6);
                    mExchangeCodeEntity.setExchangeCode(code);
                    mExchangeCodeEntity.setCreateTime(new Date());
                    mExchangeCodeEntity.setStatusFlag(1);
                    mExchangeCodeService.updateCode(mExchangeCodeEntity);
                }
            }

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取数据成功");
            mBaseDataModel.setData(mExchangeCodeEntity);

        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }
}
