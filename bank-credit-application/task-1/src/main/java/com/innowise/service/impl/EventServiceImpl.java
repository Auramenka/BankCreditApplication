package com.innowise.service.impl;

import com.innowise.readers.JsonReader;
import com.innowise.model.Event;
import com.innowise.service.EventService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EventServiceImpl implements EventService {

    private final JsonReader jsonReader = new JsonReader();

    @Override
    public List<Event> findEventsByDateTo(LocalDate date) {
        return jsonReader.getEvents()
                .stream()
                .filter(event -> date.isAfter(event.getDate()))
                .collect(Collectors.toList());
    }
}
