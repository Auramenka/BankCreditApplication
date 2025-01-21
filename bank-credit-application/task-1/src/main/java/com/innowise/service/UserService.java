package com.innowise.service;

import com.innowise.model.Settings;
import com.innowise.model.User;

import java.util.List;

public interface UserService {

    List<User> findByIds(List<Long> ids);
    List<User> findByFullNames(List<String> fullNames);
    List<User> filterUsersBySettings(Settings settings);
}
