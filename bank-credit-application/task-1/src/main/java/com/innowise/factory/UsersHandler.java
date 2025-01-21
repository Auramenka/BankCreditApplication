package com.innowise.factory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class UsersHandler implements DataHandler {

    @Override
    public void handleData(JsonObject source, JsonObject target, String key) {
        JsonArray users = source.getAsJsonArray("users");

        if (users != null) {
            JsonArray dbUsers = target.getAsJsonArray("users");
            if (dbUsers == null) {
                dbUsers = new JsonArray();
                target.add("users", dbUsers);
            }
            dbUsers.addAll(users);
            source.remove("users");
        }
    }
}
