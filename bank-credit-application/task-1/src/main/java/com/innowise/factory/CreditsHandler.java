package com.innowise.factory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CreditsHandler implements DataHandler {

    @Override
    public void handleData(JsonObject source, JsonObject target, String key) {
        JsonArray credits = source.getAsJsonArray("credits");

        if (credits != null) {
            JsonArray dbCredits = target.getAsJsonArray("credits");
            if (dbCredits == null) {
                dbCredits = new JsonArray();
                target.add("credits", dbCredits);
            }
            dbCredits.addAll(credits);
            source.remove("credits");
        }
    }
}
