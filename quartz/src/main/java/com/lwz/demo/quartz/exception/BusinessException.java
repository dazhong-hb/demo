package com.lwz.demo.quartz.exception;

public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public static BusinessException newInstance(String message) {
        return new BusinessException(message);
    }

    public static BusinessException newInstance(Integer code, String message) {
        return new BusinessException(code, message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
