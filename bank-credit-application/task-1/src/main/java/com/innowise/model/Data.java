package com.innowise.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Data {

    private List<User> users;
    private List<Credit> credits;
    private List<Discount> discounts;
    private List<Event> events;
    private List<Transaction> transactions;

}
