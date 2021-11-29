package com.spring.rabbitmq.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @version V1.0
 * @Title:
 * @Package
 * @Description:
 * @author: 李钢 2580704698@qq.com
 * @date: 2021/11/25 23:08
 * @Copyright: Copyright (c) 2019
 */
@SpringBootApplication
@EnableTransactionManagement
public class SpringRabbitmqConsumerApplication {



	public static void main(String[] args) {
		SpringApplication.run(SpringRabbitmqConsumerApplication.class, args);
	}



}
