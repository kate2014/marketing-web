package com.zhongmei.yunfu.controller.api;


import com.alibaba.fastjson.JSONObject;
import com.zhongmei.yunfu.controller.api.model.WxTempMsgFormId;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.service.CommercialPaySettingService;
import com.zhongmei.yunfu.service.WxFormService;
import com.zhongmei.yunfu.thirdlib.wxapp.WxTemplateMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-11-09
 */

@RestController
@RequestMapping("/wxapp/wxForm")
public class WxFormController {

    @Autowired
    WxFormService mWxFormService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CommercialPaySettingService commercialPaySettingService;

    @RequestMapping("/addFrom")
    public BaseDataModel addForm(@RequestBody WxTempMsgFormId mWxFormEntity) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            mWxFormService.addOrUpdateWxForm(mWxFormEntity);
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("添加from成功");
            mBaseDataModel.setData(true);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("添加from失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    @RequestMapping("/send")
    public Object sendPaySuccess(@RequestBody JSONObject jsonBody) throws Exception {
        Integer msgType = jsonBody.getInteger("msgType");
        WxTemplateMessageHandler wxTemplateMessage = WxTemplateMessageHandler.create(msgType);
        wxTemplateMessage.send(jsonBody.toJSONString());
        return BaseDataModel.newSuccess("发送成功");
    }
}

