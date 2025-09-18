package com.txn.consumer.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.txn.common.dto.TransactionDto;

@Service
public class FraudDetectionService {

	@Autowired
	EmailService emailService;
	
	@KafkaListener(topics = "bank-transactions", groupId = "Grp2")
	public void consumeFraud(TransactionDto transaction) {
		System.out.println("Fraud Consumer received: " + transaction.toString());
		// Add logic to send email/push notification
		String htmlBody = "<h3>Fraud Transaction Alert</h3>"
		        + "<p>Transaction ID: " + transaction.getAccountId() + "</p>"
		        + "<p>Amount: Rs. " + transaction.getAmount() + "</p>";

//          if(transaction.getAmount()>=10000) {

      		// Send email notification
      		if (transaction.getEmail() != null && !transaction.getEmail().isEmpty() && isFraud(transaction)) {
//      			emailService.sendSimpleMail(transaction.getEmail(), "Transaction Alert",
//      					"The amount " + transaction.getAmount() + " was "+transaction.getType()+" successful.");
      			
      			emailService.sendHtmlMail(transaction.getEmail(), " \"⚠️ Fraud Transaction Alert\"", htmlBody);
      		}
//          }

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
