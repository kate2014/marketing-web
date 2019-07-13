package com.zhongmei.yunfu.api;

public class ApiRespStatusException extends RuntimeException {

    private ApiRespStatus status;

    public ApiRespStatusException(ApiRespStatus status) {
        this(status, status.getReason());
    }

    public ApiRespStatusException(ApiRespStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ApiRespStatusException(ApiRespStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    public ApiRespStatusException(String message) {
        super(message);
    }

    public ApiRespStatusException(Throwable cause) {
        this(ApiRespStatus.UNKNOWN, cause);
    }

    public ApiRespStatus getStatus() {
        return status;
    }
}
