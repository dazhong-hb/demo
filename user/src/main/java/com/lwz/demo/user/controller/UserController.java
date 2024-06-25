package com.lwz.demo.user.controller;

import cn.hutool.jwt.JWTUtil;
import com.lwz.demo.common.constant.RedisKey;
import com.lwz.demo.common.utils.SecurityUtil;
import com.lwz.demo.common.utils.support.StringRedisUtils;
import com.lwz.demo.common.vo.ResponseVO;
import com.lwz.demo.user.client.feign.UserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController implements UserClient {

    @ApiOperation("用户测试")
    @GetMapping(value = "/test")
    public ResponseVO<String> test() {
        log.info("userId: {}, appVersion: {}", SecurityUtil.getUserId(), SecurityUtil.getAppVersion());
        return ResponseVO.success("Tanza");
    }

    @ApiOperation("用户登录")
    @PostMapping(value = "/login")
    public ResponseVO<String> login() {
        Map<String, Object> userInfoMap = new HashMap<>(2);
        int userId = 1;
        userInfoMap.put("userId", userId);
        userInfoMap.put("timestamp", System.currentTimeMillis());
        String token = JWTUtil.createToken(userInfoMap, SecurityUtil.TOKEN_KEY.getBytes());
        String key = RedisKey.TOKEN.getKeyPrefix() + userId;
        StringRedisUtils.save(key, token, RedisKey.TOKEN.getExpireTime(), RedisKey.TOKEN.getTimeUnit());
        return ResponseVO.success(token);
    }

}
