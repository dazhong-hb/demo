package com.lwz.demo.quartz.controller;

import com.lwz.demo.common.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RefreshScope
@Api(tags = "测试接口")
@RestController
@RequestMapping("/abc")
public class AbcController {

    @ApiOperation("打招呼")
    @GetMapping(value = "/hello")
    public ResponseVO<String> hello(@ApiParam(name = "param", value = "打招呼参数", required = true) @RequestParam String param) {
        return ResponseVO.success("Hello " + param + "!");
    }

}
