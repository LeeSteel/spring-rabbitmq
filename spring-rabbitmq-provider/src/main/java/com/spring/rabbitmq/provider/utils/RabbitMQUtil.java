package com.spring.rabbitmq.provider.utils;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @Title:
 * @Package
 * @Description:
 * @author: 李钢 2580704698@qq.com
 * @date: 2021/11/26 0:17
 * @Copyright: Copyright (c) 2019
 */
@Component
public class RabbitMQUtil {

    /**
     * 使用RabbitTemplate,这提供了接收/发送等等方法
     */
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void convertAndSend(String exchange, String routingKey, final Object object) throws AmqpException {
        rabbitTemplate.convertAndSend(exchange, routingKey, object, message -> {
            // persistent 开启消息持久化
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });
    }

}
