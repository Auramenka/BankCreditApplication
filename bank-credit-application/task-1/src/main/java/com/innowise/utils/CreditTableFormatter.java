package com.innowise.utils;

import com.innowise.model.CreditResults;

import java.util.List;

public class CreditTableFormatter {

    private static final String HEADER_FORMAT = "%-10s %-10s %-25s %-20s %-15s %-15s %-15s %-20s";
    private static final String ROW_FORMAT = "%-10d %-10d %-25s %-20d %-15s %-15s %-15s %-20s";

    public void printTable(List<CreditResults> credits) {
        if (isCreditsEmpty(credits)) {
            return;
        }

        printHeader();
        printRows(credits);
    }

    private boolean isCreditsEmpty(List<CreditResults> credits) {
        if (credits == null || credits.isEmpty()) {
            System.out.println("No credits to display.");
            return true;
        }
        return false;
    }

    private void printHeader() {
        System.out.format(HEADER_FORMAT, "Credit ID", "User ID", "Full Name", "Transactions", "Debt Amount", "Period", "Status", "Repayment Date");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
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
