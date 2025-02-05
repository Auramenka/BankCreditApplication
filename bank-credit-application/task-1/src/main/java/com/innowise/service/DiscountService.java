package com.innowise.service;

import com.innowise.model.Discount;

import java.time.LocalDate;
import java.util.Optional;

public interface DiscountService {

    Optional<Discount> findRelevantDiscount(LocalDate date);

}
