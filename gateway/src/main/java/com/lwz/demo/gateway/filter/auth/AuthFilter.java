package com.lwz.demo.gateway.filter.auth;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.hutool.jwt.JWTUtil;
import com.lwz.demo.common.constant.RedisKey;
import com.lwz.demo.common.constant.ResponseCode;
import com.lwz.demo.common.utils.SecurityUtil;
import com.lwz.demo.common.vo.ResponseVO;
import com.lwz.demo.gateway.config.FilterOrder;
import com.lwz.demo.gateway.provider.AuthProvider;
import com.lwz.demo.gateway.provider.ResponseProvider;
import com.lwz.demo.common.utils.support.StringRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        // 在白名单直接转发
        if (AuthProvider.isSkip(path)) {
            return chain.filter(exchange);
        }
        // 在黑名单直接拒绝
        if (AuthProvider.isRefuse(path)) {
            return ResponseProvider.response(exchange, ResponseVO.failed(ResponseCode.NO_PERMISSION.getCode(), ResponseCode.NO_PERMISSION.getMsg()));
        }

        // 获取token
        String accessKey = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(accessKey)) {
            return ResponseProvider.response(exchange, ResponseVO.failed(ResponseCode.UNAUTHENTICATED.getCode(), "Token couldn't be empty"));
        }
        String[] accessKeyArray = accessKey.split(" ");
        if (accessKeyArray.length != 2 || !"Bearer".equals(accessKeyArray[0])) {
            return ResponseProvider.response(exchange, ResponseVO.failed(ResponseCode.UNAUTHENTICATED.getCode(), "Token format is incorrect"));
        }
        String token = accessKeyArray[1];

        // 校验token的有效性
        JWT jwt;
        try {
            boolean verify = JWTUtil.verify(token, SecurityUtil.TOKEN_KEY.getBytes());
            if (verify) {
                jwt = JWTUtil.parseToken(token);
            } else {
                return ResponseProvider.response(exchange, ResponseVO.failed(ResponseCode.UNAUTHENTICATED.getCode(), "Token verification incorrect"));
            }
        } catch (JWTException e) {
            log.error("jwt解析出现异常", e);
            return ResponseProvider.response(exchange, ResponseVO.failed(ResponseCode.UNAUTHENTICATED.getCode(), "Token is wrong"));
        }
        Integer userId = (Integer) jwt.getPayload().getClaim("userId");
        String key = RedisKey.TOKEN.getKeyPrefix() + userId;
        Boolean hasKey = StringRedisUtils.hasKey(key);
        if (hasKey) {
            String value = StringRedisUtils.get(key);
            if (!token.equals(value)) {
                return ResponseProvider.response(exchange, ResponseVO.failed(ResponseCode.UNAUTHENTICATED.getCode(), "Token is not valid"));
            }
            StringRedisUtils.expire(key, RedisKey.TOKEN.getExpireTime(), RedisKey.TOKEN.getTimeUnit());
        } else {
            return ResponseProvider.response(exchange, ResponseVO.failed(ResponseCode.UNAUTHENTICATED.getCode(), "Token has expired"));
        }

        // 传递请求头
        ServerHttpRequest newRequest = exchange.getRequest().mutate().header(SecurityUtil.USER_ID, String.valueOf(userId)).build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        return FilterOrder.authFilterOrder;
    }

}
