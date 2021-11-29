package com.spring.rabbitmq.provider.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version V1.0
 * @Title:
 * @Package
 * @Description:
 * @author: 李钢 2580704698@qq.com
 * @date: 2021/11/25 23:08
 * @Copyright: Copyright (c) 2019
 */
@Configuration
public class TopicRabbitConfig {
    @Value("${rabbitmq.test-topic.queue.first}")
    private String topicFirstQueueName;
    @Value("${rabbitmq.test-topic.queue.second}")
    private String topicSecondQueueName;
    @Value("${rabbitmq.test-topic.exchange}")
    private String topicExchangeName;

    @Value("${rabbitmq.test-topic.routing-key.all}")
    private String routingKeyAllName;
    @Value("${rabbitmq.test-topic.routing-key.first}")
    private String routingKeyFirstName;
    @Value("${rabbitmq.test-topic.routing-key.second}")
    private String routingKeySecondName;

    @Bean
    public Queue firstQueue() {
        return new Queue(topicFirstQueueName);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(topicSecondQueueName);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }


    /**
     * 将 firstQueue和 topicExchange绑定,而且绑定的键值为  topic.first
     * 这样只要是消息携带的路由键是topic.first,才会分发到该队列
     *
     * @return
     */
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(routingKeyFirstName);
    }

    /**
     * 将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
     * 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
     *
     * @return
     */
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with(routingKeyAllName);
    }

}
