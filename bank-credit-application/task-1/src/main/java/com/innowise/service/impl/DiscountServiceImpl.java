package com.innowise.service.impl;

import com.innowise.strategy.DateFilterUtils;
import com.innowise.readers.JsonReader;
import com.innowise.model.Discount;
import com.innowise.service.DiscountService;

import java.time.LocalDate;
import java.util.Optional;

public class DiscountServiceImpl implements DiscountService {

    private final JsonReader jsonReader = new JsonReader();

    @Override
    public Optional<Discount> findRelevantDiscount(LocalDate date) {
        return jsonReader.getDiscounts()
                .stream()
                .filter(DateFilterUtils.discountFilter(date).isValid(null))
                .findFirst();
    }
}
