package com.spring.rabbitmq.provider.config;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
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
public class DirectRabbitConfig {
    @Value("${rabbitmq.test-direct.queue}")
    private String testDirectQueueName;
    @Value("${rabbitmq.test-direct.exchange}")
    private String testDirectExchangeName;
    @Value("${rabbitmq.test-direct.routing-key}")
    private String routingKeyName;

    /**
     * 测试 队列
     *
     * @return
     */
    @Bean
    public Queue TestDirectQueue() {
        /**
         * durable – 如果我们声明一个持久队列（该队列将在服务器重启后继续存在),则为 true
         * exclusive – 如果我们声明一个独占队列 (该队列只会被声明者的连接使用),则为 true
         * autoDelete – 如果我们声明一个临时队列 (服务器应在不再使用时删除队列),则为 true
         */

        return new Queue(testDirectQueueName, true, false, false);
    }

    /**
     * 测试 Direct交换机
     *
     * @return
     */
    @Bean
    DirectExchange TestDirectExchange() {

        /**
         * durable – 如果我们声明一个持久交换机（该交换机将在服务器重启后继续存在),则为 true
         * autoDelete – 如果我们声明一个临时交换机 (服务器应在不再使用时删除交换机),则为 true
         */
        return
                new DirectExchange(testDirectExchangeName, true, false);
    }

    /**
     * 绑定  将队列和交换机绑定, 并设置用于匹配键：
     *
     * @return
     */
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with(routingKeyName);
    }
}
