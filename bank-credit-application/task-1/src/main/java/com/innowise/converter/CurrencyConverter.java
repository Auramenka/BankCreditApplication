package com.innowise.converter;

import com.innowise.model.Event;
import com.innowise.model.Settings;
import com.innowise.model.Transaction;
import com.innowise.model.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyConverter {

    private Settings settings;
    private List<Event> events;

    public void convertToBr(List<Transaction> transactions) {

        for (Transaction transaction : transactions) {
            if (transaction.getCurrencyType() != CurrencyType.Br) {
                applyEvents(transaction);
                transaction.setCurrencyType(CurrencyType.Br);
            }
        }
    }

    private void applyEvents(Transaction transaction) {
        List<Event> relevantEvents = events.stream()
                .filter(event -> !event.getDate().isAfter(transaction.getDate()))
                .sorted(Comparator.comparing(Event::getDate))
                .toList();

        BigDecimal cost = getInitialCost(transaction.getCurrencyType());

        for (Event event : relevantEvents) {
            cost = event.getCost();
        }

        transaction.setMoney(transaction.getMoney().multiply(cost));
    }

    private BigDecimal getInitialCost(CurrencyType currencyType) {
        return switch (currencyType) {
            case EUR -> settings.getStartCostEUR();
            case USD -> settings.getStartCostUSD();
            default -> BigDecimal.ONE;
        };
    }
}
