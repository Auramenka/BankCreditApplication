package com.innowise.service;

import com.innowise.model.Settings;
import com.innowise.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactionForCredit(Long creditId, Settings settings);
}
