package com.zhongmei.yunfu.controller.api.model;

import java.util.List;

public class WxTemplateListResp {

    /**
     * errcode : 0
     * errmsg : ok
     */

    public int errcode;
    public String errmsg;
    public List<WxTemplateItemResp> list;

    public boolean isOk() {
        return errcode == 0;
    }

    public class WxTemplateItemResp {
        public String template_id;
        public String title;
        public String content;
        public String example;
    }
}
