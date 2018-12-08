package com.zhongmei.yunfu.api;

public class ApiResponseStatusException extends Exception {

    private ApiResponseStatus status;

    public ApiResponseStatusException(ApiResponseStatus status) {
        this(status, status.getReason());
    }

    public ApiResponseStatusException(ApiResponseStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ApiResponseStatusException(ApiResponseStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    public ApiResponseStatusException(Throwable cause) {
        this(ApiResponseStatus.UNKNOWN, cause);
    }

    public ApiResponseStatus getStatus() {
        return status;
    }
}
