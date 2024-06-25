package com.lwz.demo.open.controller;

import com.lwz.demo.cloud.client.feign.CloudClient;
import com.lwz.demo.common.config.RabbitMQConfig;
import com.lwz.demo.common.dto.TestDTO;
import com.lwz.demo.common.entity.Test;
import com.lwz.demo.common.mapper.TestMapper;
import com.lwz.demo.common.mapstruct.TestMapStruct;
import com.lwz.demo.common.utils.SecurityUtil;
import com.lwz.demo.common.utils.mq.RabbitMQSender;
import com.lwz.demo.common.vo.ResponseVO;
import com.lwz.demo.common.vo.TestVO;
import com.lwz.demo.user.client.feign.UserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RefreshScope
@Api(tags = "测试接口")
@RestController
@RequestMapping("/abc")
public class AbcController {

    @Value("${abc.name:unknown}")
    private String name;

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private TestMapStruct testMapStruct;

    @Autowired
    private UserClient userClient;

    @Autowired
    private CloudClient cloudClient;

    @ApiOperation("打招呼")
    @GetMapping(value = "/hello")
    public ResponseVO<String> hello(@ApiParam(name = "param", value = "打招呼参数", required = true) @RequestParam String param) {
        return ResponseVO.success("Hello " + param + "!");
    }

    @ApiOperation("配置中心测试")
    @GetMapping(value = "/nacos")
    public ResponseVO<String> nacos() {
        return ResponseVO.success("Name: " + name);
    }

    @ApiOperation("数据源连通测试")
    @GetMapping(value = "/datasource")
    public ResponseVO<TestVO> datasource() {
        Test test = testMapper.selectById(1);
        TestVO testVO = testMapStruct.toVO(test);
        return ResponseVO.success("获取测试内容", testVO);
    }

    @ApiOperation("服务间调用测试")
    @GetMapping(value = "/feign")
    public ResponseVO<String> feign() {
        log.info("userId: {}, appVersion: {}", SecurityUtil.getUserId(), SecurityUtil.getAppVersion());
        ResponseVO<String> responseVO = userClient.test();
        return ResponseVO.success("Hello, I'm Open. Feign call getProduct: " + responseVO.getData());
    }

    @ApiOperation("公共服务调用测试")
    @GetMapping(value = "/clientTest")
    public ResponseVO<String> clientTest() {
        ResponseVO<String> responseVO = cloudClient.clientTest();
        return ResponseVO.success("CloudClient call clientTest: " + responseVO.getData());
    }

    @ApiOperation("校验参数测试")
    @PostMapping(value = "/testParameterValidation")
    public ResponseVO<String> testParameterValidation(@Validated @RequestBody TestDTO testDTO) {
        return ResponseVO.success("当前参数: " + testDTO);
    }

    @ApiOperation("MQ发送测试")
    @PostMapping("/rabbitMQSenderTest")
    public ResponseVO<String> rabbitMQSenderTest(@RequestParam("message") String message) {
        log.info("发送消息内容: {}", message);
        RabbitMQSender.sendMessage(message, RabbitMQConfig.TEST_QUEUE);
        return ResponseVO.success("MQ发送成功，消息内容：" + message);
    }

}
