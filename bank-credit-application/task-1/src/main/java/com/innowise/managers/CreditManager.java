package com.innowise.managers;

import com.innowise.model.Credit;
import com.innowise.model.Transaction;
import com.innowise.utils.CreditCalculator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreditManager {

    private final CreditCalculator creditCalculator;

    public CreditManager() {
        this.creditCalculator = new CreditCalculator();
    }

    public void processTransactions(Credit credit, List<Transaction> transactions) {
        transactions.forEach(transaction -> credit.getState().processTransaction(credit, transaction));
    }

    public BigDecimal calculateInterest(Credit credit, LocalDate lastTransactionDate, LocalDate transactionDate) {
        return creditCalculator.calculateInterest(credit, lastTransactionDate, transactionDate);
    }

    public void updateBalance(Credit credit, BigDecimal interest, BigDecimal transactionAmount) {
        credit.setMoney(credit.getMoney().add(interest).subtract(transactionAmount));
    }

    public boolean isDebtSettled(Credit credit, LocalDate lastTransactionDate) {
        if (credit.getMoney().signum() < 0) {
            settleDebt(credit, lastTransactionDate);
            return true;
        }
        return false;
    }

    private void settleDebt(Credit credit, LocalDate lastTransactionDate) {
        credit.setMoney(BigDecimal.ZERO);
        credit.setRepayment(lastTransactionDate);
    }
}