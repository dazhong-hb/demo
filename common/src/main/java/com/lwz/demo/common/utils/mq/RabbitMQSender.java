package com.lwz.demo.common.utils.mq;

import com.lwz.demo.common.utils.support.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * RabbitMQSender类用于发送消息到RabbitMQ。
 * 该类封装了RabbitMQ的消息发送逻辑，使得其他部分的代码可以更方便地使用RabbitMQ进行消息通信。
 */
@Slf4j
public class RabbitMQSender {

    private static final RabbitTemplate rabbitTemplate;

    /**
     * 静态代码块，在类加载时执行。用于初始化类级变量rabbitTemplate。
     * 通过SpringUtil的getBean方法获取RabbitTemplate的实例，实现了与RabbitMQ的通信准备工作。
     * 这种初始化方式确保了在类的任何静态上下文中都可以使用rabbitTemplate，而不需要显式地进行初始化。
     */
    static {
        rabbitTemplate = SpringUtil.getBean(RabbitTemplate.class);
    }

    /**
     * 发送消息到MQ
     *
     * @param message 要发送的消息
     * @param queue 要发送到的队列名
     */
    public static void sendMessage(Object message, String queue) {
        rabbitTemplate.convertAndSend(queue, message);
        log.info("MQ消息发送完成，当前发送MQ队列名为：{}", queue);
    }
}