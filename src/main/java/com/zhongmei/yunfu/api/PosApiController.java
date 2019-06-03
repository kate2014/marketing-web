package com.zhongmei.yunfu.api;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public abstract class PosApiController {

    @Autowired
    HttpServletRequest request;

    protected void checkHeader(IPosHeader header) throws ApiRespStatusException {
        PosHeader posHeader = new PosHeader();
        posHeader.setMsgId(request.getHeader("yf-api-msgid"));
        posHeader.setDeviceId(request.getHeader("yf-api-device-id"));
        posHeader.setBrandId(Long.parseLong(request.getHeader("yf-api-brand-id")));
        posHeader.setShopId(Long.parseLong(request.getHeader("yf-api-shop-id")));
        header.setHeader(posHeader);

        if (posHeader.getBrandId() == null || posHeader.getShopId() == null) {
            throw new ApiRespStatusException(ApiRespStatus.FOUND, "yf-api-brand-id or yf-api-shop-id is null");
        }
    }
}
