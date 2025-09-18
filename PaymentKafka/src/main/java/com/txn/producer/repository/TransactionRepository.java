package com.txn.producer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.txn.producer.entity.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
    // Optional custom queries
}