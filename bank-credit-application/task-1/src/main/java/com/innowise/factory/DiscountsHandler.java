package com.innowise.factory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DiscountsHandler implements DataHandler {

    @Override
    public void handleData(JsonObject source, JsonObject target, String key) {
        JsonArray discounts = source.getAsJsonArray("discounts");

        if (discounts != null) {
            JsonArray dbDiscounts = target.getAsJsonArray("discounts");
            if (dbDiscounts == null) {
                dbDiscounts = new JsonArray();
                target.add("discounts", dbDiscounts);
            }
            dbDiscounts.addAll(discounts);
            source.remove("discounts");
        }
    }
}
