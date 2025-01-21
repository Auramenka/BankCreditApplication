package com.innowise.factory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class TransactionsHandler implements DataHandler {

    @Override
    public void handleData(JsonObject source, JsonObject target, String key) {
        JsonArray transactions = source.getAsJsonArray("transactions");

        if (transactions != null) {
            JsonArray dbTransactions = target.getAsJsonArray("transactions");
            if (dbTransactions == null) {
                dbTransactions = new JsonArray();
                target.add("transactions", dbTransactions);
            }
            dbTransactions.addAll(transactions);
            source.remove("transactions");
        }
    }
}
