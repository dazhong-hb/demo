package com.lwz.demo.cloud.client.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration("cloudFeign")
public class FeignRequestInterceptor implements RequestInterceptor {

    @Value("${cloud.productCode}")
    private String productCode;

    @Value("${cloud.authorization}")
    private String authorization;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("ProductCode", productCode).header("Authorization", authorization);
    }

}