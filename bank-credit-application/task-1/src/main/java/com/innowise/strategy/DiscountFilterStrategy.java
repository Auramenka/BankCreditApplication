package com.innowise.strategy;

import com.innowise.model.Discount;
import com.innowise.model.Settings;
import com.innowise.model.enums.DiscountType;

import java.time.LocalDate;
import java.util.function.Predicate;

public class DiscountFilterStrategy implements DateFilterStrategy<Discount> {

    private final LocalDate date;

    public DiscountFilterStrategy(LocalDate date) {
        this.date = date;
    }

    @Override
    public Predicate<Discount> isValid(Settings settings) {
        return discount -> (discount.getDiscountType() == DiscountType.ONE && date.equals(discount.getDate())) ||
                (discount.getDiscountType() == DiscountType.MANY &&
                        !date.isBefore(discount.getDateFrom()) &&
                        !date.isAfter(discount.getDateTo()));
    }
}
