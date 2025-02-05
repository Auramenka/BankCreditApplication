package com.innowise.converter;

import com.innowise.model.Event;
import com.innowise.model.Settings;
import com.innowise.model.Transaction;
import com.innowise.model.enums.CurrencyType;
import com.innowise.utils.CurrencyConverterUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CurrencyConverter {

    private final CurrencyConverterUtil converterUtil;

    public CurrencyConverter(Settings settings, List<Event> events) {
        this.converterUtil = CurrencyConverterUtil.getInstance(settings, events);
    }

    public void convertToBr(List<Transaction> transactions) {
        transactions.stream()
                .filter(transaction -> transaction.getCurrencyType() != CurrencyType.Br)
                .forEach(transaction -> {
                    converterUtil.applyEvents(transaction);
                    transaction.setCurrencyType(CurrencyType.Br);
                });
    }
}
