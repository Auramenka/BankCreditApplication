package com.innowise.service;

import com.innowise.model.Credit;
import com.innowise.model.Settings;

import java.util.List;

public interface CreditService {

    List<Credit> getCreditsForUser(Long userId, Settings settings);
}
