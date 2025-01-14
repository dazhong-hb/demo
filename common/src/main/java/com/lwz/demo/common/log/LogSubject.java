package com.lwz.demo.common.log;

import lombok.Data;

@Data
public class LogSubject {

    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作用户
     */
    private Integer userId;

    /**
     * 操作时间
     */
    private String startTime;

    /**
     * 消耗时间
     */
    private String spendTime;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private Object parameter;

    /**
     * 请求返回的结果
     */
    private Object result;

    /**
     * app版本
     */
    private String appVersion;

    /**
     * 请求设备信息
     */
    private String deviceId;

}