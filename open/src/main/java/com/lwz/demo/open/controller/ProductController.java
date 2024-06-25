package com.lwz.demo.open.controller;

import com.lwz.demo.open.client.feign.ProductClient;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "产品接口")
@RestController
@RequestMapping("/product")
public class ProductController implements ProductClient {

}
