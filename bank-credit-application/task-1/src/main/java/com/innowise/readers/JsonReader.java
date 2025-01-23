package com.innowise.readers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.innowise.exceptions.JsonReadException;
import com.innowise.model.Data;
import com.innowise.model.User;
import com.innowise.model.Credit;
import com.innowise.model.Transaction;
import com.innowise.model.Discount;
import com.innowise.model.Event;
import com.innowise.utils.LocalDateDeserializer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class JsonReader {

    private static final String FILE_PATH = "task-1/data/db.json";
    private static final String JSON_READ_ERROR = "Ошибка при чтении JSON файла";
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
            throw new JsonReadException(JSON_READ_ERROR);
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
