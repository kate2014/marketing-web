package com.zhongmei.yunfu.thirdlib.wxapp;

import com.zhongmei.yunfu.api.ApiResponseStatus;
import com.zhongmei.yunfu.api.ApiResponseStatusException;
import com.zhongmei.yunfu.controller.api.model.WxAccessToken;
import com.zhongmei.yunfu.controller.api.model.WxTemplateAddResp;
import com.zhongmei.yunfu.controller.api.model.WxTemplateListResp;
import com.zhongmei.yunfu.controller.api.model.WxTemplateObjectResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WxTemplateMessageIDImpl extends WxTemplateMessageID {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String getTemplateMessageID(String accessToken, String templateCode, String templateTitle) throws Exception {
        String templateId = checkWxTemplateMsgExist(accessToken, templateTitle);
        if (templateId == null) {
            WxAccessToken wxAccessToken = getWxAccessToken(APPID, APPSECRET);
            if (!wxAccessToken.isOk()) {
                throw new ApiResponseStatusException(ApiResponseStatus.FOUND, wxAccessToken.errmsg);
            }

            WxTemplateObjectResp wxTemplateObjectResp = getTemplateLibraryById(wxAccessToken.access_token, templateCode);
            templateId = addTemplate(accessToken, templateCode, wxTemplateObjectResp.getKeywordIds());
        }

        return templateId;
    }

    private WxAccessToken getWxAccessToken(String appID, String appSecret) throws ApiResponseStatusException {
        String accessTokenUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appID, appSecret);
        WxAccessToken wxAccessToken = restTemplate.getForObject(accessTokenUrl, WxAccessToken.class);
        return wxAccessToken;
    }

    private String checkWxTemplateMsgExist(String accessToken, String templateTitle) throws ApiResponseStatusException {
        String wxSendMessageUrl = "https://api.weixin.qq.com/cgi-bin/wxopen/template/list?access_token=" + accessToken;
        Map<String, Integer> params = new HashMap<>();
        params.put("offset", 0);
        params.put("count", 20);
        WxTemplateListResp wxTemplateListResp = restTemplate.postForObject(wxSendMessageUrl, params, WxTemplateListResp.class);
        if (wxTemplateListResp.isOk()) {
            for (WxTemplateListResp.WxTemplateItemResp itemResp : wxTemplateListResp.list) {
                if (itemResp.title.equals(templateTitle)) {
                    return itemResp.template_id;
                }
            }
            return null;
        }

        throw new ApiResponseStatusException(ApiResponseStatus.FOUND, wxTemplateListResp.errmsg);
    }

    private WxTemplateObjectResp getTemplateLibraryById(String accessToken, String templateCode) throws ApiResponseStatusException {
        String wxSendMessageUrl = "https://api.weixin.qq.com/cgi-bin/wxopen/template/library/get?access_token=" + accessToken;
        Map<String, String> params = new HashMap<>();
        params.put("id", templateCode); //"AT0002"
        WxTemplateObjectResp wxTemplateListResp = restTemplate.postForObject(wxSendMessageUrl, params, WxTemplateObjectResp.class);
        if (wxTemplateListResp.isOk()) {
            return wxTemplateListResp;
        }

        throw new ApiResponseStatusException(ApiResponseStatus.FOUND, wxTemplateListResp.errmsg);
    }

    private String addTemplate(String accessToken, String templateCode, List<Integer> keywordIds) throws ApiResponseStatusException {
        String wxSendMessageUrl = "https://api.weixin.qq.com/cgi-bin/wxopen/template/add?access_token=" + accessToken;
        Map<String, Object> params = new HashMap<>();
        params.put("id", templateCode); //"AT0002"
        params.put("keyword_id_list", keywordIds);
        WxTemplateAddResp wxTemplateAddResp = restTemplate.postForObject(wxSendMessageUrl, params, WxTemplateAddResp.class);
        if (wxTemplateAddResp.isOk()) {
            return wxTemplateAddResp.template_id;
        }

        throw new ApiResponseStatusException(ApiResponseStatus.FOUND, wxTemplateAddResp.errmsg);
    }
}
