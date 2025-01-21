package com.innowise.service;

import com.innowise.model.Discount;

import java.time.LocalDate;

public interface DiscountService {

    Discount findRelevantDiscount(LocalDate date);

}
