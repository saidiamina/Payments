package com.example.payments.controller;

import com.example.payments.model.Transaction;
import com.example.payments.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Endpoint to create a new transaction
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    // Endpoint to get all transactions
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    // Endpoint to update the status of a transaction
    @PutMapping("/{id}/status")
    public ResponseEntity<Transaction> updateTransactionStatus(@PathVariable Long id, @RequestBody Transaction.Status status) {
        Transaction updatedTransaction = transactionService.updateTransactionStatus(id, status);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }

    // Endpoint to get a transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
