package com.zhongmei.yunfu.controller.api.model;

public class WxAccessToken {

    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     */

    public String access_token;
    public int expires_in;


    /**
     * errcode : 40013
     * errmsg : invalid appid
     */
    public int errcode;
    public String errmsg;

    public boolean isOk(){
        return errcode == 0;
    }
}
