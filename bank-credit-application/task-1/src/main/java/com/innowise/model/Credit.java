package com.innowise.model;

import com.google.gson.annotations.SerializedName;
import com.innowise.utils.CreditCalculator;
import com.innowise.model.enums.PeriodType;
import com.innowise.state.ActiveCreditState;
import com.innowise.state.CreditState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class Credit {

    private Long id;
    private Long userId;
    private LocalDate date;

    @SerializedName("period")
    private PeriodType periodType;
    private BigDecimal money;
    private double rate;
    private LocalDate repayment;

    private CreditState state;

    public Credit() {
        this.state = new ActiveCreditState();
    }
}