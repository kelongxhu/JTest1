package com.common.exception;

import org.slf4j.helpers.MessageFormatter;

/**
 */
public class BusinessException extends Exception {

    private String errorCode;

    private String errorMsg;

    /**
     * @param errorCode 错误码
     * @param errorMsg  错误消息
     */
    public BusinessException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * @param errorCode 错误码
     * @param errorMsg  错误消息（模板）
     * @param extend    填充错误消息模块的内容
     */
    public BusinessException(String errorCode, String errorMsg, String... extend) {
        this.errorCode = errorCode;
        this.errorMsg = MessageFormatter.arrayFormat(errorMsg, extend).getMessage();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
