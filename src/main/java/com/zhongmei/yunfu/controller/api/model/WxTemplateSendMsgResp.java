package com.zhongmei.yunfu.controller.api.model;

public class WxTemplateSendMsgResp {

    /**
     * errcode : 0
     * errmsg : ok
     */

    public int errcode;
    public String errmsg;

    

    public boolean isOk() {
        return errcode == 0;
    }
}
