package com.lwz.demo.gateway.filter.sys;

import com.lwz.demo.common.utils.SecurityUtil;
import com.lwz.demo.gateway.config.FilterOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SysFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();

        // todo 查询系统维护状态

        // 获取app版本号
        String appVersion = request.getHeaders().getFirst("App-Version");
        String deviceId = request.getHeaders().getFirst("Device-ID");

        // 传递请求头
        ServerHttpRequest newRequest = exchange.getRequest().mutate().header(SecurityUtil.APP_VERSION, appVersion)
                .header(SecurityUtil.DEVICE_ID, deviceId).build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        return FilterOrder.sysFilterOrder;
    }

}
