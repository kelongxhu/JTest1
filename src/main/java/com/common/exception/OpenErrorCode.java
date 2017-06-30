package com.common.exception;

/**
 */
public enum OpenErrorCode {

    /**
     * 系统异常
     */
    SYSTEM_ERROR("23451000", "服务器内部错误，请稍后再试"),

    /**
     * 请求参数不合法
     */
    PARAM_INVALID("23451100", "请求参数不合法,{}");

    private String errorCode;

    private String errorMsg;

    OpenErrorCode(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }


    public String getErrorMsg() {
        return errorMsg;
    }

}
