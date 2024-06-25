package com.lwz.demo.open.client.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(contextId = "productClient", name = "open", path = "product")
public interface ProductClient {

}