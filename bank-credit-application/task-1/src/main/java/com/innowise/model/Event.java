package com.innowise.model;

import com.google.gson.annotations.SerializedName;
import com.innowise.model.enums.CurrencyType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Event {

    private Long id;

    @SerializedName("currency")
    private CurrencyType currencyType;
    private BigDecimal cost;
    private LocalDate date;

}