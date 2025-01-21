package com.innowise.factory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class EventsHandler implements DataHandler {

    @Override
    public void handleData(JsonObject source, JsonObject target, String key) {
        JsonArray events = source.getAsJsonArray("events");

        if (events != null) {
            JsonArray dbEvents = target.getAsJsonArray("events");
            if (dbEvents == null) {
                dbEvents = new JsonArray();
                target.add("events", dbEvents);
            }
            dbEvents.addAll(events);
            source.remove("events");
        }
    }
}
