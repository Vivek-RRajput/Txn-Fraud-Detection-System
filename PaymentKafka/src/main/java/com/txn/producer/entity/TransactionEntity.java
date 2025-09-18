package com.txn.producer.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "account_id")
    private String accountId;

    private String type;
    private double amount;
    private Instant timestamp;
    private String location;
    private String email;

    @Column(name = "fraud_flag")
    private String fraudFlag;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFraudFlag() {
		return fraudFlag;
	}

	public void setFraudFlag(String fraudFlag) {
		this.fraudFlag = fraudFlag;
	}

    
    
}