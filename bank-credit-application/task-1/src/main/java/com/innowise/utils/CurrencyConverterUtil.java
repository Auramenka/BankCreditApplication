package com.innowise.utils;

import com.innowise.model.Event;
import com.innowise.model.Settings;
import com.innowise.model.Transaction;
import com.innowise.model.enums.CurrencyType;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

public class CurrencyConverterUtil {

    private static CurrencyConverterUtil instance;
    private final EnumMap<CurrencyType, BigDecimal> initialCosts;
    private final List<Event> events;

    private CurrencyConverterUtil(Settings settings, List<Event> events) {
        this.initialCosts = new EnumMap<>(CurrencyType.class);
        this.events = events;
        initializeInitialCosts(settings);
    }

    public static CurrencyConverterUtil getInstance(Settings settings, List<Event> events) {
        if (instance == null) {
            instance = new CurrencyConverterUtil(settings, events);
        }
        return instance;
    }

    public void applyEvents(Transaction transaction) {
        BigDecimal cost = events.stream()
                .filter(event -> !event.getDate().isAfter(transaction.getDate()))
                .sorted(Comparator.comparing(Event::getDate))
                .map(Event::getCost)
                .reduce(getInitialCost(transaction.getCurrencyType()), (acc, current) -> current);

        transaction.setMoney(transaction.getMoney().multiply(cost));
    }

    private void initializeInitialCosts(Settings settings) {
        initialCosts.put(CurrencyType.EUR, settings.getStartCostEUR());
        initialCosts.put(CurrencyType.USD, settings.getStartCostUSD());
    }

    private BigDecimal getInitialCost(CurrencyType currencyType) {
        return initialCosts.getOrDefault(currencyType, BigDecimal.ONE);
    }
}