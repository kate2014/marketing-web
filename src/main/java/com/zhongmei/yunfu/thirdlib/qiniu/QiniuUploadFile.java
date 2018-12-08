package com.zhongmei.yunfu.thirdlib.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import com.zhongmei.yunfu.thirdlib.UploadFile;

import java.io.InputStream;
import java.util.Arrays;

public class QiniuUploadFile extends UploadFile {

    private static final String BASER_URL = "http://media.zhongmeiyunfu.com/";
    private static final String accessKey = "EJGzrqk8jLZAnVGC14mqzoiUnmUGhX0JoFn6wBoF";
    private static final String secretKey = "CcS_XBCLG-oZreB0NrK4-A8L-gaiHNPqNxzDZwdq";
    private static final String bucket = "test";

    private static Configuration getConfiguration() {
        return new Configuration(Zone.zone2());
    }

    @Override
    public String uploadFile(InputStream inputStream, String fileName) throws Exception {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = getConfiguration();
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;

        log.info("上传文件: {}", fileName);
        //byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
        //ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(inputStream, key, upToken, null, null);
            //解析上传成功的结果
            //DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("上传成功: {}", response.bodyString());
        } catch (QiniuException ex) {
            Response response = ex.response;
            log.error("上传失败: {}", response.bodyString());
            throw ex;
        }

        return BASER_URL + fileName;
    }

    /**
     * 单次批量请求的文件数量不得超过1000
     *
     * @param keyList
     */
    @Override
    public void removeFile(String[] keyList) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = getConfiguration();
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            log.info("批量删除文件: ", Arrays.toString(keyList));
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucket, keyList);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < keyList.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = keyList[i];
                if (status.code == 200) {
                    log.info("rm success: {}" + key);
                } else {
                    log.error("rm error: {}", key, status.data.error);
                }
            }
        } catch (QiniuException ex) {
            log.error("batch rm error: ", ex.response.toString());
        }
    }

}
