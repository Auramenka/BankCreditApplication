package com.innowise.service.impl;

import com.innowise.readers.JsonReader;
import com.innowise.model.Settings;
import com.innowise.model.ShowFor;
import com.innowise.model.User;
import com.innowise.model.enums.ShowForType;
import com.innowise.service.UserService;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final JsonReader jsonReader = new JsonReader();

    @Override
    public List<User> findByIds(List<Long> ids) {
        return jsonReader.getUsers()
                .stream()
                .filter(user -> ids.contains(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByFullNames(List<String> fullNames) {
        return jsonReader.getUsers()
                .stream()
                .filter(user -> fullNames.contains(user.getName() + " " + user.getSecondName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> filterUsersBySettings(Settings settings) {
        ShowFor showFor = settings.getShowFor();
        ShowForType showForType = showFor.getShowForType();

        if (showForType == ShowForType.ID) {
            List<Long> userIds = showFor.getUsers()
                    .stream()
                    .map(Long::valueOf)
                    .toList();

            return findByIds(userIds);
        } else if (showForType == ShowForType.NAME) {
            return findByFullNames(showFor.getUsers());
        }

        return List.of();
    }
}