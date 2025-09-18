package com.txn.consumer.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.txn.common.dto.TransactionDto;

@Service
public class BalanceUpdateService {

    // In-memory store (simulate DB)
    private ConcurrentMap<String, Double> accountBalances = new ConcurrentHashMap<>();

    @KafkaListener(topics = "bank-transactions", groupId = "balance-group")
    public void updateBalance(TransactionDto transaction) {
        accountBalances.putIfAbsent(transaction.getAccountId(), 0.0);

        if ("CREDIT".equalsIgnoreCase(transaction.getType())) {
            accountBalances.computeIfPresent(transaction.getAccountId(),
                (id, balance) -> balance + transaction.getAmount());
        } else if ("DEBIT".equalsIgnoreCase(transaction.getType())) {
            accountBalances.computeIfPresent(transaction.getAccountId(),
                (id, balance) -> balance - transaction.getAmount());
        }

        System.out.println("✅ Updated Balance for Account " + transaction.getAccountId() +
                " = " + accountBalances.get(transaction.getAccountId()));
    }

    // Helper to fetch balance (could be exposed via REST API)
    public Double getBalance(String accountId) {
        return accountBalances.getOrDefault(accountId, 0.0);
    }
}
