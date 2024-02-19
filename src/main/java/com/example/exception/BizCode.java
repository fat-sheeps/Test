package com.example.exception;

public enum BizCode implements ErrorCode {

    // 通用
    DEFAULT("00000", "DEFAULT:"),
    UNKNOWN_EXCEPTION("10000", "系统未知异常"),
    VALID_EXCEPTION("10001", "参数格式校验失败"),
    TOO_MANY_REQUESTS("10002", "请求流量过大"),
    SYSTEM_BUSY("10003", "系统繁忙，请稍后重试"),
    ;
    private String errorCode;

    private String errorMessage;

    BizCode(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
