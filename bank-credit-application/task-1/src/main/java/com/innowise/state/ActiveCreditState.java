package com.innowise.state;

import com.innowise.managers.CreditManager;
import com.innowise.model.Credit;
import com.innowise.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ActiveCreditState implements CreditState {

    private final CreditManager creditManager = new CreditManager();

    @Override
    public void processTransaction(Credit credit, Transaction transaction) {
        LocalDate lastTransactionDate = credit.getDate();
        LocalDate transactionDate = transaction.getDate();
        BigDecimal transactionAmount = transaction.getMoney();

        BigDecimal interest = creditManager.calculateInterest(credit, lastTransactionDate, transactionDate);
        creditManager.updateBalance(credit, interest, transactionAmount);

        if (creditManager.isDebtSettled(credit, lastTransactionDate)) {
            credit.setState(new ClosedCreditState());
        }
    }
}
