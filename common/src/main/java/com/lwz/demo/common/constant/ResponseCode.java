package com.lwz.demo.common.constant;

import lombok.Getter;

@Getter
public enum ResponseCode {

    /* 正常情况 */
    SUCCESS(2000, "Successful"),                                                                // 请求成功
    PENDING(2001, "Pending"),                                                                   // 请求处理中
    FAIL(4000, "Fail"),                                                                         // 请求处理失败


    /* 通用错误（号段：4001~4100） */
    UNAUTHENTICATED(4001, "Unauthenticated"),                                                   // 身份未认证
    NO_PERMISSION(4002, "No permission"),                                                       // 无权限访问
    INVALID_USER(4003, "Invalid user"),                                                         // 无效的用户
    PARAMETER_MISSING(4004, "Parameter missing"),                                               // 参数缺失
    INVALID_PARAMETER(4005, "Invalid parameter"),                                               // 错误的参数
    NETWORK_OVERTIME(4006, "Network overtime"),                                                 // 网络超时
    THE_NUMBER(4007, "Requests limited"),                                                       // 请求次数受限
    ILLEGAL_REQUEST(4008, "Illegal request"),                                                   // 非法请求
    SYSTEM_MAINTENANCE(4009, "System maintenance"),                                             // 系统维护
    VERSION(4010, "Your version is too low, please update to the latest version to use"),       // 版本更新

    /* Base服务错误（号段：4101~4150） */

    /* User服务错误（号段：4151~4200） */

    /* Transaction服务错误（号段：4201~4250） */

    /* Third服务错误（号段：4251~4300） */

    /* Risk服务错误（号段：4301~4350） */

    /* Quartz服务错误（号段：4351~4400） */

    /* ？服务错误（号段：4401~4450） */

    /* 异常情况 */
    ERROR(5000, "Server has an unknown error"),                                                   // 服务器出现未知错误
    UPGRADING(5001, "Server is upgrading, the feature is not currently available"),               // 服务升级中，功能暂不可用
    UNAVAILABLE(5002, "Server is unavailable");                                                   // 服务不可用


    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态描述
     */
    private String msg;

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
