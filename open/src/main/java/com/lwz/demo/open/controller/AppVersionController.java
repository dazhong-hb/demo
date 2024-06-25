package com.lwz.demo.open.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "APP版本接口")
@RestController
@RequestMapping("/app/version")
public class AppVersionController {

}
