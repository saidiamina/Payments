package com.example.payments.service;

import com.example.payments.model.Transaction;
import com.example.payments.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        transaction.setStatus(Transaction.Status.NEW);
        return transactionRepository.save(transaction);
    }

    public Transaction getTransaction(Long id) {
        return transactionRepository.findById(id).orElseThrow();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction updateTransactionStatus(Long id, Transaction.Status status) {
        Transaction transaction = getTransaction(id);
        if (transaction.getStatus() == Transaction.Status.CAPTURED || transaction.getStatus() == Transaction.Status.CANCELLED) {
            throw new IllegalStateException("Cannot change status of captured or cancelled transaction");
        }
        if (status == Transaction.Status.CAPTURED && transaction.getStatus() != Transaction.Status.AUTHORISED) {
            throw new IllegalStateException("Transaction must be authorised before it can be captured");
        }
        transaction.setStatus(status);
        return transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) {
        return getTransaction(id);
    }
}