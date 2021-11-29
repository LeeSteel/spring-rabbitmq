package com.spring.rabbitmq.provider.listener;

import com.rabbitmq.client.Channel;
import com.spring.rabbitmq.provider.utils.MessageUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

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
@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class DirectReceiver implements ChannelAwareMessageListener {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
            // String msg = message.toString();
            // String[] msgArray = msg.split("'");//可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
            // Map<String, String> msgMap = mapStringToMap(msgArray[1]);
            String msgBodyStr =  MessageUtil.getMsgBody(message);
            Map<String, String> msgMap = MessageUtil.mapStringToMap(msgBodyStr);
            String messageId = msgMap.get("messageId");
            String messageData = msgMap.get("messageData");
            String createTime = msgMap.get("createTime");
            System.out.println("messageId:" + messageId + "  messageData:" + messageData + "  createTime:" + createTime);
            channel.basicAck(deliveryTag, true);

        } catch (Exception e) {
            //消费失败,消息丢回消息队列
            //为true会重新放回队列
            channel.basicReject(deliveryTag, false);
            e.printStackTrace();
        }
    }

}