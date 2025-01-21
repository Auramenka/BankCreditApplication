package com.innowise.readers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.innowise.model.*;
import com.innowise.utils.LocalDateDeserializer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class JsonReader {

    private static final String FILE_PATH = "task-1/data/db.json";
    private final Data data;

    public JsonReader() {
        this.data = readJson();
    }

    public Data readJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            return gson.fromJson(br, Data.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getUsers() {
        return data.getUsers();
    }

    public List<Credit> getCredits() {
        return data.getCredits();
    }

    public List<Transaction> getTransactions() {
        return data.getTransactions();
    }

    public List<Discount> getDiscounts() {
        return data.getDiscounts();
    }

    public List<Event> getEvents() {
        return data.getEvents();
    }
}
