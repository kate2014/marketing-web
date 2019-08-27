package com.zhongmei.yunfu.controller.api.model;

import java.math.BigDecimal;
import java.nio.Buffer;

public class WxQRCodeResp {

    public int errcode;
    public String errmsg;
    public String contentType;
    public Buffer buffer;
    public String posterImage;

    public BigDecimal minRedPackets;

    public BigDecimal maxRedPackets;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public BigDecimal getMinRedPackets() {
        return minRedPackets;
    }

    public void setMinRedPackets(BigDecimal minRedPackets) {
        this.minRedPackets = minRedPackets;
    }

    public BigDecimal getMaxRedPackets() {
        return maxRedPackets;
    }

    public void setMaxRedPackets(BigDecimal maxRedPackets) {
        this.maxRedPackets = maxRedPackets;
    }
}
