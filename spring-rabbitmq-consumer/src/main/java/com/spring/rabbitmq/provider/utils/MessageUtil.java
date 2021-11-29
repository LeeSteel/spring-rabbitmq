package com.spring.rabbitmq.provider.utils;

import org.springframework.amqp.core.Message;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Title:
 * @Package
 * @Description:
 * @author: 李钢 2580704698@qq.com
 * @date: 2021/8/31 16:49
 * @Copyright: Copyright (c) 2019
 */
public class MessageUtil {

    /**
     *   {key=value,key=value,key=value} 格式转换成map
     * @param bodyStr  消息 内容
     * @return 键值对
     */
    public static Map<String, String> mapStringToMap(String bodyStr) {
        bodyStr = bodyStr.substring(1, bodyStr.length() - 1);
        String[] strs = bodyStr.split(",");
        Map<String, String> map;
        map = new HashMap<>(16);
        for (String string : strs) {
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取消息 内容
     * @param message 消息队列消息
     * @return 消息 内容
     */
    public static String getMsgBody(Message message) {
        try {
            Class<?> aClass = Class.forName("org.springframework.amqp.core.Message");
            Method method = aClass.getDeclaredMethod("getBodyContentAsString");
            //禁止访问检查
            method.setAccessible(true);
            Object invoke = method.invoke(message);
            if (invoke != null) {
                return invoke + "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
