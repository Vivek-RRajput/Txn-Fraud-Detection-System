package com.txn.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentKafkaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentKafkaConsumerApplication.class, args);
	}

}
