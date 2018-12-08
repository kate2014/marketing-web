package com.zhongmei.yunfu.thirdlib;

import com.zhongmei.yunfu.thirdlib.qiniu.QiniuUploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class UploadFile implements UploadInterface {

    protected Logger log = LoggerFactory.getLogger(UploadFile.class);

    public static UploadFile create() {
        return new QiniuUploadFile();
    }

}
