package com.innowise.service;

import com.innowise.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    List<Event> findEventsByDateTo(LocalDate date);

}
