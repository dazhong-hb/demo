package com.lwz.demo.gateway.provider;

import com.lwz.demo.common.vo.ResponseVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 请求响应返回
 */
public class ResponseProvider {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 构建返回的JSON数据格式
     */
    public static Mono<Void> response(ServerWebExchange exchange, ResponseVO responseVo) {
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String result = "";

        try {
            result = objectMapper.writeValueAsString(responseVo);
        } catch (JsonProcessingException ignored) {
            System.out.println(ignored);
        }

        byte[] bytes = result.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

}
