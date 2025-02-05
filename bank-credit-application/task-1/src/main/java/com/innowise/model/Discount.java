package com.innowise.model;

import com.google.gson.annotations.SerializedName;
import com.innowise.model.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Discount {

    private Long id;

    @SerializedName("type")
    private DiscountType discountType;
    private LocalDate date;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private double discount;

}
