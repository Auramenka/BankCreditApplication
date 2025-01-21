package com.innowise.managers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.innowise.factory.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class JsonDataTransferManager {

    private final Path path;
    private final Gson gson = new Gson();
    private final Map<String, DataHandler> dataHandlers = new HashMap<>();

    public JsonDataTransferManager(Path path) {
        this.path = path;
        initializeDataHandlers();
    }

    public void transferData() throws IOException {
        String jsonString = Files.readString(path);
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

        Path dbFilePath = Paths.get("task-1/data/db.json");
        JsonObject dbObject = gson.fromJson(Files.readString(dbFilePath), JsonObject.class);

        if (dbObject == null) {
            dbObject = new JsonObject();
        }

        for (Map.Entry<String, DataHandler> entry : dataHandlers.entrySet()) {
            String key = entry.getKey();
            DataHandler handler = entry.getValue();
            handler.handleData(jsonObject, dbObject, key);
        }

        Files.writeString(dbFilePath, gson.toJson(dbObject));

        Files.writeString(path, gson.toJson(jsonObject));
    }

    private void initializeDataHandlers() {
        dataHandlers.put("users", new UsersHandler());
        dataHandlers.put("credits", new CreditsHandler());
        dataHandlers.put("discounts", new DiscountsHandler());
        dataHandlers.put("events", new EventsHandler());
        dataHandlers.put("transactions", new TransactionsHandler());
    }
}