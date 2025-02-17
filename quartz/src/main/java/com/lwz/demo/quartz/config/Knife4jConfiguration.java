package com.lwz.demo.quartz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("接口文档")
                        .description("定时任务接口文档")
                        .contact(new Contact("test", "", ""))
                        .version("1.0.0")
                        .termsOfServiceUrl("")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lwz.demo.quartz.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

}