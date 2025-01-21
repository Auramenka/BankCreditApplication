package com.innowise.model;

import com.innowise.model.enums.CreditStatus;
import com.innowise.model.enums.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreditResults {

    private Long creditId;
    private Long userId;
    private String fullName;
    private Integer countTransactions;
    private BigDecimal debtAmount;
    private PeriodType periodType;
    private CreditStatus creditStatus;
    private LocalDate repaymentDate;

}
