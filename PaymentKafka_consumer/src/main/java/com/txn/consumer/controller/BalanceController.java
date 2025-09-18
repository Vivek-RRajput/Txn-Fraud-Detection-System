package com.txn.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.txn.consumer.service.BalanceUpdateService;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private BalanceUpdateService balanceUpdateService;

    @GetMapping("/{accountId}")
    public Double getBalance(@PathVariable String accountId) {
        return balanceUpdateService.getBalance(accountId);
    }
}
