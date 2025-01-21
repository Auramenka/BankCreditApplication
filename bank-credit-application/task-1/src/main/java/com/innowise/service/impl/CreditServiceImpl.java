package com.innowise.service.impl;

import com.innowise.strategy.DateFilterUtils;
import com.innowise.readers.JsonReader;
import com.innowise.model.Credit;
import com.innowise.model.Settings;
import com.innowise.service.CreditService;

import java.util.List;
import java.util.stream.Collectors;

public class CreditServiceImpl implements CreditService {

    private final JsonReader jsonReader = new JsonReader();

    @Override
    public List<Credit> getCreditsForUser(Long userId, Settings settings) {
        return jsonReader.getCredits()
                .stream()
                .filter(credit -> credit.getUserId().equals(userId))
                .filter(DateFilterUtils.creditFilter(settings).isValid(settings))
                .collect(Collectors.toList());
    }
}