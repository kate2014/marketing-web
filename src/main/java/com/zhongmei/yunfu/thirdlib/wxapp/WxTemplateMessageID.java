package com.zhongmei.yunfu.thirdlib.wxapp;

public abstract class WxTemplateMessageID {

    static final String APPID = "wx22d9607fa73e9364";
    static final String APPSECRET = "df788ed71c5ec9afb1cf46b4209b0879";

    /**
     * 获取模板ID
     *
     * @param accessToken
     * @param templateCode
     * @param templateTitle
     * @return
     * @throws Exception
     */
    public abstract String getTemplateMessageID(String accessToken, String templateCode, String templateTitle) throws Exception;
}
