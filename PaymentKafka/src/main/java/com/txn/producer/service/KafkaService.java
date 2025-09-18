package com.txn.producer.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.txn.common.dto.TransactionDto;

//import com.txn.producer.dto.TransactionDto;

@Service
public class KafkaService {

	private final KafkaTemplate<String, TransactionDto> kafkaTemplate;

	private static final String topic = "bank-transactions-ml";

	public KafkaService(KafkaTemplate<String, TransactionDto> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(TransactionDto transactionDto) {
		kafkaTemplate.send(topic, transactionDto);

	}

}
