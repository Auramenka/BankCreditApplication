package com.innowise.model.enums;

import java.math.BigDecimal;

public enum CreditStatus {

    IN_PROGRESS, DONE;

    public static CreditStatus getStatus(BigDecimal debtAmount) {
        if (debtAmount != null && debtAmount.compareTo(BigDecimal.ZERO) == 0) {
            return DONE;
        } else {
            return IN_PROGRESS;
        }
    }

}
