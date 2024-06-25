package com.lwz.demo.user.client.feign;

import com.lwz.demo.common.vo.ResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId = "userClient", name = "user", path = "user")
public interface UserClient {

    @GetMapping("/test")
    ResponseVO<String> test();

}