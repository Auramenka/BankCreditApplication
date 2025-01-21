package com.innowise.state;

import com.innowise.model.Credit;
import com.innowise.model.Transaction;

public interface CreditState {

    void processTransaction(Credit credit, Transaction transaction);

}
