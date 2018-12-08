package com.zhongmei.yunfu.thirdlib;

import java.io.InputStream;

public interface UploadInterface {

    /**
     * 上传文件
     *
     * @param inputStream
     * @param fileName
     */
    String uploadFile(InputStream inputStream, String fileName) throws Exception;

    /**
     * 删除文件
     *
     * @param keyList
     */
    void removeFile(String[] keyList);

}
