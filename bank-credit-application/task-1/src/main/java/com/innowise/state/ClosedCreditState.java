package com.innowise.state;

import com.innowise.model.Credit;
import com.innowise.model.Transaction;

public class ClosedCreditState implements CreditState {

    @Override
    public void processTransaction(Credit credit, Transaction transaction) {
    }
}
