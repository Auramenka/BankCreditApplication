package com.innowise.strategy;

import com.innowise.model.*;

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
