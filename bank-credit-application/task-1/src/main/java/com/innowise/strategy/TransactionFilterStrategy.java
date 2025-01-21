package com.innowise.strategy;

import com.innowise.model.Settings;
import com.innowise.model.Transaction;

import java.util.function.Predicate;

public class TransactionFilterStrategy implements DateFilterStrategy<Transaction> {

    @Override
    public Predicate<Transaction> isValid(Settings settings) {
        return transaction -> !transaction.getDate().isBefore(settings.getDateFrom()) &&
                !transaction.getDate().isAfter(settings.getDateTo());
    }
}
