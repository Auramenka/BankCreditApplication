package com.innowise.service.impl;

import com.innowise.strategy.DateFilterUtils;
import com.innowise.readers.JsonReader;
import com.innowise.model.Settings;
import com.innowise.model.Transaction;
import com.innowise.service.TransactionService;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionServiceImpl implements TransactionService {

    private final JsonReader jsonReader = new JsonReader();

    @Override
    public List<Transaction> getTransactionForCredit(Long creditId, Settings settings) {
        return jsonReader.getTransactions()
                .stream()
                .filter(transaction -> transaction.getCreditId().equals(creditId))
                .filter(DateFilterUtils.transactionFilter(settings).isValid(settings))
                .collect(Collectors.toList());
    }
}
