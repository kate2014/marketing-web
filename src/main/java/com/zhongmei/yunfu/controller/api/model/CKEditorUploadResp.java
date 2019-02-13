package com.zhongmei.yunfu.controller.api.model;

public class CKEditorUploadResp {

    /**
     * uploaded : 1
     * fileName : foo(2).jpg
     * url : /files/foo(2).jpg
     * error : {"message":"A file with the same nfoo(2).jpg"}
     */

    private Integer uploaded = 1;
    private String fileName;
    private String url;
    private ErrorBean error;

    public Integer getUploaded() {
        return uploaded;
    }

    public void setUploaded(Integer uploaded) {
        this.uploaded = uploaded;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(String message) {
        setUploaded(0);
        this.error = new ErrorBean(message);
    }

    public static class ErrorBean {
        /**
         * message : A file with the same nfoo(2).jpg
         */

        private String message;

        public ErrorBean(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
