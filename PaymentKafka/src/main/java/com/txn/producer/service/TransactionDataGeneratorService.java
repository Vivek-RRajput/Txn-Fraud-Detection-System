package com.txn.producer.service;

import java.time.Instant;
import java.util.*;

import com.txn.common.dto.TransactionDto;

public class TransactionDataGeneratorService {

    private static final List<String> LOCATIONS = Arrays.asList(
            "Delhi", "Mumbai", "London", "Paris", "New York", "Pune, India"
    );
    private static final List<String> EMAILS = Arrays.asList(
            "vivekrajput8244@gmail.com",
            "vrrajput1720@gmail.com",
            "arohirajput3850@gmail.com"
    );

    private static final Random random = new Random();

    // ✅ Generate a single transaction
    public static TransactionDto generateTransaction() {
        String transactionId = UUID.randomUUID().toString();
        String accountId = "ACC" + (1000 + random.nextInt(9000));
        String type = random.nextBoolean() ? "CREDIT" : "DEBIT";
        double amount = 100 + (50000 - 100) * random.nextDouble();
        Instant timestamp = Instant.now().minusSeconds(random.nextInt(60 * 60 * 24 * 30)); // last 30 days
        String location = LOCATIONS.get(random.nextInt(LOCATIONS.size()));
        String email = EMAILS.get(random.nextInt(EMAILS.size()));

        TransactionDto txn = new TransactionDto();
        txn.setTransactionId(transactionId);
        txn.setAccountId(accountId);
        txn.setType(type);
        txn.setAmount(amount);
        txn.setTimestamp(timestamp);
        txn.setLocation(location);
        txn.setEmail(email);

        return txn;
    }

    // ✅ Generate multiple transactions
    public static List<TransactionDto> generateTransactions(int count) {
        List<TransactionDto> transactions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            transactions.add(generateTransaction());
        }
        return transactions;
    }
}
