package com.lwz.demo.third.exception;

import cn.hutool.core.io.IoUtil;
import com.lwz.demo.common.constant.ResponseCode;
import com.lwz.demo.common.exception.BusinessException;
import com.lwz.demo.common.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理对象参数校验失败抛出的异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVO<?> exceptionHandler(MethodArgumentNotValidException e) {
        log.error("发生数据校验异常！原因是：{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        Map<String,String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError)-> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseVO.failed(ResponseCode.INVALID_PARAMETER.getCode(), "Problem with data validation", errorMap);
    }

    /**
     * 处理单个参数校验失败抛出的异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVO<?> exceptionHandler(ConstraintViolationException e) {
        log.info("发生数据校验异常！原因是：{}", e.getMessage());
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String defaultMessage = violations.stream().findFirst().get().getMessage();
        return ResponseVO.failed(ResponseCode.INVALID_PARAMETER.getCode(), defaultMessage);
    }

    /**
     * 处理参数绑定异常
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVO<?> exceptionHandler(HttpMessageNotReadableException e) {
        String body = null;
        try {
            InputStream inputStream = e.getHttpInputMessage().getBody();
            body = IoUtil.read(inputStream, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            log.warn("处理参数绑定异常，获取IO流失败");
        }
        log.error("发生参数绑定异常！原因是:{}，请求体：{}", e.getMessage(), body, e);
        return ResponseVO.failed(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMsg());
    }

    /**
     * 处理自定义的业务异常
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseVO<?> businessExceptionHandler(BusinessException e) {
        log.error("发生业务异常！原因是：{}", e.getMessage(), e);
        return ResponseVO.failed(ResponseCode.ERROR.getCode(), e.getMessage());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseVO<?> exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("发生未知异常！", e);
        return ResponseVO.failed(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMsg());
    }

}