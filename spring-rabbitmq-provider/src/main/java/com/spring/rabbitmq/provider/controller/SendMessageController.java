package com.spring.rabbitmq.provider.controller;

import com.spring.rabbitmq.provider.utils.RabbitMQUtil;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @version V1.0
 * @Title:
 * @Package
 * @Description:
 * @author: 李钢 2580704698@qq.com
 * @date: 2021/11/26 0:17
 * @Copyright: Copyright (c) 2019
 */
@RestController
public class SendMessageController {
    /**
     * 使用RabbitTemplate,这提供了接收/发送等等方法
     */
    @Autowired
    RabbitMQUtil rabbitMQUtil;

    @Autowired
    DirectExchange directExchange;

    @Autowired
    TopicExchange topicExchange;
    @Value("${rabbitmq.test-direct.routing-key}")
    private String routingKeyName;

    @Value("${rabbitmq.test-topic.routing-key.all}")
    private String routingKeyAllName;
    @Value("${rabbitmq.test-topic.routing-key.first}")
    private String routingKeyFirstName;
    @Value("${rabbitmq.test-topic.routing-key.second}")
    private String routingKeySecondName;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message  hello!";
        String createTime = getCreateDateStr();
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitMQUtil.convertAndSend(directExchange.getName(), routingKeyName, map);
        return "ok";
    }

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: M A N ";
        String createTime = getCreateDateStr();
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        rabbitMQUtil.convertAndSend(topicExchange.getName(), routingKeyFirstName, manMap);
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: woman is all ";
        String createTime = getCreateDateStr();
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);
        rabbitMQUtil.convertAndSend(topicExchange.getName(), routingKeySecondName, womanMap);
        return "ok";
    }

    /**
     * 测试不存在 队列交换机 的情况
     *
     * @return
     */
    @GetMapping("/TestMessageAck")
    public String testMessageAck() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message ";
        String createTime = getCreateDateStr();
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitMQUtil.convertAndSend("non-existent-exchange", routingKeyName, map);
        return "ok";
    }

    @GetMapping("/TestMessageAck2")
    public String testMessageAck2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";
        String createTime = getCreateDateStr();
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitMQUtil.convertAndSend("lonelyDirectExchange", routingKeyName, map);
        return "ok";
    }

    /**
     * @return
     */
    private String getCreateDateStr() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }
}
