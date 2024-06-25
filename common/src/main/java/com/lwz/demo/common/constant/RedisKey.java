package com.lwz.demo.common.constant;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public enum RedisKey {

    TOKEN("token:", 60L, TimeUnit.DAYS),

    ;

    /**
     * 键
     */
    private String keyPrefix;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 过期时间单位
     */
    private TimeUnit timeUnit;

    RedisKey(String keyPrefix, Long expireTime, TimeUnit timeUnit) {
        this.keyPrefix = keyPrefix;
        this.expireTime = expireTime;
        this.timeUnit = timeUnit;
    }

}
