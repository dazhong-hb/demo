package com.lwz.demo.common.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RabbitMQConfig
 * @Descripyion TOOD
 * @Date 2024/06/21  11:57:57
 * @Created by Administrator
 * @Author liuwanzhong
 * @Email liuwanzhong@finlwz.com
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 测试队列
     */
    public static final String TEST_QUEUE = "test.queue";

    @Bean
    public Queue testQueue() {
        return new Queue(TEST_QUEUE, true);
    }

}
