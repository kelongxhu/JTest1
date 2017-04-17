package com.core.exception;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1397410891768947160L;

    private int code;

    private String message;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }


    public ServiceException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public ServiceException(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
