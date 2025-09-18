package com.txn.producer.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.txn.common.dto.TransactionDto;
import com.txn.producer.entity.TransactionEntity;
import com.txn.producer.repository.TransactionRepository;

//import com.txn.producer.dto.TransactionDto;

@Service
public class TransactionService {

	@Autowired
	KafkaService kafkaService;
	@Autowired
	TransactionRepository transactionRepository;

	public void updateLocation(TransactionDto transactionDto) {
		boolean flag = isFraud(transactionDto);
		saveTransaction(transactionDto, flag);
		kafkaService.sendMessage(transactionDto);
	}

	 
	    

	    public void saveTransaction(TransactionDto txn, boolean fraud) {
	        TransactionEntity entity = new TransactionEntity();
	        entity.setTransactionId(txn.getTransactionId());
	        entity.setAccountId(txn.getAccountId());
	        entity.setType(txn.getType());
	        entity.setAmount(txn.getAmount());
	        entity.setTimestamp(txn.getTimestamp());
	        entity.setLocation(txn.getLocation());
	        entity.setEmail(txn.getEmail());
	        entity.setFraudFlag(fraud ? "Fraud" : "Normal");

	        transactionRepository.save(entity);
	    }
	    
	    private final RestTemplate restTemplate=new RestTemplate();
		private final String fraudApiUrl="http://localhost:5000/predict_fraud";
		
		public boolean isFraud(TransactionDto txn) {
			System.out.println("Inside isfraud");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<TransactionDto> request=new HttpEntity<TransactionDto>(txn,headers);
			
			ResponseEntity<Map> responseMap= restTemplate.postForEntity(fraudApiUrl, request, Map.class);
			
			if(responseMap.getStatusCode()==HttpStatus.OK) {
				String flag= (String) responseMap.getBody().get("fraud_flag");
				System.out.println("isfraud: "+"Fraud".equalsIgnoreCase(flag));
				return "Fraud".equalsIgnoreCase(flag);
			}
			
			return false;
		}
}
