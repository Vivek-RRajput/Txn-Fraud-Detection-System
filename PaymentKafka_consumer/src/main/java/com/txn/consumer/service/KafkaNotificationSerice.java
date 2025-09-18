package com.txn.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//import com.txn.consumer.dto.TransactionDto;
import com.txn.common.dto.TransactionDto;

@Service
public class KafkaNotificationSerice {

	@Autowired
	EmailService emailService;

	@KafkaListener(topics = "bank-transactions", groupId = "Grp1")
	public void consume(TransactionDto transaction) {
		System.out.println("Notification Consumer received: " + transaction.toString());
		// Add logic to send email/push notification
		String htmlBody = "<h3>Transaction Alert</h3>"
		        + "<p>Transaction ID: " + transaction.getAccountId() + "</p>"
		        + "<p>Amount: Rs. " + transaction.getAmount() + "</p>";

		

		// Send email notification
		if (transaction.getEmail() != null && !transaction.getEmail().isEmpty()) {
//			emailService.sendSimpleMail(transaction.getEmail(), "Transaction Alert",
//					"The amount " + transaction.getAmount() + " was "+transaction.getType()+" successful.");
			
			emailService.sendHtmlMail(transaction.getEmail(), "Transaction Alert", htmlBody);
		}
	}
}
