package com.spring.rabbitmq.provider.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
public class RabbitConfig {
    Logger logger  = LoggerFactory.getLogger(this.getClass());

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);


        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            logger.info("ConfirmCallback:     "+"相关数据："+correlationData);
            logger.info("ConfirmCallback:     "+"确认情况："+ack);
            logger.info("ConfirmCallback:     "+"原因："+cause);
        });

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            logger.info("ReturnCallback:     "+"消息："+message);
            logger.info("ReturnCallback:     "+"回应码："+replyCode);
            logger.info("ReturnCallback:     "+"回应信息："+replyText);
            logger.info("ReturnCallback:     "+"交换机："+exchange);
            logger.info("ReturnCallback:     "+"路由键："+routingKey);
        });

        return rabbitTemplate;
    }

}