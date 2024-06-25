package com.lwz.demo.open.receiver;

import com.lwz.demo.common.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * RabbitMQReceiver类用于接收RabbitMQ的消息。
 */
@Slf4j
@Component
public class RabbitMQReceiver {

    /**
     * 测试队列的消费者方法。
     * 该方法监听RabbitMQ的测试队列，并接收到消息后打印日志。
     *
     * @param message 接收到的消息
     */
    @RabbitListener(queues = RabbitMQConfig.TEST_QUEUE)
    public void receiveMessage(Message message) {
        String s = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("队列名：{}，接收到消息：{}", RabbitMQConfig.TEST_QUEUE, s);
    }

}
