package com.innowise.utils;

import com.innowise.model.Credit;
import com.innowise.model.Discount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class CreditCalculator {

    public void applyDiscount(Credit credit, Discount discount) {
        credit.setRate(Optional.ofNullable(discount)
                .map(Discount::getDiscount)
                .map(d -> Math.max(credit.getRate() - d, 0))
                .orElse(credit.getRate()));
    }

    public BigDecimal calculateInterest(Credit credit, LocalDate dateFrom, LocalDate dateTo) {
        BigDecimal currentDebt = credit.getMoney();

        long periodsPassed = switch (credit.getPeriodType()) {
            case DAY -> ChronoUnit.DAYS.between(dateFrom, dateTo);
            case WEEK -> ChronoUnit.WEEKS.between(dateFrom, dateTo);
            case MONTH -> ChronoUnit.MONTHS.between(dateFrom, dateTo);
            case YEAR -> ChronoUnit.YEARS.between(dateFrom, dateTo);
        };

        BigDecimal interestRate = BigDecimal.valueOf(credit.getRate()).divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP);

        return currentDebt.multiply(interestRate).multiply(BigDecimal.valueOf(periodsPassed));
    }

}
