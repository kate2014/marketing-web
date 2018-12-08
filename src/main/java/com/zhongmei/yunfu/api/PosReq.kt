package com.zhongmei.yunfu.api

abstract class PosReq : IPosHeader {

    private var header: PosHeader? = null

    override fun getHeader(): PosHeader? {
        return header
    }

    override fun setHeader(header: PosHeader) {
        this.header = header
    }
}
