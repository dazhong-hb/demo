package com.lwz.demo.common.vo;

import com.lwz.demo.common.constant.ResponseCode;
import lombok.Data;

@Data
public class ResponseVO<T> {

    private Integer code;

    private String msg;

    private T data;

    public ResponseVO() {
    }

    public ResponseVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseVO<T> success() {
        return new ResponseVO<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), null);
    }

    public static <T> ResponseVO<T> success(T data) {
        return new ResponseVO<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), data);
    }

    public static <T> ResponseVO<T> success(String message, T data) {
        return new ResponseVO<>(ResponseCode.SUCCESS.getCode(), message, data);
    }

    public static <T> ResponseVO<T> success(Integer code, String message, T data) {
        return new ResponseVO<>(code, message, data);
    }

    public static <T> ResponseVO<T> failed(String message) {
        return new ResponseVO<>(ResponseCode.FAIL.getCode(), message, null);
    }

    public static <T> ResponseVO<T> failed(Integer code, String message) {
        return new ResponseVO<>(code, message, null);
    }

    public static <T> ResponseVO<T> failed(Integer code, String message, T data) {
        return new ResponseVO<>(code, message, data);
    }

}
