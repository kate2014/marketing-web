package com.zhongmei.yunfu.controller.api.model;

import java.util.HashMap;
import java.util.Map;

public class WxTemplateSendMsgReq {

    /**
     * touser : OPENID
     * template_id : TEMPLATE_ID
     * page : index
     * form_id : FORMID
     * data : {"keyword1":{"value":"339208499"},"keyword2":{"value":"2015年01月05日 12:30"},"keyword3":{"value":"粤海喜来登酒店"},"keyword4":{"value":"广州市天河区天河路208号"}}
     * emphasis_keyword : keyword1.DATA
     */

    public String touser;
    public String template_id;
    public String page;
    public String form_id;
    //public String emphasis_keyword;
    public Map<String, Map<String, String>> data;

    public WxTemplateSendMsgReq addDataItem(String keyword, String value) {
        if (data == null) {
            data = new HashMap<>();
        }
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("value", value);
        data.put(keyword, valueMap);
        return this;
    }
}
