package com.innowise.utils;

import com.innowise.model.CreditResults;

import java.util.List;

public class CreditTableFormatter {

    private static final String HEADER_FORMAT = "%-10s %-10s %-25s %-20s %-15s %-15s %-15s %-20s";
    private static final String ROW_FORMAT = "%-10d %-10d %-25s %-20d %-15s %-15s %-15s %-20s";
    private static final String SEPARATOR = "-----------------------------------------------------------------------------------------------------------------------------------";

    private static final String CREDIT_ID_HEADER = "Credit ID";
    private static final String USER_ID_HEADER = "User ID";
    private static final String FULL_NAME_HEADER = "Full Name";
    private static final String TRANSACTIONS_HEADER = "Transactions";
    private static final String DEBT_AMOUNT_HEADER = "Debt Amount";
    private static final String PERIOD_HEADER = "Period";
    private static final String STATUS_HEADER = "Status";
    private static final String REPAYMENT_DATE_HEADER = "Repayment Date";

    private static final String NO_CREDITS_MESSAGE = "No credits to display.";

    public void printTable(List<CreditResults> credits) {
        if (isCreditsEmpty(credits)) {
            return;
        }

        printHeader();
        printRows(credits);
    }

    private boolean isCreditsEmpty(List<CreditResults> credits) {
        if (credits == null || credits.isEmpty()) {
            System.out.println(NO_CREDITS_MESSAGE);
            return true;
        }
        return false;
    }

    private void printHeader() {
        System.out.format(HEADER_FORMAT, CREDIT_ID_HEADER, USER_ID_HEADER, FULL_NAME_HEADER, TRANSACTIONS_HEADER, DEBT_AMOUNT_HEADER, PERIOD_HEADER, STATUS_HEADER, REPAYMENT_DATE_HEADER);
        System.out.println();
        System.out.println(SEPARATOR);
    }

    private void printRows(List<CreditResults> credits) {
        for (CreditResults credit : credits) {
            System.out.format(ROW_FORMAT,
                    credit.getCreditId(),
                    credit.getUserId(),
                    credit.getFullName(),
                    credit.getCountTransactions(),
                    credit.getDebtAmount(),
                    credit.getPeriodType(),
                    credit.getCreditStatus(),
                    credit.getRepaymentDate());
            System.out.println();
        }
    }

}
