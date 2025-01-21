package com.innowise.service;

import com.innowise.managers.CreditManager;
import com.innowise.utils.CreditCalculator;
import com.innowise.converter.CurrencyConverter;
import com.innowise.model.*;
import com.innowise.model.enums.CreditStatus;
import com.innowise.model.enums.SortByType;
import com.innowise.service.impl.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CreditResultsService {

    private final UserService userService = new UserServiceImpl();
    private final CreditService creditService = new CreditServiceImpl();
    private final TransactionService transactionService = new TransactionServiceImpl();
    private final DiscountService discountService = new DiscountServiceImpl();
    private final EventService eventService = new EventServiceImpl();
    private final CreditCalculator creditCalculator = new CreditCalculator();

    public List<CreditResults> getCreditResults(Settings settings) {
        List<User> users = userService.filterUsersBySettings(settings);
        List<Event> events = eventService.findEventsByDateTo(settings.getDateTo());

        return users.stream()
                .flatMap(user -> getUserCreditResults(user, settings, events).stream())
                .collect(Collectors.toList());
    }

    private List<CreditResults> getUserCreditResults(User user, Settings settings, List<Event> events) {
        List<Credit> credits = creditService.getCreditsForUser(user.getId(), settings);
        return credits.stream()
                .map(credit -> createCreditResult(user, credit, settings, events))
                .collect(Collectors.toList());
    }

    private CreditResults createCreditResult(User user, Credit credit, Settings settings, List<Event> events) {
        Discount relevantDiscount = discountService.findRelevantDiscount(credit.getDate());
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

    public List<CreditResults> sortCreditResults(List<CreditResults> creditResults, Settings settings) {
        return creditResults.stream()
                .sorted(getComparator(settings.getSortByType()))
                .collect(Collectors.toList());
    }

    private Comparator<CreditResults> getComparator(SortByType sortByType) {
        return switch (sortByType) {
            case NAME -> Comparator.comparing(CreditResults::getFullName);
            case DEBT -> Comparator.comparing(CreditResults::getDebtAmount);
            default -> throw new IllegalArgumentException("Unknown sort type: " + sortByType);
        };
    }

}
