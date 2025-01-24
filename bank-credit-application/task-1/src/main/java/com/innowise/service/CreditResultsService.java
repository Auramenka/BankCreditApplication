package com.innowise.service;

import com.innowise.exceptions.DiscountNotFoundException;
import com.innowise.managers.CreditManager;
import com.innowise.model.CreditResults;
import com.innowise.model.Settings;
import com.innowise.model.User;
import com.innowise.model.Event;
import com.innowise.model.Credit;
import com.innowise.model.Discount;
import com.innowise.model.Transaction;
import com.innowise.service.impl.UserServiceImpl;
import com.innowise.service.impl.CreditServiceImpl;
import com.innowise.service.impl.TransactionServiceImpl;
import com.innowise.service.impl.DiscountServiceImpl;
import com.innowise.service.impl.EventServiceImpl;
import com.innowise.utils.CreditCalculator;
import com.innowise.converter.CurrencyConverter;
import com.innowise.model.enums.CreditStatus;
import com.innowise.model.enums.SortByType;


import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

public class CreditResultsService {

    private static final String RELEVANT_DISCOUNT_NOT_FOUND = "Relevant discount not found";

    private final UserService userService = new UserServiceImpl();
    private final CreditService creditService = new CreditServiceImpl();
    private final TransactionService transactionService = new TransactionServiceImpl();
    private final DiscountService discountService = new DiscountServiceImpl();
    private final EventService eventService = new EventServiceImpl();
    private final CreditCalculator creditCalculator = new CreditCalculator();

    private final EnumMap<SortByType, Comparator<CreditResults>> comparatorMap = new EnumMap<>(SortByType.class);

    public CreditResultsService() {
        comparatorMap.put(SortByType.NAME, Comparator.comparing(CreditResults::getFullName));
        comparatorMap.put(SortByType.DEBT, Comparator.comparing(CreditResults::getDebtAmount));
    }

    public List<CreditResults> getCreditResults(Settings settings) {
        List<User> users = userService.filterUsersBySettings(settings);
        List<Event> events = eventService.findEventsByDateTo(settings.getDateTo());

        return users.stream()
                .flatMap(user -> getUserCreditResults(user, settings, events).stream())
                .collect(Collectors.toList());
    }

    public List<CreditResults> sortCreditResults(List<CreditResults> creditResults, Settings settings) {
        return creditResults.stream()
                .sorted(getComparator(settings.getSortByType()))
                .collect(Collectors.toList());
    }

    private List<CreditResults> getUserCreditResults(User user, Settings settings, List<Event> events) {
        List<Credit> credits = creditService.getCreditsForUser(user.getId(), settings);
        return credits.stream()
                .map(credit -> createCreditResult(user, credit, settings, events))
                .collect(Collectors.toList());
    }

    private CreditResults createCreditResult(User user, Credit credit, Settings settings, List<Event> events) {
        Discount relevantDiscount = discountService.findRelevantDiscount(credit.getDate())
                .orElseThrow(() -> new DiscountNotFoundException(RELEVANT_DISCOUNT_NOT_FOUND));

        creditCalculator.applyDiscount(credit, relevantDiscount);

        List<Transaction> transactions = transactionService.getTransactionForCredit(credit.getId(), settings);

        new CurrencyConverter(settings, events).convertToBr(transactions);

        CreditManager creditManager = new CreditManager();
        creditManager.processTransactions(credit, transactions);

        return CreditResults.builder()
                .creditId(credit.getId())
                .userId(user.getId())
                .fullName(String.format("%s %s", user.getName(), user.getSecondName()))
                .countTransactions(transactions.size())
                .debtAmount(credit.getMoney())
                .periodType(credit.getPeriodType())
                .creditStatus(CreditStatus.getStatus(credit.getMoney()))
                .repaymentDate(credit.getRepayment())
                .build();
    }

    private Comparator<CreditResults> getComparator(SortByType sortByType) {
        return comparatorMap.get(sortByType);
    }
}