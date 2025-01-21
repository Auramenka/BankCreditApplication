package com.innowise.strategy;

import com.innowise.model.Credit;
import com.innowise.model.Settings;

import java.util.function.Predicate;

public class CreditFilterStrategy implements DateFilterStrategy<Credit> {

    @Override
    public Predicate<Credit> isValid(Settings settings) {
        return credit -> !credit.getDate().isBefore(settings.getDateFrom()) &&
                !credit.getDate().isAfter(settings.getDateTo());
    }
}
