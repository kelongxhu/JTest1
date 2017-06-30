package com.common.bean;

/**
 */
public class Result {

    /**
     * 信息内容
     */
    private Object content;
    /**
     * 状态
     */
    private String status;
    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误描述
     */
    private String errorMsg;

    /**
     * 状态
     */
    public enum Status {
        SUCCESS("OK"),
        ERROR("ERROR");

        private String desc;

        private Status(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * success
     */
    public Result() {
        this.status = Status.SUCCESS.getDesc();
    }

    /**
     * success
     *
     * @param content 响应内容
     */
    public Result(Object content) {
        this.status = Status.SUCCESS.getDesc();
        this.content = content;
    }

    /**
     * success
     *
     * @param errorCode 错误码
     * @param message   错误描述
     */
    public Result(String errorCode, String message) {
        this(errorCode, message, Status.SUCCESS);
    }

    /**
     * custom
     *
     * @param errorCode 错误码
     * @param errorMsg  错误描述
     * @param status    状态
     */
    public Result(String errorCode, String errorMsg, Status status) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.status = status.getDesc();
    }

    /**
     * @param errorCode
     * @param errorMsg
     * @param status
     * @param content
     */
    public Result(String errorCode, String errorMsg, Status status, Object content) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.status = status.getDesc();
        this.content = content;
    }


    public Object getContent() {
        return content;
    }

    public Result setContent(Object content) {
        this.content = content;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Result setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }


    public String getErrorMsg() {
        return errorMsg;
    }

}
