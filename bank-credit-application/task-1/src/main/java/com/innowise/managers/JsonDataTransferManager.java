package com.innowise.managers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.innowise.constants.DataConstants;
import com.innowise.factory.DataHandler;
import com.innowise.factory.CreditsHandler;
import com.innowise.factory.UsersHandler;
import com.innowise.factory.DiscountsHandler;
import com.innowise.factory.EventsHandler;
import com.innowise.factory.TransactionsHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class JsonDataTransferManager {

    private static final String DB_FILE_PATH = "task-1/data/db.json";
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

        Path dbFilePath = Paths.get(DB_FILE_PATH);
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
        dataHandlers.put(DataConstants.USERS, new UsersHandler());
        dataHandlers.put(DataConstants.CREDITS, new CreditsHandler());
        dataHandlers.put(DataConstants.DISCOUNTS, new DiscountsHandler());
        dataHandlers.put(DataConstants.EVENTS, new EventsHandler());
        dataHandlers.put(DataConstants.TRANSACTIONS, new TransactionsHandler());
    }
}