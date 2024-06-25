package com.lwz.demo.quartz.aop;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.json.JSONUtil;
import com.lwz.demo.common.log.BaseAspect;
import com.lwz.demo.common.log.LogSubject;
import com.lwz.demo.common.utils.SecurityUtil;
import com.lwz.demo.common.utils.http.HttpServletUtil;
import com.lwz.demo.common.utils.http.RequestUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class RequestLogAspect extends BaseAspect {

    /**
     * 定义切面Pointcut
     */
    @Pointcut("execution(public * com.lwz.demo.*.controller.*.*(..))")
    public void log() {

    }

    /**
     * 环绕通知
     */
    @Around("log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录时间定时器
        TimeInterval timer = DateUtil.timer(true);
        // 执行结果
        Object result = null;
        try {
            result = joinPoint.proceed();
        } finally {
            LogSubject logSubject = new LogSubject();
            // 执行结果
            logSubject.setResult(result);
            // 执行消耗时间
            String endTime = timer.intervalPretty();
            logSubject.setSpendTime(endTime);
            // 执行参数
            Method method = resolveMethod(joinPoint);
            logSubject.setParameter(getParameter(method, joinPoint.getArgs()));
            HttpServletRequest request = HttpServletUtil.getRequest();
            // 接口请求时间
            logSubject.setStartTime(DateUtil.now());
            // 请求链接
            logSubject.setUrl(request.getRequestURL().toString());
            // 请求方法GET,POST等
            logSubject.setMethod(request.getMethod());
            // 请求设备信息
            logSubject.setDeviceId(SecurityUtil.getDeviceId());
            // 请求IP
            logSubject.setIp(RequestUtil.getRemoteIp(request));
            // 接口描述
            if (method.isAnnotationPresent(ApiOperation.class)) {
                ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                logSubject.setDescription(apiOperation.value());
            }
            // 用户id
            logSubject.setUserId(SecurityUtil.getUserId());
            // app版本
            logSubject.setAppVersion(SecurityUtil.getAppVersion());
            String logStr = JSONUtil.toJsonPrettyStr(logSubject);

            log.info(logStr);
        }

        return result;
    }

}