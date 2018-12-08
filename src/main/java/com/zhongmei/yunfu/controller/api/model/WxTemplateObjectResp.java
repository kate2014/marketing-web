package com.zhongmei.yunfu.controller.api.model;

import java.util.ArrayList;
import java.util.List;

public class WxTemplateObjectResp {

    /**
     * errcode : 0
     * errmsg : ok
     * id : AT0002
     * title : 购买成功通知
     * keyword_list : [{"keyword_id":3,"name":"购买地点","example":"TIT造舰厂"},{"keyword_id":4,"name":"购买时间","example":"2016年6月6日"},{"keyword_id":5,"name":"物品名称","example":"咖啡"}]
     */

    public int errcode;
    public String errmsg;
    public String id;
    public String title;
    public List<KeywordListBean> keyword_list;

    public boolean isOk() {
        return errcode == 0;
    }

    public List<Integer> getKeywordIds() {
        List<Integer> result = new ArrayList<>();
        if (keyword_list != null) {
            for (KeywordListBean bean : keyword_list) {
                result.add(bean.keyword_id);
            }
        }
        return result;
    }

    public static class KeywordListBean {
        /**
         * keyword_id : 3
         * name : 购买地点
         * example : TIT造舰厂
         */

        public int keyword_id;
        public String name;
        public String example;
    }
}
