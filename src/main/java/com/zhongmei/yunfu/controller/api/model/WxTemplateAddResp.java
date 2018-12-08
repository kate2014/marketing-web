package com.zhongmei.yunfu.controller.api.model;

public class WxTemplateAddResp {

    /**
     * errcode : 0
     * errmsg : ok
     */

    public int errcode;
    public String errmsg;
    public String template_id;

    public boolean isOk() {
        return errcode == 0;
    }

}
