package com.innowise.strategy;

import com.innowise.model.Credit;
import com.innowise.model.Settings;
import com.innowise.model.Discount;
import com.innowise.model.Transaction;

import java.time.LocalDate;

public class DateFilterUtils {

    public static DateFilterStrategy<Credit> creditFilter(Settings settings) {
        return new CreditFilterStrategy();
    }

    public static DateFilterStrategy<Discount> discountFilter(LocalDate date) {
        return new DiscountFilterStrategy(date);
    }

    public static DateFilterStrategy<Transaction> transactionFilter(Settings settings) {
        return new TransactionFilterStrategy();
    }
}
