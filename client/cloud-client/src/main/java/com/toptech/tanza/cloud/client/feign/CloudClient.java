package com.lwz.demo.cloud.client.feign;

import com.lwz.demo.common.vo.ResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId = "cloudClient", name = "cloud", url = "${cloud.http}")
public interface CloudClient {

    @GetMapping("/abc/clientTest")
    ResponseVO<String> clientTest();

}