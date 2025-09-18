package com.txn.producer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.txn.producer.dto.TransactionDto;
import com.txn.common.dto.TransactionDto;
import com.txn.producer.service.TransactionDataGeneratorService;
import com.txn.producer.service.TransactionService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class TransactionController {
//dummy 100 txn
	
	@Autowired
	TransactionService transactionService;
	TransactionDataGeneratorService dataGeneratorService;
	
	@PostMapping("/txn")
	public ResponseEntity<?> createTransaction(@RequestBody TransactionDto transactionDto) {
		// Push to kafka topic
		
//		List<TransactionDto> transactions = dataGeneratorService.generateTransactions(100);
//		
//		for(TransactionDto txn:transactions) {
//			System.out.println(txn.getAccountId()+" updated");
//			transactionService.updateLocation(txn);
//		}
		
		transactionService.updateLocation(transactionDto);
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Txn Booked: " + transactionDto.getTransactionId());
	}

}
