package com.spring.rabbitmq.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author Thinkive
 *
 */
@SpringBootApplication
@EnableTransactionManagement
public class SpringRabbitmqProviderApplication {



	public static void main(String[] args) {
		SpringApplication.run(SpringRabbitmqProviderApplication.class, args);
	}



}
